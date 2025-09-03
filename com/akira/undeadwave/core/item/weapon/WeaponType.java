package com.akira.undeadwave.core.item.weapon;

import com.akira.core.api.util.NumberUtils;

public enum WeaponType {
    WOOD_KNIFE(50), IRON_KNIFE(200), DIAMOND_KNIFE(400),
    BLOODY_BLADE(400), VAMPIRIC_BLADE(1000),
    LUCKY_SWORD(200), FATE_SWORD(800),
    LONG_SWORD(400), WIND_SWORD(800), WHIRLWIND_SWORD(1200),
    SHOCK_BLADE(400), STORM_BLADE(1000),
    PIERCING_KNIFE(400), TRUE_KNIFE(800), TRUTH_SWORD(1200),

    PISTOL(50), GOLD_PISTOL(200),
    SHOTGUN(400), DIAMOND_SHOTGUN(1200),
    FIRE_LAUNCHER(400), LAVA_LAUNCHER(1200),
    GAMBLER_GUN(400), FATE_GUN(800),
    COPPER_MINER(400), GOLD_MINER(800), NETHERITE_MINER(1200),
    ROCKET_LAUNCHER(800), ANCIENT_ROCKET_LAUNCHER(1200),
    SNIPER(800), INFERNAL_SNIPER(1200);

    private final int cost;

    WeaponType(int cost) {
        NumberUtils.ensurePositive(cost);
        this.cost = cost;
    }

    public int getCost() {
        return cost;
    }
}
