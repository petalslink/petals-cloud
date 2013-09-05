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
import com.google.common.collect.Lists;
import net.schmizz.sshj.SSHClient;
import org.apache.commons.io.FileUtils;
import org.ow2.petals.cloud.manager.api.CloudManagerException;
import org.ow2.petals.cloud.manager.api.actions.Context;
import org.ow2.petals.cloud.manager.api.deployment.Constants;
import org.ow2.petals.cloud.manager.api.deployment.Deployment;
import org.ow2.petals.cloud.manager.api.deployment.Node;
import org.ow2.petals.cloud.manager.api.deployment.Property;
import org.ow2.petals.cloud.manager.core.utils.SSHUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Christophe Hamerling - chamerling@linagora.com
 */
public class CopyFilesAction extends MonitoredAction {

    private static Logger logger = LoggerFactory.getLogger(CopyFilesAction.class);

    @Override
    protected String getName() {
        return CopyFilesAction.class.getName();
    }

    @Override
    protected void doExecute(Context context) throws CloudManagerException {
        Node node = getNode(context);
        Deployment deployment = getDescriptor(context);
        checkNotNull(node.getAccess());

        SSHClient client = SSHUtils.getClient(node);

        // get files to deploy for the current node
        // files are defined in properties of a node
        // or in the global deployment object with the 'file' type
        List<Property> files = getFiles(deployment, node);
        for(Property file : files) {
            logger.info("SCP file {} to {}", file.getName(), file.getValue());

            // get the content of the file from the local file system...
            try {
                String content = FileUtils.readFileToString(FileUtils.getFile(file.getName()));
                if (logger.isDebugEnabled()) {
                    logger.debug("File {} content {}", file.getName(), content);
                }
                SSHUtils.createFile(client, content, 0600, file.getValue());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Get all the properties which have a file type.
     *
     * @param deployment
     * @param node
     * @return
     */
    protected List<Property> getFiles(Deployment deployment, Node node) {
        // get files from node
        Iterable<Property> files = Iterables.filter(node.getProperties(), new Predicate<Property>() {
            public boolean apply(org.ow2.petals.cloud.manager.api.deployment.Property property) {
                return property.getType() != null && Constants.FILE_TYPE.equalsIgnoreCase(property.getType().trim());
            }
        });

        // get files from deployment (if any defined)
        Iterable<Property> globals = Iterables.filter(deployment.getProperties(), new Predicate<Property>() {
            public boolean apply(org.ow2.petals.cloud.manager.api.deployment.Property property) {
                return property.getType() != null && Constants.FILE_TYPE.equalsIgnoreCase(property.getType().trim());
            }
        });

        List<Property> result = Lists.newArrayList();
        if (files != null) {
            Iterables.addAll(result, files);
        }
        if (globals != null) {
            Iterables.addAll(result, globals);
        }
        return result;
    }
}
