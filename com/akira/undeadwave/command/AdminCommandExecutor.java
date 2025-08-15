package com.akira.undeadwave.command;

import com.akira.core.api.command.CommandNode;
import com.akira.core.api.command.EnhancedExecutor;
import com.akira.core.api.command.SenderLimit;
import com.akira.core.api.config.ConfigFile;
import com.akira.undeadwave.UndeadWave;
import com.akira.undeadwave.config.LocationConfig;
import org.apache.commons.lang3.Validate;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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
    }

    private ConfigFile getConfig(String name) {
        Validate.notNull(name);
        return plugin.getConfigManager().fromString(name);
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
            locationConfig.clearMonsterPoints();

            sender.sendMessage("§a已清空所有怪物刷新点。");
            return true;
        }
    }
}
