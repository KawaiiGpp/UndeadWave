package com.akira.undeadwave.core.weapon.melee.regular;

import com.akira.undeadwave.UndeadWave;
import com.akira.undeadwave.core.weapon.base.MeleeWeapon;
import com.akira.undeadwave.core.weapon.WeaponAttackType;
import com.akira.undeadwave.core.weapon.WeaponType;
import org.bukkit.Material;

public class DiamondKnife extends MeleeWeapon {
    public DiamondKnife(UndeadWave plugin) {
        super(plugin, WeaponType.DIAMOND_KNIFE, WeaponAttackType.MELEE,
                Material.DIAMOND_SWORD,
                "钻石匕首",
                new String[]{"使用坚硬的钻石制成，", "虽然昂贵，但相信它值得。"},
                10, 100, 55, false, true, 1.75F, 0, 0, 0);
    }
}
