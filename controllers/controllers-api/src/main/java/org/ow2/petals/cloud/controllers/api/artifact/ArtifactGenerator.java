package org.ow2.petals.cloud.controllers.api.artifact;

import com.google.common.collect.Range;
import org.ow2.petals.cloud.controllers.api.ControllerException;

import java.util.Properties;

/**
 * Artifact generator
 *
 * @author Christophe Hamerling - chamerling@linagora.com
 */
public interface ArtifactGenerator {

    /**
     * Get the generator name
     *
     * @return
     */
    String getName();

    /**
     * Get the generator version
     *
     * @return
     */
    String getVersion();

    /**
     * One generator may support a range of artifacts ie is able to generate artifact in a defined version range.
     * @return
     */
    Range getSupportedRange();

    /**
     * Generator mode (consumes/provides)
     *
     * @return
     */
    String getMode();

    /**
     * The protocol supported by this generator
     *
     * @return
     */
    String getProtocol();

    /**
     * Generates an artifact based on context
     *
     * @return
     */
    Artifact generate(Properties properties) throws ControllerException;
}
