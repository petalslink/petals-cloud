/****************************************************************************
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
 *****************************************************************************/
package org.ow2.petals.cloud.controllers.api.runtime;

import org.ow2.petals.cloud.controllers.api.access.Access;

import java.util.Properties;

/**
 * Runtime definition.
 *
 * @author Christophe Hamerling - chamerling@linagora.com
 */
public class Runtime {

    private String name;

    private String host;

    private String description;

    private String type;

    private Access adminAccess;

    /**
     * Additional properties
     */
    private Properties properties;

    public Runtime(String name, String host, String description, String type, Access adminAccess, Properties properties) {
        this.name = name;
        this.host = host;
        this.description = description;
        this.type = type;
        this.adminAccess = adminAccess;
        this.properties = properties;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Access getAdminAccess() {
        return adminAccess;
    }

    public void setAdminAccess(Access adminAccess) {
        this.adminAccess = adminAccess;
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }
}
