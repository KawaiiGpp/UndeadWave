package com.akira.undeadwave.core.weapon.ranged.miner;

import com.akira.core.api.tool.Tuple;
import com.akira.core.api.util.WorldUtils;
import com.akira.undeadwave.UndeadWave;
import com.akira.undeadwave.core.weapon.RangedWeapon;
import com.akira.undeadwave.core.weapon.WeaponAttackType;
import com.akira.undeadwave.core.weapon.WeaponType;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;

public class NetheriteMiner extends RangedWeapon {
    public NetheriteMiner(UndeadWave plugin) {
        super(plugin, WeaponType.NETHERITE_MINER, WeaponAttackType.RANGED,
                Material.NETHERITE_PICKAXE,
                "合金矿工",
                new String[]{"来源于下界合金的力量，", "满足你对攻速的一切想象。"},
                12, 90, 40, 4,
                (w, l) -> WorldUtils.playParticle(l, Particle.REDSTONE, 1, 0, new Particle.DustOptions(Color.GRAY, 3)),
                new Tuple<>(Sound.BLOCK_METAL_PLACE, 1F),
                50, 1, 1, 220, 1.0,
                false, false);
    }
}
