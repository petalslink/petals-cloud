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
package org.ow2.petals.cloud.manager.api;

/**
 * @author Christophe Hamerling - chamerling@linagora.com
 */
public class CloudManagerException extends Exception {

    public CloudManagerException() {
        super();
    }

    public CloudManagerException(String message) {
        super(message);
    }

    public CloudManagerException(String message, Throwable cause) {
        super(message, cause);
    }

    public CloudManagerException(Throwable cause) {
        super(cause);
    }
}
