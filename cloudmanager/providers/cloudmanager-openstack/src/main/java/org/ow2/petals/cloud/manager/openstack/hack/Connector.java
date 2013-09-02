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
package org.ow2.petals.cloud.manager.openstack.hack;

import com.woorea.openstack.base.client.*;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * This is a code copy hack from the initial woorea connector. Looks like the JAR is not bundle compliant
 * and that it causes classloader exceptions (at least, the connector is not found when trying to instanciate it...)
 *
 * @author Christophe Hamerling - chamerling@linagora.com
 */
public class Connector implements OpenStackClientConnector {

    protected Client client = OpenStack.CLIENT;
    private Jersey2LoggingFilter logger = new Jersey2LoggingFilter(Logger.getLogger("os"), 10000);

    public <T> OpenStackResponse request(OpenStackRequest<T> request) {
        WebTarget target = client.target(request.endpoint()).path(request.path());

        for(Map.Entry<String, List<Object>> entry : request.queryParams().entrySet()) {
            for (Object o : entry.getValue()) {
                target = target.queryParam(entry.getKey(), o);
            }
        }
        target.register(logger);
        Invocation.Builder invocation = target.request();

        for(Map.Entry<String, List<Object>> h : request.headers().entrySet()) {
            StringBuilder sb = new StringBuilder();
            for(Object v : h.getValue()) {
                sb.append(String.valueOf(v));
            }
            invocation.header(h.getKey(), sb);
        }

        Entity<?> entity = (request.entity() == null) ? null :
                Entity.entity(request.entity().getEntity(), request.entity().getContentType());

        try {
            if (entity != null) {
                return new JaxRs20Response(invocation.method(request.method().name(), entity));
            } else {
                if(HttpMethod.PUT == request.method()) {
                    return new JaxRs20Response(invocation.method(request.method().name(), Entity.entity("", MediaType.APPLICATION_JSON)));
                } else {
                    return new JaxRs20Response(invocation.method(request.method().name()));
                }
            }
        } catch (ClientErrorException e) {
            throw new OpenStackResponseException(e.getResponse()
                    .getStatusInfo().toString(), e.getResponse().getStatus());
        }
    }
}
