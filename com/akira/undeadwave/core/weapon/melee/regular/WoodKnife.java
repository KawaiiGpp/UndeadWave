package com.akira.undeadwave.core.weapon.melee.regular;

import com.akira.undeadwave.UndeadWave;
import com.akira.undeadwave.core.weapon.MeleeWeapon;
import com.akira.undeadwave.core.weapon.WeaponAttackType;
import com.akira.undeadwave.core.weapon.WeaponType;
import org.bukkit.Material;

public class WoodKnife extends MeleeWeapon {
    public WoodKnife(UndeadWave plugin) {
        super(plugin, WeaponType.WOOD_KNIFE, WeaponAttackType.MELEE,
                Material.WOODEN_SWORD,
                "木制匕首",
                new String[]{"这是你的第一把剑，", "能帮助你驱魔降妖。"},
                4, 50, 25, false, false, 1.0F, 0, 0, 0);
    }
}
