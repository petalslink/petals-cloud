package org.ow2.petals.cloud.service;

import org.osgi.framework.BundleContext;
import org.ow2.petals.cloud.service.api.ServiceCloudException;
import org.ow2.petals.cloud.service.api.service.ResourceRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Service Cloud Manager implementation
 *
 * @author Christophe Hamerling - chamerling@linagora.com
 */
public class ServiceCloud implements org.ow2.petals.cloud.service.api.ServiceCloud {

    private static Logger LOG = LoggerFactory.getLogger(ServiceCloud.class);

    private BundleContext context;

    private SupportTracker tracker;

    private ResourceRegistry resourceRegistry;

    public ServiceCloud(BundleContext context) {
        this.context = context;
    }

    /**
     * Start all the service related to service cloud management.
     * TODO : Also track for new services to come up and down...
     *
     * @throws ServiceCloudException
     */
    public void start() throws ServiceCloudException {
        LOG.info("Starting the service cloud");
        tracker = new SupportTracker(this.context);
        tracker.start();

        // TODO

    }

    public void stop() throws ServiceCloudException {
        LOG.info("Stopping the service cloud");
        if (tracker != null)
            tracker.stop();
    }

    public void setResourceRegistry(ResourceRegistry resourceRegistry) {
        this.resourceRegistry = resourceRegistry;
    }
}
