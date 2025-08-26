package com.akira.undeadwave.core.weapon.ranged.pistol;

import com.akira.core.api.util.WorldUtils;
import com.akira.undeadwave.UndeadWave;
import com.akira.undeadwave.core.weapon.RangedWeapon;
import com.akira.undeadwave.core.weapon.WeaponAttackType;
import com.akira.undeadwave.core.weapon.WeaponType;
import org.bukkit.Material;
import org.bukkit.Particle;

public class Pistol extends RangedWeapon {
    public Pistol(UndeadWave plugin) {
        super(plugin, WeaponType.PISTOL, WeaponAttackType.RANGED,
                Material.WOODEN_HOE,
                "手枪",
                new String[]{"这是你的第一把枪，", "与你并肩作战的队友。"},
                4, 40, 20, 10,
                (w, l) -> WorldUtils.playParticle(l, Particle.CRIT), null,
                30, 1, 1, 80, 1.0,
                false, false);
    }
}
