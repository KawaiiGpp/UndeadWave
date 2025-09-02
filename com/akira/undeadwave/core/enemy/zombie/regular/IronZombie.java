package com.akira.undeadwave.core.enemy.zombie.regular;

import com.akira.undeadwave.UndeadWave;
import com.akira.undeadwave.core.enemy.EnemyEquipmentPreset;
import com.akira.undeadwave.core.enemy.EnemyType;
import com.akira.undeadwave.core.enemy.zombie.ZombieEnemy;
import org.bukkit.Material;
import org.bukkit.entity.Zombie;

public class IronZombie extends ZombieEnemy {
    public IronZombie(UndeadWave plugin) {
        super(plugin, EnemyType.IRON_ZOMBIE);
    }

    protected void doEntityPresets(Zombie entity) {}

    protected EnemyEquipmentPreset getEquipmentPreset() {
        return new EnemyEquipmentPreset(
                Material.IRON_SWORD,

                new Material[]{
                        Material.IRON_BOOTS,
                        Material.IRON_LEGGINGS,
                        Material.IRON_CHESTPLATE,
                        Material.IRON_HELMET
                }
        );
    }
}
