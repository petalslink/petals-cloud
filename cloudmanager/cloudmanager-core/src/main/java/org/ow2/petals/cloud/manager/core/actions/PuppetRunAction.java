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

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import org.ow2.petals.cloud.manager.api.CloudManagerException;
import org.ow2.petals.cloud.manager.api.actions.Context;
import org.ow2.petals.cloud.manager.api.deployment.Constants;
import org.ow2.petals.cloud.manager.api.deployment.Node;
import org.ow2.petals.cloud.manager.api.deployment.Property;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Run a puppet on the remote node. This assumes that the puppet file is already deployed on the node...
 * The puppet script of defined at the node level, or at the global descriptor one (ie we want to dpeloy the same on a set of nodes...)
 *
 * In all the cases, the process to find the script is as:
 *
 * 1. Look at the node properties and try to find a property with name 'puppet-script'
 * 2. If not found, try to get property from the deployment descriptor.
 * 3. Once retrieved, look at the property type and generate the puppet script from node and deployment properties.
 *
 * @author Christophe Hamerling - chamerling@linagora.com
 */
public class PuppetRunAction extends RunRemoteScriptAction {

    private static Logger logger = LoggerFactory.getLogger(PuppetRunAction.class);

    public PuppetRunAction() {
    }

    /**
     * Get the puppet script content. ie generate it from node and context properties.
     * First look at the node, then if not found, search at the context level.
     *
     * @param node
     * @param context
     * @return
     * @throws CloudManagerException
     */
    @Override
    protected String getScriptContent(Node node, Context context) throws CloudManagerException {
        // get the script from the node properties
        Property script = Iterables.tryFind(node.getProperties(), new Predicate<Property>() {
            public boolean apply(org.ow2.petals.cloud.manager.api.deployment.Property input) {
                return input.getName() != null && input.getName().equalsIgnoreCase(Constants.PUPPET_SCRIPT);
            }
            // search at the descriptor level if not found in node...
        }).or(Iterables.tryFind(context.getDescriptor().getProperties(), new Predicate<Property>() {
            public boolean apply(org.ow2.petals.cloud.manager.api.deployment.Property input) {
                return input.getName() != null && input.getName().equalsIgnoreCase(Constants.PUPPET_SCRIPT);
            }
        })).orNull();

        if (script == null) {
            throw new CloudManagerException("Can not find puppet script content");
        }

        // get the script content or generate it...
        // First search for the type of script, ie generate, provided as content, in the classpath, as URL, ...

        checkNotNull(script.getType());
        // script type property can have several values which will define where to get the script template :

        // TODO : Later. For now, let's assume that the property value is the pattern name to retrieve from the classpath...
        // TODO : We can also get them from a pattern registry...
        String name = script.getValue();
        String path = "/org/ow2/petals/cloud/manager/puppet/" + name + ".template";

        return null;
    }

    @Override
    protected String getCommandToRun(Node node, Context context) throws CloudManagerException {
        return "while ! which puppet &> /dev/null ; " +
                "do echo 'Puppet command not found. Waiting for userdata.sh script to finish (10s)' " +
                "&& sleep 10; " +
                "done " +
                "&& sudo puppet apply --detailed-exitcodes --debug --verbose " + getRemoteScriptPath(node, context);
    }

    /**
     * Search the puppet script property like {name:puppet-destination, type:file, value:XXX}. If not found, generate one.
     *
     * @param node
     * @param context
     * @return
     * @throws CloudManagerException
     */
    @Override
    protected String getRemoteScriptPath(Node node, Context context) throws CloudManagerException {
        return Iterables.tryFind(node.getProperties(), new Predicate<Property>() {
            public boolean apply(org.ow2.petals.cloud.manager.api.deployment.Property input) {
                return input.getType().equalsIgnoreCase(Constants.FILE_TYPE) && input.getValue().endsWith(".pp") && input.getName().equalsIgnoreCase(Constants.PUPPET_DESTINATION);
            }
        }).or(getDefaultPuppetDestination()).getValue();
    }

    /**
     * Generates a default property for the puppet destination
     *
     * @return
     */
    protected Property getDefaultPuppetDestination() {
        Property p = new Property();
        p.setName(Constants.PUPPET_SCRIPT);
        p.setType(Constants.FILE_TYPE);
        p.setValue("/tmp/puppet_" + UUID.randomUUID().toString() + ".pp");
        return p;
    }
}
