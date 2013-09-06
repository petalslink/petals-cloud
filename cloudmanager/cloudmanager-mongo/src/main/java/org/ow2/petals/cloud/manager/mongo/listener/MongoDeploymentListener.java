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

import org.ow2.petals.cloud.manager.api.deployment.Node;
import org.ow2.petals.cloud.manager.api.listeners.DeploymentListener;
import org.ow2.petals.cloud.manager.mongo.document.EventDocument;
import org.springframework.data.mongodb.core.MongoTemplate;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

/**
 * @author Christophe Hamerling - chamerling@linagora.com
 */
public class MongoDeploymentListener implements DeploymentListener {

    private MongoTemplate mongoTemplate;

    public MongoDeploymentListener(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public void on(String id, Node node, String action, String step, String pattern, Object... args) {
        String message = "";
        if (pattern != null) {
            message = String.format(pattern, args);
        }
        EventDocument document = new EventDocument(id, node, action, step, message);
        mongoTemplate.save(document);
    }

    protected EventDocument get(String id) {
        return mongoTemplate.findOne(query(where("_id").is(id)), EventDocument.class);
    }
}
