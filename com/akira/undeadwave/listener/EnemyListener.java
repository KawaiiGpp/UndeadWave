package com.akira.undeadwave.listener;

import com.akira.core.api.util.EventUtils;
import com.akira.undeadwave.UndeadWave;
import com.akira.undeadwave.core.Game;
import com.akira.undeadwave.core.enemy.Enemy;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class EnemyListener extends ListenerBase {
    public EnemyListener(UndeadWave plugin) {
        super(plugin);
    }

    @EventHandler
    public void onAttack(EntityDamageByEntityEvent e) {
        if (!(e.getEntity() instanceof Player player)) return;
        if (!(e.getDamager() instanceof Monster monster)) return;
        if (!this.isIngamePlayer(player)) return;

        Game game = plugin.getGame();
        Enemy<?> enemy = game.getEnemyManager().fromMonster(monster);
        if (enemy == null) return;

        e.setDamage(enemy.getEnemyType().getBaseDamage());
    }

    @EventHandler
    public void onReinforcement(CreatureSpawnEvent e) {
        EventUtils.cancelIf(e.getSpawnReason().equals(SpawnReason.REINFORCEMENTS), e);
    }
}
