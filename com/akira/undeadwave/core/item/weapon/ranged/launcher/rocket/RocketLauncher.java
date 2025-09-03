package com.akira.undeadwave.core.item.weapon.ranged.launcher.rocket;

import com.akira.core.api.tool.Tuple;
import com.akira.undeadwave.UndeadWave;
import com.akira.undeadwave.core.item.weapon.base.RangedWeapon;
import com.akira.undeadwave.core.item.weapon.WeaponAttackType;
import com.akira.undeadwave.core.item.weapon.WeaponType;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;

public class RocketLauncher extends RangedWeapon {
    public RocketLauncher(UndeadWave plugin) {
        super(plugin, WeaponType.ROCKET_LAUNCHER, WeaponAttackType.RANGED,
                Material.STONE_SHOVEL,
                "火箭筒",
                new String[]{"硕大的肌肉，", "激情的发射。"},
                28, 80, 50, 70,
                Particle.EXPLOSION_HUGE,
                new Tuple<>(Sound.ENTITY_GENERIC_EXPLODE, 1.0F),
                12, 10, 2, 120, 1.0,
                true, false);
    }
}
