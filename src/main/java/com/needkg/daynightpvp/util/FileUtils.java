package com.needkg.daynightpvp.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import org.bukkit.configuration.file.YamlConfiguration;

import com.needkg.daynightpvp.exceptions.ResourceReadException;

public final class FileUtils {

    private FileUtils() {
        // utils
    }

    public static Reader openReader(Path path) {
        try {
            return Files.newBufferedReader(path, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new ResourceReadException(path, e);
        }
    }

}
