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

import org.ow2.petals.cloud.manager.api.ProviderManager;
import org.ow2.petals.cloud.manager.api.actions.Action;
import org.ow2.petals.cloud.manager.api.actions.Context;
import org.ow2.petals.cloud.manager.api.deployment.Deployment;
import org.ow2.petals.cloud.manager.api.deployment.Node;
import org.ow2.petals.cloud.manager.api.deployment.Provider;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Christophe Hamerling - chamerling@linagora.com
 */
public abstract class BaseAction implements Action {

    /**
     *
     */
    protected BaseAction() {
    }

    protected Node getNode(Context context) {
        return checkNotNull(context.getNode());
    }

    protected ProviderManager getProviderManager(Context context) {
        return checkNotNull(context.getProviderManager());
    }

    protected Provider getProvider(Context context) {
        return checkNotNull(context.getProvider());
    }

    protected Deployment getDescriptor(Context context) {
        return checkNotNull(context.getDescriptor());
    }

    /**
     * Notity using the context listener if not null
     *
     * @param context
     * @param node
     * @param action
     * @param step
     * @param pattern
     * @param args
     */
    protected void notity(Context context, Node node, String action, String step, String pattern, Object... args) {
        if (context.getListener() != null) {
            context.getListener().on(context.getId(), node, action, step, pattern, args);
        }
    }
}
