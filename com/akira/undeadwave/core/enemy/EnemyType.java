package com.akira.undeadwave.core.enemy;

import com.akira.core.api.util.NumberUtils;
import org.apache.commons.lang3.Validate;

public enum EnemyType {
    COMMON_ZOMBIE(20, 6, 0, 1, 5, 100, 35),
    GOLD_ZOMBIE(20, 10, 10, 5, 8, 100, 45),
    IRON_ZOMBIE(20, 14, 20, 8, 12, 100, 55),
    DIAMOND_ZOMBIE(20, 18, 30, 12, Integer.MAX_VALUE, 100, 65),

    SPEEDSTER_ZOMBIE(40, 8, 60, 6, 8, 15, 90),
    LIGHTNING_ZOMBIE(40, 12, 100, 11, Integer.MAX_VALUE, 15, 100),

    THUNDERBLOT_ZOMBIE(50, 18, 20, 7, 10, 30, 110),
    SHADOW_ASSASSIN_ZOMBIE(70, 24, 40, 13, Integer.MAX_VALUE, 20, 120),

    ROCK_ZOMBIE(30, 5, 0, 3, 6, 20, 50),
    TANK_ZOMBIE(40, 10, 0, 10, Integer.MAX_VALUE, 20, 70),


    COMMON_SKELETON(20, 4, 0, 4, 7, 30, 50),
    GOLD_SKELETON(20, 8, 15, 7, 10, 30, 65),
    IRON_SKELETON(20, 12, 30, 10, Integer.MAX_VALUE, 30, 80),

    IMPACT_SKELETON(40, 8, 20, 7, Integer.MAX_VALUE, 5, 100),
    SNIPER_SKELETON(40, 18, 20, 11, Integer.MAX_VALUE, 5, 120);

    private final double maxHealth;
    private final double baseDamage;
    private final int speedBonus;

    private final int availableRoundFrom;
    private final int availableRoundTo;

    private final int weight;
    private final int coins;

    EnemyType(double maxHealth, double baseDamage, int speedBonus,
              int availableRoundFrom, int availableRoundTo,
              int weight, int coins) {
        NumberUtils.ensurePositive(maxHealth);
        NumberUtils.ensurePositive(baseDamage);
        NumberUtils.ensurePositive(availableRoundFrom);
        NumberUtils.ensurePositive(availableRoundTo);
        NumberUtils.ensureNonNegative(coins);
        Validate.isTrue(weight > 0 && weight <= 100, "Weight must be in (0,100].");
        Validate.isTrue(availableRoundTo >= availableRoundFrom, "Illegal available round range.");

        this.maxHealth = maxHealth;
        this.baseDamage = baseDamage;
        this.speedBonus = speedBonus;

        this.availableRoundFrom = availableRoundFrom;
        this.availableRoundTo = availableRoundTo;

        this.weight = weight;
        this.coins = coins;
    }

    public double getMaxHealth() {
        return maxHealth;
    }

    public double getBaseDamage() {
        return baseDamage;
    }

    public int getSpeedBonus() {
        return speedBonus;
    }

    public int getAvailableRoundFrom() {
        return availableRoundFrom;
    }

    public int getAvailableRoundTo() {
        return availableRoundTo;
    }

    public int getWeight() {
        return weight;
    }

    public int getCoins() {
        return coins;
    }
}
