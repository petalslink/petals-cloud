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
package org.ow2.petals.cloud.manager.core;

import org.ow2.petals.cloud.manager.api.services.DeployerService;
import org.ow2.petals.cloud.manager.api.services.ManagementService;
import org.ow2.petals.cloud.manager.api.listeners.DeploymentListener;
import org.ow2.petals.cloud.manager.api.services.StorageService;

/**
 * Does nothing but provides access to services
 *
 * @author Christophe Hamerling - chamerling@linagora.com
 */
public class CloudManager implements org.ow2.petals.cloud.manager.api.CloudManager {

    private DeploymentListener deploymentListener;

    private ManagementService managementService;

    private StorageService storageService;

    private DeployerService deployerService;

    public CloudManager(ManagementService managementService, StorageService storageService) {
        this(managementService, storageService, null, null);
    }

    public CloudManager(ManagementService managementService, StorageService storageService, DeployerService deployerService, DeploymentListener deploymentListener) {
        this.managementService = managementService;
        this.storageService = storageService;
        this.deployerService = deployerService;
        this.deploymentListener = deploymentListener;
    }

    public ManagementService getManagementService() {
        return managementService;
    }

    public StorageService getStorageService() {
        return storageService;
    }

    public DeployerService getDeployerService() {
        return deployerService;
    }

    public DeploymentListener getDeploymentListener() {
        return deploymentListener;
    }
}
