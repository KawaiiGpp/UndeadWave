package com.akira.undeadwave.core.item.weapon.melee.regular;

import com.akira.undeadwave.UndeadWave;
import com.akira.undeadwave.core.item.weapon.base.MeleeWeapon;
import com.akira.undeadwave.core.item.weapon.WeaponAttackType;
import com.akira.undeadwave.core.item.weapon.WeaponType;
import org.bukkit.Material;

public class WoodKnife extends MeleeWeapon {
    public WoodKnife(UndeadWave plugin) {
        super(plugin, WeaponType.WOOD_KNIFE, WeaponAttackType.MELEE,
                Material.WOODEN_SWORD,
                "木制匕首",
                new String[]{"这是你的第一把剑，", "能帮助你驱魔降妖。"},
                6, 60, 30, false, false, 1.0F, 0, 0, 0);
    }
}
