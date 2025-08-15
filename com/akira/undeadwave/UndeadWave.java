package com.akira.undeadwave;

import com.akira.core.api.AkiraPlugin;
import com.akira.core.api.config.ConfigManager;
import com.akira.undeadwave.command.AdminCommandExecutor;
import com.akira.undeadwave.command.UserCommandExecutor;
import com.akira.undeadwave.config.LocationConfig;
import com.akira.undeadwave.config.SettingsConfig;
import org.apache.commons.lang3.Validate;

public class UndeadWave extends AkiraPlugin {
    private static UndeadWave instance;

    private final ConfigManager configManager = new ConfigManager();

    public UndeadWave() {
        instance = this;
    }

    public void onEnable() {
        super.onEnable();

        String templatePath = "com/akira/undeadwave/config/template";
        configManager.register(new LocationConfig(this));
        configManager.register(new SettingsConfig(this, templatePath));
        configManager.initializeAll();

        registerCommand(new AdminCommandExecutor(this));
        registerCommand(new UserCommandExecutor(this));
    }

    public void onDisable() {
        super.onDisable();

        configManager.saveAll();
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public static UndeadWave getInstance() {
        Validate.notNull(instance, "Plugin instance is currently null.");
        return instance;
    }
}
