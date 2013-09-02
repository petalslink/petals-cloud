package org.ow2.petals.cloud.controllers.core;

import junit.framework.TestCase;
import org.apache.commons.io.FileUtils;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.ow2.petals.cloud.controllers.api.ControllerException;
import org.ow2.petals.cloud.controllers.core.artifact.FileArtifact;
import org.ow2.petals.cloud.controllers.core.artifact.FileRepository;

import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 * @author Christophe Hamerling - chamerling@linagora.com
 */
@RunWith(JUnit4.class)
public class FileRepositoryTest extends TestCase {

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Test
    public void testSaveFile() throws IOException {
        File folder = temporaryFolder.newFolder("testSave");
        FileRepository repo = new FileRepository(folder);

        File file = temporaryFolder.newFile("saveme.zip");
        System.out.println(file.getAbsolutePath());

        FileArtifact artifact = new FileArtifact(file);
        artifact.setDate(new Date());

        String result = null;
        try {
            result = repo.put(artifact);
        } catch (ControllerException e) {
            fail(e.getMessage());
        }

        System.out.println("Output articfact " + result);

        // assert that the output file exists
        File out = new File(folder, result + ".zip");
        assertTrue(out.exists() && out.isFile());
    }

    @Test
    public void testList() throws IOException {
        File folder = temporaryFolder.newFolder("testList");
        File a = new File(folder, "a.zip");
        File b = new File(folder, "b.zip");

        FileUtils.touch(a);
        FileUtils.touch(b);

        FileRepository repo = new FileRepository(folder);
        try {
            assertEquals(2, repo.list().size());
        } catch (ControllerException e) {
            fail(e.getMessage());
        }
    }

    @Test
    @Ignore
    public void thisIsIgnored() {
    }

}
