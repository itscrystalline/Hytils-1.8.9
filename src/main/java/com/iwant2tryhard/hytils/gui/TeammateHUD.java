package com.iwant2tryhard.hytils.gui;

import com.iwant2tryhard.hytils.Hytils;
import com.iwant2tryhard.hytils.core.GameTypes;
import com.iwant2tryhard.hytils.core.Utils;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemFood;
import net.minecraft.potion.Potion;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TeammateHUD extends Gui {
    private List<String> cachedDisplayTeammates = new ArrayList<String>();
    private final ResourceLocation texBars = new ResourceLocation(Hytils.MODID, "textures/bars.png");

    @SubscribeEvent
    public void RenderOverlay(RenderGameOverlayEvent event) {
        if (event.type == RenderGameOverlayEvent.ElementType.TEXT){
            Minecraft mc = Hytils.instance.mc;
            ScaledResolution resolution = new ScaledResolution(Hytils.instance.mc);
            float width = resolution.getScaledWidth();
            float height = resolution.getScaledHeight();

            List<String> displayTeammates = Hytils.instance.getUtils().displayTeammates;

            if (!Hytils.instance.getConfig().teammateHUDShowUndetected){
                cachedDisplayTeammates.clear();
                for (String cachedName : displayTeammates) {
                    EntityPlayer comparePlayer = null;
                    for (EntityPlayer player: Hytils.instance.mc.theWorld.playerEntities) {
                        if (player.getDisplayNameString() != null){
                            String playerName = player.getDisplayNameString();
                            if (playerName.startsWith("\u00a7")){
                                playerName = playerName.replace("\u00a7", "");
                                playerName = playerName.substring(1);
                            }

                            if (playerName.equals(cachedName)){
                                comparePlayer = player;
                            }
                        }
                    }

                    if (comparePlayer == null){
                        cachedDisplayTeammates.add(cachedName);
                    }
                }
            }

            if (Hytils.instance.getConfig().teammateHUDCheckGame){
                if (Hytils.instance.getUtils().currentGame != null && Hytils.instance.getUtils().currentGame != GameTypes.NOT_IN_GAME){
                    if (cachedDisplayTeammates.size() > 0){
                        if (Hytils.instance.getUtils().currentGame.gameGroup.equals("bedwars")){
                            for (int i = 0; i < cachedDisplayTeammates.size(); i++) {
                                String cachedTeammate = cachedDisplayTeammates.get(i);
                                EntityPlayer teammate = null;
                                for (EntityPlayer player: Hytils.instance.mc.theWorld.playerEntities) {
                                    if (player.getDisplayNameString() != null){
                                        String playerName = player.getDisplayNameString();
                                        if (playerName.startsWith("\u00a7")){
                                            playerName = playerName.replace("\u00a7", "");
                                            playerName = playerName.substring(1);
                                        }
                                        if (cachedTeammate != null){
                                            if (playerName.equals(cachedTeammate)){
                                                teammate = player;
                                            }
                                        }
                                    }
                                }
                                if (teammate != null){
                                    String teammateName = teammate.getDisplayNameString();
                                    if (teammateName.startsWith("\u00a7")){
                                        teammateName = teammateName.replace("\u00a7", "");
                                        teammateName = teammateName.substring(1);
                                    }

                                    if (!Hytils.instance.getUtils().prevInitialAbsorption.containsKey(teammateName)){
                                        Hytils.instance.getUtils().prevInitialAbsorption.put(teammateName, 0f);
                                    }

                                    if (teammate.getAbsorptionAmount() > 0){
                                        if (Hytils.instance.getUtils().prevInitialAbsorption.get(teammateName) <= 0){
                                            Hytils.instance.getUtils().prevInitialAbsorption.put(teammateName, teammate.getAbsorptionAmount());
                                        }
                                    }else{
                                        Hytils.instance.getUtils().prevInitialAbsorption.put(teammateName, 0f);
                                    }

                                    float absorption = 0;
                                    if (Hytils.instance.getUtils().prevInitialAbsorption.get(teammateName) != null){
                                        absorption = Hytils.instance.getUtils().prevInitialAbsorption.get(teammateName);
                                    }

                                    int color = 11184810;
                                    if (teammate.getCurrentArmor(2) != null && teammate.getCurrentArmor(2).getItem() != null){
                                        color = ((ItemArmor) teammate.getCurrentArmor(2).getItem()).getColor(teammate.getCurrentArmor(2));
                                    }
                                    boolean canFly = teammate.capabilities.allowFlying;
                                    boolean isInvisible = teammate.isInvisible();
                                    if (Math.round(Utils.distanceOf(Hytils.instance.mc.thePlayer, teammate)) > 0){
                                        drawCenteredString(Hytils.instance.mc.fontRendererObj,  EnumChatFormatting.GRAY + Long.toString(Math.round(Utils.distanceOf(Hytils.instance.mc.thePlayer, teammate))) + "m", Math.round(width / (cachedDisplayTeammates.size() + 1)) * (i + 1), Math.round(height / 5f) - 10, 11184810);
                                    }
                                    drawCenteredString(Hytils.instance.mc.fontRendererObj, EnumChatFormatting.BOLD + teammate.getDisplayNameString(), Math.round(width / (cachedDisplayTeammates.size() + 1)) * (i + 1), Math.round(height / 5f), color);
                                    //bars
                                    Hytils.instance.mc.renderEngine.bindTexture(texBars);
                                    float xcoord = Math.round(width / (cachedDisplayTeammates.size() + 1)) * (i + 1);
                                    float ycoord = Math.round(height / 5f) + 10;
                                    if (Hytils.instance.getConfig().teammateHUDHealthBar){
                                        if (teammate.getAbsorptionAmount() > 0){
                                            if (teammate.getActivePotionEffect(Potion.wither) != null){
                                                drawTexturedModalRect(xcoord - 19f, ycoord, 0, 154, 39, 3);
                                                if (teammate.getHealth() > 0){
                                                    drawTexturedModalRect(xcoord - 19f, ycoord, 0, 157, Math.round((teammate.getHealth() / teammate.getMaxHealth()) * 30f), 3);
                                                }
                                            } else if (teammate.getActivePotionEffect(Potion.poison) != null) {
                                                drawTexturedModalRect(xcoord - 19f, ycoord, 0, 104, 39, 3);
                                                if (teammate.getHealth() > 0){
                                                    drawTexturedModalRect(xcoord - 19f, ycoord, 0, 107, Math.round((teammate.getHealth() / teammate.getMaxHealth()) * 30f), 3);
                                                }
                                            } else {
                                                drawTexturedModalRect(xcoord - 19f, ycoord, 0, 56, 39, 3);
                                                if (teammate.getHealth() > 0){
                                                    drawTexturedModalRect(xcoord - 19f, ycoord, 0, 59, Math.round((teammate.getHealth() / teammate.getMaxHealth()) * 30f), 3);
                                                }
                                            }
                                            drawTexturedModalRect(xcoord + 10f, ycoord, 29, 59, Math.round((teammate.getAbsorptionAmount() / absorption) * 10f), 3);
                                        }else{
                                            if (teammate.getActivePotionEffect(Potion.wither) != null){
                                                drawTexturedModalRect(xcoord - 16f, ycoord, 0, 154, 30, 3);
                                                if (teammate.getHealth() > 0){
                                                    drawTexturedModalRect(xcoord - 16f, ycoord, 0, 157, Math.round((teammate.getHealth() / teammate.getMaxHealth()) * 30f), 3);
                                                }
                                            } else if (teammate.getActivePotionEffect(Potion.poison) != null) {
                                                drawTexturedModalRect(xcoord - 16f, ycoord, 0, 104, 30, 3);
                                                if (teammate.getHealth() > 0){
                                                    drawTexturedModalRect(xcoord - 16f, ycoord, 0, 107, Math.round((teammate.getHealth() / teammate.getMaxHealth()) * 30f), 3);
                                                }
                                            } else {
                                                drawTexturedModalRect(xcoord - 16f, ycoord, 0, 56, 30, 3);
                                                if (teammate.getHealth() > 0){
                                                    drawTexturedModalRect(xcoord - 16f, ycoord, 0, 59, Math.round((teammate.getHealth() / teammate.getMaxHealth()) * 30f), 3);
                                                }
                                            }
                                        }
                                    }

                                    int subractionValue = 0;

                                    if (Hytils.instance.getConfig().teammateHUDHungerBar){
                                        if ((Hytils.instance.getConfig().teammateHUDArmorBar && teammate.getTotalArmorValue() > 0) || (Hytils.instance.getConfig().teammateHUDAirBar && teammate.isInsideOfMaterial(Material.water))){
                                            if ((Hytils.instance.getConfig().teammateHUDArmorBar && teammate.getTotalArmorValue() > 0) && (Hytils.instance.getConfig().teammateHUDAirBar && teammate.isInsideOfMaterial(Material.water))){
                                                subractionValue += 23;
                                            }else{
                                                subractionValue += 15;
                                            }
                                        }else{
                                            subractionValue += 7;
                                        }
                                    }

                                    if (Hytils.instance.getConfig().teammateHUDHungerBar){
                                        drawTexturedModalRect(xcoord - subractionValue, ycoord + 5, 40, 59, 15, 3);
                                        if (mc.thePlayer.getFoodStats().getFoodLevel() < 20){
                                            drawTexturedModalRect(xcoord - subractionValue, ycoord + 5, 40, 56, Math.round((1f - (mc.thePlayer.getFoodStats().getFoodLevel() / 20f)) * 15f), 3);
                                        }
                                    }

                                    if (Hytils.instance.getConfig().teammateHUDArmorBar && teammate.getTotalArmorValue() > 0){
                                        drawTexturedModalRect(xcoord - subractionValue + 16f, ycoord + 5, 56, 56, 15, 3);
                                        drawTexturedModalRect(xcoord - subractionValue + 16f, ycoord + 5, 56, 59, Math.round((teammate.getTotalArmorValue() / 20f) * 15f), 3);
                                    }

                                    if (Hytils.instance.getConfig().teammateHUDAirBar && teammate.isInsideOfMaterial(Material.water)){
                                        float airPrec = teammate.getAir() * 0.00333f;
                                        if (airPrec < 0) airPrec = 0;

                                        drawTexturedModalRect(xcoord - subractionValue + 16f, ycoord + 5, 72, 56, 15, 3);
                                        drawTexturedModalRect(xcoord - subractionValue + 16f, ycoord + 5, 72, 59, Math.round(15f * (airPrec)), 3);
                                    }
                                    //end of bars
                                    if (isInvisible){
                                        if (canFly){
                                            drawCenteredString(Hytils.instance.mc.fontRendererObj, "Dead", Math.round(width / (cachedDisplayTeammates.size() + 1)) * (i + 1), Math.round(height / 5f) + 20, 16711680);
                                        }else{
                                            drawCenteredString(Hytils.instance.mc.fontRendererObj, "Invisible", Math.round(width / (cachedDisplayTeammates.size() + 1)) * (i + 1), Math.round(height / 5f) + 20, 11184810);
                                        }
                                    }
                                }else{
                                    drawCenteredString(Hytils.instance.mc.fontRendererObj, EnumChatFormatting.BOLD + cachedTeammate, Math.round(width / (cachedDisplayTeammates.size() + 1)) * (i + 1), Math.round(height / 5f), 11184810);
                                    drawCenteredString(Hytils.instance.mc.fontRendererObj, "Disconnected", Math.round(width / (cachedDisplayTeammates.size() + 1)) * (i + 1), Math.round(height / 5f) + 20, 16711680);
                                }
                            }
                        }else if (Hytils.instance.getUtils().currentGame == GameTypes.ARCADE_BLOCKINGDEAD ||
                                Hytils.instance.getUtils().currentGame == GameTypes.ARCADE_ZOMBIES_DEADEND || Hytils.instance.getUtils().currentGame == GameTypes.ARCADE_ZOMBIES_BADBLOOD ||
                                Hytils.instance.getUtils().currentGame == GameTypes.ARCADE_ZOMBIES_ALIENARCADIUM){
                            for (int i = 0; i < cachedDisplayTeammates.size(); i++) {
                                String cachedTeammate = cachedDisplayTeammates.get(i);
                                EntityPlayer teammate = null;
                                for (EntityPlayer player: Hytils.instance.mc.theWorld.playerEntities) {
                                    if (player.getDisplayNameString() != null){
                                        String playerName = player.getDisplayNameString();
                                        if (playerName.startsWith("\u00a7")){
                                            playerName = playerName.replace("\u00a7", "");
                                            playerName = playerName.substring(1);
                                        }
                                        if (cachedTeammate != null){
                                            if (playerName.equals(cachedTeammate)){
                                                teammate = player;
                                            }
                                        }
                                    }
                                }
                                if (teammate != null){
                                    String teammateName = teammate.getDisplayNameString();
                                    if (teammateName.startsWith("\u00a7")){
                                        teammateName = teammateName.replace("\u00a7", "");
                                        teammateName = teammateName.substring(1);
                                    }

                                    if (!Hytils.instance.getUtils().prevInitialAbsorption.containsKey(teammateName)){
                                        Hytils.instance.getUtils().prevInitialAbsorption.put(teammateName, 0f);
                                    }

                                    if (teammate.getAbsorptionAmount() > 0){
                                        if (Hytils.instance.getUtils().prevInitialAbsorption.get(teammateName) <= 0){
                                            Hytils.instance.getUtils().prevInitialAbsorption.put(teammateName, teammate.getAbsorptionAmount());
                                        }
                                    }else{
                                        Hytils.instance.getUtils().prevInitialAbsorption.put(teammateName, 0f);
                                    }

                                    float absorption = 0;
                                    if (Hytils.instance.getUtils().prevInitialAbsorption.get(teammateName) != null){
                                        absorption = Hytils.instance.getUtils().prevInitialAbsorption.get(teammateName);
                                    }

                                    int color = 11184810;
                                    if (teammate.getCurrentArmor(2) != null && teammate.getCurrentArmor(2).getItem() != null){
                                        color = ((ItemArmor) teammate.getCurrentArmor(2).getItem()).getColor(teammate.getCurrentArmor(2));
                                    }
                                    boolean canFly = teammate.capabilities.allowFlying;
                                    boolean isInvisible = teammate.isInvisible();
                                    if (Math.round(Utils.distanceOf(Hytils.instance.mc.thePlayer, teammate)) > 0){
                                        drawCenteredString(Hytils.instance.mc.fontRendererObj,  EnumChatFormatting.GRAY + Long.toString(Math.round(Utils.distanceOf(Hytils.instance.mc.thePlayer, teammate))) + "m", Math.round(width / (cachedDisplayTeammates.size() + 1)) * (i + 1), Math.round(height / 5f) - 10, 11184810);
                                    }
                                    drawCenteredString(Hytils.instance.mc.fontRendererObj, EnumChatFormatting.BOLD + teammate.getDisplayNameString(), Math.round(width / (cachedDisplayTeammates.size() + 1)) * (i + 1), Math.round(height / 5f), color);
                                    //bars
                                    Hytils.instance.mc.renderEngine.bindTexture(texBars);
                                    float xcoord = Math.round(width / (cachedDisplayTeammates.size() + 1)) * (i + 1);
                                    float ycoord = Math.round(height / 5f) + 10;
                                    if (Hytils.instance.getConfig().teammateHUDHealthBar){
                                        if (teammate.getAbsorptionAmount() > 0){
                                            if (teammate.getActivePotionEffect(Potion.wither) != null){
                                                drawTexturedModalRect(xcoord - 19f, ycoord, 0, 154, 39, 3);
                                                if (teammate.getHealth() > 0){
                                                    drawTexturedModalRect(xcoord - 19f, ycoord, 0, 157, Math.round((teammate.getHealth() / teammate.getMaxHealth()) * 30f), 3);
                                                }
                                            } else if (teammate.getActivePotionEffect(Potion.poison) != null) {
                                               drawTexturedModalRect(xcoord - 19f, ycoord, 0, 104, 39, 3);
                                                if (teammate.getHealth() > 0){
                                                    drawTexturedModalRect(xcoord - 19f, ycoord, 0, 107, Math.round((teammate.getHealth() / teammate.getMaxHealth()) * 30f), 3);
                                                }
                                            } else {
                                                drawTexturedModalRect(xcoord - 19f, ycoord, 0, 56, 39, 3);
                                                if (teammate.getHealth() > 0){
                                                    drawTexturedModalRect(xcoord - 19f, ycoord, 0, 59, Math.round((teammate.getHealth() / teammate.getMaxHealth()) * 30f), 3);
                                                }
                                            }
                                            drawTexturedModalRect(xcoord + 10f, ycoord, 29, 59, Math.round((teammate.getAbsorptionAmount() / absorption) * 10f), 3);
                                        }else{
                                            if (teammate.getActivePotionEffect(Potion.wither) != null){
                                                drawTexturedModalRect(xcoord - 16f, ycoord, 0, 154, 30, 3);
                                                if (teammate.getHealth() > 0){
                                                    drawTexturedModalRect(xcoord - 16f, ycoord, 0, 157, Math.round((teammate.getHealth() / teammate.getMaxHealth()) * 30f), 3);
                                                }
                                            } else if (teammate.getActivePotionEffect(Potion.poison) != null) {
                                                drawTexturedModalRect(xcoord - 16f, ycoord, 0, 104, 30, 3);
                                                if (teammate.getHealth() > 0){
                                                    drawTexturedModalRect(xcoord - 16f, ycoord, 0, 107, Math.round((teammate.getHealth() / teammate.getMaxHealth()) * 30f), 3);
                                                }
                                            } else {
                                                drawTexturedModalRect(xcoord - 16f, ycoord, 0, 56, 30, 3);
                                                if (teammate.getHealth() > 0){
                                                    drawTexturedModalRect(xcoord - 16f, ycoord, 0, 59, Math.round((teammate.getHealth() / teammate.getMaxHealth()) * 30f), 3);
                                                }
                                            }
                                        }
                                    }

                                    int subractionValue = 0;

                                    if (Hytils.instance.getConfig().teammateHUDHungerBar){
                                        if ((Hytils.instance.getConfig().teammateHUDArmorBar && teammate.getTotalArmorValue() > 0) || (Hytils.instance.getConfig().teammateHUDAirBar && teammate.isInsideOfMaterial(Material.water))){
                                            if ((Hytils.instance.getConfig().teammateHUDArmorBar && teammate.getTotalArmorValue() > 0) && (Hytils.instance.getConfig().teammateHUDAirBar && teammate.isInsideOfMaterial(Material.water))){
                                                subractionValue += 23;
                                            }else{
                                                subractionValue += 15;
                                            }
                                        }else{
                                            subractionValue += 7;
                                        }
                                    }

                                    if (Hytils.instance.getConfig().teammateHUDHungerBar){
                                        drawTexturedModalRect(xcoord - subractionValue, ycoord + 5, 40, 59, 15, 3);
                                        if (mc.thePlayer.getFoodStats().getFoodLevel() < 20){
                                            drawTexturedModalRect(xcoord - subractionValue, ycoord + 5, 40, 56, Math.round((1f - (mc.thePlayer.getFoodStats().getFoodLevel() / 20f)) * 15f), 3);
                                        }
                                    }

                                    if (Hytils.instance.getConfig().teammateHUDArmorBar && teammate.getTotalArmorValue() > 0){
                                        drawTexturedModalRect(xcoord - subractionValue + 16f, ycoord + 5, 56, 56, 15, 3);
                                        drawTexturedModalRect(xcoord - subractionValue + 16f, ycoord + 5, 56, 59, Math.round((teammate.getTotalArmorValue() / 20f) * 15f), 3);
                                    }

                                    if (Hytils.instance.getConfig().teammateHUDAirBar && teammate.isInsideOfMaterial(Material.water)){
                                        float airPrec = teammate.getAir() * 0.00333f;
                                        if (airPrec < 0) airPrec = 0;

                                        drawTexturedModalRect(xcoord - subractionValue + 16f, ycoord + 5, 72, 56, 15, 3);
                                        drawTexturedModalRect(xcoord - subractionValue + 16f, ycoord + 5, 72, 59, Math.round(15f * (airPrec)), 3);
                                    }
                                    //end of bars
                                    if (isInvisible){
                                        drawCenteredString(Hytils.instance.mc.fontRendererObj, "Dead", Math.round(width / (cachedDisplayTeammates.size() + 1)) * (i + 1), Math.round(height / 5f) + 20, 16711680);
                                    }
                                }else{
                                    drawCenteredString(Hytils.instance.mc.fontRendererObj, EnumChatFormatting.BOLD + cachedTeammate, Math.round(width / (cachedDisplayTeammates.size() + 1)) * (i + 1), Math.round(height / 5f), 11184810);
                                    drawCenteredString(Hytils.instance.mc.fontRendererObj, "Disconnected", Math.round(width / (cachedDisplayTeammates.size() + 1)) * (i + 1), Math.round(height / 5f) + 20, 16711680);
                                }
                            }
                        }
                    }else{
                        drawCenteredString(Hytils.instance.mc.fontRendererObj, "No Teammates", Math.round(width / 2f), Math.round(height / 5f), 16711680);
                    }
                }
            }else{
                if (Hytils.instance.getConfig().teammateHUDEnabled){
                    if (cachedDisplayTeammates.size() > 0){
                        for (int i = 0; i < cachedDisplayTeammates.size(); i++) {
                            String cachedTeammate = cachedDisplayTeammates.get(i);
                            EntityPlayer teammate = null;
                            for (EntityPlayer player: Hytils.instance.mc.theWorld.playerEntities) {
                                if (player.getDisplayNameString() != null){
                                    String playerName = player.getDisplayNameString();
                                    if (playerName.startsWith("\u00a7")){
                                        playerName = playerName.replace("\u00a7", "");
                                        playerName = playerName.substring(1);
                                    }
                                    if (cachedTeammate != null){
                                        if (playerName.equals(cachedTeammate)){
                                            teammate = player;
                                        }
                                    }
                                }
                            }
                            if (teammate != null){
                                String teammateName = teammate.getDisplayNameString();
                                if (teammateName.startsWith("\u00a7")){
                                    teammateName = teammateName.replace("\u00a7", "");
                                    teammateName = teammateName.substring(1);
                                }

                                if (!Hytils.instance.getUtils().prevInitialAbsorption.containsKey(teammateName)){
                                    Hytils.instance.getUtils().prevInitialAbsorption.put(teammateName, 0f);
                                }

                                if (teammate.getAbsorptionAmount() > 0){
                                    if (Hytils.instance.getUtils().prevInitialAbsorption.get(teammateName) <= 0){
                                        Hytils.instance.getUtils().prevInitialAbsorption.put(teammateName, teammate.getAbsorptionAmount());
                                    }
                                }else{
                                    Hytils.instance.getUtils().prevInitialAbsorption.put(teammateName, 0f);
                                }

                                float absorption = 0;
                                if (Hytils.instance.getUtils().prevInitialAbsorption.get(teammateName) != null){
                                    absorption = Hytils.instance.getUtils().prevInitialAbsorption.get(teammateName);
                                }

                                int color = 11184810;
                                if (teammate.getCurrentArmor(2) != null && teammate.getCurrentArmor(2).getItem() != null){
                                    color = ((ItemArmor) teammate.getCurrentArmor(2).getItem()).getColor(teammate.getCurrentArmor(2));
                                }
                                boolean canFly = teammate.capabilities.allowFlying;
                                boolean isInvisible = teammate.isInvisible();
                                if (Math.round(Utils.distanceOf(Hytils.instance.mc.thePlayer, teammate)) > 0){
                                    drawCenteredString(Hytils.instance.mc.fontRendererObj,  EnumChatFormatting.GRAY + Long.toString(Math.round(Utils.distanceOf(Hytils.instance.mc.thePlayer, teammate))) + "m", Math.round(width / (cachedDisplayTeammates.size() + 1)) * (i + 1), Math.round(height / 5f) - 10, 11184810);
                                }
                                drawCenteredString(Hytils.instance.mc.fontRendererObj, EnumChatFormatting.BOLD + teammate.getDisplayNameString(), Math.round(width / (cachedDisplayTeammates.size() + 1)) * (i + 1), Math.round(height / 5f), color);
                                //bars
                                Hytils.instance.mc.renderEngine.bindTexture(texBars);
                                float xcoord = Math.round(width / (cachedDisplayTeammates.size() + 1)) * (i + 1);
                                float ycoord = Math.round(height / 5f) + 10;
                                if (Hytils.instance.getConfig().teammateHUDHealthBar){
                                    if (teammate.getAbsorptionAmount() > 0){
                                        if (teammate.getActivePotionEffect(Potion.wither) != null){
                                            drawTexturedModalRect(xcoord - 19f, ycoord, 0, 154, 39, 3);
                                            if (teammate.getHealth() > 0){
                                                drawTexturedModalRect(xcoord - 19f, ycoord, 0, 157, Math.round((teammate.getHealth() / teammate.getMaxHealth()) * 30f), 3);
                                            }
                                        } else if (teammate.getActivePotionEffect(Potion.poison) != null) {
                                            drawTexturedModalRect(xcoord - 19f, ycoord, 0, 104, 39, 3);
                                            if (teammate.getHealth() > 0){
                                                drawTexturedModalRect(xcoord - 19f, ycoord, 0, 107, Math.round((teammate.getHealth() / teammate.getMaxHealth()) * 30f), 3);
                                            }
                                        } else {
                                            drawTexturedModalRect(xcoord - 19f, ycoord, 0, 56, 39, 3);
                                            if (teammate.getHealth() > 0){
                                                drawTexturedModalRect(xcoord - 19f, ycoord, 0, 59, Math.round((teammate.getHealth() / teammate.getMaxHealth()) * 30f), 3);
                                            }
                                        }
                                        drawTexturedModalRect(xcoord + 10f, ycoord, 29, 59, Math.round((teammate.getAbsorptionAmount() / absorption) * 10f), 3);
                                    }else{
                                        if (teammate.getActivePotionEffect(Potion.wither) != null){
                                            drawTexturedModalRect(xcoord - 16f, ycoord, 0, 154, 30, 3);
                                            if (teammate.getHealth() > 0){
                                                drawTexturedModalRect(xcoord - 16f, ycoord, 0, 157, Math.round((teammate.getHealth() / teammate.getMaxHealth()) * 30f), 3);
                                            }
                                        } else if (teammate.getActivePotionEffect(Potion.poison) != null) {
                                            drawTexturedModalRect(xcoord - 16f, ycoord, 0, 104, 30, 3);
                                            if (teammate.getHealth() > 0){
                                                drawTexturedModalRect(xcoord - 16f, ycoord, 0, 107, Math.round((teammate.getHealth() / teammate.getMaxHealth()) * 30f), 3);
                                            }
                                        } else {
                                            drawTexturedModalRect(xcoord - 16f, ycoord, 0, 56, 30, 3);
                                            if (teammate.getHealth() > 0){
                                                drawTexturedModalRect(xcoord - 16f, ycoord, 0, 59, Math.round((teammate.getHealth() / teammate.getMaxHealth()) * 30f), 3);
                                            }
                                        }
                                    }
                                }

                                int subractionValue = 0;

                                if (Hytils.instance.getConfig().teammateHUDHungerBar){
                                    if ((Hytils.instance.getConfig().teammateHUDArmorBar && teammate.getTotalArmorValue() > 0) || (Hytils.instance.getConfig().teammateHUDAirBar && teammate.isInsideOfMaterial(Material.water))){
                                        if ((Hytils.instance.getConfig().teammateHUDArmorBar && teammate.getTotalArmorValue() > 0) && (Hytils.instance.getConfig().teammateHUDAirBar && teammate.isInsideOfMaterial(Material.water))){
                                            subractionValue += 23;
                                        }else{
                                            subractionValue += 15;
                                        }
                                    }else{
                                        subractionValue += 7;
                                    }
                                }

                                if (Hytils.instance.getConfig().teammateHUDHungerBar){
                                    drawTexturedModalRect(xcoord - subractionValue, ycoord + 5, 40, 59, 15, 3);
                                    if (mc.thePlayer.getFoodStats().getFoodLevel() < 20){
                                        drawTexturedModalRect(xcoord - subractionValue, ycoord + 5, 40, 56, Math.round((1f - (mc.thePlayer.getFoodStats().getFoodLevel() / 20f)) * 15f), 3);
                                    }
                                }

                                if (Hytils.instance.getConfig().teammateHUDArmorBar && teammate.getTotalArmorValue() > 0){
                                    drawTexturedModalRect(xcoord - subractionValue + 16f, ycoord + 5, 56, 56, 15, 3);
                                    drawTexturedModalRect(xcoord - subractionValue + 16f, ycoord + 5, 56, 59, Math.round((teammate.getTotalArmorValue() / 20f) * 15f), 3);
                                }

                                if (Hytils.instance.getConfig().teammateHUDAirBar && teammate.isInsideOfMaterial(Material.water)){
                                    float airPrec = teammate.getAir() * 0.00333f;
                                    if (airPrec < 0) airPrec = 0;

                                    drawTexturedModalRect(xcoord - subractionValue + 16f, ycoord + 5, 72, 56, 15, 3);
                                    drawTexturedModalRect(xcoord - subractionValue + 16f, ycoord + 5, 72, 59, Math.round(15f * (airPrec)), 3);
                                }
                                //end of bars
                                if (isInvisible){
                                    if (canFly){
                                        drawCenteredString(Hytils.instance.mc.fontRendererObj, "Dead", Math.round(width / (cachedDisplayTeammates.size() + 1)) * (i + 1), Math.round(height / 5f) + 20, 16711680);
                                    }else{
                                        drawCenteredString(Hytils.instance.mc.fontRendererObj, "Invisible", Math.round(width / (cachedDisplayTeammates.size() + 1)) * (i + 1), Math.round(height / 5f) + 20, 11184810);
                                    }
                                }
                            }else{
                                drawCenteredString(Hytils.instance.mc.fontRendererObj, EnumChatFormatting.BOLD + cachedTeammate, Math.round(width / (cachedDisplayTeammates.size() + 1)) * (i + 1), Math.round(height / 5f), 11184810);
                                drawCenteredString(Hytils.instance.mc.fontRendererObj, "Disconnected", Math.round(width / (cachedDisplayTeammates.size() + 1)) * (i + 1), Math.round(height / 5f) + 20, 16711680);
                            }
                        }
                    }else{
                        drawCenteredString(Hytils.instance.mc.fontRendererObj, "No Teammates", Math.round(width / 2f), Math.round(height / 5f), 16711680);
                    }
                }
            }
        }
    }
}
