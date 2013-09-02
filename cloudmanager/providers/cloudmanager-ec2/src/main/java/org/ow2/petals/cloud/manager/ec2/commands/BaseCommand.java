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
package org.ow2.petals.cloud.manager.ec2.commands;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2Client;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import org.apache.felix.gogo.commands.Option;
import org.apache.karaf.shell.console.OsgiCommandSupport;
import org.ow2.petals.cloud.manager.api.CloudManagerException;
import org.ow2.petals.cloud.manager.api.deployment.Credentials;
import org.ow2.petals.cloud.manager.api.deployment.Node;
import org.ow2.petals.cloud.manager.api.deployment.Provider;
import org.ow2.petals.cloud.manager.api.services.ProviderRegistryService;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Christophe Hamerling - chamerling@linagora.com
 */
public abstract class BaseCommand extends OsgiCommandSupport {

    protected final ProviderRegistryService providerRegistryService;

    @Option(name = "--account", description = "Account information (get the list of available accounts using iaas/accounts)", required = true)
    protected String account;

    protected BaseCommand(ProviderRegistryService providerRegistryService) {
        this.providerRegistryService = providerRegistryService;
    }

    protected AmazonEC2 getClient() throws CloudManagerException {
        Provider provider = checkNotNull(Iterables.tryFind(providerRegistryService.get(), new Predicate<Provider>() {
            public boolean apply(org.ow2.petals.cloud.manager.api.deployment.Provider input) {
                return input.getName().equals(account);
            }
        }).orNull(), "Can not retrieve account %s from the registry", account);

        Credentials credentials = provider.getCredentials();
        AWSCredentials awsCredentials = new BasicAWSCredentials(credentials.getPublicKey(), credentials.getPrivateKey());
        AmazonEC2 ec2 = new AmazonEC2Client(awsCredentials);
        ec2.setEndpoint(provider.getEndpoint());
        // TODO in properties
        //ec2.setRegion(provider.getRegion());
        ec2.setRegion(Region.getRegion(Regions.DEFAULT_REGION));
        return ec2;
    }
}
