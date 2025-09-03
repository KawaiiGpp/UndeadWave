package com.akira.undeadwave.core;

import com.akira.core.api.util.NumberUtils;
import com.akira.core.api.util.PlayerUtils;
import org.apache.commons.lang3.Validate;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.UUID;

public class GameInfoBar implements Runnable {
    private final GameSession session;

    public GameInfoBar(GameSession session) {
        Validate.notNull(session);
        this.session = session;
    }

    public void run() {
        int round = session.getCurrentRound();
        int enemyAmount = session.getAliveEnemies().size();
        Player player = session.getOwner();

        String coins = NumberUtils.format(session.getCoins());
        String kills = NumberUtils.format(session.getKills());
        String enemies = NumberUtils.format(enemyAmount);

        String title = "§f回合：§b" + round
                + " §8| §f硬币：§6" + coins
                + " §8| §f击杀：§a" + kills
                + " §8| §f剩余：§c" + enemies;

        player.setLevel((int) Math.round(NumberUtils.clamp(this.calculateDistance(), 0, 1000)));
        PlayerUtils.sendActionBarTitle(player, title);
    }

    private double calculateDistance() {
        Location location = session.getOwner().getLocation();
        Location cache = location.clone();
        double minDistance = Double.MAX_VALUE;

        for (UUID uuid : session.getAliveEnemies()) {
            Entity entity = Bukkit.getEntity(uuid);
            Validate.notNull(entity, "Entity for an enemy not found.");

            entity.getLocation(cache);
            double distance = cache.distanceSquared(location);

            if (distance >= minDistance) continue;
            minDistance = distance;
        }

        return Math.sqrt(minDistance);
    }
}
