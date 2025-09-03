package com.akira.undeadwave.core.item.weapon;

import com.akira.core.api.util.NumberUtils;

public enum WeaponType {
    WOOD_KNIFE(50), IRON_KNIFE(400), DIAMOND_KNIFE(1200),
    BLOODY_BLADE(800), VAMPIRIC_BLADE(2000),
    LUCKY_SWORD(500), FATE_SWORD(2200),
    LONG_SWORD(600), WIND_SWORD(1200), WHIRLWIND_SWORD(2800),
    SHOCK_BLADE(1200), STORM_BLADE(2200),
    PIERCING_KNIFE(800), TRUE_KNIFE(1600), TRUTH_SWORD(2800),

    PISTOL(50), GOLD_PISTOL(400),
    SHOTGUN(700), DIAMOND_SHOTGUN(3200),
    FIRE_LAUNCHER(1600), LAVA_LAUNCHER(3000),
    GAMBLER_GUN(700), FATE_GUN(1400),
    COPPER_MINER(500), GOLD_MINER(1600), NETHERITE_MINER(3500),
    ROCKET_LAUNCHER(1600), ANCIENT_ROCKET_LAUNCHER(2800),
    SNIPER(1200), INFERNAL_SNIPER(2000);

    private final int cost;

    WeaponType(int cost) {
        NumberUtils.ensurePositive(cost);
        this.cost = cost;
    }

    public int getCost() {
        return cost;
    }
}
