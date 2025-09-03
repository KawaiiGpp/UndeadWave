package com.akira.undeadwave.core.item.weapon.ranged.pistol;

import com.akira.core.api.util.WorldUtils;
import com.akira.undeadwave.UndeadWave;
import com.akira.undeadwave.core.item.weapon.base.RangedWeapon;
import com.akira.undeadwave.core.item.weapon.WeaponAttackType;
import com.akira.undeadwave.core.item.weapon.WeaponType;
import org.bukkit.Material;
import org.bukkit.Particle;

public class Pistol extends RangedWeapon {
    public Pistol(UndeadWave plugin) {
        super(plugin, WeaponType.PISTOL, WeaponAttackType.RANGED,
                Material.WOODEN_HOE,
                "手枪",
                new String[]{"这是你的第一把枪，", "与你并肩作战的队友。"},
                6, 60, 30, 10,
                (w, l) -> WorldUtils.playParticle(l, Particle.CRIT), null,
                20, 1, 1, 80, 1.0,
                false, false);
    }
}
