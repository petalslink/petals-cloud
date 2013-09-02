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
package org.ow2.petals.cloud.controllers.api.notification;

/**
 * @author Christophe Hamerling - chamerling@linagora.com
 */
public interface NotificationService {

    /**
     * Subscribe to a resource
     *
     * @param resource
     * @param listener
     * @return a subscription ID used to unregister...
     */
    String subscribe(String resource, NotificationListener listener);

    /**
     * Unsubscribe from a resource
     *
     * @param id the subscription ID returned by the subscribe operation
     */
    void unsubscribe(String id);

}
