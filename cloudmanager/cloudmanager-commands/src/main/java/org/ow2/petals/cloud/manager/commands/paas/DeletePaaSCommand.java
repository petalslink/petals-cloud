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
package org.ow2.petals.cloud.manager.commands.paas;

import com.google.common.annotations.VisibleForTesting;
import org.apache.felix.gogo.commands.Command;
import org.apache.felix.gogo.commands.Option;
import org.ow2.petals.cloud.manager.api.CloudManager;
import org.ow2.petals.cloud.manager.api.deployment.Node;
import org.ow2.petals.cloud.manager.api.listeners.DeploymentListener;
import org.ow2.petals.cloud.manager.api.utils.SysoutDeploymentListener;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Christophe Hamerling - chamerling@linagora.com
 */
@Command(scope = "paas", name = "delete", description = "Delete a PaaS")
public class DeletePaaSCommand extends BaseCommand {

    @Option(name = "--id", description = "ID of the PaaS to delete", required = true)
    protected String id;

    public DeletePaaSCommand(CloudManager cloudManager) {
        super(cloudManager, null);
    }

    @Override
    protected Object doExecute() throws Exception {
        System.out.println("Deleting the PaaS " + id);
        cloudManager.getManagementService().delete(id, new SysoutDeploymentListener());
        return null;
    }

    @VisibleForTesting
    void setId(String id) {
        this.id = checkNotNull(id);
    }
}
