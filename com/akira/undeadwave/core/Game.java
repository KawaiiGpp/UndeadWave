package com.akira.undeadwave.core;

import com.akira.core.api.config.ConfigFile;
import com.akira.undeadwave.UndeadWave;
import com.akira.undeadwave.config.LocationConfig;
import com.akira.undeadwave.config.SettingsConfig;
import org.apache.commons.lang3.Validate;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;

public class Game {
    private final UndeadWave plugin;
    private final Map<Player, GameSession> playerMap;

    private GameState state;

    public Game(UndeadWave plugin) {
        Validate.notNull(plugin);

        this.plugin = plugin;
        this.state = GameState.UNAVAILABLE;
        this.playerMap = new HashMap<>();
    }

    public void join(Player player) {
        Validate.notNull(player);

        this.validateAvailableState();
        this.validateState(GameState.WAITING);

        int maxAmount = this.getSettingsConfig().getMaximumPlayerAmount();
        Validate.isTrue(playerMap.size() + 1 <= maxAmount, "Player limit exceeded.");

        Validate.isTrue(!playerMap.containsKey(player), "Player already joined: " + player.getName());
        playerMap.put(player, new GameSession(player));

        broadcast("§f玩家 §e" + player.getName() + " §f加入了游戏（§a" + playerMap.size() + "/" + maxAmount + "§f）");
        player.teleport(this.getLocationConfig().getSpawnpoint());
    }

    public void quit(Player player) {
        Validate.notNull(player);

        this.validateAvailableState();
        this.validateState(s -> s.isIn(GameState.WAITING, GameState.STARTED));

        Validate.isTrue(playerMap.containsKey(player), "Player not joined: " + player.getName());
        playerMap.remove(player);

        String amountInfo = "§f（§a" + playerMap.size() + "/" + this.getSettingsConfig().getMaximumPlayerAmount() + "§f）";
        String amountDisplay = state == GameState.WAITING ? amountInfo : "。";
        broadcast("§f玩家 §e" + player.getName() + " §f退出了游戏" + amountDisplay);
        player.teleport(this.getLocationConfig().getLobby());
    }

    public boolean includes(Player player) {
        Validate.notNull(player);
        return playerMap.containsKey(player);
    }

    public boolean reachesMaxPlayer() {
        int maximum = this.getSettingsConfig().getMaximumPlayerAmount();
        int actual = playerMap.size();

        Validate.isTrue(actual <= maximum, "Player limit exceeded.");
        return actual == maximum;
    }

    public List<String> tryEnable() {
        List<String> tips = this.getEnableTips();
        if (tips.isEmpty()) state = GameState.WAITING;
        return tips;
    }

    public boolean disbale() {
        if (state.allowDisabling()) {
            state = GameState.UNAVAILABLE;
            return true;
        } else return false;
    }

    public GameState getState() {
        return state;
    }

    private ConfigFile getConfig(String name) {
        Validate.notNull(name);
        return plugin.getConfigManager().fromString(name);
    }

    private LocationConfig getLocationConfig() {
        return (LocationConfig) this.getConfig("location");
    }

    private SettingsConfig getSettingsConfig() {
        return (SettingsConfig) this.getConfig("settings");
    }

    private List<String> getEnableTips() {
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

    private void validateState(Function<GameState, Boolean> function) {
        Validate.notNull(function);
        Validate.isTrue(function.apply(state), "Unexpected game state: " + state.name());
    }

    private void validateState(GameState state) {
        Validate.notNull(state);
        validateState(state::equals);
    }

    private void validateAvailableState() {
        this.validateState(GameState::isAvailable);
    }

    private void broadcast(String message) {
        Validate.notNull(message);
        forEachPlayer(p -> p.sendMessage(message));
    }

    private void forEachPlayer(Consumer<Player> consumer) {
        Validate.notNull(consumer);
        playerMap.keySet().forEach(consumer);
    }
}
