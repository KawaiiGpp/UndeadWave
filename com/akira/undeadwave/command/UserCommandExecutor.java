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
        registerNode(new GameQuit());
    }

    private class GameJoin extends CommandNode {
        public GameJoin() {
            super(name, SenderLimit.PLAYER_ONLY, "join", "加入游戏。");
        }

        protected boolean onExecute(CommandSender sender, String[] args) {
            Player player = (Player) sender;
            Game game = plugin.getGame();

            if (game.getState() != GameState.WAITING) {
                sender.sendMessage("§c无法加入，游戏未处于空闲等待状态。");
                return true;
            }

            game.startGame(player);
            sender.sendMessage("§a你已成功加入游戏。");
            return true;
        }
    }

    private class GameQuit extends CommandNode {
        public GameQuit() {
            super(name, SenderLimit.PLAYER_ONLY, "quit", "退出游戏。");
        }

        protected boolean onExecute(CommandSender sender, String[] args) {
            Player player = (Player) sender;
            Game game = plugin.getGame();

            if (game.getState() != GameState.STARTED) {
                sender.sendMessage("§c无法退出，因为游戏并未开始。");
                return true;
            }

            if (!game.getSessionSnapshot().isOwnedBy(player)) {
                sender.sendMessage("§c无法退出，你并不在游戏中。");
                return true;
            }

            game.endGame(false);
            sender.sendMessage("§a你已成功退出游戏。");
            return true;
        }
    }
}
