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
package org.ow2.petals.cloud.manager.core.listeners;

import com.google.gson.Gson;
import com.ning.http.client.AsyncCompletionHandler;
import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @author Christophe Hamerling - chamerling@linagora.com
 */
public abstract class AbstractHttpNotifier {

    private static Logger logger = LoggerFactory.getLogger(AbstractHttpNotifier.class);

    protected void post(String endpoint, final Event e) {
        Gson gson = new Gson();
        AsyncHttpClient client = new AsyncHttpClient();
        try {
            client.preparePost(endpoint + "/events")
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
