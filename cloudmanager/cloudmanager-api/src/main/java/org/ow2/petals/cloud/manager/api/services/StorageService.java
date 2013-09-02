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
package org.ow2.petals.cloud.manager.api.services;

import org.ow2.petals.cloud.manager.api.CloudManagerException;
import org.ow2.petals.cloud.manager.api.PaaS;

import java.util.List;

/**
 * PaaS information store.
 *
 * @author Christophe Hamerling - chamerling@linagora.com
 */
public interface StorageService {

    /**
     * Add a PaaS to the store.
     *
     * @param paas
     * @return the stored PaaS with updated information(such as its ID)
     * @throws org.ow2.petals.cloud.manager.api.CloudManager
     */
    PaaS create(PaaS paas) throws CloudManagerException;

    /**
     * Get all the PaaS from the store
     *
     * @return
     * @throws CloudManagerException
     */
    List<PaaS> get() throws CloudManagerException;

    /**
     * Get a PaaS from its ID
     *
     * @param id
     * @return the PaaS or null if not found
     *
     * @throws CloudManagerException
     */
    PaaS get(String id) throws CloudManagerException;

    /**
     * Delete a PaaS from its ID
     *
     * @param id
     * @throws CloudManagerException
     */
    void delete(String id) throws CloudManagerException;

}
