package com.akira.undeadwave.core;

import com.akira.core.api.config.ConfigFile;
import com.akira.undeadwave.UndeadWave;
import com.akira.undeadwave.config.LocationConfig;
import com.akira.undeadwave.config.SettingsConfig;
import org.apache.commons.lang3.Validate;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

public class GameBase {
    protected final UndeadWave plugin;
    protected final Map<Player, GameSession> playerMap;

    protected GameState state;

    protected GameBase(UndeadWave plugin) {
        Validate.notNull(plugin);

        this.plugin = plugin;
        this.state = GameState.UNAVAILABLE;
        this.playerMap = new HashMap<>();
    }

    public final boolean includes(Player player) {
        Validate.notNull(player);
        return playerMap.containsKey(player);
    }

    public final boolean reachesMaxPlayer() {
        int maximum = this.getSettingsConfig().getMaximumPlayerAmount();
        int actual = playerMap.size();

        Validate.isTrue(actual <= maximum, "Player limit exceeded.");
        return actual == maximum;
    }

    public final List<String> tryEnable() {
        List<String> tips = this.getEnableTips();
        if (tips.isEmpty()) state = GameState.WAITING;
        return tips;
    }

    public final boolean disbale() {
        if (state.allowDisabling()) {
            state = GameState.UNAVAILABLE;
            return true;
        } else return false;
    }

    public final GameState getState() {
        return state;
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

    protected final List<String> getEnableTips() {
        LocationConfig config = this.getLocationConfig();
        List<String> tips = new ArrayList<>();

        if (config.getMonsterPoints().isEmpty())
            tips.add("至少需要设置一个怪物刷新点。");
        if (config.getLobby() == null)
            tips.add("需要设置大厅位置。");
        if (config.getSpawnpoint() == null)
            tips.add("需要设置出生点位置。");

        return tips;
    }

    protected final void validateState(Function<GameState, Boolean> function) {
        Validate.notNull(function);
        Validate.isTrue(function.apply(state), "Unexpected game state: " + state.name());
    }

    protected final void validateState(GameState state) {
        Validate.notNull(state);
        validateState(state::equals);
    }

    protected final void validateAvailableState() {
        this.validateState(GameState::isAvailable);
    }

    protected final void broadcast(String message) {
        Validate.notNull(message);
        forEachPlayer(p -> p.sendMessage(message));
    }

    protected final void forEachPlayer(Consumer<Player> consumer) {
        Validate.notNull(consumer);
        playerMap.keySet().forEach(consumer);
    }
}
