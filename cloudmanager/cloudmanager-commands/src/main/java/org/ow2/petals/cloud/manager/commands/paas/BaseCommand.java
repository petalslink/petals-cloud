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
package org.ow2.petals.cloud.manager.commands.paas;

import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import org.apache.karaf.shell.console.OsgiCommandSupport;
import org.ow2.petals.cloud.manager.api.CloudManager;
import org.ow2.petals.cloud.manager.api.deployment.tools.DeploymentProvider;

import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Christophe Hamerling - chamerling@linagora.com
 */
public abstract class BaseCommand extends OsgiCommandSupport {

    protected CloudManager cloudManager;

    protected List<DeploymentProvider> supportedRuntimes;

    protected BaseCommand(CloudManager cloudManager, List<DeploymentProvider> supportedRuntimes) {
        this.cloudManager = checkNotNull(cloudManager, "Cloud Manager is Null");
        this.supportedRuntimes = checkNotNull(supportedRuntimes, "Supported runtimes is null");
    }

    /**
     * Get a runtime support from its type
     *
     * @param type
     * @return
     */
    protected DeploymentProvider getSupport(final String type) {
        checkNotNull(type);
        Optional<DeploymentProvider> support = Iterables.tryFind(supportedRuntimes, new Predicate<DeploymentProvider>() {
            public boolean apply(DeploymentProvider input) {
                return type.equalsIgnoreCase(input.getType());
            }
        });
        return support.orNull();
    }
}
