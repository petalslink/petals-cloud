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

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.connection.channel.direct.Session;
import org.ow2.petals.cloud.manager.api.CloudManagerException;
import org.ow2.petals.cloud.manager.api.actions.Context;
import org.ow2.petals.cloud.manager.api.deployment.*;
import org.ow2.petals.cloud.manager.core.utils.SSHUtils;

import java.io.IOException;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Install software on remote node using defined type of provisioning
 *
 * @author Christophe Hamerling - chamerling@linagora.com
 */
public class InstallSoftwareAction extends MonitoredAction {

    public InstallSoftwareAction() {
        super();
    }

    public void doExecute(Context context) throws CloudManagerException {
        Node node = getNode(context);
        Deployment deployment = getDeployment(context);
        checkNotNull(node.getAccess());

        // get the first available IP address to connect to the node
        String hostname = Iterables.tryFind(node.getPublicIpAddress(), new Predicate<String>() {
            public boolean apply(String input) {
                return input != null;
            }
        }).or(Iterables.tryFind(node.getPrivateIpAddress(), new Predicate<String>() {
            public boolean apply(String input) {
                return input != null;
            }
        })).orNull();
        checkNotNull(hostname, "Can not access to a null IP");

        // assume that we are using SSH by default
        int port = node.getAccess().getPort();
        if (port <= 0) {
            port = Access.DEFAULT_SSH;
        }

        SSHClient client = null;
        try {
            client = SSHUtils.newClient(hostname, port, node.getAccess());
        } catch (IOException e) {
            throw new CloudManagerException("Can not create SSH client for node on hostname " + hostname, e);
        }

        // get files to deploy for the current node
        // files are defined in properties with the 'file' type
        List<Property> files = getFiles(deployment, node);
        for(Property file : files) {
            String content = "TODO";
            String destination = "/tmp/TODO";
            try {
                SSHUtils.createFile(client, content, 0600, destination);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        String destination = "/tmp/puppet_install.pp";
        try {
            SSHUtils.createFile(client, getPuppetFile(deployment, node), 0600, destination);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // let's run the puppet script!
        Session session = null;
        try {
            session = client.startSession();
        } catch (Exception e) {
            throw new CloudManagerException("Can not start SSH session to node " + hostname, e);
        }

        try {
            session.allocateDefaultPTY();
            final String runScriptWithWaitCommand = "while ! which puppet &> /dev/null ; " +
                    "do echo 'Puppet command not found. Waiting for userdata.sh script to finish (10s)' " +
                    "&& sleep 10; " +
                    "done " +
                    "&& sudo puppet apply --detailed-exitcodes --debug --verbose " + destination;
            Session.Command command = session.exec(runScriptWithWaitCommand);
            // TODO add log handling
            command.join();

            final Integer exitStatus = command.getExitStatus();
            session.close();
        } catch (Exception e) {

        } finally {
            try {
                session.close();
            } catch (Exception e) {
                e.printStackTrace();
                // NOP
            }
        }
    }

    private String getPuppetFile(Deployment deployment, Node node) {
        return null;
    }

    private List<Property> getFiles(Deployment deployment, Node node) {
        return null;
    }

    private Software getSoftware(final Deployment deployment, final String software) {
        return Iterables.tryFind(deployment.getSoftwares(), new Predicate<Software>() {
            public boolean apply(org.ow2.petals.cloud.manager.api.deployment.Software input) {
                return software.equalsIgnoreCase(input.getName());
            }
        }).orNull();
    }

    private Deployment getDeployment(Context context) {
        return null;
    }

    private Node getNode(Context context) {
        return null;
    }

    @Override
    protected String getName() {
        return InstallSoftwareAction.class.getName();
    }
}
