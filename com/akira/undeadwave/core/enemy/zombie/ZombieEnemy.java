package com.akira.undeadwave.core.enemy.zombie;

import com.akira.undeadwave.UndeadWave;
import com.akira.undeadwave.core.enemy.Enemy;
import com.akira.undeadwave.core.enemy.EnemyType;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Zombie;

public abstract class ZombieEnemy extends Enemy<Zombie> {
    public ZombieEnemy(UndeadWave plugin, EnemyType enemyType) {
        super(plugin, EntityType.ZOMBIE, Zombie.class, enemyType);
    }
}
