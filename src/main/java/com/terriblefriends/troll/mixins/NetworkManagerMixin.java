package com.terriblefriends.troll.mixins;

import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.terriblefriends.troll.SharedVariables.*;
import static com.terriblefriends.troll.Util.*;
import static com.terriblefriends.troll.MainClient.canSendPacket;

@Mixin(NetworkManager.class)
public class NetworkManagerMixin {
    @Inject(at = @At("TAIL"), method = "Lnet/minecraft/network/NetworkManager;sendPacket(Lnet/minecraft/network/Packet;)V", cancellable = true)
    public void send(Packet<?> packet, CallbackInfo ci) {
        if (packet instanceof CPacketPlayerDigging) {
            if (((CPacketPlayerDigging) packet).getAction() == CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK) {
                if (shouldDupe) {
                    quickMoveItem(0);
                    shouldDupe = false;
                    canSendPacket = true;
                } else if (shouldDupeAll) {
                    quickMoveAllItems();
                    shouldDupeAll = false;
                    canSendPacket = true;
                }
            }
        }
    }
}
