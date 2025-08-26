package com.akira.undeadwave.core.weapon.melee.sweep;

import com.akira.undeadwave.UndeadWave;
import com.akira.undeadwave.core.weapon.MeleeWeapon;
import com.akira.undeadwave.core.weapon.WeaponAttackType;
import com.akira.undeadwave.core.weapon.WeaponType;
import org.bukkit.Material;

public class WhirlwindSword extends MeleeWeapon {
    public WhirlwindSword(UndeadWave plugin) {
        super(plugin, WeaponType.WHIRLWIND_SWORD, WeaponAttackType.MELEE,
                Material.IRON_SWORD,
                "旋风剑",
                new String[]{"刮倒万物的存在，", "横扫千军，胜券在握。"},
                10, 100, 45, false, true, 1.6F, 140, 20, 5);
    }
}
