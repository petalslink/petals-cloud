package org.ow2.petals.cloud.controllers.api.artifact;

/**
 * @author Christophe Hamerling - chamerling@linagora.com
 */
public interface ArtifactController {

    /**
     * Get a generator for an artifact type. May return null if no generator has been found...
     *
     * @param type
     * @return the generator or null if not found
     */
    ArtifactGenerator getGenerator(ArtifactType type);

}
