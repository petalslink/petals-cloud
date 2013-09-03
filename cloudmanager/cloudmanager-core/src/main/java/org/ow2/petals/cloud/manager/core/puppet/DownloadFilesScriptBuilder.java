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
package org.ow2.petals.cloud.manager.core.puppet;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import org.ow2.petals.cloud.manager.api.actions.Context;
import org.ow2.petals.cloud.manager.api.deployment.Node;
import org.ow2.petals.cloud.manager.api.deployment.Property;

import java.util.List;
import java.util.Map;

/**
 * TODO : get files to install in the software section of the node and the deployment descriptor
 * TODO : cf InstallPackagesScript
 *
 * @author Christophe Hamerling - chamerling@linagora.com
 */
public class DownloadFilesScriptBuilder extends ClasspathScriptBuilder {

    public static String TEMPLATE = "/org/ow2/petals/cloud/manager/puppet/download.pp";

    @Override
    protected Object getScope(Node node, Context context) {
        return ImmutableMap.of("uris", getURIs(node, context));
    }

    @Override
    protected String getTemplate() {
        return TEMPLATE;
    }

    /**
     * Get all the URIs from the node and the context. URIs can be found on node software, properties, ...
     *
     * @param node
     * @param context
     * @return
     */
    protected List<Map<String, String>> getURIs(Node node, Context context) {

        // get node uris
        Iterable<Map<String, String>> n = Iterables.transform(Iterables.filter(node.getProperties(), new Predicate<Property>() {
            public boolean apply(org.ow2.petals.cloud.manager.api.deployment.Property input) {
                return input.getType() != null && input.getType().equals("uri") && input.getValue() != null && input.getValue().startsWith("http://");
            }
        }), new Function<Property, Map<String, String>>() {
            public Map<String, String> apply(org.ow2.petals.cloud.manager.api.deployment.Property input) {
                // we got an input property which is a uri type with http value
                // TODO : Get destination from the input value suffix
                return ImmutableMap.of("source", input.getValue(), "destination", input.getName());
            }
        });

        return Lists.newArrayList(n);
    }
}
