package com.akira.undeadwave.core.weapon.ranged.luck;

import com.akira.core.api.tool.Tuple;
import com.akira.core.api.util.WorldUtils;
import com.akira.undeadwave.UndeadWave;
import com.akira.undeadwave.core.weapon.RangedWeapon;
import com.akira.undeadwave.core.weapon.WeaponAttackType;
import com.akira.undeadwave.core.weapon.WeaponType;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;

public class GamblerGun extends RangedWeapon {
    public GamblerGun(UndeadWave plugin) {
        super(plugin, WeaponType.GAMBLER_GUN, WeaponAttackType.RANGED,
                Material.WOODEN_PICKAXE,
                "赌徒之枪",
                new String[]{"相信自己的运气，", "体验一把赌狗的快乐。"},
                6, 275, 10, 10,
                (w, l) -> WorldUtils.playParticle(l, Particle.CRIT_MAGIC),
                new Tuple<>(Sound.BLOCK_BAMBOO_HIT, 1.0F),
                30, 1, 1, 120, 1.0,
                false, false);
    }
}
