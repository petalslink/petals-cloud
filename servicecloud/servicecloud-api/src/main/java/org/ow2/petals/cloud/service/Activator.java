package org.ow2.petals.cloud.service;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Christophe Hamerling - chamerling@linagora.com
 */
public class Activator implements BundleActivator {

    private static Logger LOG = LoggerFactory.getLogger(Activator.class);

    private ServiceCloud serviceCloud;

    public void start(BundleContext context) throws Exception {
        LOG.info("Starting the Service Cloud API");

        serviceCloud = new ServiceCloud(context);
        serviceCloud.start();

        context.registerService(ServiceCloud.class.getName(), serviceCloud, null);
    }

    public void stop(BundleContext context) throws Exception {
        LOG.info("Stopping the Service Cloud API");



    }
}
