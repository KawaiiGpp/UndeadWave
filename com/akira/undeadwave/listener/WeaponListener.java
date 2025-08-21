package com.akira.undeadwave.listener;

import com.akira.undeadwave.UndeadWave;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;

public class WeaponListener extends ListenerBase {
    public WeaponListener(UndeadWave plugin) {
        super(plugin);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        if (!this.isIngamePlayer(player)) return;
    }
}
