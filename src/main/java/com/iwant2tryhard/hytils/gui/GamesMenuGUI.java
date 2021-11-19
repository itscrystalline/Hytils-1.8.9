package com.iwant2tryhard.hytils.gui;

import com.iwant2tryhard.hytils.Hytils;
import com.iwant2tryhard.hytils.gui.elements.GuiGameButton;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GamesMenuGUI extends GuiScreen {
    private final ResourceLocation TEXTURE_0 = new ResourceLocation(Hytils.MODID, "textures/games_menu_0.png");
    private final ResourceLocation TEXTURE_1 = new ResourceLocation(Hytils.MODID, "textures/games_menu_1.png");
    private final ResourceLocation TEXTURE_2 = new ResourceLocation(Hytils.MODID, "textures/games_menu_2.png");
    private final ResourceLocation TEXTURE_3 = new ResourceLocation(Hytils.MODID, "textures/games_menu_3.png");
    private final ResourceLocation TEXTURE_4 = new ResourceLocation(Hytils.MODID, "textures/games_menu_4.png");
    private final int texWidth = 166;
    private final int texHeight = 92;

    private final ScaledResolution res = new ScaledResolution(Hytils.instance.mc);
    private final int widthCenter = res.getScaledWidth() / 2;
    private final int heightCenter = res.getScaledHeight() / 2;

    public List<GuiGameButton> gameButtons = new ArrayList<GuiGameButton>();
    private GuiGameButton selectedButton;

    @Override
    public void initGui() {
        this.gameButtons.clear();
        this.gameButtons.add(new GuiGameButton(0, widthCenter - texWidth + 61, heightCenter - texHeight + 45, "Test Button"));
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {

        drawDefaultBackground();
        mc.renderEngine.bindTexture(TEXTURE_1);
        drawTexturedModalRect(widthCenter - texWidth, heightCenter - texHeight, 0, 0, texWidth, texHeight);
        mc.renderEngine.bindTexture(TEXTURE_2);
        drawTexturedModalRect(widthCenter, heightCenter - texHeight, 0, 0, texWidth, texHeight);
        mc.renderEngine.bindTexture(TEXTURE_3);
        drawTexturedModalRect(widthCenter - texWidth, heightCenter, 0, 0, texWidth, texHeight);
        mc.renderEngine.bindTexture(TEXTURE_4);
        drawTexturedModalRect(widthCenter, heightCenter, 0, 0, texWidth, texHeight);
        mc.fontRendererObj.drawString("Game Menu", (widthCenter - texWidth) + 5, (heightCenter - texHeight) + 5, 1);

        for (GuiGameButton gameButton : this.gameButtons) {
            gameButton.drawButton(this.mc, mouseX, mouseY);
        }

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        if (mouseButton == 0) {
            for (int i = 0; i < this.gameButtons.size(); ++i) {
                GuiGameButton guibutton = this.gameButtons.get(i);

                if (guibutton.mousePressed(this.mc, mouseX, mouseY)) {
                    this.selectedButton = guibutton;
                    guibutton.playPressSound(this.mc.getSoundHandler());
                    this.actionPerformed(guibutton);
                }
            }
        }
    }

    protected void actionPerformed(GuiGameButton button) throws IOException {

    }

    @Override
    public void setWorldAndResolution(Minecraft mc, int width, int height) {
        this.mc = mc;
        this.itemRender = mc.getRenderItem();
        this.fontRendererObj = mc.fontRendererObj;
        this.width = width;
        this.height = height;
    }
}
