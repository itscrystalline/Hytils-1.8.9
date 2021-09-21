package com.iwant2tryhard.hytils.gui;

import com.iwant2tryhard.hytils.Hytils;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.item.ItemFood;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.GuiIngameForge;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class OnScreenHUD extends Gui {
    private final ResourceLocation texBars = new ResourceLocation(Hytils.MODID, "textures/bars.png");
    private float prevInitialAbsorption = 0f;

    @SubscribeEvent
    public void RenderOverlay(RenderGameOverlayEvent event) {
        Minecraft mc = Hytils.instance.mc;
        ScaledResolution resolution = new ScaledResolution(mc);
        float width = resolution.getScaledWidth();
        float height = resolution.getScaledHeight();
        if (!mc.thePlayer.capabilities.isCreativeMode){
            if (mc.thePlayer.getAbsorptionAmount() > 0){
                if (prevInitialAbsorption <= 0){
                    prevInitialAbsorption = mc.thePlayer.getAbsorptionAmount();
                }
            }else{
                prevInitialAbsorption = 0f;
            }


            GuiIngameForge.renderHealth = !Hytils.instance.getConfig().showCustomHealthBar;
            GuiIngameForge.renderFood = !Hytils.instance.getConfig().showCustomHungerBar;

            GuiIngameForge.renderArmor = !Hytils.instance.getConfig().showCustomArmorBar;

            GuiIngameForge.renderAir = !Hytils.instance.getConfig().showCustomAirBar;

            GuiIngameForge.renderExperiance = !(Hytils.instance.getConfig().showCustomLevelBar && Hytils.instance.getUtils().isInHypixelLobby());

            float ycoord = height - 39;
            if (mc.thePlayer.experienceLevel > 0) {
                ycoord -= 4;
            }

            if (event.type == RenderGameOverlayEvent.ElementType.TEXT){
                if (Hytils.instance.getConfig().showCustomHealthBar){
                    mc.getTextureManager().bindTexture(texBars);
                    if (Hytils.instance.getConfig().showCustomHungerBar){
                        //normal health bar
                        if (mc.thePlayer.getAbsorptionAmount() > 0){
                            if (mc.thePlayer.getActivePotionEffect(Potion.wither) != null){
                                drawTexturedModalRect((width / 2f) - 91f, ycoord, 0, 143, 90, 5);
                                if (mc.thePlayer.getHealth() > 0){
                                    drawTexturedModalRect((width / 2f) - 91f, ycoord, 0, 148, Math.round((mc.thePlayer.getHealth() / mc.thePlayer.getMaxHealth()) * 62f), 5);
                                }
                            } else if (mc.thePlayer.getActivePotionEffect(Potion.poison) != null) {
                                drawTexturedModalRect((width / 2f) - 91f, ycoord, 0, 93, 90, 5);
                                if (mc.thePlayer.getHealth() > 0){
                                    drawTexturedModalRect((width / 2f) - 91f, ycoord, 0, 98, Math.round((mc.thePlayer.getHealth() / mc.thePlayer.getMaxHealth()) * 62f), 5);
                                }
                            } else {
                                drawTexturedModalRect((width / 2f) - 91f, ycoord, 0, 35, 90, 5);
                                if (mc.thePlayer.getHealth() > 0){
                                    drawTexturedModalRect((width / 2f) - 91f, ycoord, 0, 40, Math.round((mc.thePlayer.getHealth() / mc.thePlayer.getMaxHealth()) * 62f), 5);
                                }
                            }

                            drawTexturedModalRect((width / 2f) - 28f, ycoord, 63, 40, Math.round((mc.thePlayer.getAbsorptionAmount() / prevInitialAbsorption) * 27f), 5);

                        }else{
                            if (mc.thePlayer.getActivePotionEffect(Potion.wither) != null){
                                drawTexturedModalRect((width / 2f) - 91f, ycoord, 0, 133, 90, 5);
                                if (mc.thePlayer.getHealth() > 0){
                                    drawTexturedModalRect((width / 2f) - 91f, ycoord, 0, 138, Math.round((mc.thePlayer.getHealth() / mc.thePlayer.getMaxHealth()) * 90f), 5);
                                }
                            } else if (mc.thePlayer.getActivePotionEffect(Potion.poison) != null) {
                                drawTexturedModalRect((width / 2f) - 91f, ycoord, 0, 83, 90, 5);
                                if (mc.thePlayer.getHealth() > 0){
                                    drawTexturedModalRect((width / 2f) - 91f, ycoord, 0, 88, Math.round((mc.thePlayer.getHealth() / mc.thePlayer.getMaxHealth()) * 90f), 5);
                                }
                            } else {
                                drawTexturedModalRect((width / 2f) - 91f, ycoord, 0, 20, 90, 5);
                                if (mc.thePlayer.getHealth() > 0){
                                    drawTexturedModalRect((width / 2f) - 91f, ycoord, 0, 25, Math.round((mc.thePlayer.getHealth() / mc.thePlayer.getMaxHealth()) * 90f), 5);
                                }
                            }
                        }
                        //food bar
                        drawTexturedModalRect((width / 2f) + 1f, ycoord, 92, 25, 90, 5);
                        if (mc.thePlayer.getFoodStats().getFoodLevel() < 20){
                            drawTexturedModalRect((width / 2f) + 1f, ycoord, 92, 20, Math.round((1f - (mc.thePlayer.getFoodStats().getFoodLevel() / 20f)) * 90f), 5);
                        }
                        if (mc.thePlayer.getHeldItem() != null && mc.thePlayer.getHeldItem().getItem() != null){
                            if (mc.thePlayer.getHeldItem().getItem() instanceof ItemFood){
                                float hungerRegen = ((ItemFood) mc.thePlayer.getHeldItem().getItem()).getHealAmount(mc.thePlayer.getHeldItem());
                                float potentialFoodAmount = mc.thePlayer.getFoodStats().getFoodLevel() + hungerRegen;
                                int round = Math.round(Math.min(1 - ((potentialFoodAmount / 20f)), 1f) * 90f);
                                drawTexturedModalRect((width / 2f) + 2f + round, ycoord + 1f, 93 + round, 31, 90 - round, 5);
                            }
                        }
                    }else{
                        if (mc.thePlayer.getAbsorptionAmount() > 0){
                            drawTexturedModalRect((width / 2f) - 91f, ycoord, 0, 10, 182, 5);
                            if (mc.thePlayer.getHealth() > 0){
                                drawTexturedModalRect((width / 2f) - 91f, ycoord, 0, 15, Math.round((mc.thePlayer.getHealth() / mc.thePlayer.getMaxHealth()) * 127f), 5);
                            }
                            if (mc.thePlayer.getAbsorptionAmount() > 0){
                                drawTexturedModalRect((width / 2f) + 37f, ycoord, 128, 15, Math.round((mc.thePlayer.getAbsorptionAmount() / prevInitialAbsorption) * 54f), 5);
                            }
                        }else{
                            drawTexturedModalRect((width / 2f) - 91f, ycoord, 0, 0, 182, 5);
                            if (mc.thePlayer.getHealth() > 0){
                                drawTexturedModalRect((width / 2f) - 91f, ycoord, 0, 5, Math.round((mc.thePlayer.getHealth() / mc.thePlayer.getMaxHealth()) * 182f), 5);
                            }
                        }
                    }
                }

                if (Hytils.instance.getConfig().showCustomArmorBar){
                    mc.getTextureManager().bindTexture(texBars);
                    if (mc.thePlayer.getTotalArmorValue() > 0){
                        drawTexturedModalRect((width / 2f) - 91f, ycoord - 7, 0, 45, 90, 5);
                        drawTexturedModalRect((width / 2f) - 91f, ycoord - 7, 0, 50, Math.round((mc.thePlayer.getTotalArmorValue() / 20f) * 90f), 5);
                    }
                }

                if (Hytils.instance.getConfig().showCustomAirBar){
                    mc.getTextureManager().bindTexture(texBars);
                    if (mc.thePlayer.isInsideOfMaterial(Material.water)) {

                        float airPrec = mc.thePlayer.getAir() * 0.00333f;
                        if (airPrec < 0) airPrec = 0;

                        drawTexturedModalRect((width / 2f) + 1f, ycoord - 7, 92, 50, 90, 5);
                        drawTexturedModalRect((width / 2f) + 1f, ycoord - 7, 92, 45, Math.round(90f * (1f - airPrec)), 5);

                    }
                }

                /*if (Hytils.instance.getConfig().showCustomLevelBar && Hytils.instance.getUtils().isInHypixelLobby()){
                    mc.fontRendererObj.drawString("Lvl. " + mc.thePlayer.experienceLevel, Math.round((width / 2f) - 91f), Math.round(height - 33), 2752297);
                    drawTexturedModalRect((width / 2f) - 91f + mc.fontRendererObj.getStringWidth("Lvl. " + mc.thePlayer.experienceLevel) + 5f, height - 33, );
                }*/
            }
        }else{
            GuiIngameForge.renderHealth = true;
            GuiIngameForge.renderFood = true;

            GuiIngameForge.renderArmor = true;

            GuiIngameForge.renderAir = true;
        }

    }
}
