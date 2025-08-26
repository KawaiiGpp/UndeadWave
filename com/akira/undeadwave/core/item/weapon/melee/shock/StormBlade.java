package com.akira.undeadwave.core.item.weapon.melee.shock;

import com.akira.undeadwave.UndeadWave;
import com.akira.undeadwave.core.item.weapon.base.MeleeWeapon;
import com.akira.undeadwave.core.item.weapon.WeaponAttackType;
import com.akira.undeadwave.core.item.weapon.WeaponType;
import org.bukkit.Material;

public class StormBlade extends MeleeWeapon {
    public StormBlade(UndeadWave plugin) {
        super(plugin, WeaponType.STORM_BLADE, WeaponAttackType.MELEE,
                Material.IRON_SWORD,
                "风暴之剑",
                new String[]{"如同风暴席卷万物而去，", "为下一轮战斗争取时间。"},
                7, 90, 35, false, true, 3.5F, 0, 20, 8);
    }
}
