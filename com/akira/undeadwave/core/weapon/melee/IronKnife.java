package com.akira.undeadwave.core.weapon.melee;

import com.akira.undeadwave.UndeadWave;
import com.akira.undeadwave.core.weapon.MeleeWeapon;
import com.akira.undeadwave.core.weapon.WeaponAttackType;
import com.akira.undeadwave.core.weapon.WeaponType;
import org.bukkit.Material;

public class IronKnife extends MeleeWeapon {
    public IronKnife(UndeadWave plugin) {
        super(plugin, WeaponType.IRON_KNIFE, WeaponAttackType.MELEE,
                Material.IRON_SWORD,
                "铁制匕首",
                new String[]{"精心锻造而成的好剑，", "材料升级后战力更强了。"},
                6, 75, 40, false, false, 1.25F, 0, 0, 0);
    }
}
