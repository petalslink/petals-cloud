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
package org.ow2.petals.cloud.manager.api.deployment.tools;

import org.ow2.petals.cloud.manager.api.deployment.Deployment;

import java.util.Map;

/**
 * Deployment Providers are custom hooks to be used by providers in order to enrich initial deployment.
 * For example, a provider may be able to add some configuration files, some scripts, add nodes, update VM properties...
 *
 * The Deployment Provider is retrieve at deployment time and populate is called before the first call to the provider API.
 *
 * @author Christophe Hamerling - chamerling@linagora.com
 */
public interface DeploymentProvider {

    /**
     * Populate the deployment descriptor with some custom properties.
     *
     * @param deployment initial deployment to populate
     * @return
     */
    void populate(Deployment deployment, Map<String, String> args);

    /**
     * DeploymentProvider type
     *
     * @return
     */
    String getType();
}
