package org.ow2.petals.cloud.service.api;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * Basic resource definition
 *
 * @author Christophe Hamerling - chamerling@linagora.com
 */
public class Resource {

    private String id;

    private String name;

    private String type;

    /**
     * Where to get resource in the system
     *
     */
    private String url;

    /**
     * Additional properties for sort of genericity...
     */
    private Map<String, String> properties;

    /**
     *
     */
    public Resource() {
        this.properties = Maps.newHashMap();
    }

    public Map<String, String> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
