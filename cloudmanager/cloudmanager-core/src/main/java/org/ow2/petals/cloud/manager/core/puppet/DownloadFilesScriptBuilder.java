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
import org.ow2.petals.cloud.manager.api.deployment.Constants;
import org.ow2.petals.cloud.manager.api.deployment.Node;
import org.ow2.petals.cloud.manager.api.deployment.Property;
import org.ow2.petals.cloud.manager.api.deployment.Software;

import java.util.List;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Create the script which will download all the defined files from HTTP locations.
 * This is the case on each node for node properties (URL type) and required software.
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

        List<Map<String, String>> result = Lists.newArrayList();

        // get node uris
        Iterable<Map<String, String>> n = Iterables.transform(Iterables.filter(node.getProperties(), new Predicate<Property>() {
            public boolean apply(org.ow2.petals.cloud.manager.api.deployment.Property input) {
                return input.getType() != null && input.getType().equals(Constants.URL_TYPE) && input.getValue() != null && input.getValue().startsWith("http://");
            }
        }), new Function<Property, Map<String, String>>() {
            public Map<String, String> apply(org.ow2.petals.cloud.manager.api.deployment.Property input) {
                // we got an input property which is a uri type with http value
                // TODO : Get destination from the input value suffix
                return ImmutableMap.of("source", input.getValue(), "destination", input.getName());
            }
        });

        if (n != null) {
            Iterables.addAll(result, n);
        }

        // get software uris
        Iterable<Map<String, String>> softwares = Iterables.transform(getSoftwares(node, context), new Function<Software, Map<String, String>>() {
            public Map<String, String> apply(org.ow2.petals.cloud.manager.api.deployment.Software input) {
                return ImmutableMap.of("source", input.getSource(), "destination", input.getName());
            }
        });
        if (softwares != null) {
            Iterables.addAll(result, softwares);
        }
        return result;
    }

    /**
     * Get all the software which can be downloaded from URLs ie type is URL and source starts with http and name is used in node.
     *
     * @param node
     * @param context
     * @return
     */
    protected List<Software> getSoftwares(final Node node, final Context context) {
        checkNotNull(node);

        if (node.getSoftwares() == null || context.getDescriptor() == null || context.getDescriptor().getSoftwares() == null) {
            return Lists.newArrayList();
        }

        return Lists.newArrayList(Iterables.filter(context.getDescriptor().getSoftwares(), new Predicate<Software>() {
            public boolean apply(org.ow2.petals.cloud.manager.api.deployment.Software input) {
                return input.getType() != null && input.getType().equals(Constants.URL_TYPE)
                        && input.getSource() != null && input.getSource().startsWith("http://")
                        && node.getSoftwares() != null && node.getSoftwares().contains(input.getName());
            }
        }));
    }
}
