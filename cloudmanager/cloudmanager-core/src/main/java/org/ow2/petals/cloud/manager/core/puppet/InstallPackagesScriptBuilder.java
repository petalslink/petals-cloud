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
import com.google.common.collect.Maps;
import org.ow2.petals.cloud.manager.api.actions.Context;
import org.ow2.petals.cloud.manager.api.deployment.Constants;
import org.ow2.petals.cloud.manager.api.deployment.Node;
import org.ow2.petals.cloud.manager.api.deployment.Software;

import java.util.List;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Christophe Hamerling - chamerling@linagora.com
 */
public class InstallPackagesScriptBuilder extends ClasspathScriptBuilder {

    public static String TEMPLATE = "/org/ow2/petals/cloud/manager/puppet/packages.pp";

    @Override
    protected Object getScope(Node node, Context context) {
        return ImmutableMap.of("packages", Iterables.transform(getPackages(node, context), new Function<String, Map<String, String>>() {
            public Map<String, String> apply(String input) {
                return ImmutableMap.of("package", input);
            }
        }));
    }

    /**
     * Get packages to install from the software properties.
     * Node contains softwares which are links to upper level deployment script.
     *
     * @param node
     * @param context
     * @return
     */
    protected List<String> getPackages(final Node node, final Context context) {
        checkNotNull(context.getDescriptor());
        checkNotNull(context.getDescriptor().getSoftwares());

        // have a look to all the software which link to a package type software in the descriptor.
        return Lists.newArrayList(Iterables.filter(node.getSoftwares(), new Predicate<String>() {
            public boolean apply(final String input) {
                return Iterables.tryFind(context.getDescriptor().getSoftwares(), new Predicate<Software>() {
                    public boolean apply(org.ow2.petals.cloud.manager.api.deployment.Software software) {
                        return input.equals(software.getName()) && software.getType().equals(Constants.PACKAGE_TYPE);
                    }
                }).isPresent();
            }
        }));
    }

    @Override
    protected String getTemplate() {
        return TEMPLATE;
    }
}
