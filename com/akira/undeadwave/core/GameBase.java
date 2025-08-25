package com.akira.undeadwave.core;

import com.akira.core.api.config.ConfigFile;
import com.akira.undeadwave.UndeadWave;
import com.akira.undeadwave.config.LocationConfig;
import com.akira.undeadwave.config.SettingsConfig;
import com.akira.undeadwave.core.weapon.WeaponManager;
import com.akira.undeadwave.core.weapon.melee.BeginnerSword;
import com.akira.undeadwave.core.weapon.ranged.Pistol;
import org.apache.commons.lang3.Validate;

import java.util.ArrayList;
import java.util.List;

public class GameBase {
    protected final UndeadWave plugin;
    protected final WeaponManager weaponManager;

    protected GameSession session;
    protected GameState state;

    protected GameBase(UndeadWave plugin) {
        Validate.notNull(plugin);

        this.plugin = plugin;
        this.state = GameState.UNAVAILABLE;
        this.weaponManager = new WeaponManager();
    }

    public final List<String> tryEnable() {
        LocationConfig config = this.getLocationConfig();
        List<String> tips = new ArrayList<>();

        if (config.getMonsterPoints().isEmpty())
            tips.add("至少需要设置一个怪物刷新点。");
        if (config.getLobby() == null)
            tips.add("需要设置大厅位置。");
        if (config.getSpawnpoint() == null)
            tips.add("需要设置出生点位置。");

        if (tips.isEmpty()) state = GameState.WAITING;
        return tips;
    }

    public final void disbale() {
        Validate.isTrue(state.allowDisabling(),
                "Disabling now allowed in state: " + state.name());
        state = GameState.UNAVAILABLE;
    }

    public final GameState getState() {
        Validate.notNull(state);
        return state;
    }

    public final GameSession getSessionSnapshot() {
        return this.getSessionSafely().copy();
    }

    public final WeaponManager getWeaponManager() {
        return weaponManager;
    }

    public final void initializeWeapons() {
        weaponManager.register(new BeginnerSword(plugin));
        weaponManager.register(new Pistol(plugin));
    }

    protected final ConfigFile getConfig(String name) {
        Validate.notNull(name);
        return plugin.getConfigManager().fromString(name);
    }

    protected final LocationConfig getLocationConfig() {
        return (LocationConfig) this.getConfig("location");
    }

    protected final SettingsConfig getSettingsConfig() {
        return (SettingsConfig) this.getConfig("settings");
    }

    protected final GameSession getSessionSafely() {
        Validate.notNull(session, "Session is currently null.");
        return session;
    }

    protected final void validateState(GameState expected) {
        Validate.notNull(expected);

        String cur = state.name();
        String exp = expected.name();
        Validate.isTrue(state == expected, "Unexpected state: " + cur + " (" + exp + " expected)");
    }
}
