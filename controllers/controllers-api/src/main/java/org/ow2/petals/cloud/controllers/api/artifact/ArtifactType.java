package org.ow2.petals.cloud.controllers.api.artifact;

/**
 * @author Christophe Hamerling - chamerling@linagora.com
 */
public class ArtifactType {

    public String name;

    public String version;

    public ArtifactType(String name, String version) {
        this.name = name;
        this.version = version;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
