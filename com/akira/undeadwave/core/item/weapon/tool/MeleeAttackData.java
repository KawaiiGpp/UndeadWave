package com.akira.undeadwave.core.item.weapon.tool;

import com.akira.core.api.util.NumberUtils;
import org.apache.commons.lang3.Validate;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class MeleeAttackData {
    private final Player attacker;
    private final LivingEntity victim;
    private final boolean sweep;

    private double damage;
    private boolean trueDamage;
    private boolean cancel;

    public MeleeAttackData(Player attacker, LivingEntity victim, boolean sweep) {
        Validate.notNull(attacker);
        Validate.notNull(victim);

        this.attacker = attacker;
        this.victim = victim;
        this.sweep = sweep;
    }

    public Player getAttacker() {
        return attacker;
    }

    public LivingEntity getVictim() {
        return victim;
    }

    public boolean isSweep() {
        return sweep;
    }

    public double getDamage() {
        return damage;
    }

    public void setDamage(double damage) {
        NumberUtils.ensurePositive(damage);
        this.damage = damage;
    }

    public boolean isTrueDamage() {
        return trueDamage;
    }

    public void setTrueDamage(boolean trueDamage) {
        this.trueDamage = trueDamage;
    }

    public boolean shouldBeCancelled() {
        return cancel;
    }

    public void setCancel(boolean cancel) {
        this.cancel = cancel;
    }
}
