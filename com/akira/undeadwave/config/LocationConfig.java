package com.akira.undeadwave.config;

import com.akira.core.api.AkiraPlugin;
import com.akira.core.api.config.ConfigFile;

public class LocationConfig extends ConfigFile {
    public LocationConfig(AkiraPlugin plugin) {
        super(plugin, "location");
    }
}
