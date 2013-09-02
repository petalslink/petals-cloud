package org.ow2.petals.cloud.service.api;

/**
 * ServiceCloud runtime
 *
 * @author Christophe Hamerling - chamerling@linagora.com
 */
public interface ServiceCloud {

    void start() throws ServiceCloudException;

    void stop() throws ServiceCloudException;
}
