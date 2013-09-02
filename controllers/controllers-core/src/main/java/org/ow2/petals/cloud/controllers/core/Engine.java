package org.ow2.petals.cloud.controllers.core;

import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.ow2.petals.cloud.controllers.api.ControllerException;
import org.ow2.petals.cloud.controllers.api.artifact.*;
import org.ow2.petals.cloud.controllers.api.runtime.RuntimeSelector;

/**
 * @author Christophe Hamerling - chamerling@linagora.com
 */
public class Engine implements ArtifactController {

    private final BundleContext context;
    private final ArtifactRepository repository;

    public Engine(ArtifactRepository repository, BundleContext context) {
        this.context = context;
        this.repository = repository;
    }

    public ArtifactGenerator getGenerator(ArtifactType type) {
        try {
            ServiceReference[] refs = context.getServiceReferences(ArtifactGenerator.class.getName(), "(protocol=" + type.getName() + ")");
            if (refs != null) {
                return (ArtifactGenerator) context.getService(refs[0]);
            }
        } catch (InvalidSyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArtifactDeployer getDeployer(Artifact artifact) {
        try {
            // TODO : Filter choice...
            ServiceReference[] refs = context.getServiceReferences(ArtifactDeployer.class.getName(), "(protocol=*)");
            if (refs != null) {
                // FIXME : For now, get the first one
                return (ArtifactDeployer) context.getService(refs[0]);
            }
        } catch (InvalidSyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *
     * @param artifact
     */
    public void deploy(Artifact artifact) throws ControllerException {
        if (artifact == null) {
            throw new ControllerException("Artifact can not be null");
        }

        ArtifactGenerator generator = getGenerator(artifact.getType());
        if (generator == null) {
            throw new ControllerException("Can not find generator for %s", artifact.getType());
        }

        // TODO : Create a process/status object to store all steps and their status
        // !!!

        // 1. generate
        Artifact a = generator.generate(artifact.getProperties());

        // 2. deploy
        ArtifactDeployer deployer = getDeployer(a);
        if (deployer == null) {
            // TODO : Notify then store for later use...
            // TODO : We can have bundle/service listeners which will reuse artifact
            // TODO : Store in status!!!
            a.getProperties().setProperty("status", "error");
            a.getProperties().setProperty("error", "!deployer");
        } else {
            org.ow2.petals.cloud.controllers.api.runtime.Runtime runtime = selectRuntime(a);
            if (runtime == null) {
                a.getProperties().setProperty("runtime", "!found");
            } else {
                try {
                    deployer.deploy(a, runtime);
                } catch (ControllerException e) {
                    e.printStackTrace();
                }
            }
        }

        // 3. update status
        // TODO

        // 4. register in storage
        try {
            repository.put(a);
        } catch (ControllerException e) {
            e.printStackTrace();
            // TODO : Status
        }
    }

    /**
     * Select a runtime based on artifact, current topology and rules
     *
     * @param artifact
     * @return
     */
    protected org.ow2.petals.cloud.controllers.api.runtime.Runtime selectRuntime(Artifact artifact) {
        // TODO
        TopologyManager topologyManager = null;
        RuntimeSelector selector = null;
        return selector.select();
    }

}
