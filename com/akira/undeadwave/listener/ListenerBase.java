package com.akira.undeadwave.listener;

import com.akira.core.api.config.ConfigManager;
import com.akira.undeadwave.UndeadWave;
import com.akira.undeadwave.config.LocationConfig;
import com.akira.undeadwave.core.Game;
import com.akira.undeadwave.core.GameState;
import org.apache.commons.lang3.Validate;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public abstract class ListenerBase implements Listener {
    protected final UndeadWave plugin;

    public ListenerBase(UndeadWave plugin) {
        Validate.notNull(plugin);
        this.plugin = plugin;
    }

    protected final boolean isIngamePlayer(Entity entity) {
        Validate.notNull(entity);
        if (!(entity instanceof Player player)) return false;

        Game game = plugin.getGame();
        if (game.getState() != GameState.STARTED) return false;
        return game.getSession().isOwnedBy(player);
    }

    protected final boolean isInLobby(Player player) {
        Validate.notNull(player);

        Game game = plugin.getGame();
        if (game.getState() == GameState.UNAVAILABLE) return false;

        LocationConfig config = (LocationConfig) plugin.getConfigManager().fromString("location");
        return player.getWorld().equals(config.getLobby().getWorld());
    }

    protected final boolean isValidPlayer(Player player) {
        Validate.notNull(player);
        return this.isInLobby(player) || this.isIngamePlayer(player);
    }

    protected final boolean isValidEntity(Entity entity) {
        Validate.notNull(entity);

        if (plugin.getGame().getState() == GameState.UNAVAILABLE) return false;

        World world = entity.getWorld();
        ConfigManager man = plugin.getConfigManager();
        LocationConfig location = (LocationConfig) man.fromString("location");

        return world.equals(location.getLobby().getWorld())
                || world.equals(location.getSpawnpoint().getWorld());
    }
}
