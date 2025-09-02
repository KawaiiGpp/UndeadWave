package com.akira.undeadwave.core.enemy.zombie.tank;

import com.akira.undeadwave.UndeadWave;
import com.akira.undeadwave.core.enemy.EnemyEquipmentPreset;
import com.akira.undeadwave.core.enemy.EnemyType;
import com.akira.undeadwave.core.enemy.zombie.ZombieEnemy;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Zombie;

public class TankZombie extends ZombieEnemy {
    public TankZombie(UndeadWave plugin) {
        super(plugin, EnemyType.TANK_ZOMBIE);
    }

    protected void doEntityPresets(Zombie entity) {
        applyAddModifier(entity, Attribute.GENERIC_KNOCKBACK_RESISTANCE, "tank_modifier", 1.0);
    }

    protected EnemyEquipmentPreset getEquipmentPreset() {
        return new EnemyEquipmentPreset(
                null,

                new Material[]{
                        Material.NETHERITE_BOOTS,
                        Material.DIAMOND_LEGGINGS,
                        Material.DIAMOND_CHESTPLATE,
                        Material.NETHERITE_HELMET
                }
        );
    }
}
