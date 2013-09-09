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
package org.ow2.petals.cloud.manager.listener.commands;

import org.apache.karaf.shell.console.OsgiCommandSupport;
import org.ow2.petals.cloud.manager.api.deployment.Node;
import org.ow2.petals.cloud.manager.api.listeners.DeploymentListener;

import java.util.List;
import java.util.UUID;

/**
 * Send an event to the listeners
 *
 * @author Christophe Hamerling - chamerling@linagora.com
 */
public class TestListenersCommand extends OsgiCommandSupport {

    private final List<DeploymentListener> listeners;

    public TestListenersCommand(List<DeploymentListener> listeners) {
        this.listeners = listeners;
    }

    @Override
    protected Object doExecute() throws Exception {
        System.out.println("Sending event to listeners...");
        if (listeners == null || listeners.size() == 0) {
            System.out.println("No listeners");
            return null;
        }

        String uuid = UUID.randomUUID().toString();
        Node node = new Node();
        node.setId("testnode");
        String action = "test";
        String step = "send";
        String pattern = "This is a test message from command line";
        for(DeploymentListener listener : listeners) {
            System.out.println(" - Sending to listener " + listener.getClass().getName());
            listener.on(uuid, node, action, step, pattern, null);
            System.out.println("... Done!");
        }
        return null;
    }
}
