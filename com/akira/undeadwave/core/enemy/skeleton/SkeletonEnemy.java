package com.akira.undeadwave.core.enemy.skeleton;

import com.akira.undeadwave.UndeadWave;
import com.akira.undeadwave.core.enemy.Enemy;
import com.akira.undeadwave.core.enemy.EnemyType;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Skeleton;

public abstract class SkeletonEnemy extends Enemy<Skeleton> {
    public SkeletonEnemy(UndeadWave plugin, EnemyType enemyType) {
        super(plugin, EntityType.SKELETON, Skeleton.class, enemyType);
    }
}
