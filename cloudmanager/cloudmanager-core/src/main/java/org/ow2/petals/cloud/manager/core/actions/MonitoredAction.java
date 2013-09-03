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
import org.ow2.petals.cloud.manager.api.listeners.Monitor;
import org.ow2.petals.cloud.manager.core.listeners.SysoutMonitor;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Christophe Hamerling - chamerling@linagora.com
 */
public abstract class MonitoredAction extends BaseAction {

    private final Monitor monitor;

    protected MonitoredAction() {
        this(new SysoutMonitor());
    }

    public MonitoredAction(Monitor monitor) {
        if (monitor == null) {
            monitor = new SysoutMonitor();
        }
        this.monitor = monitor;
    }

    public void execute(Context context) throws CloudManagerException {
        checkNotNull(context);

        if (monitor == null) {
            doExecute(context);
            return;
        }
        monitor.start(getName());
        long diff = 0L;
        try {
            long start = System.currentTimeMillis();
            doExecute(context);
            diff = System.currentTimeMillis() - start;
        } catch (CloudManagerException e) {
            monitor.error(getName(), e);
        } finally {
            monitor.time(getName(), diff);
            monitor.end(getName());
        }
    }

    /**
     * @return
     */
    protected abstract String getName();

    /**
     *
     * @throws CloudManagerException
     */
    protected abstract void doExecute(Context context) throws CloudManagerException;
}
