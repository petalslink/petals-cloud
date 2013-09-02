package org.ow2.petals.cloud.controllers.api.artifact;

import org.ow2.petals.cloud.controllers.api.ControllerException;

import java.util.Set;

/**
 * CRUD-like artifact repository
 *
 * @author Christophe Hamerling - chamerling@linagora.com
 */
public interface ArtifactRepository<A extends Artifact> {

    /**
     * Save an artifact
     *
     * @param artifact
     * @return an unique artifact ID. We should be able to retrieve the artifact based on this ID.
     */
    String put(A artifact) throws ControllerException;

    /**
     * Get all artifacts IDs
     *
     * @return
     */
    Set<String> list() throws ControllerException;

    /**
     * Get an artifact from its ID
     *
     * @param id
     * @return
     */
    A get(String id) throws ControllerException;

    /**
     * Delete an artifact from its ID
     *
     * @param id
     */
    void delete(String id) throws ControllerException;
}
