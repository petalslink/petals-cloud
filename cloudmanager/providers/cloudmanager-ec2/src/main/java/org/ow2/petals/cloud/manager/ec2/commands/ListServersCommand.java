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

import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.model.DescribeInstancesResult;
import com.amazonaws.services.ec2.model.Instance;
import com.amazonaws.services.ec2.model.Reservation;
import org.ow2.petals.cloud.manager.api.services.ProviderRegistryService;

/**
 * @author Christophe Hamerling - chamerling@linagora.com
 */
public class ListServersCommand extends BaseCommand {

    public ListServersCommand(ProviderRegistryService providerRegistryService) {
        super(providerRegistryService);
    }

    @Override
    protected Object doExecute() throws Exception {
        AmazonEC2 client = getClient();
        System.out.println("Servers :");

        DescribeInstancesResult result = client.describeInstances();
        for(Reservation reservation : result.getReservations()) {
            for(Instance instance : reservation.getInstances()) {
                System.out.println(" - " + instance.toString());
            }
        }
        return null;
    }
}
