package me.needkg.daynightpvp.service.player;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.World;
import org.bukkit.entity.Player;

public class SleepService {

    private Set<Player> sleepingPlayers = new HashSet<>();    

    public void addSleeping(Player player) {
        this.sleepingPlayers.add(player);
    }

    public void removeSleeping(Player player) {
        this.sleepingPlayers.remove(player);
    }

    public int getSleepingPlayersSize() {
        return sleepingPlayers.size();
    }

    public double getSleepingPercentage(World world) {
        double playersOnline = world.getPlayers().size();

        return (sleepingPlayers.size() / playersOnline) * 100;
    }
}
