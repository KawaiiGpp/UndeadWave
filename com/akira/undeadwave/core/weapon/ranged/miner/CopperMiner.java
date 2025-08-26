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
import org.bukkit.Particle.DustOptions;
import org.bukkit.Sound;

public class CopperMiner extends RangedWeapon {
    public CopperMiner(UndeadWave plugin) {
        super(plugin, WeaponType.COPPER_MINER, WeaponAttackType.RANGED,
                Material.STONE_PICKAXE,
                "青铜矿工",
                new String[]{"有一天矿工忽然发现，", "他的镐子竟然能发射子弹。"},
                8, 40, 20, 10,
                (w, l) -> WorldUtils.playParticle(l, Particle.REDSTONE, 1, 0, new DustOptions(Color.fromRGB(100, 40, 40), 1)),
                new Tuple<>(Sound.BLOCK_METAL_PLACE, 1F),
                30, 1, 1, 100, 1.0,
                false, false);
    }
}
