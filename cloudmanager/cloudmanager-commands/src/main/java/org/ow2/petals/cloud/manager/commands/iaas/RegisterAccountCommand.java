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
import org.ow2.petals.cloud.manager.api.ProviderManager;
import org.ow2.petals.cloud.manager.api.deployment.Credentials;
import org.ow2.petals.cloud.manager.api.deployment.Provider;
import org.ow2.petals.cloud.manager.api.services.ProviderRegistryService;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Register a new IaaS into the platform
 *
 * @author Christophe Hamerling - chamerling@linagora.com
 */
@Command(scope = "iaas", name = "account", description = "Register a new IaaS account in the platform")
public class RegisterAccountCommand extends OsgiCommandSupport {

    private final List<ProviderManager> providersManager;

    private ProviderRegistryService registryService;

    // TODO : More options for key-based access!
    @Option(name = "--username", description = "Provider login", required = true)
    protected String username;

    @Option(name = "--password", description = "Provider password", required = true)
    protected String password;

    @Option(name = "--endpoint", description = "Provider endpoint", required = true)
    protected String endpoint;

    @Option(name = "--name", description = "Provider name", required = true)
    protected String name;

    // TODO : Check value with the providers list
    @Option(name = "--type", description = "Provider type, check iaas/providers for possible values", required = true)
    protected String type;

    public RegisterAccountCommand(List<ProviderManager> providerManagers, ProviderRegistryService providerRegistryService) {
        this.providersManager = providerManagers;
        this.registryService = providerRegistryService;
    }

    @Override
    protected Object doExecute() throws Exception {

        // check that the input provider exist
        checkNotNull(Iterables.tryFind(providersManager, new Predicate<ProviderManager>() {
            public boolean apply(ProviderManager input) {
                return input.getProviderName().equalsIgnoreCase(type);
            }
        }).orNull(), "Can not find the %s provider in the platform, aborting account registration", type);

        Credentials c = new Credentials();
        c.setUsername(username);
        c.setPassword(password);

        Provider provider = new Provider();
        provider.setEndpoint(endpoint);
        provider.setName(name);
        provider.setType(type);
        provider.setCredentials(c);
        this.registryService.create(provider);

        System.out.println("Account has been registered for provider " + type);
        return null;
    }
}
