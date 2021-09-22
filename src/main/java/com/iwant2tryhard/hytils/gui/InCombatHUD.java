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
    private final int cardWidth = 230;
    private final int cardHeight = 63;

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
                mc.getTextureManager().bindTexture(texCombatCard);
                drawTexturedModalRect(widthCenter - (cardWidth / 2f), (height / 4f * 3f) - (cardHeight / 2f), 0, 0, cardWidth, cardHeight);
                drawTexturedModalRect(widthCenter - (cardWidth / 2f), (height / 4f * 3f) - (cardHeight / 2f) + 61f, 0, 140, Math.round((Hytils.instance.getUtils().lastPlayerInteractedTimer / 100f) * cardWidth), 3);
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
                if (interactedPlayer != null) {
                    playerHealth = interactedPlayer.getHealth();
                    playerMaxHealth = interactedPlayer.getMaxHealth();
                    playerAbsorption = interactedPlayer.getAbsorptionAmount();
                    playerHunger = interactedPlayer.getFoodStats().getFoodLevel();
                    playerArmor = interactedPlayer.getTotalArmorValue();
                    playerXpLevel = interactedPlayer.experienceLevel;
                    playerXpProgress = interactedPlayer.experience;
                }


            }
        }
    }
}
