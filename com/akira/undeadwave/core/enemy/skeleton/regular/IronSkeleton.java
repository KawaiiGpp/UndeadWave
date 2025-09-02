package com.akira.undeadwave.core.enemy.skeleton.regular;

import com.akira.undeadwave.UndeadWave;
import com.akira.undeadwave.core.enemy.EnemyEquipmentPreset;
import com.akira.undeadwave.core.enemy.EnemyType;
import com.akira.undeadwave.core.enemy.skeleton.SkeletonEnemy;
import org.bukkit.Material;
import org.bukkit.entity.Skeleton;

public class IronSkeleton extends SkeletonEnemy {
    public IronSkeleton(UndeadWave plugin) {
        super(plugin, EnemyType.IRON_SKELETON);
    }

    protected void doEntityPresets(Skeleton entity) {}

    protected EnemyEquipmentPreset getEquipmentPreset() {
        return new EnemyEquipmentPreset(
                Material.BOW,

                new Material[]{
                        Material.IRON_BOOTS,
                        Material.IRON_LEGGINGS,
                        Material.IRON_CHESTPLATE,
                        Material.IRON_HELMET
                }
        );
    }
}
