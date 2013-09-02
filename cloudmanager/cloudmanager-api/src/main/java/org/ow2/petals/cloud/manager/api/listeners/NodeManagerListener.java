/**
 *
 * Copyright (c) 2013, Linagora
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNodeU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANodeY WARRANodeTY; without even the implied warranty of
 * MERCHANodeTABILITY or FITNodeESS FOR A PARTICULAR PURPOSE.  See the GNodeU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNodeU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA 
 *
 */
package org.ow2.petals.cloud.manager.api.listeners;

import org.ow2.petals.cloud.manager.api.deployment.Node;

/**
 * Listen to node deployment lifecycles
 *
 * @author Christophe Hamerling - chamerling@linagora.com
 */
public interface NodeManagerListener {

    void onDeploySuccess(Node node);

    void onDeployFailure(Node node);

    void onCreateFailure(Node node);

    void onCreateSuccess(Node node);

    void onUndeploySucess(Node node);

    void onUndeployFailure(Node node);
}
