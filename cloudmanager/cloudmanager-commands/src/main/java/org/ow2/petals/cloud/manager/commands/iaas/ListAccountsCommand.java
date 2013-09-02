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

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import org.apache.felix.gogo.commands.Command;
import org.apache.felix.gogo.commands.Option;
import org.apache.karaf.shell.console.OsgiCommandSupport;
import org.ow2.petals.cloud.manager.api.deployment.Provider;
import org.ow2.petals.cloud.manager.api.services.ProviderRegistryService;

/**
 * Get the list of registered IaaS
 *
 * @author Christophe Hamerling - chamerling@linagora.com
 */
@Command(scope = "iaas", name = "accounts", description = "List all the IaaS registered in the platform")
public class ListAccountsCommand extends OsgiCommandSupport {

    private ProviderRegistryService registryService;

    @Option(name = "--filter", description = "Filter by provider name")
    protected String filter;

    public ListAccountsCommand(ProviderRegistryService registryService) {
        this.registryService = registryService;
    }

    @Override
    protected Object doExecute() throws Exception {
        Iterable<Provider> filtered = Iterables.filter(registryService.get(), new Predicate<Provider>() {
            public boolean apply(Provider p) {
                if (filter == null || filter.trim().length() == 0){
                    return true;
                }
                return p.getName().contains(filter);
            }
        });
        System.out.println("Registered IaaS accounts :");
        for (Provider p : filtered) {
            System.out.println(" - " + p);
        }
        return null;
    }
}
