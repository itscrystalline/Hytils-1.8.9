package com.iwant2tryhard.hytils.gui;

import com.iwant2tryhard.hytils.Hytils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class GamesMenuGui extends Gui {
    private final ResourceLocation TEXTURE = new ResourceLocation(Hytils.MODID, "games_menu.png");
    private final int texWidth = 430;
    private final int texHeight = 282;

    @SubscribeEvent
    public void RenderOverlay(TickEvent.RenderTickEvent event) {
        //System.out.println("event");

            //System.out.println("Render");

            Minecraft mc = Minecraft.getMinecraft();

            mc.renderEngine.bindTexture(TEXTURE);

            if (Minecraft.getMinecraft().currentScreen instanceof GuiContainer){
                GuiContainer container = ((GuiContainer) Minecraft.getMinecraft().currentScreen);

                mc.renderEngine.bindTexture(TEXTURE);
                if (container.inventorySlots instanceof ContainerChest){
                    ContainerChest chest = (ContainerChest) container.inventorySlots;
                    IInventory chest1 = chest.getLowerChestInventory();

                    mc.renderEngine.bindTexture(TEXTURE);
                    if (chest1.getDisplayName().getUnformattedText().equals("Game Menu")){
                        //event.setCanceled(true);
                        mc.renderEngine.bindTexture(TEXTURE);
                        drawTexturedModalRect(mc.displayWidth / 4 - texWidth / 2, mc.displayHeight / 4 - texHeight / 2, 0, 0, texWidth, texHeight);
                        mc.fontRendererObj.drawString("Game Menu", (mc.displayWidth / 4 - texWidth / 2) + 5, (mc.displayHeight / 4 - texHeight / 2) + 5, 1);
                    }
                }
            }

    }
}
