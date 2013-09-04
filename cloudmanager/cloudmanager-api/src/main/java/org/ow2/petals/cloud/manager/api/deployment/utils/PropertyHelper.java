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

import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.google.common.collect.Maps;
import org.ow2.petals.cloud.manager.api.deployment.Constants;
import org.ow2.petals.cloud.manager.api.deployment.Property;

import java.util.List;
import java.util.Map;

/**
 * @author Christophe Hamerling - chamerling@linagora.com
 */
public class PropertyHelper {

    private PropertyHelper() {
    }

    public static final Property get(String name, String type, String value) {
        Property p = new Property();
        p.setName(name);
        p.setType(type);
        p.setValue(value);
        return p;
    }

    /**
     *
     * @param level
     * @return
     */
    public static final Property getPriorityProperty(int level) {
        return get(Constants.PRIORITY, "int", level + "");
    }

    /**
     *
     * @param properties
     * @return
     */
    public static Map<String, String> hasMap(List<Property> properties) {
        Map<String, String> result = Maps.newHashMap();
        for(Property p : properties) {
            result.put(p.getName(), p.getValue());
        }
        return result;
    }
}
