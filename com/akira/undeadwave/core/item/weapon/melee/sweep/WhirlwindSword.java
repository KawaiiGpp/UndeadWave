package com.akira.undeadwave.core.item.weapon.melee.sweep;

import com.akira.undeadwave.UndeadWave;
import com.akira.undeadwave.core.item.weapon.base.MeleeWeapon;
import com.akira.undeadwave.core.item.weapon.WeaponAttackType;
import com.akira.undeadwave.core.item.weapon.WeaponType;
import org.bukkit.Material;

public class WhirlwindSword extends MeleeWeapon {
    public WhirlwindSword(UndeadWave plugin) {
        super(plugin, WeaponType.WHIRLWIND_SWORD, WeaponAttackType.MELEE,
                Material.IRON_SWORD,
                "旋风剑",
                new String[]{"刮倒万物的存在，", "横扫千军，胜券在握。"},
                26, 180, 75, false, true, 1.6F, 180, 8, 8);
    }
}
