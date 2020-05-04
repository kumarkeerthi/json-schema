package org.everit.json.schema.loader;

import java8.lang.FunctionalInterface;

/**
 * @author erosb
 */
@FunctionalInterface
interface JsonObjectIterator {

    void apply(String key, JsonValue value);

}
