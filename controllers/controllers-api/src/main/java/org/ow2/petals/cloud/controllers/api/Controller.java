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
package org.ow2.petals.cloud.controllers.api;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * A controller bean. Controllers provides sort of automatic management functionality at the Petals PaaS level.
 *
 * @author Christophe Hamerling - chamerling@linagora.com
 */
public class Controller {

    /**
     * Type of controller
     */
    public String type;

    /**
     * Simple controller description
     */
    public String description;

    /**
     * List of controller endpoint; Key is a string, value is the endpoint URL (most likely HTTP).
     */
    public Map<String, String> endpoints = Maps.newHashMap();

    public Controller() {
    }

}
