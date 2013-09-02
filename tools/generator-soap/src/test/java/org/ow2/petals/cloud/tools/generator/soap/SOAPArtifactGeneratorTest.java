package org.ow2.petals.cloud.tools.generator.soap;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.File;
import java.net.URL;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.fail;

/**
 * @author Christophe Hamerling - chamerling@linagora.com
 */
@RunWith(JUnit4.class)
public class SOAPArtifactGeneratorTest {

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Test
    public void generateValidWSDL() {

        try {
            URL u = SOAPArtifactGeneratorTest.class.getResource("/Simple.wsdl");
            File folder = temporaryFolder.newFolder();
            SOAPArtifactGenerator generator = new SOAPArtifactGenerator(folder);

            File file = generator.generateFile(u.toString());

            System.out.println(file.getAbsolutePath());
            System.out.println(file.exists());

            assertTrue(file.exists());
            assertTrue(file.getName().endsWith(".zip"));

            // TODO : Check content

        } catch(Exception e) {
            fail(e.getMessage());
        }
    }
}
