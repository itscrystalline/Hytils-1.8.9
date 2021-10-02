package com.iwant2tryhard.hytils.gui;

import com.iwant2tryhard.hytils.Hytils;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.EnumChatFormatting;

import java.io.IOException;

public class HytilsConfigScreen extends GuiScreen {
    private final byte maxPages = 2;
    private byte page = 1;

    @Override
    public void initGui() {
        //page 1
        GuiButton buttonTeammateHUDEnabled = new GuiButton(0, this.width / 2 - 205, (this.height / 7 * 2) + 10, "Enabled: " + (Hytils.instance.getConfig().teammateHUDEnabled ? EnumChatFormatting.GREEN + "True" : EnumChatFormatting.RED + "False"));
        GuiButton buttonTeammateHUDCheckGame = new GuiButton(1, this.width / 2 + 5, (this.height / 7 * 2) + 10, "Check Game: " + (Hytils.instance.getConfig().teammateHUDCheckGame ? EnumChatFormatting.GREEN + "True" : EnumChatFormatting.RED + "False"));
        GuiButton buttonTeammateHUDHealthBar = new GuiButton(2, this.width / 2 - 205, (this.height / 7 * 2) + 35, "Health Bar: " + (Hytils.instance.getConfig().teammateHUDHealthBar ? EnumChatFormatting.GREEN + "True" : EnumChatFormatting.RED + "False"));
        GuiButton buttonTeammateHUDHungerBar = new GuiButton(3, this.width / 2 + 5, (this.height / 7 * 2) + 35, "Hunger Bar: " + (Hytils.instance.getConfig().teammateHUDHungerBar ? EnumChatFormatting.GREEN + "True" : EnumChatFormatting.RED + "False"));
        GuiButton buttonTeammateHUDArmorBar = new GuiButton(4, this.width / 2 - 205, (this.height / 7 * 2) + 60, "Armor Bar: " + (Hytils.instance.getConfig().teammateHUDArmorBar ? EnumChatFormatting.GREEN + "True" : EnumChatFormatting.RED + "False"));
        GuiButton buttonTeammateHUDAirBar = new GuiButton(5, this.width / 2 + 5, (this.height / 7 * 2) + 60, "Air Bar: " + (Hytils.instance.getConfig().teammateHUDAirBar ? EnumChatFormatting.GREEN + "True" : EnumChatFormatting.RED + "False"));
        GuiButton buttonTeammateHUDShowUndetected = new GuiButton(6, this.width / 2 - 205, (this.height / 7 * 2) + 85, "Show Undetected Teammates: " + (Hytils.instance.getConfig().teammateHUDShowUndetected ? EnumChatFormatting.GREEN + "True" : EnumChatFormatting.RED + "False"));
        GuiButton buttonTeammateHUDMaxDisplay = new GuiButton(7, this.width / 2 + 5, (this.height / 7 * 2) + 85, "Max display Teammates: " + EnumChatFormatting.YELLOW + Hytils.instance.getConfig().maxTeammateDisplay);

        //page 2
        GuiButton buttonOnScreenHUDHealthBar = new GuiButton(8, this.width / 2 - 205, (this.height / 7 * 2) + 10, "Health Bar: " + (Hytils.instance.getConfig().showCustomHealthBar ? EnumChatFormatting.GREEN + "True" : EnumChatFormatting.RED + "False"));
        GuiButton buttonOnScreenHUDHungerBar = new GuiButton(9, this.width / 2 + 5, (this.height / 7 * 2) + 10, "Hunger Bar: " + (Hytils.instance.getConfig().showCustomHungerBar ? EnumChatFormatting.GREEN + "True" : EnumChatFormatting.RED + "False"));
        GuiButton buttonOnScreenHUDArmorBar = new GuiButton(10, this.width / 2 - 205, (this.height / 7 * 2) + 35, "Armor Bar: " + (Hytils.instance.getConfig().showCustomArmorBar ? EnumChatFormatting.GREEN + "True" : EnumChatFormatting.RED + "False"));
        GuiButton buttonOnScreenHUDAirBar = new GuiButton(11, this.width / 2 + 5, (this.height / 7 * 2) + 35, "Air Bar: " + (Hytils.instance.getConfig().showCustomAirBar ? EnumChatFormatting.GREEN + "True" : EnumChatFormatting.RED + "False"));
        GuiButton buttonOnScreenHUDLevelBar = new GuiButton(12, this.width / 2 - 205, (this.height / 7 * 2) + 60, "Level Bar: " + (Hytils.instance.getConfig().showCustomLevelBar ? EnumChatFormatting.GREEN + "True" : EnumChatFormatting.RED + "False"));

        GuiButton buttonPrevious = new GuiButton(-1, 10, this.height - 30, 100, 20, "Previous");
        GuiButton buttonNext = new GuiButton(-2, this.width - 110, this.height - 30, 100, 20, "Next");

        buttonTeammateHUDEnabled.enabled = !Hytils.instance.getConfig().teammateHUDCheckGame;
        buttonPrevious.enabled = page > 1;
        buttonNext.enabled = !(page >= maxPages);

        this.buttonList.clear();

        if (page == 1) {
            this.buttonList.add(buttonTeammateHUDEnabled);
            this.buttonList.add(buttonTeammateHUDCheckGame);
            this.buttonList.add(buttonTeammateHUDHealthBar);
            this.buttonList.add(buttonTeammateHUDHungerBar);
            this.buttonList.add(buttonTeammateHUDArmorBar);
            this.buttonList.add(buttonTeammateHUDAirBar);
            this.buttonList.add(buttonTeammateHUDShowUndetected);
            //TODO: Unintuitive, will fix to use slider or textbox later
            this.buttonList.add(buttonTeammateHUDMaxDisplay);
        } else if (page == 2) {
            this.buttonList.add(buttonOnScreenHUDHealthBar);
            this.buttonList.add(buttonOnScreenHUDHungerBar);
            this.buttonList.add(buttonOnScreenHUDArmorBar);
            this.buttonList.add(buttonOnScreenHUDAirBar);
            this.buttonList.add(buttonOnScreenHUDLevelBar);
        }

        this.buttonList.add(buttonPrevious);
        this.buttonList.add(buttonNext);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        this.fontRendererObj.drawString(EnumChatFormatting.BOLD + "Hytils Settings", this.width / 2 - (this.fontRendererObj.getStringWidth("Hytils Settings") / 2), this.height / 6, 16777215);
        if (page == 1) {
            this.fontRendererObj.drawString("Teammate HUD", this.width / 2 - (this.fontRendererObj.getStringWidth("Teammate HUD") / 2), (this.height / 7 * 2) - 10, 16777215);
        } else if (page == 2) {
            this.fontRendererObj.drawString("On Screen HUD", this.width / 2 - (this.fontRendererObj.getStringWidth("On Screen HUD") / 2), (this.height / 7 * 2) - 10, 16777215);
        }
        this.fontRendererObj.drawString("Hytils version " + Hytils.VERSION, this.width / 2 - (this.fontRendererObj.getStringWidth("Hytils version " + Hytils.VERSION) / 2), this.height - 40, 16777215);
        this.fontRendererObj.drawString("Page " + page + "/" + maxPages, this.width / 2 - (this.fontRendererObj.getStringWidth("Page " + page + "/" + maxPages) / 2), this.height - 20, 16777215);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if (button.enabled) {
            switch (button.id) {
                case -1:
                    --page;
                    break;
                case -2:
                    ++page;
                    break;
                case 0:
                    Hytils.instance.getConfig().teammateHUDEnabled = !Hytils.instance.getConfig().teammateHUDEnabled;
                    break;
                case 1:
                    Hytils.instance.getConfig().teammateHUDCheckGame = !Hytils.instance.getConfig().teammateHUDCheckGame;
                    break;
                case 2:
                    Hytils.instance.getConfig().teammateHUDHealthBar = !Hytils.instance.getConfig().teammateHUDHealthBar;
                    break;
                case 3:
                    Hytils.instance.getConfig().teammateHUDHungerBar = !Hytils.instance.getConfig().teammateHUDHungerBar;
                    break;
                case 4:
                    Hytils.instance.getConfig().teammateHUDArmorBar = !Hytils.instance.getConfig().teammateHUDArmorBar;
                    break;
                case 5:
                    Hytils.instance.getConfig().teammateHUDAirBar = !Hytils.instance.getConfig().teammateHUDAirBar;
                    break;
                case 6:
                    Hytils.instance.getConfig().teammateHUDShowUndetected = !Hytils.instance.getConfig().teammateHUDShowUndetected;
                    break;
                case 7:
                    if (Hytils.instance.getConfig().maxTeammateDisplay < 15) {
                        ++Hytils.instance.getConfig().maxTeammateDisplay;
                    } else {
                        Hytils.instance.getConfig().maxTeammateDisplay = 1;
                    }
                    Hytils.instance.getUtils().randomizeDisplayTeammates();
                    break;
                case 8:
                    Hytils.instance.getConfig().showCustomHealthBar = !Hytils.instance.getConfig().showCustomHealthBar;
                    break;
                case 9:
                    Hytils.instance.getConfig().showCustomHungerBar = !Hytils.instance.getConfig().showCustomHungerBar;
                    break;
                case 10:
                    Hytils.instance.getConfig().showCustomArmorBar = !Hytils.instance.getConfig().showCustomArmorBar;
                    break;
                case 11:
                    Hytils.instance.getConfig().showCustomAirBar = !Hytils.instance.getConfig().showCustomAirBar;
                    break;
                case 12:
                    Hytils.instance.getConfig().showCustomLevelBar = !Hytils.instance.getConfig().showCustomLevelBar;
                    break;
            }
            initGui();
        }
    }

    @Override
    public void onGuiClosed() {
        Hytils.instance.getConfig().saveConfig();
        page = 1;
    }
}
