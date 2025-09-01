package com.akira.undeadwave.core;

import com.akira.core.api.util.NumberUtils;
import com.akira.core.api.util.PlayerUtils;
import org.apache.commons.lang3.Validate;

public class GameInfoBar implements Runnable {
    private final GameSession session;

    public GameInfoBar(GameSession session) {
        Validate.notNull(session);
        this.session = session;
    }

    public void run() {
        int round = session.getCurrentRound();
        String coins = NumberUtils.format(session.getCoins());
        String kills = NumberUtils.format(session.getKills());
        String enemies = NumberUtils.format(session.getAliveEnemies().size());

        String title = "§f回合：§b" + round
                + " §8- §f硬币：§6" + coins
                + " §8- §f击杀：§a" + kills
                + " §8- §f剩余：§c" + enemies;
        PlayerUtils.sendActionBarTitle(session.getOwner(), title);
    }
}
