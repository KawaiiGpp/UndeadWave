package com.akira.undeadwave.core.enemy.skeleton.regular;

import com.akira.undeadwave.UndeadWave;
import com.akira.undeadwave.core.enemy.EnemyEquipmentPreset;
import com.akira.undeadwave.core.enemy.EnemyType;
import com.akira.undeadwave.core.enemy.skeleton.SkeletonEnemy;
import org.bukkit.Material;
import org.bukkit.entity.Skeleton;

public class GoldSkeleton extends SkeletonEnemy {
    public GoldSkeleton(UndeadWave plugin) {
        super(plugin, EnemyType.GOLD_SKELETON);
    }

    protected void doEntityPresets(Skeleton entity) {}

    protected EnemyEquipmentPreset getEquipmentPreset() {
        return new EnemyEquipmentPreset(
                Material.BOW,

                new Material[]{
                        Material.GOLDEN_BOOTS,
                        Material.GOLDEN_LEGGINGS,
                        Material.GOLDEN_CHESTPLATE,
                        Material.GOLDEN_HELMET
                }
        );
    }
}
