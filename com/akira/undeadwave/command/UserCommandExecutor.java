package com.akira.undeadwave.command;

import com.akira.core.api.command.CommandNode;
import com.akira.core.api.command.EnhancedExecutor;
import com.akira.core.api.command.SenderLimit;
import com.akira.undeadwave.UndeadWave;
import com.akira.undeadwave.core.Game;
import com.akira.undeadwave.core.GameState;
import org.apache.commons.lang3.Validate;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class UserCommandExecutor extends EnhancedExecutor {
    private final UndeadWave plugin;

    public UserCommandExecutor(UndeadWave plugin) {
        super("undeadwave");

        Validate.notNull(plugin);
        this.plugin = plugin;

        registerNode(new GameJoin());
    }

    private class GameJoin extends CommandNode {
        public GameJoin() {
            super(name, SenderLimit.PLAYER_ONLY, "join", "加入游戏。");
        }

        protected boolean onExecute(CommandSender sender, String[] args) {
            Player player = (Player) sender;
            Game game = plugin.getGame();

            if (game.includes(player)) {
                sender.sendMessage("§c你已经在游戏中了。");
                return true;
            }

            if (game.getState() != GameState.WAITING) {
                sender.sendMessage("§c只有游戏处于空闲等待状态下才能加入。");
                return true;
            }

            if (game.reachesMaxPlayer()) {
                sender.sendMessage("§c游戏已满员，无法加入。");
                return true;
            }

            game.join(player);
            sender.sendMessage("§a你已成功加入游戏。");
            return true;
        }
    }
}
