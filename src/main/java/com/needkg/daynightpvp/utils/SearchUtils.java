package com.needkg.daynightpvp.utils;

import org.bukkit.World;

import java.util.List;

public class SearchUtils {

    public boolean stringInList(List<String> list, String searchString) {
        for (String element : list) {
            if (element.equals(searchString)) {
                return true;
            }
        }
        return false;
    }

    public boolean worldExistsInList(List<World> list, String searchString) {
        return list.stream().map(World::getName).anyMatch(name -> name.contains(searchString));
    }

}
