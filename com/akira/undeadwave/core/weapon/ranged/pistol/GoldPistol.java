package com.akira.undeadwave.core.weapon.ranged.pistol;

import com.akira.core.api.tool.Tuple;
import com.akira.core.api.util.WorldUtils;
import com.akira.undeadwave.UndeadWave;
import com.akira.undeadwave.core.weapon.RangedWeapon;
import com.akira.undeadwave.core.weapon.WeaponAttackType;
import com.akira.undeadwave.core.weapon.WeaponType;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;

public class GoldPistol extends RangedWeapon {
    public GoldPistol(UndeadWave plugin) {
        super(plugin, WeaponType.GOLD_PISTOL, WeaponAttackType.RANGED,
                Material.GOLDEN_HOE,
                "黄金手枪",
                new String[]{"高纯度黄金的加持下，", "战力得到显著提升。"},
                6, 70, 30, 8,
                (w, l) -> WorldUtils.playParticle(l, Particle.BLOCK_DUST, 1, 0, Material.GOLD_BLOCK.createBlockData()),
                new Tuple<>(Sound.BLOCK_STONE_PLACE, 1F),
                40, 1, 1, 100, 1.0,
                false, false);
    }
}
