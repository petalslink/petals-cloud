package org.ow2.petals.cloud.controllers.core.artifact;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.common.collect.Sets;
import org.apache.commons.io.FileUtils;
import org.ow2.petals.cloud.controllers.api.artifact.ArtifactRepository;
import org.ow2.petals.cloud.controllers.api.ControllerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;
import java.util.Set;
import java.util.UUID;

/**
 * File implementation of artifact repository.
 * Artifacts are stored as ZIP files in a flat way. File name is the ID used to retrieve them.
 *
 * @author Christophe Hamerling - chamerling@linagora.com
 */
public class FileRepository implements ArtifactRepository<FileArtifact> {

    private static final Logger LOG = LoggerFactory.getLogger(FileRepository.class);

    /**
     * Base folder where artifacts are stored. This should have been created...
     */
    private File folder;

    public FileRepository(File folder) {
        this.folder = folder;
    }

    public String put(FileArtifact artifact) throws ControllerException {
        if (artifact == null || artifact.getFile() == null) {
            throw new ControllerException("Can not store a null artifact");
        }

        String uuid = UUID.randomUUID().toString();
        String finalName = uuid + ".zip";
        File finalFile = new File(getFolder(), finalName);

        if (artifact.getFile() != null) {
            try {
                FileUtils.copyFile(artifact.getFile(), finalFile);
            } catch (IOException e) {
                throw new ControllerException(e);
            }
        }

        // TODO : Store information in a repository file
        Properties props = new Properties();
        props.putAll(artifact.getProperties());
        if (artifact.getUrl() != null)
            props.put("artifact.url", artifact.getUrl().toString());
        if (artifact.getDate() != null)
            props.put("artifact.date", artifact.getDate().toString());
        if (artifact.getType() != null) {
            props.put("artifact.type.name", artifact.getType().name);
            props.put("artifact.type.version", artifact.getType().version);
        }

        String propName = uuid + ".properties";
        File propFile = new File(getFolder(), propName);

        try {
            props.store(new FileOutputStream(propFile), "Stored on " + new Date());
        } catch (IOException e) {
            LOG.warn(e.getMessage());
        }

        return uuid;
    }

    public FileArtifact get(String id) throws ControllerException {
        if (id == null) {
            throw new ControllerException("Can not get artifact from null ID");
        }

        File file = new File(getFolder(), id + ".zip");
        if (!file.exists()) {
            throw new ControllerException("Can not find artifact from ID + " + id);
        }

        FileArtifact result = new FileArtifact(file);
        Properties props = new Properties();
        File propFile = new File(getFolder(), id + ".properties");
        if(propFile.exists() && !propFile.isDirectory()) {
            try {
                props.load(new FileInputStream(propFile));
                result.setProperties(props);
                // TODO : Fill artifact
            } catch (IOException e) {
                LOG.debug("Can not load properties", e);
            }
        }
        return result;
    }

    public void delete(String id) throws ControllerException {
        if (id == null) {
            throw new ControllerException("Can not delete artifact from null ID");
        }

        // try to delete quietly
        try {
            FileUtils.forceDelete(new File(getFolder(), id + ".zip"));
        } catch (IOException e) {
        }
        try {
            FileUtils.forceDelete(new File(getFolder(), id + ".properties"));
        } catch (IOException e) {
        }
    }

    public Set<String> list() throws ControllerException {
        return Sets.newHashSet(Collections2.transform(FileUtils.listFiles(getFolder(), new String[]{"zip"}, false), new Function() {
            public String apply(java.lang.Object input) {
                System.out.println(input.toString());
                return input.toString();
            }
        }));
    }

    protected File getFolder() throws ControllerException {
        if (folder == null) {
            throw new ControllerException("Can not get null folder");
        }

        createFolderIfNeeded();
        return folder;
    }

    protected void createFolderIfNeeded() throws ControllerException {
        if (folder == null) {
            throw new ControllerException("Can not create a folder from null path...");
        }

        if (folder.exists() && folder.isDirectory()) {
            return;
        }

        if (!folder.exists()) {
            folder.mkdirs();
        }
    }
}
