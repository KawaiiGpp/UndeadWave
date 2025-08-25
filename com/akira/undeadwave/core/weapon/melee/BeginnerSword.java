package com.akira.undeadwave.core.weapon.melee;

import com.akira.undeadwave.UndeadWave;
import com.akira.undeadwave.core.weapon.WeaponAttackType;
import com.akira.undeadwave.core.weapon.WeaponType;
import org.bukkit.Material;

public class BeginnerSword extends MeleeWeapon {
    public BeginnerSword(UndeadWave plugin) {
        super(plugin, plugin.getGame(),
                WeaponType.BEGINNER_SWORD,
                WeaponAttackType.MELEE,
                Material.WOODEN_SWORD,
                "桃木剑",
                new String[]{"驱魔降妖的好工具，", "你的第一把趁手武器。"},
                4,
                50,
                30,
                true,
                true,
                2.5F,
                15,
                12,
                20);
    }
}
