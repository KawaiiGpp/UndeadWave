package com.akira.undeadwave.core.weapon.ranged.launcher.rocket;

import com.akira.core.api.tool.Tuple;
import com.akira.undeadwave.UndeadWave;
import com.akira.undeadwave.core.weapon.RangedWeapon;
import com.akira.undeadwave.core.weapon.WeaponAttackType;
import com.akira.undeadwave.core.weapon.WeaponType;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;

public class RocketLauncher extends RangedWeapon {
    public RocketLauncher(UndeadWave plugin) {
        super(plugin, WeaponType.ROCKET_LAUNCHER, WeaponAttackType.RANGED,
                Material.STONE_SHOVEL,
                "火箭筒",
                new String[]{"硕大的肌肉，", "激情的战斗。"},
                9, 60, 30, 70,
                Particle.EXPLOSION_HUGE,
                new Tuple<>(Sound.ENTITY_GENERIC_EXPLODE, 1.0F),
                15, 4, 2, 90, 1.0,
                true, false);
    }
}
