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
import org.ow2.petals.cloud.manager.api.deployment.Property;
import org.ow2.petals.cloud.manager.api.deployment.Provider;
import org.ow2.petals.cloud.manager.api.deployment.utils.NodeHelper;
import org.ow2.petals.cloud.manager.api.deployment.utils.PropertyHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.ow2.petals.cloud.manager.api.deployment.utils.NodeHelper.getProperty;
import static org.ow2.petals.cloud.manager.api.deployment.utils.PropertyHelper.hasMap;

/**
 * @author Christophe Hamerling - chamerling@linagora.com
 */
public class OpenStackProviderManager implements ProviderManager {

    private static Logger logger = LoggerFactory.getLogger(OpenStackProviderManager.class);

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
     * The supported API version range
     * TODO
     *
     * @return
     */
    public String getProviderVersion() {
        return "1.0";
    }

    public Node createNode(final Provider provider, final Node node) throws CloudManagerException {
        logger.debug("Creating node on provider {} : {}", provider, node);

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

        // TODO : Get key from context
        if (getProperty(node, "iaas.key") != null) {
            create.setKeyName((getProperty(node, "iaas.key")).getValue());
        } else {
            Property p = getProperty(provider.getProperties(), "iaas.key");
            if (p != null) {
                create.setKeyName(p.getValue());
            }
        }

        if (create.getKeyName() == null) {
            logger.warn("Attempting to create a node without a security key...");
        } else {
            logger.info("Key name " + create.getKeyName());
        }

        create.setFlavorRef(flavor.getId());
        create.setImageRef(image.getId());
        Map<String, String> meta = Maps.newHashMap();

        // add all the properties from the input node, will be used if needed...
        meta.putAll(hasMap(node.getProperties()));
        meta.putAll(hasMap(provider.getProperties()));
        create.setMetadata(meta);

        Server server = client.servers().boot(create).execute();

        if (logger.isInfoEnabled()) {
            logger.info("Openstack create server API call returned : " + server);
        }
        return Adapter.to(server);
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
