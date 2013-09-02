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
import org.ow2.petals.cloud.manager.api.deployment.Provider;

import java.util.List;

/**
 * Provider registry service. Store and retrieve all the needed information for IaaS access.
 *
 * @author Christophe Hamerling - chamerling@linagora.com
 */
public interface ProviderRegistryService {

    /**
     * Add a PaaS to the store.
     *
     * @param provider
     * @return the stored Provider with updated information(such as its ID)
     * @throws org.ow2.petals.cloud.manager.api.CloudManager
     */
    Provider create(Provider provider) throws CloudManagerException;

    /**
     * Get all the Provider from the store
     *
     * @return
     * @throws CloudManagerException
     */
    List<Provider> get() throws CloudManagerException;

    /**
     * Get a Provider from its ID
     *
     * @param id
     * @return the PaaS or null if not found
     *
     * @throws CloudManagerException
     */
    Provider get(String id) throws CloudManagerException;

    /**
     * Delete a Provider from its ID
     *
     * @param id
     * @throws CloudManagerException
     */
    void delete(String id) throws CloudManagerException;
}
