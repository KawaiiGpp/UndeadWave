package com.akira.undeadwave.core.enemy;

import com.akira.core.api.util.NumberUtils;
import org.apache.commons.lang3.Validate;

public enum EnemyType {
    COMMON_ZOMBIE(20, 6, 0, 1, 5, false, 10), // 1~5
    GOLD_ZOMBIE(20, 9, 15, 5, 10, false, 10), // 5~10
    IRON_ZOMBIE(20, 12, 30, 10, false, 10), // 10+

    SPEED_ZOMBIE(20, 6, 50, 5, 10, false, 3), // 5~10
    LIGHTNING_ZOMBIE(20, 10, 100, 10, false, 3), // 10+

    ROCK_ZOMBIE(30, 4, -25, 5, 10, false, 6), // 5~10
    TANK_ZOMBIE(40, 8, -50, 10, false, 6), // 10+
    HEALTHY_ZOMBIE(60, 8, 15, 12, false, 4), // 12+
    TERMINATOPR_ZOMBIE(30, 16, 15, 12, false, 2), // 12+


    COMMON_SKELETON(20, 4, 0, 1, 5, true, 6), // 1~5
    GOLD_SKELETON(20, 6, 15, 5, 10, true, 6), // 5~10
    IRON_SKELETON(20, 9, 30, 10, true, 6), // 10+

    TANK_SKELETON(40, 6, -25, 8, true, 3), // 8+
    TERMINATOR_SKELETON(30, 13, 20, 12, true, 1); // 12+

    private final double maxHealth;
    private final double baseDamage;
    private final int speedBonus;

    private final int availableRoundFrom;
    private final int availableRoundTo;

    private final boolean rangedAttacker;
    private final int weight;

    EnemyType(double maxHealth, double baseDamage, int speedBonus,
              int availableRoundFrom, int availableRoundTo,
              boolean rangedAttacker, int weight) {
        NumberUtils.ensurePositive(maxHealth);
        NumberUtils.ensurePositive(baseDamage);
        NumberUtils.ensurePositive(availableRoundFrom);
        NumberUtils.ensurePositive(availableRoundTo);
        Validate.isTrue(weight > 0 && weight <= 10, "Weight must be in (0,10].");
        Validate.isTrue(availableRoundTo >= availableRoundFrom, "Illegal available round range.");

        this.maxHealth = maxHealth;
        this.baseDamage = baseDamage;
        this.speedBonus = speedBonus;

        this.availableRoundFrom = availableRoundFrom;
        this.availableRoundTo = availableRoundTo;

        this.rangedAttacker = rangedAttacker;
        this.weight = weight;
    }

    EnemyType(double maxHealth, double baseDamage, int speedBonus,
              int availableRoundFrom,
              boolean rangedAttacker, int weight) {
        this(maxHealth, baseDamage, speedBonus,
                availableRoundFrom, Integer.MAX_VALUE,
                rangedAttacker, weight);
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

    public boolean isRangedAttacker() {
        return rangedAttacker;
    }

    public int getWeight() {
        return weight;
    }
}
