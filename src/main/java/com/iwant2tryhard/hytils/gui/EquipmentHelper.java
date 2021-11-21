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
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.awt.*;
import java.util.Map;

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
            float maxSize = Float.MIN_VALUE;
            Map.Entry<Integer, Float>[] cache = (Map.Entry<Integer, Float>[]) Hytils.instance.getUtils().headScoreMap.entrySet().toArray();
            for (Map.Entry<Integer, Float> mapItem : cache) {
                if (mapItem.getValue() > maxSize) {
                    maxSize = mapItem.getValue();
                }
            }
            for (int i = 0; i < chestContainer.inventorySlots.size(); ++i) {
                Slot slot = chestContainer.inventorySlots.get(i);
                int slotX = type ? Math.round(((width / 2) - 98) + slot.xDisplayPosition) : Math.round(((width / 2) - 88) + slot.xDisplayPosition);
                int slotY = type ? Math.round(((height / 2) - 69) + slot.yDisplayPosition) : Math.round(((height / 2) - 84) + slot.yDisplayPosition);
                if (slot.getStack() != null) {
                    if (invGui.getSlotUnderMouse() != null && invGui.getSlotUnderMouse().getStack() != null && invGui.getSlotUnderMouse().getStack().getItem() != null) {
                        Item hoverItem = invGui.getSlotUnderMouse().getStack().getItem();
                        if (hoverItem == Items.leather_helmet |
                                hoverItem == Items.golden_helmet |
                                hoverItem == Items.chainmail_helmet |
                                hoverItem == Items.iron_helmet |
                                hoverItem == Items.diamond_helmet) {
                            if (Hytils.instance.getUtils().headScoreMap.containsKey(i)) {
                                float itemScore = Hytils.instance.getUtils().headScoreMap.get(i);
                                float scaledScore = (itemScore / maxSize) * 100f;
                                Color color = new Color(255, 0, 0, 128);
                                if (scaledScore <= 50f) {
                                    int scaledcolor = Math.round((scaledScore / 50f) * 254);
                                    color = new Color(255, scaledcolor, 0, 128);
                                } else if (scaledScore > 50f && scaledScore <= 100f) {
                                    int scaledcolor = Math.round(((scaledScore - 50f) / 50f) * 254);
                                    color = new Color(scaledcolor, 255, 0, 128);
                                }
                                RenderUtils.drawColoredRect(slotX, slotY, slotX + 16, slotY + 16, Utils.GetColorIntValue(color), 50);
                            }
                        } else if (hoverItem == Items.leather_chestplate |
                                hoverItem == Items.golden_chestplate |
                                hoverItem == Items.chainmail_chestplate |
                                hoverItem == Items.iron_chestplate |
                                hoverItem == Items.diamond_chestplate) {
                            if (Hytils.instance.getUtils().chestScoreMap.containsKey(i)) {
                                float itemScore = Hytils.instance.getUtils().chestScoreMap.get(i);
                                float scaledScore = (itemScore / maxSize) * 100f;
                                Color color = new Color(255, 0, 0, 128);
                                if (scaledScore <= 50f) {
                                    int scaledcolor = Math.round((scaledScore / 50f) * 255);
                                    color = new Color(255, scaledcolor, 0, 128);
                                } else if (scaledScore > 50f) {
                                    int scaledcolor = Math.round(((scaledScore - 50f) / 50f) * 255);
                                    color = new Color(scaledcolor, 255, 0, 128);
                                }
                                RenderUtils.drawColoredRect(slotX, slotY, slotX + 16, slotY + 16, Utils.GetColorIntValue(color), 50);
                            }
                        } else if (hoverItem == Items.leather_leggings |
                                hoverItem == Items.golden_leggings |
                                hoverItem == Items.chainmail_leggings |
                                hoverItem == Items.iron_leggings |
                                hoverItem == Items.diamond_leggings) {
                            if (Hytils.instance.getUtils().legsScoreMap.containsKey(i)) {
                                float itemScore = Hytils.instance.getUtils().legsScoreMap.get(i);
                                float scaledScore = (itemScore / maxSize) * 100f;
                                Color color = new Color(255, 0, 0, 128);
                                if (scaledScore <= 50f) {
                                    int scaledcolor = Math.round((scaledScore / 50f) * 255);
                                    color = new Color(255, scaledcolor, 0, 128);
                                } else if (scaledScore > 50f) {
                                    int scaledcolor = Math.round(((scaledScore - 50f) / 50f) * 255);
                                    color = new Color(scaledcolor, 255, 0, 128);
                                }
                                RenderUtils.drawColoredRect(slotX, slotY, slotX + 16, slotY + 16, Utils.GetColorIntValue(color), 50);
                            }
                        } else if (hoverItem == Items.leather_boots |
                                hoverItem == Items.golden_boots |
                                hoverItem == Items.chainmail_boots |
                                hoverItem == Items.iron_boots |
                                hoverItem == Items.diamond_boots) {
                            if (Hytils.instance.getUtils().feetScoreMap.containsKey(i)) {
                                float itemScore = Hytils.instance.getUtils().feetScoreMap.get(i);
                                float scaledScore = (itemScore / maxSize) * 100f;
                                Color color = new Color(255, 0, 0, 128);
                                if (scaledScore <= 50f) {
                                    int scaledcolor = Math.round((scaledScore / 50f) * 255);
                                    color = new Color(255, scaledcolor, 0, 128);
                                } else if (scaledScore > 50f) {
                                    int scaledcolor = Math.round(((scaledScore - 50f) / 50f) * 255);
                                    color = new Color(scaledcolor, 255, 0, 128);
                                }
                                RenderUtils.drawColoredRect(slotX, slotY, slotX + 16, slotY + 16, Utils.GetColorIntValue(color), 50);
                            }
                        }
                    }
                }
            }
            //test
            GlStateManager.colorMask(true, true, true, true);
        }

    }
}
