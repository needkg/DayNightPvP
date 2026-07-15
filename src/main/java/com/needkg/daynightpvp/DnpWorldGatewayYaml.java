package com.needkg.daynightpvp;

import java.net.URI;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.needkg.daynightpvp.config.WorldConfig;

public class DnpWorldGatewayYaml implements DnpWorldGateway {

    private final File yamlFile;

    private static final ObjectMapper mapper = new ObjectMapper(new YAMLFactory())
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .registerModule(new Jdk8Module());

    public DnpWorldGatewayYaml(URI yamlFileUri) {
        this.yamlFile = new File(yamlFileUri);
    }

    @Override
    public Optional<WorldConfig> findByName(String name) {
        return Optional.ofNullable(readWorldsFromYaml().get(name));
    }

    @Override
    public Set<WorldConfig> findAll() {
        return readWorldsFromYaml().values().stream().collect(java.util.stream.Collectors.toSet());
    }

    private Map<String, WorldConfig> readWorldsFromYaml() {
        try {
            return mapper.readValue(
                    yamlFile,
                    mapper.getTypeFactory().constructMapType(Map.class, String.class, WorldConfig.class));
        } catch (IOException e) {
            // e.printStackTrace(); //TODO logar erro como aviso
            return Map.of();
        }
    }

}
