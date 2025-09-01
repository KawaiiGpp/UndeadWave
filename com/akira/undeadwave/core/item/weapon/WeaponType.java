package com.akira.undeadwave.core.item.weapon;

import com.akira.core.api.util.NumberUtils;

public enum WeaponType {
    WOOD_KNIFE(50), IRON_KNIFE(150), DIAMOND_KNIFE(300),
    BLOODY_BLADE(175), VAMPIRIC_BLADE(400),
    LUCKY_SWORD(150), FATE_SWORD(375),
    LONG_SWORD(275), WIND_SWORD(450), WHIRLWIND_SWORD(600),
    SHOCK_BLADE(225), STORM_BLADE(375),
    PIERCING_KNIFE(250), TRUE_KNIFE(400), TRUTH_SWORD(600),

    PISTOL(50), GOLD_PISTOL(125),
    SHOTGUN(300), DIAMOND_SHOTGUN(600),
    FIRE_LAUNCHER(250), LAVA_LAUNCHER(500),
    GAMBLER_GUN(225), FATE_GUN(375),
    COPPER_MINER(175), GOLD_MINER(350), NETHERITE_MINER(700),
    ROCKET_LAUNCHER(350), ANCIENT_ROCKET_LAUNCHER(600),
    SNIPER(300), INFERNAL_SNIPER(550);

    private final int cost;

    WeaponType(int cost) {
        NumberUtils.ensurePositive(cost);
        this.cost = cost;
    }

    public int getCost() {
        return cost;
    }
}
