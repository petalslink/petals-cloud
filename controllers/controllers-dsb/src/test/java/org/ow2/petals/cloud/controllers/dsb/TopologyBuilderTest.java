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
package org.ow2.petals.cloud.controllers.dsb;

import com.google.common.collect.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.ow2.petals.cloud.controllers.api.runtime.PetalsRuntime;
import org.ow2.petals.cloud.controllers.api.runtime.Topology;

import java.util.List;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

/**
 * @author Christophe Hamerling - chamerling@linagora.com
 */
@RunWith(JUnit4.class)
public class TopologyBuilderTest {

    @Test
    public void testCanBuildTopology() {
        List<PetalsRuntime> runtimes = Lists.newArrayList();
        PetalsRuntime runtime = new PetalsRuntime("1", "master.petalslink.org", "description", null, null, PetalsRuntime.MODE.MASTER);
        runtimes.add(runtime);
        runtime = new PetalsRuntime("2", "salve.petalslink.org", "description", null, null, PetalsRuntime.MODE.SLAVE);
        runtimes.add(runtime);
        Topology t = new Topology(runtimes);

        TopologyBuilder builder = new TopologyBuilder();
        String s = builder.serialize(t);
        assertNotNull(s);

        System.out.println(s);
    }

    @Test(expected = NullPointerException.class)
    public void testNullTopology() {
        TopologyBuilder builder = new TopologyBuilder();
        builder.serialize(null);
    }
}
