package org.ow2.petals.cloud.service.api.service;

import org.ow2.petals.cloud.service.api.Resource;
import org.ow2.petals.cloud.service.api.ServiceCloudException;

/**
 * Registry to store service cloud resources
 *
 * @author Christophe Hamerling - chamerling@linagora.com
 */
public interface ResourceRegistry {

    /**
     * Store a resource and get back a resource ID
     *
     * @param resource
     */
    String store(Resource resource) throws ServiceCloudException;

    /**
     * Get a resource from its ID
     *
     * @param id
     * @return
     */
    Resource get(String id) throws ServiceCloudException;

    /**
     * Delete a resource. Send back the resource information on success.
     *
     * @param id
     * @return
     * @throws ServiceCloudException
     */
    Resource delete(String id) throws ServiceCloudException;

}
