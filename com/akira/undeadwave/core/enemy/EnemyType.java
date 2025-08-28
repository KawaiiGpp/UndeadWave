package com.akira.undeadwave.core.enemy;

import com.akira.core.api.util.NumberUtils;

public enum EnemyType {
    COMMON_ZOMBIE(20, 4, 0, 1),
    COMMON_SKELETON(true, 20, 6, 0, 1);

    private final boolean rangedAttacker;
    private final double maxHealth;
    private final double baseDamage;
    private final int speedBonus;
    private final int availableRound;

    EnemyType(boolean rangedAttacker,
              double maxHealth, double baseDamage,
              int speedBonus, int availableRound) {
        NumberUtils.ensurePositive(maxHealth);
        NumberUtils.ensurePositive(baseDamage);
        NumberUtils.ensurePositive(availableRound);

        this.rangedAttacker = rangedAttacker;
        this.maxHealth = maxHealth;
        this.baseDamage = baseDamage;
        this.speedBonus = speedBonus;
        this.availableRound = availableRound;
    }

    EnemyType(double maxHealth, double baseDamage,
              int speedBonus, int availableRound) {
        this(false, maxHealth, baseDamage, speedBonus, availableRound);
    }

    public boolean isRangedAttacker() {
        return rangedAttacker;
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

    public int getAvailableRound() {
        return availableRound;
    }
}
