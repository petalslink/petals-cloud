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
public class Provider {

    /**
     *
     */
    private String name;

    /**
     * Unique type in platform
     */
    private String type;

    /**
     * Access endpoint for the provider
     */
    private String endpoint;

    /**
     * Credentials to access to the provider
     */
    private Credentials credentials;

    /**
     * Additional properties (region, zone, ...)
     */
    private List<Property> properties;

    public Provider() {
        properties = Lists.newArrayList();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public Credentials getCredentials() {
        return credentials;
    }

    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
    }

    public List<Property> getProperties() {
        return properties;
    }

    public void setProperties(List<Property> properties) {
        this.properties = properties;
    }

    @Override
    public String toString() {
        return "Provider{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", endpoint='" + endpoint + '\'' +
                ", credentials=" + credentials +
                ", properties=" + properties +
                '}';
    }
}
