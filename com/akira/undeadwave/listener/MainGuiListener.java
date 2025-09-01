package com.akira.undeadwave.listener;

import com.akira.core.api.gui.GuiListener;
import com.akira.core.api.gui.GuiManager;
import com.akira.undeadwave.UndeadWave;
import com.akira.undeadwave.core.Game;
import com.akira.undeadwave.core.GameState;
import org.apache.commons.lang3.Validate;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

@SuppressWarnings("CallToPrintStackTrace")
public class MainGuiListener extends GuiListener {
    private final UndeadWave plugin;

    public MainGuiListener(UndeadWave plugin, GuiManager manager) {
        super(manager);

        Validate.notNull(plugin);
        this.plugin = plugin;
    }

    protected void onExceptionCaught(Exception e) {
        plugin.logErr("执行GUI相关事件时发生异常。");
        e.printStackTrace();
    }

    protected boolean shouldHandleEvent(InventoryClickEvent e) {
        if (!(e.getWhoClicked() instanceof Player player)) return false;

        Game game = plugin.getGame();
        if (game.getState() != GameState.STARTED) return false;

        return game.getSession().isOwnedBy(player);
    }

    protected boolean shouldCallGui(InventoryClickEvent e) {
        return true;
    }
}
