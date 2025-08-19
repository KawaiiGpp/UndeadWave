package com.akira.undeadwave.listener;

import com.akira.undeadwave.UndeadWave;
import com.akira.undeadwave.core.Game;
import com.akira.undeadwave.core.GameState;
import org.apache.commons.lang3.Validate;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class MainListener implements Listener {
    private final UndeadWave plugin;

    public MainListener(UndeadWave plugin) {
        Validate.notNull(plugin);
        this.plugin = plugin;
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Game game = plugin.getGame();
        GameState state = game.getState();
        Player player = e.getPlayer();

        if (!state.isIn(GameState.STARTED, GameState.WAITING)) return;
        if (!game.includes(player)) return;

        game.quit(player);
    }
}
