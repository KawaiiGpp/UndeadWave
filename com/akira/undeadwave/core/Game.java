package com.akira.undeadwave.core;

import com.akira.undeadwave.UndeadWave;
import org.apache.commons.lang3.Validate;
import org.bukkit.entity.Player;

public class Game extends GameBase {
    public Game(UndeadWave plugin) {
        super(plugin);
    }

    public void join(Player player) {
        Validate.notNull(player);
        this.validateState(GameState.WAITING);

        int maxAmount = this.getSettingsConfig().getMaximumPlayerAmount();
        Validate.isTrue(sessionMap.size() + 1 <= maxAmount);

        Validate.isTrue(!sessionMap.containsKey(player));
        sessionMap.put(player, new GameSession(player));

        String amountInfo = sessionMap.size() + "/" + maxAmount;
        broadcast("§f玩家 §e" + player.getName() + " §f加入了游戏（§e" + amountInfo + "§f）");

        player.teleport(this.getLocationConfig().getSpawnpoint());
        if (sessionMap.size() == maxAmount) this.startGame();
    }

    public void quit(Player player) {
        Validate.notNull(player);
        this.validateState(s -> s.isIn(GameState.WAITING, GameState.STARTED));

        Validate.isTrue(sessionMap.containsKey(player));
        sessionMap.remove(player);

        String amountInfo = sessionMap.size() + "/" + this.getSettingsConfig().getMaximumPlayerAmount();
        String amountDisplay = state == GameState.WAITING ? "§f（§e" + amountInfo + "§f）" : "。";
        broadcast("§f玩家 §e" + player.getName() + " §f退出了游戏" + amountDisplay);

        player.teleport(this.getLocationConfig().getLobby());
        if (state == GameState.STARTED && sessionMap.isEmpty()) this.endGame(false);
    }

    private void startGame() {
    }

    private void endGame(boolean victory) {
    }
}
