package org.ow2.petals.cloud.service;

import com.google.common.collect.Maps;
import org.ow2.petals.cloud.service.api.Resource;
import org.ow2.petals.cloud.service.api.ServiceCloudException;
import org.ow2.petals.cloud.service.api.service.ResourceRegistry;

import java.util.Map;
import java.util.UUID;

/**
 * @author Christophe Hamerling - chamerling@linagora.com
 */
public class InMemoryResourceRegistry implements ResourceRegistry {

    Map<String, Resource> map = Maps.newConcurrentMap();

    public InMemoryResourceRegistry() {
    }

    public String store(Resource resource) throws ServiceCloudException {
        if (resource == null) {
            throw new ServiceCloudException("Null resource");
        }

        String result = UUID.randomUUID().toString();
        resource.setId(result);
        map.put(result, resource);
        return result;
    }

    public Resource get(String id) throws ServiceCloudException {
        if(id == null) {
            throw new ServiceCloudException("Null ID");
        }
        return map.get(id);
    }

    public Resource delete(String id) throws ServiceCloudException {
        return map.remove(id);
    }
}
