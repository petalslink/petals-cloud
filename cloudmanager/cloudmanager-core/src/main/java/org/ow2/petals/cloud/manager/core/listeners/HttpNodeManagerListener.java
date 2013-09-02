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
package org.ow2.petals.cloud.manager.core.listeners;

import org.ow2.petals.cloud.manager.api.deployment.Node;
import org.ow2.petals.cloud.manager.api.listeners.NodeManagerListener;

/**
 * @author Christophe Hamerling - chamerling@linagora.com
 */
public class HttpNodeManagerListener extends AbstractHttpNotifier implements NodeManagerListener {

    String endpoint;

    public HttpNodeManagerListener() {
    }

    public void onDeploySuccess(Node node) {
        this.post(endpoint, new Event(node.getId(), "node.deploy.success",
                String.format("Deploy Success for Node %s", node.getId())));
    }

    public void onDeployFailure(Node node) {
        this.post(endpoint, new Event(node.getId(), "node.deploy.failure",
                String.format("Deploy Failure for Node %s", node.getId())));
    }

    public void onCreateFailure(Node node) {
        this.post(endpoint, new Event(node.getId(), "node.create.failure",
                String.format("Create Failure for Node %s", node.getId())));
    }

    public void onCreateSuccess(Node node) {
        this.post(endpoint, new Event(node.getId(), "node.create.success",
                String.format("Create Success for Node %s", node.getId())));
    }

    public void onUndeploySucess(Node node) {
        this.post(endpoint, new Event(node.getId(), "node.undeploy.success",
                String.format("Undeploy Success for Node %s", node.getId())));
    }

    public void onUndeployFailure(Node node) {
        this.post(endpoint, new Event(node.getId(), "node.undeploy.failure",
                String.format("Undeploy Failure for Node %s", node.getId())));
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }
}
