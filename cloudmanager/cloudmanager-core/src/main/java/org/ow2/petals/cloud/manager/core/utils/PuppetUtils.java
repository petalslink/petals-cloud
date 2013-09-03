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
package org.ow2.petals.cloud.manager.core.utils;

import org.ow2.petals.cloud.manager.api.deployment.Software;

import java.util.Set;

/**
 * Generate puppet files utilities
 *
 * @author Christophe Hamerling - chamerling@linagora.com
 */
public class PuppetUtils {

    private PuppetUtils() {
    }

    /**
     * Generate puppet script which can be used to install input packages.
     * We just get compliant software inputs.
     *
     * @param packages
     * @return
     */
    public static String installPackages(Set<Software> packages) {
        return null;
    }

    /**
     * Generate a puppet script to add repositories
     *
     * @param packages
     * @return
     */
    public static String installRepositories(Set<Software> packages) {
        return null;
    }

    /**
     * Generate puppet script which can be used to download files from http locations.
     * We just get compliant software inputs.
     *
     * @param urls
     * @return
     */
    public static String downloadFiles(Set<Software> urls) {
        return null;
    }

}
