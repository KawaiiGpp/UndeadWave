package com.akira.undeadwave.core.item.weapon.ranged.sniper;

import com.akira.core.api.tool.Tuple;
import com.akira.core.api.util.WorldUtils;
import com.akira.undeadwave.UndeadWave;
import com.akira.undeadwave.core.item.weapon.base.RangedWeapon;
import com.akira.undeadwave.core.item.weapon.WeaponAttackType;
import com.akira.undeadwave.core.item.weapon.WeaponType;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;

public class Sniper extends RangedWeapon {
    public Sniper(UndeadWave plugin) {
        super(plugin, WeaponType.SNIPER, WeaponAttackType.RANGED,
                Material.WOODEN_SHOVEL,
                "狙击枪",
                new String[]{"远程精准打击，", "露头就秒。"},
                24, 70, 40, 30,
                (w, l) -> WorldUtils.playParticle(l, Particle.SMOKE_NORMAL),
                new Tuple<>(Sound.BLOCK_GRAVEL_STEP, 2.0F),
                60, 2, 1, 140, 1,
                true, false);
    }
}
