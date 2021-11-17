package com.iwant2tryhard.hytils.gui.elements;

import com.iwant2tryhard.hytils.Hytils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class GuiGameButton extends Gui {
    protected static final ResourceLocation buttonTextures = new ResourceLocation(Hytils.MODID, "textures/games_menu_0.png");
    /**
     * Button width in pixels
     */
    public int width = 87;
    /**
     * Button height in pixels
     */
    public int height = 32;
    /**
     * The x position of this control.
     */
    public int xPosition;
    /**
     * The y position of this control.
     */
    public int yPosition;
    /**
     * The string displayed on this control.
     */
    public String displayString;
    public int id;
    /**
     * Hides the button completely if false.
     */
    public boolean visible;
    public int packedFGColour; //FML
    protected boolean hovered;

    public GuiGameButton(int buttonId, int x, int y, String buttonText) {
        this.visible = true;
        this.id = buttonId;
        this.xPosition = x;
        this.yPosition = y;
        this.displayString = buttonText;
    }

    /**
     * Returns 0 if the button is disabled, 1 if the mouse is NOT hovering over this button and 2 if it IS hovering over
     * this button.
     */
    protected int getHoverState(boolean mouseOver) {
        int i = 1;

        if (mouseOver) {
            i = 2;
        }

        return i;
    }

    /**
     * Draws this button to the screen.
     */
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        if (this.visible) {
            FontRenderer fontrenderer = mc.fontRendererObj;
            mc.getTextureManager().bindTexture(buttonTextures);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            this.hovered = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
            int i = this.getHoverState(this.hovered);
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            GlStateManager.blendFunc(770, 771);
            if (i == 2) {
                this.drawTexturedModalRect(this.xPosition, this.yPosition, 90, 40, this.width, this.height);
            } else {
                this.drawTexturedModalRect(this.xPosition, this.yPosition, 1, 40, this.width, this.height);
            }
            this.mouseDragged(mc, mouseX, mouseY);
            int j = 0;

            if (packedFGColour != 0) {
                j = packedFGColour;
            }

            this.drawCenteredString(fontrenderer, this.displayString, this.xPosition + this.width / 2, this.yPosition + (this.height - 8) / 2, j);
        }
    }

    /**
     * Fired when the mouse button is dragged. Equivalent of MouseListener.mouseDragged(MouseEvent e).
     */
    protected void mouseDragged(Minecraft mc, int mouseX, int mouseY) {
    }

    /**
     * Fired when the mouse button is released. Equivalent of MouseListener.mouseReleased(MouseEvent e).
     */
    public void mouseReleased(int mouseX, int mouseY) {
    }

    /**
     * Returns true if the mouse has been pressed on this control. Equivalent of MouseListener.mousePressed(MouseEvent
     * e).
     */
    public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
        return this.visible && mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
    }

    /**
     * Whether the mouse cursor is currently over the button.
     */
    public boolean isMouseOver() {
        return this.hovered;
    }

    public void drawButtonForegroundLayer(int mouseX, int mouseY) {
    }

    public void playPressSound(SoundHandler soundHandlerIn) {
        soundHandlerIn.playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0F));
    }

    public int getButtonWidth() {
        return this.width;
    }

    public void setWidth(int width) {
        this.width = width;
    }
}
