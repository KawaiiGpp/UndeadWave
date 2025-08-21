package com.akira.undeadwave.listener;

import com.akira.undeadwave.UndeadWave;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class MainListener extends ListenerBase {
    public MainListener(UndeadWave plugin) {
        super(plugin);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        if (!this.isIngamePlayer(e.getPlayer())) return;
        plugin.getGame().endGame(false);
    }

    @EventHandler
    public void onHunger(FoodLevelChangeEvent e) {
        if (this.isIngamePlayer(e.getEntity())) e.setFoodLevel(20);
    }
}
