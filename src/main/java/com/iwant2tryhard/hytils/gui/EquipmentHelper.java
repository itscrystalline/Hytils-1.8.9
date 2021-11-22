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
import java.util.concurrent.ConcurrentHashMap;

public class EquipmentHelper {
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
                            float maxHelmetScore = Float.MIN_VALUE;
                            ConcurrentHashMap<Integer, Float> cache = new ConcurrentHashMap<>(Hytils.instance.getUtils().headScoreMap);
                            for (Map.Entry<Integer, Float> mapItem : cache.entrySet()) {
                                if (mapItem.getValue() > maxHelmetScore) {
                                    maxHelmetScore = mapItem.getValue();
                                }
                            }
                            if (Hytils.instance.getUtils().headScoreMap.containsKey(i)) {
                                float itemScore = Hytils.instance.getUtils().headScoreMap.get(i);
                                float scaledScore = (itemScore / maxHelmetScore) * 100f;
                                Color color = new Color(255, 0, 0, 192);
                                if (scaledScore <= 50f) {
                                    int scaledcolor = Math.round((scaledScore / 50f) * 255);
                                    color = new Color(255, scaledcolor, 0, 192);
                                } else if (scaledScore > 50f && scaledScore <= 100f) {
                                    int scaledcolor = Math.round(((scaledScore - 50f) / 50f) * 255);
                                    color = new Color(255 - scaledcolor, 255, 0, 192);
                                }
                                RenderUtils.drawColoredRect(slotX, slotY, slotX + 16, slotY + 16, Utils.GetColorIntValue(color), 50);
                            }
                        } else if (hoverItem == Items.leather_chestplate |
                                hoverItem == Items.golden_chestplate |
                                hoverItem == Items.chainmail_chestplate |
                                hoverItem == Items.iron_chestplate |
                                hoverItem == Items.diamond_chestplate) {
                            float maxChestScore = Float.MIN_VALUE;
                            ConcurrentHashMap<Integer, Float> cache = new ConcurrentHashMap<>(Hytils.instance.getUtils().chestScoreMap);
                            for (Map.Entry<Integer, Float> mapItem : cache.entrySet()) {
                                if (mapItem.getValue() > maxChestScore) {
                                    maxChestScore = mapItem.getValue();
                                }
                            }
                            if (Hytils.instance.getUtils().chestScoreMap.containsKey(i)) {
                                float itemScore = Hytils.instance.getUtils().chestScoreMap.get(i);
                                float scaledScore = (itemScore / maxChestScore) * 100f;
                                Color color = new Color(255, 0, 0, 192);
                                if (scaledScore <= 50f) {
                                    int scaledcolor = Math.round((scaledScore / 50f) * 255);
                                    color = new Color(255, scaledcolor, 0, 192);
                                } else if (scaledScore > 50f) {
                                    int scaledcolor = Math.round(((scaledScore - 50f) / 50f) * 255);
                                    color = new Color(255 - scaledcolor, 255, 0, 192);
                                }
                                RenderUtils.drawColoredRect(slotX, slotY, slotX + 16, slotY + 16, Utils.GetColorIntValue(color), 50);
                            }
                        } else if (hoverItem == Items.leather_leggings |
                                hoverItem == Items.golden_leggings |
                                hoverItem == Items.chainmail_leggings |
                                hoverItem == Items.iron_leggings |
                                hoverItem == Items.diamond_leggings) {
                            float maxLegsScore = Float.MIN_VALUE;
                            ConcurrentHashMap<Integer, Float> cache = new ConcurrentHashMap<>(Hytils.instance.getUtils().legsScoreMap);
                            for (Map.Entry<Integer, Float> mapItem : cache.entrySet()) {
                                if (mapItem.getValue() > maxLegsScore) {
                                    maxLegsScore = mapItem.getValue();
                                }
                            }
                            if (Hytils.instance.getUtils().legsScoreMap.containsKey(i)) {
                                float itemScore = Hytils.instance.getUtils().legsScoreMap.get(i);
                                float scaledScore = (itemScore / maxLegsScore) * 100f;
                                Color color = new Color(255, 0, 0, 192);
                                if (scaledScore <= 50f) {
                                    int scaledcolor = Math.round((scaledScore / 50f) * 255);
                                    color = new Color(255, scaledcolor, 0, 192);
                                } else if (scaledScore > 50f) {
                                    int scaledcolor = Math.round(((scaledScore - 50f) / 50f) * 255);
                                    color = new Color(255 - scaledcolor, 255, 0, 192);
                                }
                                RenderUtils.drawColoredRect(slotX, slotY, slotX + 16, slotY + 16, Utils.GetColorIntValue(color), 50);
                            }
                        } else if (hoverItem == Items.leather_boots |
                                hoverItem == Items.golden_boots |
                                hoverItem == Items.chainmail_boots |
                                hoverItem == Items.iron_boots |
                                hoverItem == Items.diamond_boots) {
                            float maxFeetSize = Float.MIN_VALUE;
                            ConcurrentHashMap<Integer, Float> cache = new ConcurrentHashMap<>(Hytils.instance.getUtils().feetScoreMap);
                            for (Map.Entry<Integer, Float> mapItem : cache.entrySet()) {
                                if (mapItem.getValue() > maxFeetSize) {
                                    maxFeetSize = mapItem.getValue();
                                }
                            }
                            if (Hytils.instance.getUtils().feetScoreMap.containsKey(i)) {
                                float itemScore = Hytils.instance.getUtils().feetScoreMap.get(i);
                                float scaledScore = (itemScore / maxFeetSize) * 100f;
                                Color color = new Color(255, 0, 0, 192);
                                if (scaledScore <= 50f) {
                                    int scaledcolor = Math.round((scaledScore / 50f) * 255);
                                    color = new Color(255, scaledcolor, 0, 192);
                                } else if (scaledScore > 50f) {
                                    int scaledcolor = Math.round(((scaledScore - 50f) / 50f) * 255);
                                    color = new Color(255 - scaledcolor, 255, 0, 192);
                                }
                                RenderUtils.drawColoredRect(slotX, slotY, slotX + 16, slotY + 16, Utils.GetColorIntValue(color), 50);
                            }
                        } else if (hoverItem == Items.wooden_sword |
                                hoverItem == Items.stone_sword |
                                hoverItem == Items.iron_sword |
                                hoverItem == Items.golden_sword |
                                hoverItem == Items.diamond_sword) {
                            float maxSwordSize = Float.MIN_VALUE;
                            ConcurrentHashMap<Integer, Float> cache = new ConcurrentHashMap<>(Hytils.instance.getUtils().swordScoreMap);
                            for (Map.Entry<Integer, Float> mapItem : cache.entrySet()) {
                                if (mapItem.getValue() > maxSwordSize) {
                                    maxSwordSize = mapItem.getValue();
                                }
                            }
                            if (Hytils.instance.getUtils().swordScoreMap.containsKey(i)) {
                                float itemScore = Hytils.instance.getUtils().swordScoreMap.get(i);
                                float scaledScore = (itemScore / maxSwordSize) * 100f;
                                Color color = new Color(255, 0, 0, 192);
                                if (scaledScore <= 50f) {
                                    int scaledcolor = Math.round((scaledScore / 50f) * 255);
                                    color = new Color(255, scaledcolor, 0, 192);
                                } else if (scaledScore > 50f) {
                                    int scaledcolor = Math.round(((scaledScore - 50f) / 50f) * 255);
                                    color = new Color(255 - scaledcolor, 255, 0, 192);
                                }
                                RenderUtils.drawColoredRect(slotX, slotY, slotX + 16, slotY + 16, Utils.GetColorIntValue(color), 50);
                            }
                        } else if (hoverItem == Items.bow) {
                            float maxBowSize = Float.MIN_VALUE;
                            ConcurrentHashMap<Integer, Float> cache = new ConcurrentHashMap<>(Hytils.instance.getUtils().bowScoreMap);
                            for (Map.Entry<Integer, Float> mapItem : cache.entrySet()) {
                                if (mapItem.getValue() > maxBowSize) {
                                    maxBowSize = mapItem.getValue();
                                }
                            }
                            if (Hytils.instance.getUtils().bowScoreMap.containsKey(i)) {
                                float itemScore = Hytils.instance.getUtils().bowScoreMap.get(i);
                                float scaledScore = (itemScore / maxBowSize) * 100f;
                                Color color = new Color(255, 0, 0, 192);
                                if (scaledScore <= 50f) {
                                    int scaledcolor = Math.round((scaledScore / 50f) * 255);
                                    color = new Color(255, scaledcolor, 0, 192);
                                } else if (scaledScore > 50f) {
                                    int scaledcolor = Math.round(((scaledScore - 50f) / 50f) * 255);
                                    color = new Color(255 - scaledcolor, 255, 0, 192);
                                }
                                RenderUtils.drawColoredRect(slotX, slotY, slotX + 16, slotY + 16, Utils.GetColorIntValue(color), 50);
                            }
                        } else if (hoverItem == Items.fishing_rod) {
                            float maxRodSize = Float.MIN_VALUE;
                            ConcurrentHashMap<Integer, Float> cache = new ConcurrentHashMap<>(Hytils.instance.getUtils().rodScoreMap);
                            for (Map.Entry<Integer, Float> mapItem : cache.entrySet()) {
                                if (mapItem.getValue() > maxRodSize) {
                                    maxRodSize = mapItem.getValue();
                                }
                            }
                            if (Hytils.instance.getUtils().rodScoreMap.containsKey(i)) {
                                if (maxRodSize <= 0) {
                                    RenderUtils.drawColoredRect(slotX, slotY, slotX + 16, slotY + 16, Utils.GetColorIntValue(new Color(0, 255, 0, 192)), 50);
                                } else {
                                    float itemScore = Hytils.instance.getUtils().rodScoreMap.get(i);
                                    float scaledScore = (itemScore / maxRodSize) * 100f;
                                    Color color = new Color(255, 0, 0, 192);
                                    if (scaledScore <= 50f) {
                                        int scaledcolor = Math.round((scaledScore / 50f) * 255);
                                        color = new Color(255, scaledcolor, 0, 192);
                                    } else if (scaledScore > 50f) {
                                        int scaledcolor = Math.round(((scaledScore - 50f) / 50f) * 255);
                                        color = new Color(255 - scaledcolor, 255, 0, 192);
                                    }
                                    RenderUtils.drawColoredRect(slotX, slotY, slotX + 16, slotY + 16, Utils.GetColorIntValue(color), 50);
                                }
                            }
                        }
                    }
                }
            }
            GlStateManager.colorMask(true, true, true, true);
        }

    }
}
