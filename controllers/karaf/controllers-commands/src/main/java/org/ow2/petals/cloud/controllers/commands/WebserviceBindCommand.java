/****************************************************************************
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
 *****************************************************************************/
package org.ow2.petals.cloud.controllers.commands;

import com.google.common.annotations.VisibleForTesting;
import org.apache.felix.gogo.commands.Command;
import org.apache.felix.gogo.commands.Option;
import org.apache.karaf.shell.console.OsgiCommandSupport;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Christophe Hamerling - chamerling@linagora.com
 */
@Command(scope = "petals", name = "bind", description = "Web service binder command")
public class WebserviceBindCommand extends OsgiCommandSupport {

    @Option(name = "--wsdl", description = "WSDL URL", required = true)
    protected String wsdl;

    public WebserviceBindCommand() {
    }

    @Override
    protected Object doExecute() throws Exception {
        return String.format("%s is bound", wsdl);
    }

    @VisibleForTesting
    void setWsdl(String wsdl) {
        this.wsdl = checkNotNull(wsdl, "wsdl is null");
    }
}
