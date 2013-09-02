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
import org.ow2.petals.cloud.manager.api.CloudManagerException;
import org.ow2.petals.cloud.manager.api.ProviderManager;
import org.ow2.petals.cloud.manager.api.deployment.Node;
import org.ow2.petals.cloud.manager.api.deployment.Provider;
import org.ow2.petals.cloud.manager.api.deployment.VM;
import org.ow2.petals.cloud.manager.api.services.ProviderRegistryService;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Create a single VM
 *
 * @author Christophe Hamerling - chamerling@linagora.com
 */
@Command(scope = "vm", name = "create", description = "Create a new VM on a provider")
public class CreateVMCommand extends OsgiCommandSupport {

    private final List<ProviderManager> providerManagers;

    private final ProviderRegistryService providerRegistryService;

    @Option(name = "--iaas", description = "Where to create the VM (get the list of available IaaS using iaas/providers)", required = true)
    protected String iaas;

    @Option(name = "--account", description = "Account information (get the list of available accounts using iaas/accounts)", required = true)
    protected String account;

    @Option(name = "--name", description = "VM name")
    protected String name;

    public CreateVMCommand(List<ProviderManager> providerManagers, ProviderRegistryService providerRegistryService) {
        this.providerManagers = providerManagers;
        this.providerRegistryService = providerRegistryService;
    }

    @Override
    protected Object doExecute() throws Exception {
        ProviderManager manager = checkNotNull(getManager(iaas), "Can not retrieve provider for IaaS %s", iaas);
        Provider provider = checkNotNull(getProvider(account), "Can not find the account information with name %s. Check iaas/accounts", account);

        Node node = new Node();
        if (name != null) {
            node.setName(name);
        } else {
            name = "cloud";
        }

        // TODO : VM paramaters as input
        VM vm = new VM();
        vm.setImage("");
        vm.setOs("");
        node.setVm(vm);

        System.out.println("Creating VM...");
        Node result = manager.createNode(provider, node);
        System.out.println("... Done : ");
        System.out.println(result);
        return result;
    }

    private Provider getProvider(final String account) throws CloudManagerException {
        return Iterables.tryFind(providerRegistryService.get(), new Predicate<Provider>() {
            public boolean apply(org.ow2.petals.cloud.manager.api.deployment.Provider input) {
                return input.getName().equals(account);
            }
        }).orNull();
    }

    private ProviderManager getManager(final String iaas) {
        return Iterables.tryFind(providerManagers, new Predicate<ProviderManager>() {
            public boolean apply(ProviderManager input) {
                return iaas.equalsIgnoreCase(input.getProviderName());
            }
        }).orNull();
    }
}
