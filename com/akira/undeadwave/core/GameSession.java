package com.akira.undeadwave.core;

import com.akira.core.api.config.ConfigFile;
import com.akira.core.api.util.NumberUtils;
import com.akira.undeadwave.UndeadWave;
import com.akira.undeadwave.config.LocationConfig;
import com.akira.undeadwave.config.SettingsConfig;
import org.apache.commons.lang3.Validate;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GameSession {
    private final UndeadWave plugin;
    private final Player owner;

    private int kills;
    private int coins;
    private int score;

    private long startTime;
    private long roundStartTime;

    private final List<UUID> aliveEnemies;
    private final int maxRound;

    private int currentRound;

    public GameSession(UndeadWave plugin, Player owner) {
        Validate.notNull(plugin);
        Validate.notNull(owner);

        this.owner = owner;
        this.plugin = plugin;

        this.aliveEnemies = new ArrayList<>();
        this.maxRound = this.getSettingsConfig().getMaxRound();
    }

    public void markStartTime() {
        this.startTime = System.currentTimeMillis();
    }

    public void markRoundStartTime() {
        this.roundStartTime = System.currentTimeMillis();
    }

    public long calculateElapsed() {
        Validate.isTrue(startTime > 0, "Start time not set.");
        return System.currentTimeMillis() - startTime;
    }

    public long calculateRoundElapsed() {
        Validate.isTrue(roundStartTime > 0, "Start time for a round not set.");
        return System.currentTimeMillis() - roundStartTime;
    }

    public void increaseRoundCounter() {
        Validate.isTrue(aliveEnemies.isEmpty(),
                "Enemies still alive: " + aliveEnemies.size() + " left.");
        Validate.isTrue(currentRound < maxRound,
                "Max round reached already. Current: " + currentRound);

        currentRound++;
    }

    public void addAliveEnemy(UUID uniqueId) {
        Validate.notNull(uniqueId);
        Validate.isTrue(!aliveEnemies.contains(uniqueId), "Enemy already existing.");

        aliveEnemies.add(uniqueId);
    }

    public void removeAliveEnemy(UUID uniqueId) {
        Validate.notNull(uniqueId);
        Validate.isTrue(aliveEnemies.contains(uniqueId), "Enemy not found in the list.");

        aliveEnemies.remove(uniqueId);
    }

    public boolean isOwnedBy(Player player) {
        Validate.notNull(player);
        return this.owner.equals(player);
    }

    public boolean isMaxRoundReached() {
        return currentRound == maxRound;
    }

    public boolean isRoundCleared() {
        return aliveEnemies.isEmpty();
    }

    public Player getOwner() {
        return owner;
    }

    public void increaseKills() {
        this.kills++;
    }

    public void increaseCoins(int coins) {
        NumberUtils.ensureNonNegative(coins);
        this.coins += coins;
        this.score += coins;
    }

    public void decreaseCoins(int coins) {
        NumberUtils.ensurePositive(coins);
        Validate.isTrue(this.coins >= coins, "No enough coins to descrease.");
        this.coins -= coins;
    }

    public int getKills() {
        return kills;
    }

    public int getCoins() {
        return coins;
    }

    public int getScore() {
        return score;
    }

    public List<UUID> getAliveEnemies() {
        return new ArrayList<>(aliveEnemies);
    }

    public int getCurrentRound() {
        return currentRound;
    }

    private ConfigFile getConfig(String name) {
        Validate.notNull(name);
        return plugin.getConfigManager().fromString(name);
    }

    private LocationConfig getLocationConfig() {
        return (LocationConfig) this.getConfig("location");
    }

    private SettingsConfig getSettingsConfig() {
        return (SettingsConfig) this.getConfig("settings");
    }
}
