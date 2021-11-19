package com.iwant2tryhard.hytils.gui;

import com.iwant2tryhard.hytils.Hytils;
import com.iwant2tryhard.hytils.core.RenderUtils;
import com.iwant2tryhard.hytils.core.Utils;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiContainerCreative;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.InventoryEffectRenderer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.awt.*;

public class EquipmentHelper {
    //key: -2147418368 : green
    //     -2130706688 : yellow
    //     -2130771968 : red
    @SubscribeEvent
    public void RenderOverlay(TickEvent.RenderTickEvent event) {
        ScaledResolution resolution = new ScaledResolution(Hytils.instance.mc);
        float width = resolution.getScaledWidth();
        float height = resolution.getScaledHeight();
        if (Hytils.instance.mc.currentScreen instanceof GuiInventory | Hytils.instance.mc.currentScreen instanceof GuiContainerCreative) {
            boolean type = Hytils.instance.mc.currentScreen instanceof GuiContainerCreative;
            InventoryEffectRenderer invGui;
            invGui = (InventoryEffectRenderer) Hytils.instance.mc.currentScreen;
            Container chestContainer = invGui.inventorySlots;

            GlStateManager.colorMask(true, true, true, false);
            for (Slot slot : chestContainer.inventorySlots) {
                int slotX = type ? Math.round(((width / 2) - 98) + slot.xDisplayPosition) : Math.round(((width / 2) - 88) + slot.xDisplayPosition);
                int slotY = type ? Math.round(((height / 2) - 69) + slot.yDisplayPosition) : Math.round(((height / 2) - 84) + slot.yDisplayPosition);
                if (slot.getStack() != null) {
                    if (invGui.getSlotUnderMouse() != null && invGui.getSlotUnderMouse().getStack() != null &&
                            (invGui.getSlotUnderMouse().getStack().getItem() == Items.diamond_helmet |
                                    invGui.getSlotUnderMouse().getStack().getItem() == Items.chainmail_helmet |
                                    invGui.getSlotUnderMouse().getStack().getItem() == Items.golden_helmet |
                                    invGui.getSlotUnderMouse().getStack().getItem() == Items.iron_helmet |
                                    invGui.getSlotUnderMouse().getStack().getItem() == Items.leather_helmet)) {
                        int invSlotX = type ? Math.round(((width / 2) - 98) + invGui.getSlotUnderMouse().xDisplayPosition) : Math.round(((width / 2) - 88) + invGui.getSlotUnderMouse().xDisplayPosition);
                        int invSlotY = type ? Math.round(((height / 2) - 69) + invGui.getSlotUnderMouse().yDisplayPosition) : Math.round(((height / 2) - 84) + invGui.getSlotUnderMouse().yDisplayPosition);
                        RenderUtils.drawColoredRect(invSlotX, invSlotY, invSlotX + 16, invSlotY + 16, Utils.GetColorIntValue(new Color(0, 255, 0, 128)), 5);
                        if (slot.getStack().getItem() == Items.diamond_helmet |
                                slot.getStack().getItem() == Items.chainmail_helmet |
                                slot.getStack().getItem() == Items.golden_helmet |
                                slot.getStack().getItem() == Items.iron_helmet |
                                slot.getStack().getItem() == Items.leather_helmet)
                            RenderUtils.drawColoredRect(slotX, slotY, slotX + 16, slotY + 16, Utils.GetColorIntValue(new Color(255, 255, 0, 128)), 5);
                    }
                }
            }
            GlStateManager.colorMask(true, true, true, true);
        }

    }
}
