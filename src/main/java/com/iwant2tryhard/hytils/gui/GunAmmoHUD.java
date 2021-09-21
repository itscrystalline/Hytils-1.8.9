package com.iwant2tryhard.hytils.gui;

import com.iwant2tryhard.hytils.Hytils;
import com.iwant2tryhard.hytils.core.GameTypes;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class GunAmmoHUD extends Gui {
    private final ResourceLocation texBars = new ResourceLocation(Hytils.MODID, "textures/bars.png");
    @SubscribeEvent
    public void RenderOverlay(RenderGameOverlayEvent event) {
        if (event.type == RenderGameOverlayEvent.ElementType.ALL){
            ScaledResolution resolution = new ScaledResolution(Hytils.instance.mc);
            int width = Math.round(resolution.getScaledWidth() / 2f);
            int height = Math.round(resolution.getScaledHeight() / 2f);
            EntityPlayerSP player = Hytils.instance.mc.thePlayer;
            if (Hytils.instance.getUtils().currentGame == GameTypes.ARCADE_BLOCKINGDEAD){
                int pistolAmmo = 0, m16Ammo = 0, shotgunAmmo = 0;
                for (ItemStack stack: Hytils.instance.mc.thePlayer.inventory.mainInventory) {
                    if (stack != null && stack.getItem() != null){
                        if (stack.getItem() == Items.ghast_tear){
                            pistolAmmo += stack.stackSize;
                        }
                        if (stack.getItem() == Items.arrow){
                            m16Ammo += stack.stackSize;
                        }
                        if (stack.getItem() == Items.nether_wart){
                            shotgunAmmo += stack.stackSize;
                        }
                    }
                }
                ItemStack holding = Hytils.instance.mc.thePlayer.getHeldItem();
                if (holding != null && holding.getItem() != null){
                    if (holding.getItem() == Items.golden_shovel){
                        if (pistolAmmo > 0){
                            drawCenteredString(Hytils.instance.mc.fontRendererObj, Integer.toString(pistolAmmo), width, height + 10, 11184810);
                        }else{
                            drawCenteredString(Hytils.instance.mc.fontRendererObj, "No Ammo!", width, height + 10, 16711680);
                        }
                    }
                    if (holding.getItem() == Items.iron_shovel){
                        if (m16Ammo > 0){
                            drawCenteredString(Hytils.instance.mc.fontRendererObj, Integer.toString(m16Ammo), width, height + 10, 11184810);
                        }else{
                            drawCenteredString(Hytils.instance.mc.fontRendererObj, "No Ammo!", width, height + 10, 16711680);
                        }
                    }
                    if (holding.getItem() == Items.diamond_shovel){
                        if (shotgunAmmo > 0){
                            drawCenteredString(Hytils.instance.mc.fontRendererObj, Integer.toString(shotgunAmmo), width, height + 10, 11184810);
                        }else{
                            drawCenteredString(Hytils.instance.mc.fontRendererObj, "No Ammo!", width, height + 10, 16711680);
                        }
                    }
                }
            }else if (Hytils.instance.getUtils().currentGame == GameTypes.ARCADE_ZOMBIES_BADBLOOD |
                    Hytils.instance.getUtils().currentGame == GameTypes.ARCADE_ZOMBIES_DEADEND |
                    Hytils.instance.getUtils().currentGame == GameTypes.ARCADE_ZOMBIES_ALIENARCADIUM){
                if (player.getHeldItem() != null && player.getHeldItem().getItem() != null){
                    if (player.experienceLevel > 0){
                        drawCenteredString(Hytils.instance.mc.fontRendererObj, Integer.toString(player.experienceLevel), width, height + 10, 11184810);
                    }else{
                        drawCenteredString(Hytils.instance.mc.fontRendererObj, "No Ammo!", width, height + 10, 16711680);
                    }
//                if (player.experience)
//                drawCenteredString(Hytils.instance.mc.fontRendererObj, Integer.toString(player.experienceLevel), width, height + 15, 11184810);
                    if (Math.round((1f - (((float)(player.getHeldItem().getItemDamage())) / ((float)(player.getHeldItem().getMaxDamage())))) * 100f) < 100){
                        Hytils.instance.mc.renderEngine.bindTexture(texBars);
                        drawTexturedModalRect(width - 7f, height + 30f, 56, 56, 15, 3);
                        drawTexturedModalRect(width - 7f, height + 30f, 56, 59, Math.round((1f - (((float)(player.getHeldItem().getItemDamage())) / ((float)(player.getHeldItem().getMaxDamage())))) * 15f), 3);
                    }
                }
            }
        }
    }
}
