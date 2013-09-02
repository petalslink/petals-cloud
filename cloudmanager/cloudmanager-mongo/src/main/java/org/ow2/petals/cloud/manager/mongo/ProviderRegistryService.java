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
import org.ow2.petals.cloud.manager.api.deployment.Provider;
import org.ow2.petals.cloud.manager.mongo.document.ProviderDocument;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

/**
 * @author Christophe Hamerling - chamerling@linagora.com
 */
public class ProviderRegistryService implements org.ow2.petals.cloud.manager.api.services.ProviderRegistryService {

    private MongoTemplate mongoTemplate;

    private static Logger logger = Logger.getLogger(ProviderRegistryService.class.getName());

    public ProviderRegistryService(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public Provider create(Provider provider) throws CloudManagerException {
        logger.fine("Register a new provider" + provider);
        ProviderDocument result = toDocument(provider);
        mongoTemplate.save(result);
        return result;
    }

    public List<Provider> get() throws CloudManagerException {
        logger.fine("Get all PaaS");
        List<ProviderDocument> all = mongoTemplate.findAll(ProviderDocument.class);
        if (all != null) {
            return new ArrayList<Provider>(Collections2.transform(all, new Function<ProviderDocument, Provider>() {
                public Provider apply(ProviderDocument input) {
                    return input;
                }
            }));
        }
        return null;
    }

    public Provider get(String id) throws CloudManagerException {
        logger.fine("Get provider where ID=" + id);
        return mongoTemplate.findOne(query(where("_id").is(id)), ProviderDocument.class);
    }

    public void delete(String id) throws CloudManagerException {
        logger.fine("Delete provider where ID=" + id);
        mongoTemplate.remove(query(where("_id").is(id)), ProviderDocument.class);
    }

    protected ProviderDocument toDocument(Provider provider) {
        ProviderDocument doc = new ProviderDocument();
        doc.setName(provider.getName());
        doc.setType(provider.getType());
        doc.setCredentials(provider.getCredentials());
        doc.setEndpoint(provider.getEndpoint());
        return doc;
    }
}
