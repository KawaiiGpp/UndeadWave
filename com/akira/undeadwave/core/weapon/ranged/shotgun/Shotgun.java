package com.akira.undeadwave.core.weapon.ranged.shotgun;

import com.akira.core.api.tool.Tuple;
import com.akira.core.api.util.WorldUtils;
import com.akira.undeadwave.UndeadWave;
import com.akira.undeadwave.core.weapon.RangedWeapon;
import com.akira.undeadwave.core.weapon.WeaponAttackType;
import com.akira.undeadwave.core.weapon.WeaponType;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;

public class Shotgun extends RangedWeapon {
    public Shotgun(UndeadWave plugin) {
        super(plugin, WeaponType.SHOTGUN, WeaponAttackType.RANGED,
                Material.IRON_HOE,
                "霰弹枪",
                new String[]{"非常适合贴脸开大，", "瞬间制造范围大额伤害。"},
                4, 60, 30, 40,
                (w, l) -> WorldUtils.playParticle(l, Particle.CLOUD, 10, 1, 1, 1, 0.1),
                new Tuple<>(Sound.ENTITY_GENERIC_EXPLODE, 2.0F),
                3, 6, 1, 80, 2.0,
                true, true);
    }
}
