package com.akira.undeadwave.core.enemy;

import com.akira.core.api.util.NumberUtils;
import org.apache.commons.lang3.Validate;

public enum EnemyType {
    COMMON_ZOMBIE(20, 6, 0, 1, false, 10),
    COMMON_SKELETON(20, 4, 0, 1, true, 6);

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
