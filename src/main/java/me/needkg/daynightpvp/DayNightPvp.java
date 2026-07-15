package me.needkg.daynightpvp;

import me.needkg.daynightpvp.feature.command.CommandInitializer;
import me.needkg.daynightpvp.feature.config.ResourceLoader;
import me.needkg.daynightpvp.feature.config.models.GlobalSettings;
import me.needkg.daynightpvp.feature.config.models.MessagesSettings;
import me.needkg.daynightpvp.feature.config.models.ResourceFile;
import me.needkg.daynightpvp.feature.config.repository.GlobalRepository;
import me.needkg.daynightpvp.feature.config.repository.MessagesRepository;
import me.needkg.daynightpvp.feature.config.repository.WorldRepository;
import me.needkg.daynightpvp.integration.bstats.MetricsInitializer;
import me.needkg.daynightpvp.shared.logging.Logger;
import me.needkg.daynightpvp.world.WorldLoader;
import me.needkg.daynightpvp.world.WorldManager;

import java.io.File;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import com.needkg.daynightpvp.DnpWorldGatewayYaml;
import com.needkg.daynightpvp.event.listener.EntityDamageByEntityEventListener;
import com.needkg.daynightpvp.event.listener.PlayerDeathEventListener;
import com.needkg.daynightpvp.event.listener.PotionSplashEventListener;
import com.needkg.daynightpvp.event.listener.ProjectileHitEventListener;
import com.needkg.daynightpvp.feature.DayNightFeature;
import com.needkg.daynightpvp.feature.TestFeature;

public final class DayNightPvp extends JavaPlugin {

    @Override
    public void onEnable() {

        final var worldGateway = new DnpWorldGatewayYaml(new File(getDataFolder(), "worlds.yml").toURI());

        DayNightFeature dayNightFeature = new DayNightFeature(worldGateway);

        TestFeature testFeature = new TestFeature();

        var playerDeathEventListener = new PlayerDeathEventListener(Set.of());

        var entityDamageByEntityEventListener = new EntityDamageByEntityEventListener(Set.of(dayNightFeature));
        var projectileHitEventListener = new ProjectileHitEventListener(Set.of(dayNightFeature));
        var potionSplashEventListener = new PotionSplashEventListener(Set.of(dayNightFeature));

        Bukkit.getPluginManager().registerEvents(potionSplashEventListener, this);
        Bukkit.getPluginManager().registerEvents(projectileHitEventListener, this);
        Bukkit.getPluginManager().registerEvents(entityDamageByEntityEventListener, this);
        Bukkit.getPluginManager().registerEvents(playerDeathEventListener, this);

    }

    @Override
    public void onDisable() {
    }
}
