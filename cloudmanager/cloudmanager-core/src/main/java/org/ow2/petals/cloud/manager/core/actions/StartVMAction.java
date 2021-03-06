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
package org.ow2.petals.cloud.manager.core.actions;

import org.ow2.petals.cloud.manager.api.CloudManagerException;
import org.ow2.petals.cloud.manager.api.actions.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Start a VM from its ID
 *
 * @author Christophe Hamerling - chamerling@linagora.com
 */
public class StartVMAction extends MonitoredAction {

    private static Logger logger = LoggerFactory.getLogger(StartVMAction.class);

    public StartVMAction() {
        super();
    }

    @Override
    protected String getName() {
        return StartVMAction.class.getName();
    }

    @Override
    protected void doExecute(Context context) throws CloudManagerException {
        throw new CloudManagerException("Not implemented");
    }
}
