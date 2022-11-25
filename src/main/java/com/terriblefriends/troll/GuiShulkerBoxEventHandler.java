package com.terriblefriends.troll;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiShulkerBox;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

import static com.terriblefriends.troll.SharedVariables.shouldDupe;
import static com.terriblefriends.troll.SharedVariables.shouldDupeAll;

@Mod.EventBusSubscriber(Side.CLIENT)
public class GuiShulkerBoxEventHandler {

    @SubscribeEvent
    public static void initGui(GuiScreenEvent.InitGuiEvent.Post event) {
        GuiScreen guiScreen = event.getGui();

        if (guiScreen instanceof GuiShulkerBox) {
            GuiButton buttonDupeOne = new GuiButton(1337, guiScreen.width / 2 - 90, guiScreen.height / 2 + 35 - 145, 50, 20, "Dupe One");
            GuiButton buttonDupeAll = new GuiButton(1338, guiScreen.width / 2 + 40, guiScreen.height / 2 + 35 - 145, 50, 20, "Dupe All");

            event.getButtonList().add(buttonDupeOne);
            event.getButtonList().add(buttonDupeAll);

            if (guiScreen.width != MainClient.thex || guiScreen.height != MainClient.they) {
                setFra(true);
            }
        }
    }

    @SubscribeEvent
    public static void performAction(GuiScreenEvent.ActionPerformedEvent.Pre event) {
        if(event.getGui() instanceof GuiShulkerBox) {
            GuiButton button = event.getButton();
            if (isFra()) {
                if (button.id == 1337) {
                    setFra(false);
                    if (shouldDupeAll) shouldDupeAll = false;
                    shouldDupe = true;
                }
                if (button.id == 1338) {
                    setFra(false);
                    if (shouldDupe) shouldDupe = false;
                    shouldDupeAll = true;
                }
            }
        }
    }


    public static boolean isFra() {
        return MainClient.fra;
    }

    public static void setFra(boolean fra) {
        MainClient.fra = fra;
    }
}
