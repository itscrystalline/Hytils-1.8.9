package com.iwant2tryhard.hytils.gui;

import com.iwant2tryhard.hytils.Hytils;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.inventory.ContainerChest;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ToolHelperHUD extends Gui {
    @SubscribeEvent
    public void RenderOverlay(RenderGameOverlayEvent event) {
        if (event.type == RenderGameOverlayEvent.ElementType.ALL){
            if (Hytils.instance.mc.currentScreen instanceof GuiChest){
                GuiChest chestGui = (GuiChest) Hytils.instance.mc.currentScreen;
                ContainerChest chestContainer = (ContainerChest) chestGui.inventorySlots;
                String chestName = chestContainer.getLowerChestInventory().getDisplayName().getUnformattedText();
                if (chestName.equals("Chest") || chestName.equals("Large Chest")){
                    drawRect(0, 0, 16, 16, 65536);
                }
            }
        }
    }
}
