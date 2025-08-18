package com.akira.undeadwave.command;

import com.akira.core.api.command.CommandNode;
import com.akira.core.api.command.EnhancedExecutor;
import com.akira.core.api.command.SenderLimit;
import com.akira.core.api.config.ConfigFile;
import com.akira.undeadwave.UndeadWave;
import com.akira.undeadwave.config.LocationConfig;
import com.akira.undeadwave.core.Game;
import org.apache.commons.lang3.Validate;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.function.Consumer;

public class AdminCommandExecutor extends EnhancedExecutor {
    private final UndeadWave plugin;
    private final LocationConfig locationConfig;

    public AdminCommandExecutor(UndeadWave plugin) {
        super("undeadwaveadmin");

        Validate.notNull(plugin);
        this.plugin = plugin;
        this.locationConfig = (LocationConfig) this.getConfig("location");

        registerNode(new LocationSet("lobby", "大厅", locationConfig::setLobby));
        registerNode(new LocationSet("spawnpoint", "出生点", locationConfig::setSpawnpoint));
        registerNode(new MonsterPointAdd());
        registerNode(new MonsterPointReset());
        registerNode(new EnableGame());
        registerNode(new DisableGame());
    }

    private ConfigFile getConfig(String name) {
        Validate.notNull(name);
        return plugin.getConfigManager().fromString(name);
    }

    private boolean handleIncorrectGameState(CommandSender sender) {
        Validate.notNull(sender);
        Game game = plugin.getGame();

        if (game.getState().isUnavailable())
            return false;

        sender.sendMessage("§c游戏已经启用，无法中途修改位置。");
        return true;
    }

    private class LocationSet extends CommandNode {
        private final String locationDisplayName;
        private final Consumer<Location> locationSaver;

        public LocationSet(String locationName, String locationDisplayName, Consumer<Location> locationSaver) {
            super(name, SenderLimit.PLAYER_ONLY,
                    "location." + locationName,
                    "设置" + locationDisplayName + "的位置");

            Validate.notNull(locationName);
            Validate.notNull(locationDisplayName);
            Validate.notNull(locationSaver);

            this.locationDisplayName = locationDisplayName;
            this.locationSaver = locationSaver;
        }

        protected boolean onExecute(CommandSender sender, String[] args) {
            Player player = (Player) sender;
            if (handleIncorrectGameState(sender)) return true;

            locationSaver.accept(player.getLocation());
            sender.sendMessage("§a已成功设置§e" + locationDisplayName + "§a的位置。");
            return true;
        }
    }

    private class MonsterPointAdd extends CommandNode {
        public MonsterPointAdd() {
            super(name, SenderLimit.PLAYER_ONLY, "monsterpoint.add", "添加怪物刷新点");
        }

        protected boolean onExecute(CommandSender sender, String[] args) {
            Player player = (Player) sender;
            if (handleIncorrectGameState(sender)) return true;

            locationConfig.addMonsterPoint(player.getLocation());
            int amount = locationConfig.getMonsterPoints().size();

            sender.sendMessage("§a已添加新的怪物刷新点，当前共§e" + amount + "§a个。");
            return true;
        }
    }

    private class MonsterPointReset extends CommandNode {
        public MonsterPointReset() {
            super(name, SenderLimit.NONE, "monsterpoint.reset", "移除所有怪物刷新点");
        }

        protected boolean onExecute(CommandSender sender, String[] args) {
            if (handleIncorrectGameState(sender)) return true;
            locationConfig.clearMonsterPoints();

            sender.sendMessage("§a已清空所有怪物刷新点。");
            return true;
        }
    }

    private class EnableGame extends CommandNode {
        public EnableGame() {
            super(name, SenderLimit.NONE, "enable", "自检配置文件并启用游戏");
        }

        protected boolean onExecute(CommandSender sender, String[] args) {
            Game game = plugin.getGame();

            if (game.getState().isAvailable()) {
                sender.sendMessage("§c仅当游戏不可用时才需要进行此操作。");
                return true;
            }

            List<String> tips = game.tryEnable();
            if (!tips.isEmpty()) {
                sender.sendMessage("§c配置文件自检不通过，以下是提示：");
                tips.forEach(m -> sender.sendMessage("§8-> §f" + m));
            } else sender.sendMessage("§a自检通过，游戏已经启用。");

            return true;
        }
    }

    private class DisableGame extends CommandNode {
        public DisableGame() {
            super(name, SenderLimit.NONE, "disable", "禁用游戏以修改配置文件");
        }

        protected boolean onExecute(CommandSender sender, String[] args) {
            Game game = plugin.getGame();

            if (game.getState().isUnavailable()) {
                sender.sendMessage("§c游戏已经处于禁用状态了。");
                return true;
            }

            boolean success = game.disbale();
            if (success) sender.sendMessage("§a禁用成功，目前游戏为不可用状态。");
            else sender.sendMessage("§c禁用失败，只有在游戏处于等待状态才能禁用。");

            return true;
        }
    }
}
