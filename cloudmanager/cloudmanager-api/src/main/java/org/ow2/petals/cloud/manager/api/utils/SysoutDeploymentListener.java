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
package org.ow2.petals.cloud.manager.api.utils;

import org.ow2.petals.cloud.manager.api.deployment.Node;
import org.ow2.petals.cloud.manager.api.listeners.DeploymentListener;

/**
 * @author Christophe Hamerling - chamerling@linagora.com
 */
public class SysoutDeploymentListener implements DeploymentListener {

    public static DeploymentListener get() {
        return new SysoutDeploymentListener();
    }

    public void on(String id, Node node, String action, String step, String pattern, Object... args) {
        StringBuffer sb = new StringBuffer("DeploymentListener : ID=")
                .append(id).append(", nodeId=").append(node.getId()).append(", action=").append(action).append(", step=").append(step);

        if (pattern != null) {
            sb.append(String.format(pattern, args));
        }
        System.out.println(sb.toString());
    }
}
