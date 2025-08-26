package com.akira.undeadwave.core;

import com.akira.undeadwave.UndeadWave;
import org.apache.commons.lang3.Validate;
import org.bukkit.scheduler.BukkitTask;

public class GameRound {
    private final UndeadWave plugin;
    private final Game game;

    private int currentRound;
    private int passedRounds;

    private BukkitTask spawningTask;

    public GameRound(UndeadWave plugin, Game game) {
        Validate.notNull(plugin);
        Validate.notNull(game);

        this.plugin = plugin;
        this.game = game;

        this.currentRound = 1;
        this.passedRounds = 0;
    }

    public void startSpawnMonsters() {}

    public void removeMonsters() {}

    public int getCurrentRound() {
        return currentRound;
    }

    public int getPassedRounds() {
        return passedRounds;
    }
}
