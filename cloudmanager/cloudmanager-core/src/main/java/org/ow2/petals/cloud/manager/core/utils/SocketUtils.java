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

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * @author Christophe Hamerling - chamerling@linagora.com
 */
public class SocketUtils {

    public static final int TIMEOUT_IN_MILLISECONDS = 1000;

    private SocketUtils() {
    }

    public static boolean isPortOpen(String host, int port) {
        InetSocketAddress socketAddress = new InetSocketAddress(host, port);
        Socket socket = null;
        try {
            socket = new Socket();
            socket.setReuseAddress(false);
            socket.setSoLinger(false, 1);
            socket.setSoTimeout(TIMEOUT_IN_MILLISECONDS);
            socket.connect(socketAddress, TIMEOUT_IN_MILLISECONDS);

        } catch (IOException e) {
            return false;
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException ioe) {
                }
            }
        }
        return true;
    }
}
