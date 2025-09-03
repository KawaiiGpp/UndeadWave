package com.akira.undeadwave.core.item.weapon.ranged.luck;

import com.akira.core.api.tool.Tuple;
import com.akira.core.api.util.WorldUtils;
import com.akira.undeadwave.UndeadWave;
import com.akira.undeadwave.core.item.weapon.base.RangedWeapon;
import com.akira.undeadwave.core.item.weapon.WeaponAttackType;
import com.akira.undeadwave.core.item.weapon.WeaponType;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;

public class GamblerGun extends RangedWeapon {
    public GamblerGun(UndeadWave plugin) {
        super(plugin, WeaponType.GAMBLER_GUN, WeaponAttackType.RANGED,
                Material.WOODEN_PICKAXE,
                "赌徒之枪",
                new String[]{"相信自己的运气，", "体验一把赌狗的快乐。"},
                8, 430, 20, 7,
                (w, l) -> WorldUtils.playParticle(l, Particle.CRIT_MAGIC),
                new Tuple<>(Sound.BLOCK_BAMBOO_HIT, 1.0F),
                25, 1, 1, 270, 1.0,
                false, false);
    }
}
