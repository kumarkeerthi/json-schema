package org.everit.json.schema.loader;

import static java.util.Arrays.asList;
import static java.util.Collections.unmodifiableList;
import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java8.util.Optional;
import java8.util.stream.StreamSupport;

import org.everit.json.schema.combatibility.UncheckedIOException;

class ClassPathAwareSchemaClient implements SchemaClient {

    private static final List<String> HANDLED_PREFIXES = unmodifiableList(asList("classpath://", "classpath:/", "classpath:"));

    private final SchemaClient fallbackClient;

    ClassPathAwareSchemaClient(SchemaClient fallbackClient) {
        this.fallbackClient = requireNonNull(fallbackClient, "fallbackClient cannot be null");
    }

    @Override public InputStream get(String url) {
        Optional<String> maybeString = handleProtocol(url);
        if(maybeString.isPresent()) {
            InputStream stream = this.loadFromClasspath(maybeString.get());
            if(stream != null) {
                return stream;
            } else {
                throw new UncheckedIOException(new IOException(String.format("Could not find %s", url)));
            }
        } else {
            return fallbackClient.get(url);
        }
    }

    private InputStream loadFromClasspath(String str) {
        return getClass().getResourceAsStream(str);
    }

    private Optional<String> handleProtocol(String url) {
        return StreamSupport.stream(HANDLED_PREFIXES).filter(url::startsWith)
                .map(prefix -> "/" + url.substring(prefix.length()))
                .findFirst();
    }

}
