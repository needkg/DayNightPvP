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

public class DnpWorldGatewayYaml implements DnpWorldGateway {

    private final File yamlFile;

    private static final ObjectMapper mapper = new ObjectMapper(new YAMLFactory())
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);;

    public DnpWorldGatewayYaml(URI yamlFileUri) {
        this.yamlFile = new File(yamlFileUri);
    }

    @Override
    public Optional<DnpWorld> findByName(String name) {
        return Optional.ofNullable(readWorldsFromYaml().get(name));
    }

    @Override
    public Set<DnpWorld> findAll() {
        return readWorldsFromYaml().values().stream().collect(java.util.stream.Collectors.toSet());
    }

    private Map<String, DnpWorld> readWorldsFromYaml() {
        try {
            return mapper.readValue(
                    yamlFile,
                    mapper.getTypeFactory().constructMapType(Map.class, String.class, DnpWorld.class));
        } catch (IOException e) {
            // e.printStackTrace(); //TODO logar erro como aviso
            return Map.of();
        }
    }

}
