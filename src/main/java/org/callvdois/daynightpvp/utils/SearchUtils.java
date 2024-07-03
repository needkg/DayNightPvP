package org.callvdois.daynightpvp.utils;

import org.bukkit.World;

import java.io.File;
import java.util.List;

public class SearchUtils {

    public boolean worldExistsInWorldList(List<World> list, String worldName) {
        return list.stream().map(World::getName).anyMatch(name -> name.contains(worldName));
    }

}
