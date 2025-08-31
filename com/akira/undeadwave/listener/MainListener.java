package com.akira.undeadwave.listener;

import com.akira.undeadwave.UndeadWave;
import com.akira.undeadwave.config.LocationConfig;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageEvent;
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
        if (!(e.getEntity() instanceof Player player)) return;

        LocationConfig config = (LocationConfig) plugin
                .getConfigManager()
                .fromString("location");
        World world = player.getWorld();

        if (world.equals(config.getLobby().getWorld())
                || this.isIngamePlayer(player))
            e.setFoodLevel(20);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onDeath(EntityDamageEvent e) {
        if (!(e.getEntity() instanceof Player player)) return;
        if (!this.isIngamePlayer(player)) return;

        double finalDamage = e.getFinalDamage();
        if (finalDamage < player.getHealth()) return;

        e.setCancelled(true);
        plugin.getGame().endGame(false);
    }
}
