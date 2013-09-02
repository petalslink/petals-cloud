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

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.woorea.openstack.base.client.OpenStackClientConnector;
import com.woorea.openstack.nova.Nova;
import org.apache.felix.gogo.commands.Option;
import org.apache.karaf.shell.console.OsgiCommandSupport;
import org.ow2.petals.cloud.manager.api.CloudManagerException;
import org.ow2.petals.cloud.manager.api.deployment.Provider;
import org.ow2.petals.cloud.manager.api.services.ProviderRegistryService;
import org.ow2.petals.cloud.manager.openstack.Utils;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Christophe Hamerling - chamerling@linagora.com
 */
public abstract class BaseCommand extends OsgiCommandSupport {

    protected final ProviderRegistryService providerRegistryService;

    private final OpenStackClientConnector connector;

    @Option(name = "--account", description = "Account information (get the list of available accounts using iaas/accounts)", required = true)
    protected String account;

    protected BaseCommand(com.woorea.openstack.base.client.OpenStackClientConnector openStackClientConnector, ProviderRegistryService providerRegistryService) {
        this.connector = openStackClientConnector;
        this.providerRegistryService = providerRegistryService;
    }

    protected Nova getClient() throws CloudManagerException {
        Provider provider = checkNotNull(Iterables.tryFind(providerRegistryService.get(), new Predicate<Provider>() {
            public boolean apply(org.ow2.petals.cloud.manager.api.deployment.Provider input) {
                return input.getName().equals(account);
            }
        }).orNull(), "Can not retrieve account %s from the registry", account);

        return Utils.getClient(provider, connector);
    }

}
