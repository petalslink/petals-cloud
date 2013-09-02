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
package org.ow2.petals.cloud.manager.openstack;

import com.woorea.openstack.base.client.OpenStackClientConnector;
import com.woorea.openstack.keystone.Keystone;
import com.woorea.openstack.keystone.model.Access;
import com.woorea.openstack.keystone.model.authentication.UsernamePassword;
import com.woorea.openstack.keystone.utils.KeystoneUtils;
import com.woorea.openstack.nova.Nova;
import org.ow2.petals.cloud.manager.api.deployment.Provider;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Christophe Hamerling - chamerling@linagora.com
 */
public class Utils {

    public static Nova getClient(Provider provider, OpenStackClientConnector connector) {
        checkNotNull(provider);
        Nova result = null;
        Keystone keystone = new Keystone(provider.getEndpoint(), connector);
        Access access = keystone.tokens().authenticate(
                new UsernamePassword(provider.getCredentials().getUsername(),
                        provider.getCredentials().getPassword()))
                // TODO : Get it from the node properties
                .withTenantName("petals")
                .execute();
        keystone.token(access.getToken().getId());
        // get the compute endpoint from the catalog
        String endpoint = KeystoneUtils.findEndpointURL(access.getServiceCatalog(), "compute", null, "public");
        result = new Nova(endpoint, connector);
        result.token(access.getToken().getId());
        return result;
    }
}
