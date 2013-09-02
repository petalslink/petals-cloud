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
package org.ow2.petals.cloud.controllers.api.artifact;

import org.ow2.petals.cloud.controllers.api.ControllerException;

/**
 * Manage artifacts deployments to runtime
 *
 * @author Christophe Hamerling - chamerling@linagora.com
 */
public interface ArtifactDeployer {

    /**
     * Deploy an artifact to a runtime
     *
     * @param artifact the artifact reference to deploy
     * @param runtime the runtime to deploy artifact to
     * @throws ControllerException
     */
    DeploymentResult deploy(Artifact artifact, org.ow2.petals.cloud.controllers.api.runtime.Runtime runtime) throws ControllerException;

    /**
     *
     */
    class DeploymentResult {

        /**
         * Deployment may provide an id to be used later (for undeploy...)
         */
        private String id;

        /**
         * Output message
         */
        private String message;

        /**
         * Output message type (info, error, ...)
         */
        private String type;

    }

}
