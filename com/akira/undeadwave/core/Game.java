package com.akira.undeadwave.core;

import com.akira.core.api.config.ConfigFile;
import com.akira.core.api.util.PlayerUtils;
import com.akira.undeadwave.UndeadWave;
import com.akira.undeadwave.config.LocationConfig;
import com.akira.undeadwave.config.SettingsConfig;
import org.apache.commons.lang3.Validate;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private final UndeadWave plugin;
    private final List<Player> players;

    private GameState state;

    public Game(UndeadWave plugin) {
        Validate.notNull(plugin);

        this.plugin = plugin;
        this.state = GameState.UNAVAILABLE;
        this.players = new ArrayList<>();
    }

    public void join(Player player) {
        Validate.notNull(player);
        this.validateAvailableState();
        this.validateState(GameState.WAITING);

        Validate.isTrue(!players.contains(player), "Player already joined: " + player.getName());

        players.add(player);
        this.onPlayerJoin(player);
    }

    public void quit(Player player) {
        Validate.notNull(player);
        this.validateAvailableState();

        Validate.isTrue(players.contains(player), "Player not contained: " + player.getName());

        players.remove(player);
        this.onPlayerQuit(player);
    }

    public List<String> tryEnable() {
        List<String> tips = this.getEnableTips();
        if (tips.isEmpty()) state = GameState.WAITING;
        return tips;
    }

    public boolean disbale() {
        if (this.state.equals(GameState.WAITING)) {
            this.state = GameState.UNAVAILABLE;
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

    private void validateState(GameState state) {
        validateState(state, false);
    }

    private void validateState(GameState state, boolean reverse) {
        Validate.notNull(state);

        boolean condition = this.state.equals(state);
        if (reverse) condition = !condition;

        String message = reverse ?
                "Game state cannot be: " + state.name() :
                "Invalid game state: " + this.state + " (" + state.name() + " expected).";
        Validate.isTrue(condition, message);
    }

    private void validateAvailableState() {
        validateState(GameState.UNAVAILABLE, true);
    }

    private void onPlayerJoin(Player player) {
        int amount = players.size();
        int max = this.getSettingsConfig().getMinimumPlayerAmount();

        player.teleport(this.getLocationConfig().getSpawnpoint());
        this.broadcast("玩家 §a" + player.getName() + " §f加入了游戏（" + amount + "/" + max + "）。");
    }

    private void onPlayerQuit(Player player) {
        int amount = players.size();
        int max = this.getSettingsConfig().getMinimumPlayerAmount();

        this.broadcast("玩家 §a" + player.getName() + " §f离开了游戏（" + amount + "/" + max + "）。");
        player.teleport(this.getLocationConfig().getLobby());
    }

    private void broadcast(String message) {
        players.forEach(p -> p.sendMessage(message));
    }

    private void playSound(Sound sound, float pitch) {
        players.forEach(p -> PlayerUtils.playSound(p, sound, pitch));
    }

    private void playSound(Sound sound) {
        this.playSound(sound, 1.0F);
    }

    private void sendTitle(String title, String subTitle) {
        players.forEach(p -> PlayerUtils.sendTitle(p, title, subTitle));
    }

    private void sendActionBarTitle(String title) {
        players.forEach(p -> PlayerUtils.sendActionBarTitle(p, title));
    }
}
