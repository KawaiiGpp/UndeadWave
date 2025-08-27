package com.akira.undeadwave;

import com.akira.core.api.AkiraPlugin;
import com.akira.core.api.config.ConfigManager;
import com.akira.undeadwave.command.AdminCommandExecutor;
import com.akira.undeadwave.command.UserCommandExecutor;
import com.akira.undeadwave.config.LocationConfig;
import com.akira.undeadwave.config.SettingsConfig;
import com.akira.undeadwave.core.Game;
import com.akira.undeadwave.listener.MainListener;
import com.akira.undeadwave.listener.WeaponListener;
import org.apache.commons.lang3.Validate;

import java.util.List;

public class UndeadWave extends AkiraPlugin {
    private static UndeadWave instance;

    private final ConfigManager configManager = new ConfigManager();
    private final Game game = new Game(this);

    public UndeadWave() {
        instance = this;
    }

    public void onEnable() {
        super.onEnable();

        String templatePath = "com/akira/undeadwave/config/template";
        configManager.register(new LocationConfig(this));
        configManager.register(new SettingsConfig(this, templatePath));
        configManager.initializeAll();

        game.initializeWeapons();
        game.initializeEnemies();

        this.tryEnableGame();

        registerCommand(new AdminCommandExecutor(this));
        registerCommand(new UserCommandExecutor(this));
        registerListener(new MainListener(this));
        registerListener(new WeaponListener(this));
    }

    public void onDisable() {
        super.onDisable();

        configManager.saveAll();
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public Game getGame() {
        return game;
    }

    private void tryEnableGame() {
        logInfo("正在检查小游戏是否满足启用条件。");

        List<String> tips = game.tryEnable();
        if (!tips.isEmpty()) {
            logWarn("未满足条件，以下是提示：");
            tips.forEach(this::logWarn);
        } else logInfo("已满足条件，小游戏已启用。");
    }

    public static UndeadWave getInstance() {
        Validate.notNull(instance, "Plugin instance is currently null.");
        return instance;
    }
}
