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

        this.validateAvailableState();
        this.validateState(GameState.WAITING);

        int maxAmount = this.getSettingsConfig().getMaximumPlayerAmount();
        Validate.isTrue(playerMap.size() + 1 <= maxAmount, "Player limit exceeded.");

        Validate.isTrue(!playerMap.containsKey(player), "Player already joined: " + player.getName());
        playerMap.put(player, new GameSession(player));

        String amountInfo = playerMap.size() + "/" + maxAmount;
        broadcast("§f玩家 §e" + player.getName() + " §f加入了游戏（§e" + amountInfo + "§f）");
        plugin.logInfo("Player " + player.getName() + " joined. (" + amountInfo + ")");

        player.teleport(this.getLocationConfig().getSpawnpoint());
    }

    public void quit(Player player) {
        Validate.notNull(player);

        this.validateAvailableState();
        this.validateState(s -> s.isIn(GameState.WAITING, GameState.STARTED));

        Validate.isTrue(playerMap.containsKey(player), "Player not joined: " + player.getName());
        playerMap.remove(player);

        String amountInfo = playerMap.size() + "/" + this.getSettingsConfig().getMaximumPlayerAmount();
        String amountDisplay = state == GameState.WAITING ? "§f（§e" + amountInfo + "§f）" : "。";
        broadcast("§f玩家 §e" + player.getName() + " §f退出了游戏" + amountDisplay);
        plugin.logInfo("Player " + player.getName() + " quit. (" + amountInfo + ")");

        player.teleport(this.getLocationConfig().getLobby());
    }
}
