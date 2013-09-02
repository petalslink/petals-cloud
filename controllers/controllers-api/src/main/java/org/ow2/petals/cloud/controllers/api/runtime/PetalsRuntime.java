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
package org.ow2.petals.cloud.controllers.api.runtime;

import org.ow2.petals.cloud.controllers.api.access.Access;
import org.ow2.petals.cloud.controllers.api.access.BasicAuthAccess;

import java.util.Properties;

/**
 * Bean definition of a Petals Runtime
 *
 * @author Christophe Hamerling - chamerling@linagora.com
 */
public class PetalsRuntime extends Runtime {

    public static final String TYPE = "petals";

    /**
     * The petals node mode
     */
    private MODE mode;

    public static PetalsRuntime get(String name, String host, String description, BasicAuthAccess adminAccess, Properties properties, MODE mode) {
        return new PetalsRuntime(name, host, description, adminAccess, properties, mode);
    }

    public PetalsRuntime(String name, String host, String description, Access adminAccess, Properties properties, MODE mode) {
        super(name, host, description, TYPE, adminAccess, properties);
        this.mode = mode;
    }

    public MODE getMode() {
        return mode;
    }

    public enum MODE {
        SLAVE, MASTER, PEER, XXX
    }
}
