/****************************************************************************
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
 *****************************************************************************/
package org.ow2.petals.cloud.service.soap;

import org.ow2.petals.cloud.service.api.ServiceCloudException;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Manage Web services deployment in the service cloud.
 * The service cloud runtime will manage the best place to bind services to, etc...
 *
 * @author Christophe Hamerling - chamerling@linagora.com
 */
@Path(Constants.SOAPSUPPORT_PATH)
public interface WSDLSupport {

    /**
     * Bind a webservice from its WSDL URL to the service cloud.
     *
     * @param wsdl
     * @return
     * @throws org.ow2.petals.cloud.service.api.ServiceCloudException
     */
    @Path("/bind")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    Response bind(String wsdl) throws ServiceCloudException;

    /**
     * Get the list of bound services
     *
     * @return a list of bound service (WSDL URLs)
     * @throws ServiceCloudException
     */
    @Path("/bind")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    Response bound() throws ServiceCloudException;

}
