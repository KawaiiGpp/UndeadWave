package com.akira.undeadwave.core.weapon.melee.shock;

import com.akira.undeadwave.UndeadWave;
import com.akira.undeadwave.core.weapon.MeleeWeapon;
import com.akira.undeadwave.core.weapon.WeaponAttackType;
import com.akira.undeadwave.core.weapon.WeaponType;
import org.bukkit.Material;

public class ShockBlade extends MeleeWeapon {
    public ShockBlade(UndeadWave plugin) {
        super(plugin, WeaponType.SHOCK_BLADE, WeaponAttackType.MELEE,
                Material.WOODEN_SWORD,
                "击退剑",
                new String[]{"短暂击退敌人，", "为自己争取时间。"},
                5, 50, 20, false, true, 2F, 0, 0, 0);
    }
}
