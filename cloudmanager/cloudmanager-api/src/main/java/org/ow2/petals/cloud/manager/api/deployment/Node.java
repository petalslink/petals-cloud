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
 * @author Christophe Hamerling - chamerling@linagora.com
 */
public class Node {

    /**
     * Node has an ID when deployed on a provider (at least)
     */
    private String id;

    /**
     * Node name
     */
    private String name;

    /**
     * Softwares to install
     */
    private List<String> softwares;

    /**
     * Provider to install node on or where the node is running (account)
     */
    private String provider;

    /**
     * Properties of the VM to deploy this node on
     */
    private VM vm;

    /**
     * Ports to open or which are open on this node
     */
    private List<Integer> ports;

    /**
     * Additional properties
     */
    private List<Property> properties;

    /**
     *
     */
    private List<String> privateIpAddress;

    /**
     *
     */
    private List<String> publicIpAddress;

    /**
     * How to access on this node?
     */
    private Access access;

    public Node() {
        ports = Lists.newArrayList();
        properties = Lists.newArrayList();
        privateIpAddress = Lists.newArrayList();
        publicIpAddress = Lists.newArrayList();
        softwares = Lists.newArrayList();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getSoftwares() {
        return softwares;
    }

    public void setSoftwares(List<String> softwares) {
        this.softwares = softwares;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public VM getVm() {
        return vm;
    }

    public void setVm(VM vm) {
        this.vm = vm;
    }

    public List<Integer> getPorts() {
        return ports;
    }

    public void setPorts(List<Integer> ports) {
        this.ports = ports;
    }

    public List<Property> getProperties() {
        return properties;
    }

    public void setProperties(List<Property> properties) {
        this.properties = properties;
    }

    public List<String> getPrivateIpAddress() {
        return privateIpAddress;
    }

    public void setPrivateIpAddress(List<String> privateIpAddress) {
        this.privateIpAddress = privateIpAddress;
    }

    public List<String> getPublicIpAddress() {
        return publicIpAddress;
    }

    public void setPublicIpAddress(List<String> publicIpAddress) {
        this.publicIpAddress = publicIpAddress;
    }

    public Access getAccess() {
        return access;
    }

    public void setAccess(Access access) {
        this.access = access;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Node{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", softwares=" + softwares +
                ", provider='" + provider + '\'' +
                ", vm=" + vm +
                ", ports=" + ports +
                ", properties=" + properties +
                ", privateIpAddress=" + privateIpAddress +
                ", publicIpAddress=" + publicIpAddress +
                ", access=" + access +
                '}';
    }
}
