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
package org.ow2.petals.cloud.manager.api.actions;

import org.ow2.petals.cloud.manager.api.ProviderManager;
import org.ow2.petals.cloud.manager.api.deployment.Deployment;
import org.ow2.petals.cloud.manager.api.deployment.Node;
import org.ow2.petals.cloud.manager.api.deployment.Provider;
import org.ow2.petals.cloud.manager.api.listeners.DeploymentListener;

import java.util.HashMap;
import java.util.Map;

/**
 * Execution context
 *
 * @author Christophe Hamerling - chamerling@linagora.com
 */
public class Context {

    private ProviderManager providerManager;

    /**
     * Provider account. Note that this should be the same as the one defined in node.getProvider.
     */
    private Provider provider;

    private Map<String, Object> params;

    private Map<String, Object> output;

    private String id;

    private Deployment descriptor;

    private DeploymentListener listener;

    /**
     * Current node we want to work on...
     */
    private Node node;

    public Context(String id) {
        this.id = id;
        this.params = new HashMap<String, Object>();
        this.output = new HashMap<String, Object>();
    }

    public Object get(String name) {
        return params.get(name);
    }

    public void set(String name, Object value) {
        if (name != null) {
            this.params.put(name, value);
        }
    }

    public Object getOutput(String name) {
        return output.get(name);
    }

    public void setOutput(String name, Object value) {
        if (name != null) {
            this.output.put(name, value);
        }
    }

    public String getId() {
        return this.id;
    }

    public ProviderManager getProviderManager() {
        return providerManager;
    }

    public void setProviderManager(ProviderManager providerManager) {
        this.providerManager = providerManager;
    }

    public Node getNode() {
        return this.node;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    public Deployment getDescriptor() {
        return descriptor;
    }

    public void setDescriptor(Deployment descriptor) {
        this.descriptor = descriptor;
    }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    public DeploymentListener getListener() {
        return listener;
    }

    public void setListener(DeploymentListener listener) {
        this.listener = listener;
    }
}
