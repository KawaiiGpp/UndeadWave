package com.akira.undeadwave.core.enemy.zombie;

import com.akira.undeadwave.UndeadWave;
import com.akira.undeadwave.core.enemy.EnemyEquipmentPreset;
import com.akira.undeadwave.core.enemy.EnemyType;
import org.bukkit.Material;
import org.bukkit.entity.Zombie;

public class DiamondZombie extends ZombieEnemy {
    public DiamondZombie(UndeadWave plugin) {
        super(plugin, EnemyType.DIAMOND_ZOMBIE);
    }

    protected void doEntityPresets(Zombie entity) {}

    protected EnemyEquipmentPreset getEquipmentPreset() {
        return new EnemyEquipmentPreset(
                Material.DIAMOND_SWORD,

                new Material[]{
                        Material.DIAMOND_BOOTS,
                        Material.DIAMOND_LEGGINGS,
                        Material.DIAMOND_CHESTPLATE,
                        Material.DIAMOND_HELMET
                }
        );
    }
}
