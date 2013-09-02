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
package org.ow2.petals.cloud.manager.openstack.commands;

import com.woorea.openstack.nova.Nova;
import com.woorea.openstack.nova.model.Server;
import org.apache.felix.gogo.commands.Command;
import org.ow2.petals.cloud.manager.api.services.ProviderRegistryService;

/**
 * Karaf commands for OpenStack
 *
 * @author Christophe Hamerling - chamerling@linagora.com
 */
@Command(scope = "openstack", name = "servers", description = "List available servers")
public class ListServersCommand extends BaseCommand {

    public ListServersCommand(com.woorea.openstack.base.client.OpenStackClientConnector openStackClientConnector, ProviderRegistryService providerRegistryService) {
        super(openStackClientConnector, providerRegistryService);
    }

    @Override
    protected Object doExecute() throws Exception {
        Nova nova = getClient();
        System.out.println("Servers :");
        for(Server server : nova.servers().list(true).execute().getList()) {
            System.out.println(" - " + server);
        }
        return null;
    }
}
