/**
 *
 * Copyright (c) 2013, Linagora
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA 
 *
 */
package org.ow2.petals.cloud.manager.core.actions;

import net.schmizz.sshj.SSHClient;
import org.ow2.petals.cloud.manager.api.CloudManagerException;
import org.ow2.petals.cloud.manager.api.actions.Context;
import org.ow2.petals.cloud.manager.api.deployment.Node;
import org.ow2.petals.cloud.manager.api.listeners.DeploymentListener;
import org.ow2.petals.cloud.manager.api.listeners.SSHListener;
import org.ow2.petals.cloud.manager.core.utils.SSHUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Abstract class to run scripts on remote node.
 * The implementation must provide the script content which will be copied to the remote node and the command to run.
 *
 * @author Christophe Hamerling - chamerling@linagora.com
 */
public abstract class RunRemoteScriptAction extends MonitoredAction {

    private static Logger logger = LoggerFactory.getLogger(RunRemoteScriptAction.class);

    public RunRemoteScriptAction() {
        super();
    }

    /**
     * Get script to run on remote node.
     *
     * @return
     * @throws CloudManagerException
     */
    protected abstract String getScriptContent(Node node, Context context) throws CloudManagerException;

    /**
     * Get the command to run on the node. This command will be used to run the script generated by #getScript
     *
     * @param node
     * @param context
     * @return
     * @throws CloudManagerException
     */
    protected abstract String getCommandToRun(Node node, Context context) throws CloudManagerException;

    /**
     * Get the remote script path. This will be used to copy the generated script to the remote node and execute it.
     *
     * @param node
     * @param context
     * @return
     * @throws CloudManagerException
     */
    protected abstract String getRemoteScriptPath(Node node, Context context) throws CloudManagerException;

    public void doExecute(final Context context) throws CloudManagerException {
        final Node node = getNode(context);
        checkNotNull(node.getAccess());

        SSHClient client = SSHUtils.getClient(node);

        String script = getScriptContent(node, context);
        if (logger.isDebugEnabled()) {
            logger.debug("Script to run {}", script);
        }

        // copy the script
        String destination = getRemoteScriptPath(node, context);
        try {
            SSHUtils.createFile(client, script, 0600, destination);
        } catch (IOException e) {
            throw new CloudManagerException("Can not copy script to remote node", e);
        }

        String command = getCommandToRun(node, context);
        if (logger.isDebugEnabled()) {
            logger.debug("Command to run {}", command);
        }

        DeploymentListener listener = context.getListener();
        if (listener == null) {
            listener = new DeploymentListener() {
                public void on(String id, Node node, String action, String step, String pattern, Object... args) {
                    logger.debug("Deployment " + id + ", node " + node.getId() + ", action: " + action + " , step " + step + ":" + pattern);
                }
            };
        }

        final DeploymentListener finalListener = listener;
        SSHUtils.executeScript(client, command, new SSHListener() {
            public void onMessage(String line) {
                finalListener.on(context.getId(), node, "ssh", "exec", line);
            }

            public void onError(String error) {
                finalListener.on(context.getId(), node, "ssh", "error", error);
            }
        });
    }

    @Override
    protected String getName() {
        return RunRemoteScriptAction.class.getName();
    }
}
