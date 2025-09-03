package com.akira.undeadwave.core.item.weapon.melee.shock;

import com.akira.undeadwave.UndeadWave;
import com.akira.undeadwave.core.item.weapon.base.MeleeWeapon;
import com.akira.undeadwave.core.item.weapon.WeaponAttackType;
import com.akira.undeadwave.core.item.weapon.WeaponType;
import org.bukkit.Material;

public class ShockBlade extends MeleeWeapon {
    public ShockBlade(UndeadWave plugin) {
        super(plugin, WeaponType.SHOCK_BLADE, WeaponAttackType.MELEE,
                Material.WOODEN_SWORD,
                "击退剑",
                new String[]{"短暂击退敌人，", "为自己争取时间。"},
                10, 80, 45, false, true, 3F, 0, 0, 0);
    }
}
