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

import com.google.common.collect.Lists;
import org.ow2.petals.cloud.service.api.Resource;
import org.ow2.petals.cloud.service.api.ResultBuilder;
import org.ow2.petals.cloud.service.api.ServiceCloudException;

import javax.ws.rs.core.Response;
import java.util.List;

/**
 * @author Christophe Hamerling - chamerling@linagora.com
 */
public class WSDLSupportImpl implements WSDLSupport {

    /**
     *
     */
    public WSDLSupportImpl() {
    }

    public Response bind(String wsdl) throws ServiceCloudException {
        // TODO : get the core support service...

        return Response.ok(new ResultBuilder().message("Not implemented").type("error").createResult()).build();
    }

    public Response bound() throws ServiceCloudException {
        // TODO : Get resources references in the registry
        List<Resource> resources = Lists.newArrayList();
        Resource r = new Resource();
        r.setName("http://foo/bar/baz");
        r.setId("1234567890");
        resources.add(r);
        return Response.ok(resources).build();
    }
}
