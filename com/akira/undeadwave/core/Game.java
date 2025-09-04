package com.akira.undeadwave.core;

import com.akira.core.api.util.CommonUtils;
import com.akira.core.api.util.EntityUtils;
import com.akira.core.api.util.NumberUtils;
import com.akira.core.api.util.PlayerUtils;
import com.akira.undeadwave.UndeadWave;
import com.akira.undeadwave.config.LocationConfig;
import com.akira.undeadwave.config.SettingsConfig;
import com.akira.undeadwave.core.enemy.EnemyType;
import com.akira.undeadwave.core.item.weapon.WeaponType;
import org.apache.commons.lang3.Validate;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class Game extends GameBase {
    public Game(UndeadWave plugin) {
        super(plugin);
    }

    public void startGame(Player player) {
        Validate.notNull(player);
        validateState(GameState.WAITING);

        this.session = new GameSession(plugin, player);
        this.state = GameState.STARTED;

        teleport(this.getLocationConfig().getSpawnpoint());
        sendGameStartMessages();
        resetPlayerStats();
        setupStartingGears();
        startInfoBarLoop();

        session.markStartTime();
        startNextRound();
    }

    public void endGame(boolean victory) {
        validateState(GameState.STARTED);

        try {
            stopInfoBarLoop();
            resetPlayerStats();
            teleport(this.getLocationConfig().getLobby());
            sendGameEndMessage(victory);

            weaponManager.resetRangedWeaponCooldown();
            consumableItemManager.resetCooldown();
        } catch (Exception e) {
            plugin.logErr("在游戏结束时发生了异常。");
            e.printStackTrace();
        }

        if (!victory) this.removeExistingEnemies();

        this.session = null;
        this.state = GameState.WAITING;
    }

    public void handleEnemyKilled(UUID uniqueId) {
        Validate.notNull(uniqueId);

        session.removeAliveEnemy(uniqueId);
        updateExpBarProgress();
        if (!session.isRoundCleared()) return;

        if (session.isMaxRoundReached())
            endGame(true);
        else startNextRound();
    }

    private void removeExistingEnemies() {
        for (UUID uuid : session.getAliveEnemies()) {
            Entity entity = Bukkit.getEntity(uuid);

            if (entity != null) {
                if (!entity.isValid()) {
                    String type = entity.getType().name();

                    Location l = entity.getLocation();
                    String x = NumberUtils.format(l.getX());
                    String y = NumberUtils.format(l.getY());
                    String z = NumberUtils.format(l.getZ());

                    plugin.logErr("试图清除实体 " + type + " (" + uuid + ") 时发现其无效。");
                    plugin.logErr("该实体位于：" + l.getWorld() + ":" + x + "," + y + "," + z);
                } else entity.remove();
            } else plugin.logErr("试图清除实体时，无法找到对象：" + uuid);
        }
    }

    private void startNextRound() {
        session.increaseRoundCounter();

        SettingsConfig settings = this.getSettingsConfig();
        LocationConfig locations = this.getLocationConfig();
        int round = session.getCurrentRound();

        sendRoundMessage();
        this.getIngamePlayer().setExp(1.0F);

        List<EnemyType> filteredEnemies = new ArrayList<>(Arrays.asList(EnemyType.values()));
        filteredEnemies.removeIf(e -> round < e.getAvailableRoundFrom() || round > e.getAvailableRoundTo());

        int enemyAmount = round * settings.getMonstersPerRound();
        int weightSum = filteredEnemies.stream().mapToInt(EnemyType::getWeight).sum();

        for (int i = 0; i < enemyAmount; i++) {
            int weightPoint = CommonUtils.getRandom().nextInt(weightSum) + 1;
            EnemyType type = this.randomEnemyType(filteredEnemies, weightPoint);
            Location location = CommonUtils.getRandomElement(locations.getMonsterPoints());

            Monster monster = enemyManager.fromType(type).spawn(location);
            monster.setTarget(this.getIngamePlayer());
            this.session.addAliveEnemy(monster.getUniqueId());
        }
    }

    private EnemyType randomEnemyType(List<EnemyType> filtered, int randomPoint) {
        NumberUtils.ensurePositive(randomPoint);

        int counter = 0;
        for (EnemyType value : filtered) {
            int weight = value.getWeight();
            NumberUtils.ensurePositive(weight);

            counter += weight;
            if (randomPoint <= counter) return value;
        }

        throw new UnsupportedOperationException("Unreachable code executed.");
    }

    private void resetPlayerStats() {
        Player player = this.getIngamePlayer();
        player.setGameMode(GameMode.SURVIVAL);

        player.getActivePotionEffects().stream()
                .map(PotionEffect::getType)
                .forEach(player::removePotionEffect);
        player.getInventory().clear();
        player.closeInventory();

        PlayerUtils.sendActionBarTitle(player, "");
        EntityUtils.setMaxHealth(player, 20);
        player.setHealth(EntityUtils.getMaxHealth(player));
        player.setAbsorptionAmount(0);
        player.setArrowsInBody(0);
        player.setExp(0);
        player.setLevel(0);
    }

    private void setupStartingGears() {
        this.giveWeapon(WeaponType.WOOD_KNIFE);
        this.giveWeapon(WeaponType.PISTOL);
    }

    private void giveWeapon(WeaponType type) {
        PlayerInventory inventory = this.getIngamePlayer().getInventory();
        inventory.addItem(weaponManager.fromWeaponType(type).buildItem());
    }

    private void sendGameStartMessages() {
        String line = CommonUtils.generateLine(60);

        send("§b游戏已经开始，祝你好运。");
        send("§8" + line);
        send("§7击杀所有对你虎视眈眈的怪物，并活下去。你会成功的。");
        send("§7命令方块藏着珍贵的武器和道具，相信会对你帮助。");
        send("§7探测器已配备，与最近僵尸的距离将显示在经验条的上方。");
        send("§8" + line);

        playSound(Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.5F);
    }

    private void sendGameEndMessage(boolean victory) {
        int round = session.getCurrentRound();

        if (victory) {
            sendTitle("§6恭喜胜利", "§f你坚持到了第 §a" + round + " §f回合");
            send("§b恭喜你！你击杀了所有怪物，拯救了这片土地。");

            playSound(Sound.ENTITY_PLAYER_LEVELUP);
        } else {
            sendTitle("§c游戏结束", "§f你坚持到了第 §c" + round + " §f回合");
            send("§f你死了，游戏结束。你坚持到了第 §c" + round + " §f回合。");

            playSound(Sound.BLOCK_ANVIL_LAND);
        }

        sendSummary();
    }

    private void sendRoundMessage() {
        int round = session.getCurrentRound();

        SettingsConfig config = this.getSettingsConfig();
        int max = config.getMaxRound();
        int amount = config.getMonstersPerRound() * round;

        String subTitle = round == max ?
                "§f最后一波攻势，共 §e" + amount + " §f只" :
                "§f目前共有 §e" + amount + " §f只怪物";
        send("§f第 §c" + round + " §f回合开始！地图中共有 §e" + amount + " §f只怪物。");
        sendTitle("§c第 " + round + " 回合", subTitle);

        if (round == 1) return;
        playSound(Sound.ENTITY_PLAYER_LEVELUP);
    }

    private void sendSummary() {
        long total = session.calculateElapsed();
        DecimalFormat formatter = new DecimalFormat("00.##");

        String minutes = formatter.format((int) (total / 60000));
        String seconds = formatter.format((total % 60000) / 1000.0);
        String line = CommonUtils.generateLine(60);
        String score = NumberUtils.format(session.getScore());

        int totalKills = session.getKills();
        int totalEnemies = this.getTotalEnemyAmount();
        double ratio = (totalKills / (double) totalEnemies) * 100;
        String kills = NumberUtils.format(totalKills);
        String killsRatio = NumberUtils.format(ratio) + "%";
        ChatColor color = ratio == 100 ? ChatColor.GOLD : ChatColor.WHITE;

        send("§8" + line);
        send("§7击杀：§c" + kills + " " + color + "(" + killsRatio + ") §8| " + "§7得分：§a" + score);
        send("§7游戏全程用时：§3" + minutes + ":" + seconds);
        send("§8" + line);
    }

    private int getTotalEnemyAmount() {
        SettingsConfig config = this.getSettingsConfig();

        int maxRound = config.getMaxRound();
        int per = config.getMonstersPerRound();

        int result = 0;
        for (int i = 1; i <= maxRound; i++)
            result += per * i;

        return result;
    }

    private void updateExpBarProgress() {
        int multiplier = this.getSettingsConfig().getMonstersPerRound();
        int max = session.getCurrentRound() * multiplier;
        int current = session.getAliveEnemies().size();
        Validate.isTrue(current <= max);

        float progress = current / (float) max;
        session.getOwner().setExp(progress);
    }
}
