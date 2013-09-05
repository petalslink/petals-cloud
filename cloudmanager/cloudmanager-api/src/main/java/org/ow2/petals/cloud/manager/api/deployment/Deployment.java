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
package org.ow2.petals.cloud.manager.api.deployment;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * A deployment descriptor. The Cloud Manager is able to deploy distributed architectures using it.
 *
 * @author Christophe Hamerling - chamerling@linagora.com
 */
public class Deployment {

    /**
     * Deployment have an unique ID which is created by the framework
     */
    private String id;
    private String name;
    private String description;
    private List<Provider> providers;
    private List<Software> softwares;
    private List<Node> nodes;

    /**
     * Define properties at the deployment level. These can be globals for providers/software/nodes/...
     */
    private List<Property> properties;

    /**
     * Default VM to use if none is defined in Node
     */
    private VM vm;

    public Deployment() {
        providers = Lists.newArrayList();
        softwares = Lists.newArrayList();
        nodes = Lists.newArrayList();
        properties = Lists.newArrayList();
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Provider> getProviders() {
        return providers;
    }

    public void setProviders(List<Provider> providers) {
        this.providers = providers;
    }

    public List<Software> getSoftwares() {
        return softwares;
    }

    public void setSoftwares(List<Software> softwares) {
        this.softwares = softwares;
    }

    public List<Node> getNodes() {
        return nodes;
    }

    public void setNodes(List<Node> nodes) {
        this.nodes = nodes;
    }

    public VM getVm() {
        return vm;
    }

    public void setVm(VM vm) {
        this.vm = vm;
    }

    public List<Property> getProperties() {
        return properties;
    }

    public void setProperties(List<Property> properties) {
        this.properties = properties;
    }

    @Override
    public String toString() {
        return "Deployment{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", providers=" + providers +
                ", softwares=" + softwares +
                ", nodes=" + nodes +
                ", properties=" + properties +
                ", vm=" + vm +
                '}';
    }
}
