package com.akira.undeadwave.core;

import com.akira.core.api.config.ConfigFile;
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
    private double damageTaken;
    private double damageDealt;

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

    public int getKills() {
        return kills;
    }

    public int getCoins() {
        return coins;
    }

    public double getDamageTaken() {
        return damageTaken;
    }

    public double getDamageDealt() {
        return damageDealt;
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
