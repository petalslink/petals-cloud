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
package org.ow2.petals.cloud.manager.mongo.listener;

import com.mongodb.Mongo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.UUID;

/**
 * @author Christophe Hamerling - chamerling@linagora.com
 */
@RunWith(JUnit4.class)
public class MongoDeploymentListenerTest {

    @Test
    public void testOn() throws Exception {

        // TODO : OSGI test!
        Mongo mongo = new Mongo("127.0.0.1");
        MongoDbFactory factory = new org.springframework.data.mongodb.core.SimpleMongoDbFactory(mongo, "petals-cloud-test");
        MongoTemplate template = new MongoTemplate(factory);
        MongoDeploymentListener listener = new MongoDeploymentListener(template);

        String id = UUID.randomUUID().toString();
        listener.on(id, null, "testaction", "teststep", "This is a %s", "test");

        // use the mongo api to get serach the event!
        System.out.println(listener.get(id));
    }
}
