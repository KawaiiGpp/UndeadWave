package com.akira.undeadwave.listener;

import com.akira.core.api.util.EntityUtils;
import com.akira.core.api.util.EventUtils;
import com.akira.undeadwave.UndeadWave;
import com.akira.undeadwave.core.Game;
import com.akira.undeadwave.core.enemy.Enemy;
import org.apache.commons.lang3.Validate;
import org.bukkit.Bukkit;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.*;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.scheduler.BukkitScheduler;

public class EnemyListener extends ListenerBase {
    public EnemyListener(UndeadWave plugin) {
        super(plugin);
    }

    @EventHandler
    public void onAttack(EntityDamageByEntityEvent e) {
        Monster monster = this.parseDamager(e.getDamager());
        if (monster == null) return;

        Enemy<?> enemy = this.parseEnemy(monster);
        if (enemy == null) return;

        Entity entity = e.getEntity();
        if (entity instanceof Player player) {
            if (!this.isIngamePlayer(player)) return;

            e.setDamage(enemy.getEnemyType().getBaseDamage());
            return;
        }

        e.setCancelled(true);
    }

    @EventHandler(ignoreCancelled = true)
    public void onReinforcement(CreatureSpawnEvent e) {
        EventUtils.cancelIf(e.getSpawnReason().equals(SpawnReason.REINFORCEMENTS), e);
    }

    @EventHandler(ignoreCancelled = true)
    public void onTargetChange(EntityTargetLivingEntityEvent e) {
        if (!(e.getEntity() instanceof Monster monster)) return;

        Enemy<?> enemy = this.parseEnemy(monster);
        if (enemy == null) return;

        LivingEntity target = e.getTarget();
        if (EventUtils.cancelUnless(target instanceof Player, e)) return;

        Player player = (Player) e.getTarget();
        if (this.isIngamePlayer(player)) return;

        e.setCancelled(true);
    }

    @EventHandler(ignoreCancelled = true)
    public void onShoot(ProjectileLaunchEvent e) {
        Projectile projectile = e.getEntity();

        Monster monster = this.parseDamager(projectile);
        if (monster == null) return;

        Enemy<?> enemy = this.parseEnemy(monster);
        if (enemy == null) return;

        BukkitScheduler scheduler = Bukkit.getScheduler();
        scheduler.runTaskLater(plugin, () -> EntityUtils.removeIfValid(projectile), 40);
    }

    @EventHandler
    public void onDeath(EntityDeathEvent e) {
        LivingEntity victim = e.getEntity();
        Enemy<?> enemy = this.parseEnemy(victim);
        if (enemy == null) return;

        e.setDroppedExp(0);
        e.getDrops().clear();
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
