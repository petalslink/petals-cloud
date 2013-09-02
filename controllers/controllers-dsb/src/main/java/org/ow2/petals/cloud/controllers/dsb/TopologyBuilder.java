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

import org.ow2.petals.cloud.controllers.api.runtime.PetalsRuntime;
import org.ow2.petals.cloud.controllers.api.runtime.Topology;
import org.ow2.petals.cloud.controllers.api.tools.TopologySerializer;
import org.ow2.petals.topology.TopologyException;
import org.ow2.petals.topology.generated.*;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Build a DSB compliant topology
 *
 * @author Christophe Hamerling - chamerling@linagora.com
 */
public class TopologyBuilder implements TopologySerializer {

    private static Marshaller marshaller;

    private static TopologyException marshCreationEx;

    static {

        final InputStream schemaInputStream = TopologyBuilder.class.getResourceAsStream("/"
                + org.ow2.petals.topology.TopologyBuilder.TOPOLOGY_XSD);

        try {
            final SchemaFactory factory = SchemaFactory.newInstance(org.ow2.petals.topology.TopologyBuilder.NAMESPACE_XSD);
            final Source[] schemas = new StreamSource[] { new StreamSource(schemaInputStream) };
            final Schema schema = factory.newSchema(schemas);

            try {
                final JAXBContext jaxbContext = JAXBContext.newInstance(new Class[]{org.ow2.petals.topology.generated.Topology.class});

                try {
                    TopologyBuilder.marshaller = jaxbContext.createMarshaller();
                    TopologyBuilder.marshaller.setSchema(schema);
                    TopologyBuilder.marshaller.setProperty(javax.xml.bind.Marshaller.JAXB_ENCODING, "UTF-8");
                    TopologyBuilder.marshaller.setProperty(javax.xml.bind.Marshaller.JAXB_FORMATTED_OUTPUT, java.lang.Boolean.TRUE);

                } catch (final JAXBException ex) {
                    TopologyBuilder.marshCreationEx = new TopologyException(
                            "Failed to create the JAXB marshaller", ex);
                }
            } catch (JAXBException ex) {
                ex.printStackTrace();
            }

        } catch (final SAXException e) {
            e.printStackTrace();
        }
    }

    public String serialize(Topology topology) {
        checkNotNull(topology, "Topology can not be null");

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectFactory jaxbObjectFactory = new ObjectFactory();
        // create a basic petals topology from the input one
        Domain domain = jaxbObjectFactory.createDomain();
        domain.setDescription("Petals DSB Cloud Domain");
        domain.setName("DSBCloud");
        domain.setMode(DomainMode.DYNAMIC);
        Subdomain subdomain = jaxbObjectFactory.createSubdomain();
        subdomain.setMode(SubdomainMode.MASTER_SLAVE);
        subdomain.setDescription("The Petals DSB Subsdomain");
        subdomain.setName("petalscloud");

        if (topology.getRuntimes() != null && topology.getRuntimes().size() > 0) {
            for(PetalsRuntime runtime : topology.getRuntimes()) {
                Container container = jaxbObjectFactory.createContainer();
                container.setDescription(runtime.getDescription());
                container.setName(runtime.getName());
                container.setHost(runtime.getHost());
                JmxService jmx = jaxbObjectFactory.createJmxService();
                jmx.setRmiPort(7700);
                container.setJmxService(jmx);
                container.setPassword("edelweiss");
                RegistryService rs = jaxbObjectFactory.createRegistryService();
                rs.setPort(7701);
                container.setRegistryService(rs);
                TransportService ts = jaxbObjectFactory.createTransportService();
                ts.setTcpPort(7702);
                container.setTransportService(ts);
                NodeType type = NodeType.MASTER;
                if (runtime.getMode() == PetalsRuntime.MODE.MASTER) {
                    type = NodeType.MASTER;
                } else if (runtime.getMode() == PetalsRuntime.MODE.SLAVE){
                    type = NodeType.SLAVE;
                } else if (runtime.getMode() == PetalsRuntime.MODE.PEER) {
                    type = NodeType.PEER;
                }
                container.setType(type);
                container.setUser("petalscloud");
                WebServiceService ws = jaxbObjectFactory.createWebServiceService();
                ws.setPort(7600);
                ws.setPrefix("/ws");
                container.setWebserviceService(ws);
                subdomain.getContainer().add(container);
            }
        }
        domain.getSubDomain().add(subdomain);
        org.ow2.petals.topology.generated.Topology top = jaxbObjectFactory.createTopology();
        top.setDomain(domain);
        JAXBElement<org.ow2.petals.topology.generated.Topology> topologyElement = jaxbObjectFactory.createTopology(top);
        try {
            TopologyBuilder.marshaller.marshal(topologyElement, bos);
        } catch (JAXBException e) {
            e.printStackTrace();
            // TODO : Handle
        }
        return bos.toString();
    }
}
