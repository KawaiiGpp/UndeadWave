package com.akira.undeadwave.core.enemy.zombie;

import com.akira.undeadwave.UndeadWave;
import com.akira.undeadwave.core.enemy.DyedEnemy;
import com.akira.undeadwave.core.enemy.EnemyType;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Zombie;

public abstract class DyedZombieEnemy extends DyedEnemy<Zombie> {
    public DyedZombieEnemy(UndeadWave plugin, EnemyType enemyType) {
        super(plugin, EntityType.ZOMBIE, Zombie.class, enemyType);
    }
}
