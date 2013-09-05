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
package org.ow2.petals.cloud.manager.api.deployment.utils;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import org.ow2.petals.cloud.manager.api.deployment.Constants;
import org.ow2.petals.cloud.manager.api.deployment.Node;
import org.ow2.petals.cloud.manager.api.deployment.Property;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Christophe Hamerling - chamerling@linagora.com
 */
public class NodeHelper {

    private NodeHelper() {
    }

    /**
     * Get the property with the given name, or null if not found
     *
     * @param node
     * @param name
     * @return
     */
    public static final Property getProperty(Node node, final String name) {
        return getProperty(node.getProperties(), name);
    }

    /**
     * Get a property from a set of properties or null if not found
     *
     * @param properties
     * @param name
     * @return
     */
    public static final Property getProperty(List<Property> properties, final String name) {
        return Iterables.tryFind(properties, new Predicate<Property>() {
            public boolean apply(org.ow2.petals.cloud.manager.api.deployment.Property property) {
                return property != null && property.getName() != null && property.getName().equals(name);
            }
        }).orNull();
    }

    /**
     * Set a priority to the node. This can be used at deployment time to classify nodes.
     *
     * @param node
     * @param priority
     */
    public static final void setPriority(Node node, int priority) {
        checkNotNull(node);
        node.getProperties().add(PropertyHelper.getPriorityProperty(priority));
    }

    /**
     * Create a property from values and set it to node
     *
     * @param node
     * @param name
     * @param type
     * @param value
     */
    public static final void setProperty(Node node, String name, String type, String value) {
        checkNotNull(node);
        node.getProperties().add(PropertyHelper.get(name, type, value));
    }

    /**
     * Return the node priority value if found, else -1
     *
     * @param node
     * @return
     */
    public static int getPriority(Node node) {
        checkNotNull(node);
        Property result = getProperty(node, Constants.PRIORITY);
        if(result == null) {
            return -1;
        }
        if (result.getValue() != null) {
            try {
            return Integer.parseInt(result.getValue());
            } catch (NumberFormatException e){
                return -1;
            }
        }
        return -1;
    }
}
