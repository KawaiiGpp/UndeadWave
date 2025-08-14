package com.akira.undeadwave.config;

import com.akira.core.api.AkiraPlugin;
import com.akira.core.api.config.ConfigFile;
import org.bukkit.configuration.file.YamlConfiguration;

public class SettingsConfig extends ConfigFile {
    public SettingsConfig(AkiraPlugin plugin, String templatePath) {
        super(plugin, "settings", templatePath);
    }

    public General getGeneral() {
        YamlConfiguration config = this.getConfig();

        return new General(config.getInt("general.max_round"),
                config.getInt("general.monsters_for_each_round"));
    }

    public record General(int maxRound, int monstersForEachRound) {}
}
