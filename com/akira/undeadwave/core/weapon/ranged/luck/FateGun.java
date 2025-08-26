package com.akira.undeadwave.core.weapon.ranged.luck;

import com.akira.core.api.tool.Tuple;
import com.akira.core.api.util.WorldUtils;
import com.akira.undeadwave.UndeadWave;
import com.akira.undeadwave.core.weapon.base.RangedWeapon;
import com.akira.undeadwave.core.weapon.WeaponAttackType;
import com.akira.undeadwave.core.weapon.WeaponType;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;

public class FateGun extends RangedWeapon {
    public FateGun(UndeadWave plugin) {
        super(plugin, WeaponType.FATE_GUN, WeaponAttackType.RANGED,
                Material.IRON_PICKAXE,
                "命运之枪",
                new String[]{"相信命运的指引，", "把一切交给命运。"},
                9, 350, 20, 7,
                (w, l) -> WorldUtils.playParticle(l, Particle.GLOW),
                new Tuple<>(Sound.BLOCK_BAMBOO_HIT, 2.0F),
                40, 1, 1, 160, 1.0,
                false, false);
    }
}
