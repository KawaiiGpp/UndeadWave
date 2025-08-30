package com.akira.undeadwave.listener;

import com.akira.undeadwave.UndeadWave;
import com.akira.undeadwave.core.Game;
import com.akira.undeadwave.core.GameState;
import org.apache.commons.lang3.Validate;
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
}
