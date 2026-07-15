package com.needkg.daynightpvp.util;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;

public final class YamlUtils {

    private static final ObjectMapper mapper = new ObjectMapper(new YAMLFactory())
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .registerModule(new Jdk8Module());

    private YamlUtils() {
        // Private constructor to prevent instantiation
    }

    private static <T> T readYaml(File yamlFile, Class<T> valueType) {
        try {
            return mapper.readValue(
                    yamlFile,
                    valueType);
        } catch (IOException e) {
            // e.printStackTrace(); //TODO logar erro como aviso
            return null;
        }
    }

}
