package com.akira.undeadwave.core.enemy.zombie;

import com.akira.undeadwave.UndeadWave;
import com.akira.undeadwave.core.enemy.EnemyEquipmentPreset;
import com.akira.undeadwave.core.enemy.EnemyType;
import org.bukkit.Material;
import org.bukkit.entity.Zombie;

public class GoldZombie extends ZombieEnemy {
    public GoldZombie(UndeadWave plugin) {
        super(plugin, EnemyType.GOLD_ZOMBIE);
    }

    protected void doEntityPresets(Zombie entity) {}

    protected EnemyEquipmentPreset getEquipmentPreset() {
        return new EnemyEquipmentPreset(
                Material.GOLDEN_SWORD,

                new Material[]{
                        Material.GOLDEN_BOOTS,
                        Material.GOLDEN_LEGGINGS,
                        Material.GOLDEN_CHESTPLATE,
                        Material.GOLDEN_HELMET
                }
        );
    }
}
