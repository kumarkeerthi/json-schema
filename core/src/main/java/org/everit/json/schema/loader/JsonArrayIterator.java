package org.everit.json.schema.loader;

import java8.lang.FunctionalInterface;

/**
 * @author erosb
 */
@FunctionalInterface
interface JsonArrayIterator {

    void apply(int index, JsonValue value);

}
