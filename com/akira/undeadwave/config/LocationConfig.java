package com.akira.undeadwave.config;

import com.akira.core.api.AkiraPlugin;
import com.akira.core.api.config.ConfigFile;
import com.akira.core.api.util.CommonUtils;
import org.apache.commons.lang3.Validate;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.List;

public class LocationConfig extends ConfigFile {
    public LocationConfig(AkiraPlugin plugin) {
        super(plugin, "location");
    }

    public void setLobby(Location location) {
        this.setLocation("lobby", location);
    }

    public Location getLobby() {
        return this.getLocation("lobby");
    }

    public void setSpawnpoint(Location location) {
        this.setLocation("spawnpoint", location);
    }

    public Location getSpawnpoint() {
        return this.getLocation("spawnpoint");
    }

    public void addMonsterPoint(Location location) {
        Validate.notNull(location);

        YamlConfiguration config = this.getConfig();
        List<String> list = config.getStringList("monster_spawnpoints");

        list.add(CommonUtils.serializeLocation(location));
        config.set("monster_spawnpoints", list);
    }

    public void clearMonsterPoints() {
        this.getConfig().set("monster_spawnpoints", null);
    }

    public List<Location> getMonsterPoints() {
        return this.getConfig().getStringList("monster_spawnpoints").stream()
                .map(CommonUtils::deserializeLocation).toList();
    }

    private void setLocation(String path, Location location) {
        Validate.notNull(path);

        String serialized = location == null ? null : CommonUtils.serializeLocation(location);
        this.getConfig().set(path, serialized);
    }

    private Location getLocation(String path) {
        Validate.notNull(path);

        String raw = this.getConfig().getString(path);
        return raw == null ? null : CommonUtils.deserializeLocation(raw);
    }
}
