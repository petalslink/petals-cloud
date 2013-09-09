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
package org.ow2.petals.cloud.manager.listener.http;

import com.google.gson.Gson;
import com.ning.http.client.AsyncCompletionHandler;
import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.Response;
import org.ow2.petals.cloud.manager.api.deployment.Node;
import org.ow2.petals.cloud.manager.api.listeners.DeploymentListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * HTTP Deployment listener: Sends JSON events to an external HTTP listener using HTTP POST.
 *
 * @author Christophe Hamerling - chamerling@linagora.com
 */
public class HttpDeploymentListener implements DeploymentListener {

    private static Logger logger = LoggerFactory.getLogger(HttpDeploymentListener.class);

    /**
     * Where to HTTP Post JSON payloads (http://localhost:3000/events).
     */
    private String endpoint;

    public void on(String id, Node node, String action, String step, String pattern, Object... args) {
        String message = "";
        if (pattern != null) {
            message = String.format(pattern, args);
        }
        this.post(endpoint, new Event(id, action, step, message));
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    protected void post(String endpoint, final Event e) {
        Gson gson = new Gson();
        AsyncHttpClient client = new AsyncHttpClient();
        try {
            client.preparePost(endpoint)
                    .setHeader("Content-Type","application/json")
                    .setBody(gson.toJson(e)).execute(new AsyncCompletionHandler<Response>() {

                @Override
                public Response onCompleted(Response response) throws Exception {
                    if(logger.isDebugEnabled())
                        logger.debug("Event sent to HTTP listener : " + e);

                    return response;
                }

                @Override
                public void onThrowable(Throwable t) {
                    logger.error("Error while sending Event to listener", t);
                }
            });
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    class Event {

        public String id;

        public String action;

        public String step;

        public String message;

        public Event(String id, String action, String step, String message) {
            this.id = id;
            this.action = action;
            this.step = step;
            this.message = message;
        }

        @Override
        public String toString() {
            return "Event{" +
                    "id='" + id + '\'' +
                    ", action='" + action + '\'' +
                    ", step='" + step + '\'' +
                    ", message='" + message + '\'' +
                    '}';
        }
    }
}
