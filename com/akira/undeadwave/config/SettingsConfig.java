package com.akira.undeadwave.config;

import com.akira.core.api.AkiraPlugin;
import com.akira.core.api.config.ConfigFile;
import org.apache.commons.lang3.Validate;

import java.util.function.Predicate;

public class SettingsConfig extends ConfigFile {
    public SettingsConfig(AkiraPlugin plugin, String templatePath) {
        super(plugin, "settings", templatePath);
    }

    public int getMaxRound() {
        return validate(this.getConfig().getInt("general.max_round"), x -> x > 0);
    }

    public int getMonstersPerRound() {
        return validate(this.getConfig().getInt("general.monsters_per_round"), x -> x > 0);
    }

    public int getMinimumPlayerAmount() {
        return validate(this.getConfig().getInt("general.minimum_player_amount"), x -> x > 0);
    }

    private <T> T validate(T result, Predicate<T> predicate) {
        Validate.notNull(predicate);
        Validate.isTrue(predicate.test(result), "Invalid value from config.");
        return result;
    }
}
