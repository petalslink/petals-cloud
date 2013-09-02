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

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.woorea.openstack.nova.Nova;
import com.woorea.openstack.nova.model.*;
import org.ow2.petals.cloud.manager.api.CloudManagerException;
import org.ow2.petals.cloud.manager.api.ProviderManager;
import org.ow2.petals.cloud.manager.api.deployment.Node;
import org.ow2.petals.cloud.manager.api.deployment.Provider;

import java.util.List;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Christophe Hamerling - chamerling@linagora.com
 */
public class OpenStackProviderManager implements ProviderManager {

    private Map<Provider, Nova> clients;

    private com.woorea.openstack.base.client.OpenStackClientConnector openStackClientConnector;

    public OpenStackProviderManager(com.woorea.openstack.base.client.OpenStackClientConnector openStackClientConnector) {
        clients = Maps.newHashMap();
        this.openStackClientConnector = openStackClientConnector;
    }

    public String getProviderName() {
        return "openstack";
    }

    /**
     * The supported API version
     * TODO
     *
     * @return
     */
    public String getProviderVersion() {
        return "1.0";
    }

    public Node createNode(final Provider provider, final Node node) throws CloudManagerException {
        Nova client = getClient(provider);
        checkNotNull(client, "Can not create client with the provider information");

        Images images = client.images().list(false).execute();
        Image image = Iterables.tryFind(images, new Predicate<Image>() {
            public boolean apply(com.woorea.openstack.nova.model.Image image) {
                return image.getName().toLowerCase().contains(node.getVm().getImage());
            }
        }).orNull();

        if (image == null) {
            throw new CloudManagerException("No valid image found");
        }

        final String flavorName = null;
        Flavors flavors = client.flavors().list(true).execute();
        if (flavors.getList().size() == 0) {
            throw new CloudManagerException("Can not find any flavor on the provider");

        }
        Flavor flavor = Iterables.tryFind(flavors, new Predicate<Flavor>() {
            public boolean apply(com.woorea.openstack.nova.model.Flavor flavor) {
                return flavor.getName().equals(flavorName);
            }
        }).or(flavors.getList().get(0));

        ServerForCreate create = new ServerForCreate();
        create.setName(node.getName());
        //create.setKeyName(properties.keyName);
        create.setFlavorRef(flavor.getId());
        create.setImageRef(image.getId());
        Map<String, String> meta = Maps.newHashMap();

        // FIXME : Use some props from the API.
        //meta.put("controller.endpoint", env.controllerEndpoint);
        //meta.put("container.id", env.localContainerId);
        //meta.put("virtual.id", env.virtualContainerId);
        //meta.put("topology.url", env.controllerEndpoint + "/paas/" + env.localContainerId + "/topology/raw");
        create.setMetadata(meta);
        return Adapter.to(client.servers().boot(create).execute());
    }

    public List<Node> getNodes(Provider provider) {
        Nova client = getClient(provider);
        Servers servers = client.servers().list(true).execute();
        return Lists.transform(servers.getList(), new Adapter());
    }

    public Node getNode(Provider provider, String id) throws CloudManagerException {
        throw new CloudManagerException("Not implemented");
    }

    public boolean deleteNode(Provider provider, Node node) throws CloudManagerException {
        throw new CloudManagerException("Not implemented");
    }

    protected synchronized Nova getClient(Provider provider) {
        Nova client = this.clients.get(provider);
        if (client == null) {
            client = Utils.getClient(provider, openStackClientConnector);
            this.clients.put(provider, client);
        }
        return client;
    }
}
