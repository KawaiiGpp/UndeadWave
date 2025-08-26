package com.akira.undeadwave.core.weapon.ranged.launcher.fire;

import com.akira.core.api.tool.Tuple;
import com.akira.core.api.util.WorldUtils;
import com.akira.undeadwave.UndeadWave;
import com.akira.undeadwave.core.weapon.RangedWeapon;
import com.akira.undeadwave.core.weapon.WeaponAttackType;
import com.akira.undeadwave.core.weapon.WeaponType;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;

public class LavaLauncher extends RangedWeapon {
    public LavaLauncher(UndeadWave plugin) {
        super(plugin, WeaponType.LAVA_LAUNCHER, WeaponAttackType.RANGED,
                Material.GOLDEN_SHOVEL,
                "岩浆发射器",
                new String[]{"喷出炙热的岩浆，", "对敌人造成致命打击。"},
                10, 65, 35, 20,
                (w, l) -> WorldUtils.playParticle(l, Particle.LAVA, 2),
                new Tuple<>(Sound.ITEM_FIRECHARGE_USE, 1.0F),
                15, 12, 0.5, 140, 1.0,
                true, false);
    }
}
