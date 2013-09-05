/**
 *
 * Copyright (c) 2013, Linagora
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA 
 *
 */
package org.ow2.petals.cloud.manager.core.utils;

import com.google.common.base.Charsets;
import com.google.common.base.Predicate;
import com.google.common.base.Throwables;
import com.google.common.collect.Iterables;
import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.common.SecurityUtils;
import net.schmizz.sshj.connection.channel.direct.Session;
import net.schmizz.sshj.transport.TransportException;
import net.schmizz.sshj.transport.verification.HostKeyVerifier;
import net.schmizz.sshj.userauth.UserAuthException;
import net.schmizz.sshj.userauth.keyprovider.OpenSSHKeyFile;
import net.schmizz.sshj.userauth.method.AuthMethod;
import net.schmizz.sshj.xfer.InMemorySourceFile;
import org.apache.commons.io.IOUtils;
import org.ow2.petals.cloud.manager.api.CloudManagerException;
import org.ow2.petals.cloud.manager.api.deployment.Access;
import org.ow2.petals.cloud.manager.api.deployment.Credentials;
import org.ow2.petals.cloud.manager.api.deployment.Node;
import org.ow2.petals.cloud.manager.api.listeners.DeploymentListener;
import org.ow2.petals.cloud.manager.api.listeners.SSHListener;
import org.ow2.petals.cloud.manager.api.utils.NotNullDeploymentListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.security.PublicKey;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Christophe Hamerling - chamerling@linagora.com
 */
public class SSHUtils {

    private static Logger logger = LoggerFactory.getLogger(SSHUtils.class);

    private SSHUtils() {
    }

    public static final int DEFAULT_CONNECT_TIMEOUT = 30 * 1000;

    public static final int DEFAULT_READ_TIMEOUT = 10 * 60 * 1000;

    private enum AcceptAnyHostKeyVerifier implements HostKeyVerifier {
        INSTANCE;
        public boolean verify(String hostname, int port, PublicKey key) {
            String fingerprint = SecurityUtils.getFingerprint(key);
            return true;
        }
    }

    public static SSHClient newClient(String hostname, int port, Access adminAccess) throws CloudManagerException {
        return newClient(hostname, port, adminAccess, DEFAULT_READ_TIMEOUT);
    }

    /**
     * Create a new {@code SSHClient} connected to the remote machine using the
     * AdminAccess credentials as provided
     */
    public static SSHClient newClient(
            String hostname, int port, Access adminAccess, int timeoutInMillis
    ) throws CloudManagerException {
        checkArgument(timeoutInMillis >= 0, "timeoutInMillis should be positive or 0");
        final SSHClient client = new SSHClient();
        client.addHostKeyVerifier(AcceptAnyHostKeyVerifier.INSTANCE);

        if (timeoutInMillis != 0) {
            client.setConnectTimeout(DEFAULT_CONNECT_TIMEOUT);
            client.setTimeout(timeoutInMillis);
        }
        try {
            client.connect(hostname, port);
        } catch (IOException e) {
            throw new CloudManagerException(e);
        }

        if (Credentials.BACIC_TYPE.equals(adminAccess.getCredentials().getType())) {
            try {
                client.authPassword(adminAccess.getCredentials().getUsername(), adminAccess.getCredentials().getPassword());
            } catch (Exception e) {
                throw new CloudManagerException(e);
            }
        } else if (Credentials.KEY_TYPE.equals(adminAccess.getCredentials().getType())) {
            OpenSSHKeyFile key = new OpenSSHKeyFile();
            key.init(adminAccess.getCredentials().getPrivateKey(), adminAccess.getCredentials().getPrivateKey());
            try {
                client.authPublickey(adminAccess.getCredentials().getUsername(), key);
            } catch (Exception e) {
                throw new CloudManagerException(e);
            }
        } else {
            throw new CloudManagerException("Can not find a valid SSH credentials in " + adminAccess.getCredentials());
        }

        return client;
    }

    /**
     * Create a remote file on SSH from a string
     *
     * @param client      ssh client instance
     * @param content     content for the new file
     * @param permissions unix permissions
     * @param destination destination path
     * @throws IOException
     */
    public static void createFile(
            SSHClient client, String content, final int permissions, String destination
    ) throws IOException {
        final byte[] bytes = content.getBytes(Charsets.UTF_8);
        client.newSCPFileTransfer().upload(new InMemorySourceFile() {
            public String getName() {
                return "in-memory";
            }

            public long getLength() {
                return bytes.length;
            }

            public int getPermissions() throws IOException {
                return permissions;
            }

            public InputStream getInputStream() throws IOException {
                return new ByteArrayInputStream(bytes);
            }
        }, destination);
    }

    /**
     * Execute a script on the remote node.
     *
     * @param client the SSH client instance used to run the script
     * @param script the script content
     */
    public static void executeScript(SSHClient client, String script, SSHListener listener) throws CloudManagerException {
        checkNotNull(client);

        Session session = null;
        try {
            session = client.startSession();
        } catch (Exception e) {
            throw new CloudManagerException("Can not start SSH session while trying to execute script", e);
        }

        try {
            session.allocateDefaultPTY();
            Session.Command command = session.exec(script);
            if (listener == null) {
                listener = new SSHListener() {
                    public void onMessage(String line) {
                        logger.info(line);
                    }

                    public void onError(String error) {
                        logger.error(error);
                    }
                };
            }
            SSHUtils.listen(command, listener);
            command.join();
            final Integer exitStatus = command.getExitStatus();
            session.close();
        } catch (Exception e) {

        } finally {
            try {
                session.close();
            } catch (Exception e) {
                e.printStackTrace();
                // NOP
            }
        }
    }

    /**
     * Listen to a SSH command results
     *
     * @param command
     * @param listener
     */
    public static void listen(final Session.Command command, final SSHListener listener) {
        checkNotNull(command);

        // launch new threads to listen to command streams...
        Thread in = new Thread() {
            @Override
            public void run() {
                BufferedReader reader = new BufferedReader(new InputStreamReader(command.getInputStream()));
                try {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        if (!line.isEmpty()) {
                            listener.onMessage(line);
                        }
                    }
                } catch (IOException e) {
                    throw Throwables.propagate(e);
                }
            }
        };
        in.start();

        Thread error = new Thread() {
            @Override
            public void run() {
                BufferedReader reader = new BufferedReader(new InputStreamReader(command.getErrorStream()));
                try {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        if (!line.isEmpty()) {
                            listener.onError(line);
                        }
                    }
                } catch (IOException e) {
                    throw Throwables.propagate(e);
                }
            }
        };
        error.start();
    }

    /**
     * Get client from Node
     *
     * @param node
     * @return
     */
    public static SSHClient getClient(Node node) throws CloudManagerException {
        String hostname = Iterables.tryFind(node.getPublicIpAddress(), new Predicate<String>() {
            public boolean apply(String input) {
                return input != null;
            }
        }).or(Iterables.tryFind(node.getPrivateIpAddress(), new Predicate<String>() {
            public boolean apply(String input) {
                return input != null;
            }
        })).orNull();
        checkNotNull(hostname, "Can not access to a node using a null IP...");

        // assume that we are using SSH by default
        int port = node.getAccess().getPort();
        if (port <= 0) {
            port = Access.DEFAULT_SSH;
        }

        return SSHUtils.newClient(hostname, port, node.getAccess());
    }


    /**
     * Try to connect to the remote node using defined SSH port
     * TODO
     * @param node
     * @return
     */
    public static boolean isReachable(Node node) {
        return false;
    }
}
