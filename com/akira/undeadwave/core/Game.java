package com.akira.undeadwave.core;

import com.akira.core.api.util.EntityUtils;
import com.akira.undeadwave.UndeadWave;
import com.akira.undeadwave.core.weapon.WeaponType;
import org.apache.commons.lang3.Validate;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

public class Game extends GameBase {
    public Game(UndeadWave plugin) {
        super(plugin);
    }

    public void startGame(Player player) {
        Validate.notNull(player);
        validateState(GameState.WAITING);

        this.resetPlayerStat(player);
        player.teleport(this.getLocationConfig().getSpawnpoint());
        player.sendMessage("§a游戏已经开始！");
        player.getInventory().setItem(0, weaponManager.fromWeaponType(WeaponType.BEGINNER_SWORD).buildItem());
        player.getInventory().setItem(1, weaponManager.fromWeaponType(WeaponType.PISTOL).buildItem());

        this.session = new GameSession(player);
        this.state = GameState.STARTED;
    }

    public void endGame(boolean victory) {
        validateState(GameState.STARTED);
        Player player = this.getSessionSafely().getOwner();

        this.resetPlayerStat(player);
        player.teleport(this.getLocationConfig().getLobby());
        player.sendMessage("§a游戏已经结束！" + (victory ? "你赢了！" : "你输了！"));

        this.session = null;
        this.state = GameState.WAITING;
    }

    private void resetPlayerStat(Player player) {
        Validate.notNull(player);

        player.getActivePotionEffects().stream()
                .map(PotionEffect::getType)
                .forEach(player::removePotionEffect);
        player.getInventory().clear();

        EntityUtils.setMaxHealth(player, 20);
        player.setHealth(EntityUtils.getMaxHealth(player));
    }
}
