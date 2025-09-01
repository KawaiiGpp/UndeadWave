package com.akira.undeadwave.core.enemy.skeleton;

import com.akira.undeadwave.UndeadWave;
import com.akira.undeadwave.core.enemy.EnemyEquipmentPreset;
import com.akira.undeadwave.core.enemy.EnemyType;
import org.bukkit.Material;
import org.bukkit.entity.Skeleton;

public class CommonSkeleton extends SkeletonEnemy {
    public CommonSkeleton(UndeadWave plugin) {
        super(plugin, EnemyType.COMMON_SKELETON);
    }

    protected void doEntityPresets(Skeleton entity) {}

    protected EnemyEquipmentPreset getEquipmentPreset() {
        return new EnemyEquipmentPreset(Material.BOW, new Material[]{null, null, null, null});
    }
}
