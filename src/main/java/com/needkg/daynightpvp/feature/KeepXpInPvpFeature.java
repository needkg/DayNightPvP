package com.needkg.daynightpvp.feature;

import java.util.Comparator;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;

import com.needkg.daynightpvp.event.handler.PlayerDeathEventHandler;
import com.needkg.daynightpvp.util.EntityUtils;

public class KeepXpInPvpFeature implements PlayerDeathEventHandler {

    private final Map<String, KeepXpInPvpFeature.Config> worldConfigs = new ConcurrentHashMap<>();
    private final KeepXpInPvpFeature.Language languageConfigs;

    private static final String PERMISSION_LOSE_XP_PERCENT = "dnp.lose-exp-group.";

    public KeepXpInPvpFeature(
            Map<String, KeepXpInPvpFeature.Config> worldConfigs,
            KeepXpInPvpFeature.Language languageConfigs) {
        this.worldConfigs.putAll(worldConfigs);
        this.languageConfigs = languageConfigs;
    }

    @Override
    public void handle(PlayerDeathEvent event) {

        if (!isPvp(event.getEntity(), event.getEntity().getKiller())) {
            return;
        }

        final var worldConfig = worldConfigs.get(EntityUtils.getWorldName(event.getEntity()));

        if (!worldConfig.enabled) {
            return;
        }

        if (!timeMatch(event.getEntity(), worldConfig.when, worldConfig.dayEnd)) {
            return;
        }

        event.setKeepLevel(true);

        Player victim = event.getEntity();

        int loseAmountPorcent = getGroupLosePercent(victim, worldConfig.groupLosePercents)
                .orElse(worldConfig.defaultLosePercent);

        Integer loseAmount = loseAmount(victim.getLevel(), loseAmountPorcent);
        Integer remaingLevel = victim.getLevel() - loseAmount;

        victim.setLevel(remaingLevel);

        if (loseAmount > 0) {
            victim.setExp(0);
            victim.sendMessage(languageConfigs.lose());
        } else {
            victim.sendMessage(languageConfigs.noLose());
        }

        event.setDroppedExp(loseAmount.intValue());
    }

    private static Boolean isPvp(Entity entity1, Entity entity2) {
        return entity1 instanceof Player && entity2 instanceof Player;
    }

    private static KeepXpInPvpFeature.Config.When worldTime(Entity entity, Long dayEnd) {
        return entity.getWorld().getTime() < dayEnd ? Config.When.DAY : Config.When.NIGHT;
    }

    private static Boolean timeMatch(Entity entity, Config.When configTime, Long dayEnd) {

        return KeepXpInPvpFeature.Config.When.ALL.equals(configTime)
                || worldTime(entity, dayEnd).equals(configTime);

    }

    private static Optional<Integer> getGroupLosePercent(Player victim, Map<String, Integer> worldGroupLosePercents) {
        return victim.getEffectivePermissions()
                .stream()
                .filter(permission -> permission.getPermission().startsWith(PERMISSION_LOSE_XP_PERCENT))
                .filter(permission -> worldGroupLosePercents.containsKey(permission.getPermission()))
                .map(permission -> worldGroupLosePercents.get(permission.getPermission()))
                .min(Comparator.naturalOrder());

    }

    private static Integer loseAmount(Integer actualXp, Integer losePercent) {
        if (actualXp <= 0 || losePercent <= 0) {
            return 0;
        }

        return actualXp * losePercent / 100;
    }

    public record Config(
            Boolean enabled,
            Long dayEnd,
            When when,
            int defaultLosePercent,
            Map<String, Integer> groupLosePercents) {

        public Config {
            groupLosePercents = groupLosePercents
                    .entrySet()
                    .stream()
                    .collect(Collectors.toMap(
                            entry -> PERMISSION_LOSE_XP_PERCENT + entry.getKey(),
                            Map.Entry::getValue));
        }

        public enum When {
            DAY,
            NIGHT,
            ALL
        }

    }

    public record Language(
            String lose,
            String noLose) {

    }

}