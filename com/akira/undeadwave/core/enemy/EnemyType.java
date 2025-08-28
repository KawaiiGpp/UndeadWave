package com.akira.undeadwave.core.enemy;

import com.akira.core.api.util.NumberUtils;

public enum EnemyType {
    COMMON_ZOMBIE(4, 0, 1);

    private final double baseDamage;
    private final int speedBonus;
    private final int availableRound;

    EnemyType(double baseDamage, int speedBonus, int availableRound) {
        NumberUtils.ensurePositive(baseDamage);
        NumberUtils.ensurePositive(availableRound);

        this.baseDamage = baseDamage;
        this.speedBonus = speedBonus;
        this.availableRound = availableRound;
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
