package org.ow2.petals.cloud.service.api;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Christophe Hamerling - chamerling@linagora.com
 */
public class ResultBuilder {

    String id;

    String message;

    String type;

    public ResultBuilder() {
    }

    public ResultBuilder id(String id) {
        this.id = checkNotNull(id, "ID is null");
        return this;
    }

    public ResultBuilder message(String message) {
        this.message = checkNotNull(message, "Message is null");
        return this;
    }

    public ResultBuilder type(String type) {
        this.type = checkNotNull(message, "Type is null");
        return this;
    }

    public Result createResult() {
        return new Result(message, type, id);
    }

    public static Result notImplemented() {
        return null;
    }
}
