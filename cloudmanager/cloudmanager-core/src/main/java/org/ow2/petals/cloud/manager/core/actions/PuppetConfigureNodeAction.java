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

import com.google.common.collect.Lists;
import net.schmizz.sshj.SSHClient;
import org.ow2.petals.cloud.manager.api.CloudManagerException;
import org.ow2.petals.cloud.manager.api.actions.Context;
import org.ow2.petals.cloud.manager.api.deployment.Node;
import org.ow2.petals.cloud.manager.api.deployment.Property;
import org.ow2.petals.cloud.manager.api.deployment.utils.PropertyHelper;
import org.ow2.petals.cloud.manager.api.listeners.SSHListener;
import org.ow2.petals.cloud.manager.core.puppet.Constants;
import org.ow2.petals.cloud.manager.core.puppet.DownloadFilesScriptBuilder;
import org.ow2.petals.cloud.manager.core.puppet.InstallPackagesScriptBuilder;
import org.ow2.petals.cloud.manager.core.utils.SSHUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

/**
 * Configure the node using Puppet.
 * Assume that puppet is available on the remote node and that this node is reachable with node credentials.
 * Generate all the configuration files and scripts then copy files and scripts to the remote node and run scripts.
 *
 * @author Christophe Hamerling - chamerling@linagora.com
 */
public class PuppetConfigureNodeAction extends MonitoredAction {

    private static final String CONFIGURE_ACTION = "configure";

    private static Logger logger = LoggerFactory.getLogger(PuppetConfigureNodeAction.class);

    public PuppetConfigureNodeAction() {
        super();
    }

    @Override
    protected String getName() {
        return PuppetConfigureNodeAction.class.getName();
    }

    @Override
    protected void doExecute(Context context) throws CloudManagerException {
        Node node = getNode(context);

        // create list of files and scripts as properties
        // Property name is the target filename and value is the content

        notity(context, node, CONFIGURE_ACTION, "files.generate", null);
        List<Property> files = getFiles(context);
        if (files != null && logger.isDebugEnabled()) {
            for(Property file : files) {
                logger.debug("Generated file {}", file);
            }
        }

        notity(context, node, CONFIGURE_ACTION, "scripts.generate", null);
        List<Property> scripts = getScripts(context);
        if (scripts != null && logger.isDebugEnabled()) {
            for(Property script : scripts) {
                logger.debug("Generated script {}", script);
            }
        }

        notity(context, node, CONFIGURE_ACTION, "files.copy", null);
        copyFiles(context, node, files);

        notity(context, node, CONFIGURE_ACTION, "scripts.copy", null);
        copyScripts(context, node, scripts);

        notity(context, node, CONFIGURE_ACTION, "scripts.run", null);
        runScripts(context, node, scripts);
    }

    /**
     * Run puppet scripts on the remote node
     *
     * @param node
     * @param scripts
     */
    protected void runScripts(final Context context, final Node node, List<Property> scripts) throws CloudManagerException {
        if (scripts == null || scripts.size() == 0) {
            logger.info("No script to run");
            return;
        }

        SSHClient client = SSHUtils.getClient(node);
        for (Property script : scripts) {
            if (logger.isDebugEnabled()) {
                logger.debug("Running script {} on remote node", script.getName());
            }

            final String exec = String.format(Constants.PUPPET_RUN_COMMAND_PATTERN, script.getName());
            if (logger.isDebugEnabled()) {
                logger.debug("Running script {}", exec);
            }

            try {
                notity(context, node, CONFIGURE_ACTION, "script.exec", "Running script %s", script.getName());
                SSHUtils.executeScript(client, exec, new SSHListener() {
                    public void onMessage(String line) {
                        notity(context, node, "ssh", "exec", line);
                    }

                    public void onError(String error) {
                        notity(context, node, "ssh", "error", error);
                    }
                });
            } catch (CloudManagerException e) {
                logger.warn("Can not execute script {} on remote node", script);
            }
        }
    }

    /**
     *
     * @param context
     * @param node
     * @param scripts
     * @throws CloudManagerException
     */
    protected void copyScripts(Context context, Node node, List<Property> scripts) throws CloudManagerException {
        if (scripts == null || scripts.size() == 0) {
            logger.debug("No script to copy to remote node");
            return;
        }

        SSHClient client = SSHUtils.getClient(node);
        for(Property script : scripts) {
            if (logger.isDebugEnabled()) {
                logger.debug("Copying {} script to remote node", script.getName());
            }
            notity(context, node, CONFIGURE_ACTION, "script.copy", "Copying script %s", script.getName());
            try {
                SSHUtils.createFile(client, script.getValue(), 0600, script.getName());
            } catch (IOException e) {
                // FIXME : Mark the script type as error so we do not try to run it...
                logger.warn("Can not copy the script " + script.getName(), e);
            }
        }
    }

    /**
     *
     * @param context
     * @param node
     * @param files
     * @throws CloudManagerException
     */
    protected void copyFiles(Context context, Node node, List<Property> files) throws CloudManagerException {
        if (files == null || files.size() == 0) {
            logger.info("No file to copy to remote node");
            return;
        }

        SSHClient client = SSHUtils.getClient(node);
        for(Property file : files) {
            if (logger.isDebugEnabled()) {
                logger.debug("Copying {} file to remote node", file.getName());
            }
            notity(context, node, CONFIGURE_ACTION, "file.copy", "Copying file %s", file.getName());

            try {
                SSHUtils.createFile(client, file.getValue(), 0600, file.getName());
            } catch (IOException e) {
                // ignore and try to copy the next one...
                // FIXME : At least we can mark the property as error...
                logger.warn("Can not copy the file " + file.getName(), e);
            }
        }
    }

    /**
     *
     * @param context
     * @return
     */
    protected List<Property> getScripts(Context context) {
        List<Property> result = Lists.newArrayList();

        DownloadFilesScriptBuilder downloadFilesScriptBuilder = new DownloadFilesScriptBuilder();
        try {
            result.add(PropertyHelper.get("download.pp", "puppet", downloadFilesScriptBuilder.build(getNode(context), context)));
        } catch (CloudManagerException e) {
            logger.warn("Can not generate download.pp file", e);
        }

        InstallPackagesScriptBuilder installPackagesScriptBuilder = new InstallPackagesScriptBuilder();
        try {
            result.add(PropertyHelper.get("packages.pp", "puppet", installPackagesScriptBuilder.build(getNode(context), context)));
        } catch (CloudManagerException e) {
            logger.warn("Can not generate packages.pp file", e);
        }

        return result;
        // one may have injected a script in a node property or in the context (ie global script)
        // TODO : Handle it!
    }

    protected List<Property> getFiles(Context context) {
        // TODO
        return Lists.newArrayList();
    }
}
