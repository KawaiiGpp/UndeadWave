package com.akira.undeadwave.core.item.weapon.melee.regular;

import com.akira.undeadwave.UndeadWave;
import com.akira.undeadwave.core.item.weapon.base.MeleeWeapon;
import com.akira.undeadwave.core.item.weapon.WeaponAttackType;
import com.akira.undeadwave.core.item.weapon.WeaponType;
import org.bukkit.Material;

public class IronKnife extends MeleeWeapon {
    public IronKnife(UndeadWave plugin) {
        super(plugin, WeaponType.IRON_KNIFE, WeaponAttackType.MELEE,
                Material.IRON_SWORD,
                "铁制匕首",
                new String[]{"精心锻造而成的好剑，", "材料升级后战力更强了。"},
                10, 80, 40, false, false, 1.25F, 0, 0, 0);
    }
}
