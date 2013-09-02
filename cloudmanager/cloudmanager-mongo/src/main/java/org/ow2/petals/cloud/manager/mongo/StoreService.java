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
package org.ow2.petals.cloud.manager.mongo;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import org.ow2.petals.cloud.manager.api.CloudManagerException;
import org.ow2.petals.cloud.manager.api.PaaS;
import org.ow2.petals.cloud.manager.api.services.StorageService;
import org.ow2.petals.cloud.manager.mongo.document.PaaSDocument;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

/**
 * @author Christophe Hamerling - chamerling@linagora.com
 */
public class StoreService implements StorageService {

    private MongoTemplate mongoTemplate;

    private static Logger logger = Logger.getLogger(StoreService.class.getName());

    public StoreService(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public PaaS create(PaaS paas) throws CloudManagerException {
        logger.fine("Register a new PaaS" + paas);
        PaaSDocument result = toDocument(paas);
        mongoTemplate.save(result);
        return result;
    }

    public List<PaaS> get() throws CloudManagerException {
        logger.fine("Get all PaaS");
        List<PaaSDocument> all = mongoTemplate.findAll(PaaSDocument.class);
        if (all != null) {
            return new ArrayList<PaaS>(Collections2.transform(all, new Function<PaaSDocument, PaaS>() {
                public PaaS apply(PaaSDocument input) {
                    return input;
                }
            }));
        }
        return null;
    }

    public PaaS get(String id) throws CloudManagerException {
        logger.fine("Get PaaS where ID=" + id);
        return mongoTemplate.findOne(query(where("_id").is(id)), PaaSDocument.class);
    }

    public void delete(String id) throws CloudManagerException {
        logger.fine("Delete PaaS where ID=" + id);
        mongoTemplate.remove(query(where("_id").is(id)), PaaSDocument.class);
    }

    protected PaaSDocument toDocument(PaaS paas) {
        PaaSDocument doc = new PaaSDocument();
        doc.setCreatedAt(paas.getCreatedAt());
        doc.setDescription(paas.getDescription());
        doc.setName(paas.getName());
        doc.setNodes(paas.getNodes());
        doc.setStatus(paas.getStatus());
        doc.setType(paas.getType());
        return doc;
    }
}
