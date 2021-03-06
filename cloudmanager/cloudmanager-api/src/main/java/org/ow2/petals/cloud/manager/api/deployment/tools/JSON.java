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
package org.ow2.petals.cloud.manager.api.deployment.tools;

import com.google.gson.Gson;
import org.ow2.petals.cloud.manager.api.deployment.Deployment;

import java.io.Reader;
import java.io.Writer;

/**
 * Read deployments described in JSON
 *
 * @author Christophe Hamerling - chamerling@linagora.com
 */
public class JSON {

    /**
     *
     * @param reader
     * @return
     */
    public static Deployment read(Reader reader) {
        Gson gson = new Gson();
        Deployment deployment = gson.fromJson(reader, Deployment.class);
        return deployment;
    }

    /**
     *
     * @param descriptor
     * @param writer
     */
    public static void write(Deployment descriptor, Writer writer) {
        Gson gson = new Gson();
        gson.toJson(descriptor, writer);
    }
}
