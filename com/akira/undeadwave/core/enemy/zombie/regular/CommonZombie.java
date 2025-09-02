package com.akira.undeadwave.core.enemy.zombie.regular;

import com.akira.undeadwave.UndeadWave;
import com.akira.undeadwave.core.enemy.EnemyEquipmentPreset;
import com.akira.undeadwave.core.enemy.EnemyType;
import com.akira.undeadwave.core.enemy.zombie.ZombieEnemy;
import org.bukkit.entity.Zombie;

public class CommonZombie extends ZombieEnemy {
    public CommonZombie(UndeadWave plugin) {
        super(plugin, EnemyType.COMMON_ZOMBIE);
    }

    protected void doEntityPresets(Zombie entity) {}

    protected EnemyEquipmentPreset getEquipmentPreset() {
        return null;
    }
}
