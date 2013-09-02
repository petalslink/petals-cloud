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
package org.ow2.petals.cloud.controllers.api.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * The topology resource service
 *
 * @author Christophe Hamerling - chamerling@linagora.com
 */
@Path("/topology")
public interface Topology {

    /**
     * Get the topology of a Petals PaaS as String (Serialized XML).
     *
     * @param id the id of the Petals Virtual Container
     * @return
     */
    @GET
    @Path("/{id}/raw/topology")
    @Produces(MediaType.TEXT_PLAIN)
    Response getRawTopology(@PathParam("id") String id);

    /**
     * Get the topology of a Petals PaaS as JSON
     *
     * @param id the ID of the Petals Virtual Container
     * @return
     */
    @GET
    @Path("/{id}/topology")
    @Produces(MediaType.APPLICATION_JSON)
    Response getTopology(@PathParam("id") String id);
}
