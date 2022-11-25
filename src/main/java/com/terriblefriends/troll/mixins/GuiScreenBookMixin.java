package com.terriblefriends.troll.mixins;

import com.terriblefriends.troll.GuiScreenBookEventHandler;
import com.terriblefriends.troll.access.GuiScreenBookAccess2;
import net.minecraft.client.gui.GuiScreenBook;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiScreenBook.class)
public class GuiScreenBookMixin implements GuiScreenBookAccess2 {
    @Shadow @Final
    private boolean bookIsUnsigned;
    @Shadow private boolean bookGettingSigned;

    public boolean getBookIsUnsigned() {
        return bookIsUnsigned;
    }

    public boolean getBookGettingSigned() {
        return bookGettingSigned;
    }

    @Inject(at=@At("HEAD"),method="Lnet/minecraft/client/gui/GuiScreenBook;updateButtons()V")
    private void updateNewButtons(CallbackInfo ci) {
        GuiScreenBookEventHandler.buttonOverload.visible = !bookGettingSigned;
        GuiScreenBookEventHandler.buttonOverloadDrop.visible = !bookGettingSigned;
        GuiScreenBookEventHandler.buttonPageStuff.visible = !bookGettingSigned;
        GuiScreenBookEventHandler.buttonUnicodeStuff.visible = !bookGettingSigned;
    }
}
