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
package org.ow2.petals.cloud.manager.core.puppet;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.MustacheFactory;
import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import org.ow2.petals.cloud.manager.api.CloudManagerException;
import org.ow2.petals.cloud.manager.api.actions.Context;
import org.ow2.petals.cloud.manager.api.deployment.Node;

import java.io.*;
import java.net.URL;

/**
 * @author Christophe Hamerling - chamerling@linagora.com
 */
public abstract class ClasspathScriptBuilder implements ScriptBuilder {

    public String build(Node node, Context context) throws CloudManagerException {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            Writer writer = new OutputStreamWriter(outputStream);
            URL resource = Resources.getResource(DownloadFilesScriptBuilder.class, getTemplate());
            String content = Resources.toString(resource, Charsets.UTF_8);
            MustacheFactory factory = new DefaultMustacheFactory();
            factory.compile(new StringReader(content), resource.toString()).execute(writer, getScope(node, context));
            writer.close();
            return outputStream.toString();
        } catch (IOException e) {
            throw new CloudManagerException("Can not load resource", e);
        } finally {

        }
    }

    /**
     *
     * @return
     */
    protected abstract Object getScope(Node node, Context context);

    /**
     *
     * @return
     */
    protected abstract String getTemplate();
}