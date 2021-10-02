package com.iwant2tryhard.hytils.gui;

import com.iwant2tryhard.hytils.Hytils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class InCombatHUD extends Gui {
    private final ResourceLocation texCombatCard = new ResourceLocation(Hytils.MODID, "textures/combat_card.png");
    private final int cardWidth = 144;
    private final int cardHeight = 64;

    @SubscribeEvent
    public void RenderOverlay(RenderGameOverlayEvent event) {
        Minecraft mc = Hytils.instance.mc;
        ScaledResolution resolution = new ScaledResolution(mc);
        float width = resolution.getScaledWidth();
        float height = resolution.getScaledHeight();
        float widthCenter = resolution.getScaledWidth() / 2f;
        float heightCenter = resolution.getScaledHeight() / 2f;
        if (Hytils.instance.getUtils().lastPlayerInteractedTimer > 0) {
            if (Hytils.instance.getUtils().lastPlayerNameInteracted != null) {
                EntityPlayer interactedPlayer = null;
                for (EntityPlayer player : Hytils.instance.mc.theWorld.playerEntities) {
                    String name = player.getDisplayNameString();
                    if (name.startsWith("\u00a7")) {
                        name = name.replace("\u00a7", "");
                        name = name.substring(1);
                    }
                    if (Hytils.instance.getUtils().lastPlayerNameInteracted.equals(name)) {
                        interactedPlayer = player;
                    }
                }
                float playerHealth = 20;
                float playerMaxHealth = 20;
                float playerAbsorption = 0;
                float playerHunger = 20;
                float playerArmor = 0;
                int playerXpLevel = 0;
                float playerXpProgress = 0;
                float playerAir = 0;
                if (interactedPlayer != null) {
                    playerHealth = Math.round(interactedPlayer.getHealth() * 10f) / 10f;
                    playerMaxHealth = Math.round(interactedPlayer.getMaxHealth() * 10f) / 10f;
                    playerAbsorption = Math.round(interactedPlayer.getAbsorptionAmount() * 10f) / 10f;
                    playerHunger = interactedPlayer.getFoodStats().getFoodLevel();
                    playerArmor = interactedPlayer.getTotalArmorValue();
                    playerXpLevel = interactedPlayer.experienceLevel;
                    playerXpProgress = interactedPlayer.experience;
                    playerAir = interactedPlayer.getAir();

                    //TODO: Add skin

                    String playerName = interactedPlayer.getDisplayNameString();
                    if (playerName.startsWith("\u00a7")) {
                        playerName = playerName.replace("\u00a7", "");
                        playerName = playerName.substring(1);
                    }

                    mc.getTextureManager().bindTexture(texCombatCard);
                    drawTexturedModalRect(widthCenter - (cardWidth / 2f), (height / 5f * 4f) - (cardHeight / 2f), 0, 0, cardWidth, cardHeight);
                    drawTexturedModalRect(widthCenter - (cardWidth / 2f), (height / 5f * 4f) - (cardHeight / 2f) + cardHeight - 2f, 0, 119, Math.round((Hytils.instance.getUtils().lastPlayerInteractedTimer / 100f) * cardWidth), 3);

                    drawString(mc.fontRendererObj, playerName, Math.round(widthCenter - (cardWidth / 2f) + 43f), Math.round((height / 5f * 4f) - (cardHeight / 2f) + 6f), 16777215);

                    //health and hunger bar
                    mc.getTextureManager().bindTexture(texCombatCard);
                    float yCoord = (height / 5f * 4f) - (cardHeight / 2f) + 15f;
                    drawTexturedModalRect(widthCenter - (cardWidth / 2f) + 43f, yCoord, 0, 108, 95, 5);
                    if (playerHealth > 0) {
                        drawTexturedModalRect(widthCenter - (cardWidth / 2f) + 43f, yCoord, 0, 113, Math.round((playerHealth / playerMaxHealth) * 47f), 5);
                    }
                    if (playerHunger > 0) {
                        drawTexturedModalRect(widthCenter - (cardWidth / 2f) + 43f + 49f, yCoord, 49, 113, Math.round((playerHunger / 20f) * 46f), 5);
                    }

                    //armor & air bar
                    mc.getTextureManager().bindTexture(texCombatCard);
                    drawTexturedModalRect(widthCenter - (cardWidth / 2f) + 43f, yCoord + 6f, 0, 75, 95, 5);
                    if (playerArmor > 0) {
                        drawTexturedModalRect(widthCenter - (cardWidth / 2f) + 43f, yCoord + 6f, 0, 80, Math.round((playerArmor / 20f) * 46f), 5);
                    }
                    float airPrec = playerAir * 0.00333f;
                    if (airPrec < 0) airPrec = 0;
                    if (airPrec > 0) {
                        drawTexturedModalRect(widthCenter - (cardWidth / 2f) + 43f + 48f, yCoord + 6f, 48, 80, Math.round(airPrec * 47f), 5);
                    }

                    //xp
                    //TODO: Fix texture glitch
                    mc.getTextureManager().bindTexture(texCombatCard);
                    drawString(mc.fontRendererObj, Integer.toString(playerXpLevel), Math.round(widthCenter - (cardWidth / 2f) + 88), Math.round((height / 5f * 4f) - (cardHeight / 2f) + 27), 5409832);
                    drawTexturedModalRect(widthCenter - (cardWidth / 2f) + 43f, yCoord + 20f, 0, 86, 95, 5);
                    if (playerXpProgress > 0) {
                        drawTexturedModalRect(widthCenter - (cardWidth / 2f) + 43f, yCoord + 20f, 0, 91, Math.round(playerXpProgress * 95f), 5);
                    }

                    //numbers
                    mc.getTextureManager().bindTexture(texCombatCard);

                    float a = (height / 5f * 4f) - (cardHeight / 2f) + 45;
                    float yCoord1 = (height / 5f * 4f) - (cardHeight / 2f) + 44;

                    //health
                    drawString(mc.fontRendererObj, Float.toString(playerHealth), Math.round(widthCenter - (cardWidth / 2f) + 9), Math.round(a), 16716563);
                    drawTexturedModalRect(widthCenter - (cardWidth / 2f) + 29, yCoord1, 1, 65, 9, 9);

                    //absorption
                    drawString(mc.fontRendererObj, Float.toString(playerAbsorption), Math.round(widthCenter - (cardWidth / 2f) + 41), Math.round(a), 13938487);
                    drawTexturedModalRect(widthCenter - (cardWidth / 2f) + 61, yCoord1, 11, 65, 9, 9);

                    //hunger
                    drawString(mc.fontRendererObj, Float.toString(playerHunger), Math.round(widthCenter - (cardWidth / 2f) + 73), Math.round(a), 10317123);
                    drawTexturedModalRect(widthCenter - (cardWidth / 2f) + 93, yCoord1, 21, 65, 9, 9);

                    //armor
                    drawString(mc.fontRendererObj, Float.toString(playerArmor), Math.round(widthCenter - (cardWidth / 2f) + 105), Math.round(a), 12106180);
                    drawTexturedModalRect(widthCenter - (cardWidth / 2f) + 125, yCoord1, 31, 65, 9, 9);
                }
            }
        }
    }
}
