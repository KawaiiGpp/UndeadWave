package com.akira.undeadwave.core.enemy;

import com.akira.core.api.util.NumberUtils;
import org.apache.commons.lang3.Validate;

public enum EnemyType {
    COMMON_ZOMBIE(20, 6, 0, 1, 5, 100, 15),
    GOLD_ZOMBIE(20, 10, 15, 5, 10, 100, 20),
    IRON_ZOMBIE(20, 14, 30, 10, 13, 100, 25),
    DIAMOND_ZOMBIE(20, 18, 45, 13, Integer.MAX_VALUE, 100, 30),

    SPEEDSTER_ZOMBIE(40, 8, 60, 5, 8, 15, 40),
    LIGHTNING_ZOMBIE(40, 12, 100, 10, Integer.MAX_VALUE, 15, 60),

    THUNDERBLOT_ZOMBIE(50, 18, 20, 7, 10, 30, 75),
    SHADOW_ASSASSIN_ZOMBIE(70, 24, 40, 12, Integer.MAX_VALUE, 20, 95),

    ROCK_ZOMBIE(30, 5, 0, 3, 6, 25, 30),
    TANK_ZOMBIE(40, 10, 0, 10, Integer.MAX_VALUE, 25, 40),


    COMMON_SKELETON(20, 4, 0, 3, 6, 30, 20),
    GOLD_SKELETON(20, 8, 15, 6, 9, 30, 25),
    IRON_SKELETON(20, 12, 30, 9, Integer.MAX_VALUE, 30, 30),

    IMPACT_SKELETON(40, 8, 20, 7, Integer.MAX_VALUE, 20, 50),
    SNIPER_SKELETON(40, 18, 20, 10, Integer.MAX_VALUE, 10, 50);

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
