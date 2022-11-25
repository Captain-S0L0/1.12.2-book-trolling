package com.terriblefriends.troll;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiShulkerBox;
import net.minecraft.inventory.ClickType;
import net.minecraft.util.text.TextComponentString;

public class Util {
    public static final Minecraft CLIENT = Minecraft.getMinecraft();

    public static void log(String msg) {
        CLIENT.player.sendMessage(new TextComponentString("[Shulker Dupe]: " + msg));
    }
    public static void quickMoveAllItems() {
        for (int i = 0; i < 27; i++) {
            quickMoveItem(i);
        }
    }

    public static void quickMoveItem(int slot) {
        if (CLIENT.currentScreen instanceof GuiShulkerBox) {
            GuiShulkerBox screenHandler = (GuiShulkerBox) CLIENT.currentScreen;
            CLIENT.playerController.windowClick(screenHandler.inventorySlots.windowId, slot, 0, ClickType.QUICK_MOVE, CLIENT.player);
        }
    }
}
