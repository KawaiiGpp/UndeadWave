package com.akira.undeadwave.core.weapon.ranged;

import com.akira.undeadwave.UndeadWave;
import com.akira.undeadwave.core.weapon.WeaponAttackType;
import com.akira.undeadwave.core.weapon.WeaponType;
import org.bukkit.Material;
import org.bukkit.Particle;

public class Pistol extends RangedWeapon {
    public Pistol(UndeadWave plugin) {
        super(plugin, plugin.getGame(),
                WeaponType.PISTOL,
                WeaponAttackType.RANGED,
                Material.WOODEN_HOE,
                "手枪",
                new String[]{"你的第一把枪，", "也是你的可靠战友。"},
                4,
                0,
                0,
                15,
                Particle.VILLAGER_HAPPY,
                5,
                1,
                0.5,
                50,
                1.0,
                true,
                true);
    }
}
