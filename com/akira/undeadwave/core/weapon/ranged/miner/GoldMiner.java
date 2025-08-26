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

public class GoldMiner extends RangedWeapon {
    public GoldMiner(UndeadWave plugin) {
        super(plugin, WeaponType.GOLD_MINER, WeaponAttackType.RANGED,
                Material.GOLDEN_PICKAXE,
                "黄金矿工",
                new String[]{"黄金的信仰，", "黄金的输出。"},
                10, 70, 30, 7,
                (w, l) -> WorldUtils.playParticle(l, Particle.REDSTONE, 1, 0, new Particle.DustOptions(Color.YELLOW, 2)),
                new Tuple<>(Sound.BLOCK_METAL_PLACE, 1F),
                40, 1, 1, 150, 1.0,
                false, false);
    }
}
