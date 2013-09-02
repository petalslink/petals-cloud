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
package org.ow2.petals.cloud.manager.api.deployment;

import java.util.Date;

/**
 * @author Christophe Hamerling - chamerling@linagora.com
 */
public class DeploymentResult {

    /**
     * The deployment ID
     */
    private String id;

    /**
     * When do we deployed?

     */
    private Date deployedAt;

    /**
     * The initial deployment may have been enriched (ids, ips, ...)
     */
    private Deployment deployment;


    public DeploymentResult() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getDeployedAt() {
        return deployedAt;
    }

    public void setDeployedAt(Date deployedAt) {
        this.deployedAt = deployedAt;
    }

    public Deployment getDeployment() {
        return deployment;
    }

    public void setDeployment(Deployment deployment) {
        this.deployment = deployment;
    }
}
