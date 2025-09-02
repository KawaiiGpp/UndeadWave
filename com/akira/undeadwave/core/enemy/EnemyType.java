package com.akira.undeadwave.core.enemy;

import com.akira.core.api.util.NumberUtils;
import org.apache.commons.lang3.Validate;

public enum EnemyType {
    COMMON_ZOMBIE(20, 6, 0, 1, 5, 10, 15),
    GOLD_ZOMBIE(20, 10, 15, 5, 10, 10, 20),
    IRON_ZOMBIE(20, 14, 30, 10, 13, 10, 25),
    DIAMOND_ZOMBIE(20, 18, 45, 13, Integer.MAX_VALUE, 10, 30),

    COMMON_SKELETON(20, 4, 0, 3, 6, 3, 20),
    GOLD_SKELETON(20, 8, 15, 6, 9, 3, 25),
    IRON_SKELETON(20, 12, 30, 9, Integer.MAX_VALUE, 3, 30);

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
        Validate.isTrue(weight > 0 && weight <= 10, "Weight must be in (0,10].");
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
