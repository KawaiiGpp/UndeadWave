package com.akira.undeadwave.command;

import com.akira.core.api.command.EnhancedExecutor;
import com.akira.undeadwave.UndeadWave;
import org.apache.commons.lang3.Validate;

public class UserCommandExecutor extends EnhancedExecutor {
    private final UndeadWave plugin;

    public UserCommandExecutor(UndeadWave plugin) {
        super("undeadwave");

        Validate.notNull(plugin);
        this.plugin = plugin;
    }
}
