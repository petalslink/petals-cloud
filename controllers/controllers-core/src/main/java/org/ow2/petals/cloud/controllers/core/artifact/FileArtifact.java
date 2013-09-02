package org.ow2.petals.cloud.controllers.core.artifact;

import org.ow2.petals.cloud.controllers.api.artifact.Artifact;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Local file artifact
 *
 * @author Christophe Hamerling - chamerling@linagora.com
 */
public class FileArtifact extends Artifact {

    private File file;

    public static final FileArtifact build(File file) {
        return new FileArtifact(file);
    }

    public FileArtifact(File file) {
        this.file = file;
    }

    @Override
    public URL getUrl() {
        if (file == null) {
            return null;
        }

        try {
            return file.toURI().toURL();
        } catch (MalformedURLException e) {
        }
        return null;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
}
