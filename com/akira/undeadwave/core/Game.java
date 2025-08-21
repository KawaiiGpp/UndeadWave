package com.akira.undeadwave.core;

import com.akira.undeadwave.UndeadWave;
import org.apache.commons.lang3.Validate;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

public class Game extends GameBase {
    public Game(UndeadWave plugin) {
        super(plugin);
    }

    public void startGame(Player player) {
        Validate.notNull(player);
        validateState(GameState.WAITING);

        player.teleport(this.getLocationConfig().getSpawnpoint());
        player.sendMessage("§a游戏已经开始！");

        this.resetPlayerStat(player);
        this.session = new GameSession(player);
        this.state = GameState.STARTED;
    }

    public void endGame(boolean victory) {
        validateState(GameState.STARTED);

        Player player = this.getSessionSafely().getOwner();
        player.teleport(this.getLocationConfig().getLobby());
        player.sendMessage("§a游戏已经结束！" + (victory ? "你赢了！" : "你输了！"));

        this.resetPlayerStat(player);
        this.session = null;
        this.state = GameState.WAITING;
    }

    private void resetPlayerStat(Player player) {
        Validate.notNull(player);

        AttributeInstance attribute = player.getAttribute(Attribute.GENERIC_MAX_HEALTH);
        Validate.notNull(attribute);
        attribute.setBaseValue(20);

        player.getActivePotionEffects().stream()
                .map(PotionEffect::getType)
                .forEach(player::removePotionEffect);
        player.setHealth(attribute.getValue());
    }
}
