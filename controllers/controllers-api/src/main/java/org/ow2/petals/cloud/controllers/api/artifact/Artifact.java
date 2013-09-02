package org.ow2.petals.cloud.controllers.api.artifact;

import java.net.URL;
import java.util.Date;
import java.util.Properties;

/**
 * A Petals artifact
 *
 * @author Christophe Hamerling - chamerling@linagora.com
 */
public class Artifact {

    private ArtifactType type;

    private Date date;

    /**
     * Artifact must be downloadable at a given URL or at a local one...
     */
    private URL url;

    public Artifact() {
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public ArtifactType getType() {
        return type;
    }

    public void setType(ArtifactType type) {
        this.type = type;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }

    private Properties properties = new Properties();
}
