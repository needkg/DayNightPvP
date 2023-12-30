package org.callvdois.daynightpvp.utils;

import org.bukkit.World;

import java.io.File;
import java.util.List;

public class SearchUtils {

    public static boolean stringExistInList(List<String> list, String searchString) {
        for (String element : list) {
            if (element.equals(searchString)) {
                return true;
            }
        }
        return false;
    }

    public static boolean fileExistInListOfFiles(File[] files, String searchString) {
        for (File file : files) {
            if (file.getName().contains(searchString)) {
                return true;
            }
        }
        return false;
    }

    public static boolean worldExistsInList(List<World> list, String searchString) {
        return list.stream().map(World::getName).anyMatch(name -> name.contains(searchString));
    }

}
