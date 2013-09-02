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
package org.ow2.petals.cloud.manager.commands.iaas;

import org.apache.felix.gogo.commands.Command;
import org.apache.karaf.shell.console.OsgiCommandSupport;
import org.ow2.petals.cloud.manager.api.ProviderManager;

import java.util.List;

/**
 * List all the available providers in the platform.
 * Providers are injected in the platform using OSGI bundles.
 *
 * @author Christophe Hamerling - chamerling@linagora.com
 */
@Command(scope = "iaas", name = "providers", description = "List all the IaaS providers available in the platform")
public class ListProvidersCommand extends OsgiCommandSupport {

    private List<ProviderManager> providerManagers;

    public ListProvidersCommand(List<ProviderManager> providerManagers) {
        this.providerManagers = providerManagers;
    }

    @Override
    protected Object doExecute() throws Exception {
        System.out.println("Providers list :");
        for(ProviderManager providerManager : providerManagers) {
            System.out.println(" - " + providerManager.getProviderName() + "(v" + providerManager.getProviderVersion() + ")");
        }
        return null;
    }
}
