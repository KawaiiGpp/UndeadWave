package com.akira.undeadwave.core;

import com.akira.core.api.util.NumberUtils;
import org.apache.commons.lang3.Validate;
import org.bukkit.entity.Player;

public class GameSession {
    private final Player owner;

    private int kills;
    private int deathes;
    private int coins;
    private double damageTaken;
    private double damageDealt;

    public GameSession(Player owner) {
        Validate.notNull(owner);
        this.owner = owner;
    }

    public boolean isOwnedBy(Player player) {
        Validate.notNull(player);
        return this.owner.equals(player);
    }

    public Player getOwner() {
        return owner;
    }

    public void increaseKills() {
        this.kills++;
    }

    public void increaseDeathes() {
        this.deathes++;
    }

    public void increaseCoins(int coins) {
        NumberUtils.ensurePositive(coins);
        this.coins += coins;
    }

    public void increaseDamageTaken(double damageTaken) {
        NumberUtils.ensureLegit(damageTaken);
        NumberUtils.ensurePositive(damageTaken);
        this.damageTaken += damageTaken;
    }

    public void increaseDamageDealt(double damageDealt) {
        NumberUtils.ensureLegit(damageDealt);
        NumberUtils.ensurePositive(damageDealt);
        this.damageDealt += damageDealt;
    }

    public int getKills() {
        return kills;
    }

    public int getDeathes() {
        return deathes;
    }

    public int getCoins() {
        return coins;
    }

    public double getDamageTaken() {
        return damageTaken;
    }

    public double getDamageDealt() {
        return damageDealt;
    }
}
