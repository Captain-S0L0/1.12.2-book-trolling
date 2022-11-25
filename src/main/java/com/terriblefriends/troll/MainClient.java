package com.terriblefriends.troll;

import net.minecraft.block.BlockShulkerBox;
import net.minecraft.client.gui.inventory.GuiShulkerBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import static com.terriblefriends.troll.SharedVariables.*;
import static com.terriblefriends.troll.Util.CLIENT;
import static com.terriblefriends.troll.Util.log;

@Mod(modid="terriblefriends")
public class MainClient {

    public static final String MOD_ID = "troll";

    @Mod.EventHandler
    public void onPreInit(FMLPreInitializationEvent event) {
        System.out.println("Hello world!");
    }
    public static boolean fra = true;
    public static int thex = 0;
    public static int they = 0;

    public static boolean canSendPacket = true;

    public static void setFra(boolean fra) {
        MainClient.fra = fra;
    }

    public static void tick() {
        boolean b1 = (CLIENT.currentScreen instanceof GuiShulkerBox);

        if (shouldDupe || shouldDupeAll) {
            RayTraceResult blockHit = CLIENT.objectMouseOver;
            if (CLIENT.objectMouseOver.typeOfHit == RayTraceResult.Type.BLOCK) {
                BlockPos blockpos = blockHit.getBlockPos();
                if (CLIENT.world.getBlockState(blockHit.getBlockPos()).getBlock() instanceof BlockShulkerBox && b1) {
                    if (canSendPacket) {
                        CLIENT.playerController.clickBlock(blockpos, blockHit.sideHit);
                        canSendPacket = false;
                    }
                    CLIENT.playerController.onPlayerDamageBlock(blockpos, blockHit.sideHit);
                } else {
                    log("You need to have a shulker box screen open and look at a shulker box.");
                    CLIENT.player.closeScreen();
                    shouldDupe = false;
                    shouldDupeAll = false;
                }
            }
        }
        if (b1) {

        } else {
            setFra(true);
        }
    }
}