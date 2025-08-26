package com.akira.undeadwave.core.item.weapon.ranged.shotgun;

import com.akira.core.api.tool.Tuple;
import com.akira.core.api.util.WorldUtils;
import com.akira.undeadwave.UndeadWave;
import com.akira.undeadwave.core.item.weapon.base.RangedWeapon;
import com.akira.undeadwave.core.item.weapon.WeaponAttackType;
import com.akira.undeadwave.core.item.weapon.WeaponType;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;

public class DiamondShotgun extends RangedWeapon {
    public DiamondShotgun(UndeadWave plugin) {
        super(plugin, WeaponType.DIAMOND_SHOTGUN, WeaponAttackType.RANGED,
                Material.DIAMOND_HOE,
                "钻石霰弹枪",
                new String[]{"钻石加成，值得信赖，", "瞬间制造范围巨额伤害。"},
                10, 70, 40, 30,
                (w, l) -> WorldUtils.playParticle(l, Particle.CLOUD, 20, 1, 1, 1, 0.1),
                new Tuple<>(Sound.ENTITY_GENERIC_EXPLODE, 2.0F),
                4, 12, 1, 180, 2.0,
                true, true);
    }
}
