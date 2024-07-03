package org.callvdois.daynightpvp.utils;

import org.bukkit.World;

import java.io.File;
import java.util.List;

public class SearchUtils {

    public static boolean fileExistsInFileList(File[] files, String searchString) {
        for (File file : files) {
            if (file.getName().contains(searchString)) {
                return true;
            }
        }
        return false;
    }

    public static boolean worldExistsInWorldList(List<World> list, String worldName) {
        return list.stream().map(World::getName).anyMatch(name -> name.contains(worldName));
    }

}
