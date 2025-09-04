package com.akira.undeadwave.listener;

import com.akira.core.api.config.ConfigManager;
import com.akira.undeadwave.UndeadWave;
import com.akira.undeadwave.config.LocationConfig;
import com.akira.undeadwave.core.Game;
import com.akira.undeadwave.core.GameState;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.hanging.HangingBreakEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class MainListener extends ListenerBase {
    public MainListener(UndeadWave plugin) {
        super(plugin);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Game game = plugin.getGame();
        if (game.getState() == GameState.UNAVAILABLE) return;

        ConfigManager man = plugin.getConfigManager();
        LocationConfig config = (LocationConfig) man.fromString("location");
        Player player = e.getPlayer();

        player.setGameMode(GameMode.SURVIVAL);
        player.teleport(config.getLobby());
    }

    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        if (!(e.getEntity() instanceof Player player)) return;
        if (this.isInLobby(player)) e.setCancelled(true);
    }

    @EventHandler
    public void onHangBreak(HangingBreakEvent e) {
        if (this.isValidEntity(e.getEntity()))
            e.setCancelled(true);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if (this.isInLobby(e.getPlayer()))
            e.setCancelled(true);
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent e) {
        if (this.isValidPlayer(e.getPlayer()))
            e.setCancelled(true);
    }

    @EventHandler
    public void onHunger(FoodLevelChangeEvent e) {
        if (!(e.getEntity() instanceof Player player)) return;
        if (this.isValidPlayer(player)) e.setFoodLevel(20);
    }

    @EventHandler
    public void onCraft(CraftItemEvent e) {
        if (!(e.getWhoClicked() instanceof Player player)) return;
        if (this.isValidPlayer(player)) e.setCancelled(true);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        if (!this.isIngamePlayer(e.getPlayer())) return;
        plugin.getGame().endGame(false);
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
