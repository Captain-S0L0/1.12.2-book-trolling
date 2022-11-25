package com.terriblefriends.troll.mixins;

import com.terriblefriends.troll.MainClient;
import net.minecraft.client.entity.EntityPlayerSP;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityPlayerSP.class)
public abstract class EntityPlayerSPMixin {
    @Inject(at = @At("TAIL"), method = "onUpdate")
    public void tick(CallbackInfo ci) {
        MainClient.tick();
    }
}
