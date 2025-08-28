package com.akira.undeadwave.listener;

import com.akira.core.api.util.EventUtils;
import com.akira.undeadwave.UndeadWave;
import com.akira.undeadwave.core.Game;
import com.akira.undeadwave.core.enemy.Enemy;
import org.apache.commons.lang3.Validate;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
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
        if (!this.isIngamePlayer(player)) return;

        Monster monster = this.parseDamager(e.getDamager());
        if (monster == null) return;
        Enemy<?> enemy = this.parseEnemy(monster);
        if (enemy == null) return;

        e.setDamage(enemy.getEnemyType().getBaseDamage());
    }

    @EventHandler
    public void onReinforcement(CreatureSpawnEvent e) {
        EventUtils.cancelIf(e.getSpawnReason().equals(SpawnReason.REINFORCEMENTS), e);
    }

    private Enemy<?> parseEnemy(Entity entity) {
        Validate.notNull(entity);

        Game game = plugin.getGame();
        return game.getEnemyManager().fromEntity(entity);
    }

    private Monster parseDamager(Entity damager) {
        Validate.notNull(damager);

        if (damager instanceof Projectile projectile) {
            if ((projectile.getShooter() instanceof Monster shooter))
                return shooter;
        } else if (damager instanceof Monster monster)
            return monster;

        return null;
    }
}
