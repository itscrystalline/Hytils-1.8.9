package com.iwant2tryhard.hytils.gui;

import com.iwant2tryhard.hytils.Hytils;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.EnumChatFormatting;
import org.lwjgl.input.Keyboard;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class HytilsConfigScreen extends GuiScreen {
    private final byte maxPages = 3;
    private byte page = 1;

    private static float round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.floatValue();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        this.fontRendererObj.drawString(EnumChatFormatting.BOLD + "Hytils Settings", this.width / 2 - (this.fontRendererObj.getStringWidth("Hytils Settings") / 2), this.height / 10 - 30, 16777215);
        if (Hytils.instance.getConfig().devMode)
            this.fontRendererObj.drawString(EnumChatFormatting.BOLD + "Debug Mode", 0, 0, 16755200);
        if (page == 1) {
            this.fontRendererObj.drawString("Teammate HUD", this.width / 2 - (this.fontRendererObj.getStringWidth("Teammate HUD") / 2), (this.height / 7 * 2) - 10, 16777215);
        } else if (page == 2) {
            this.fontRendererObj.drawString("On Screen HUD", this.width / 2 - (this.fontRendererObj.getStringWidth("On Screen HUD") / 2), (this.height / 7 * 2) - 10, 16777215);
        } else if (page == 3) {
            this.fontRendererObj.drawString("Equipment Helper", this.width / 2 - (this.fontRendererObj.getStringWidth("Equipment Helper") / 2), (this.height / 6) - 10, 16777215);
            this.fontRendererObj.drawString("Hold Left Alt to decrease value", this.width / 2 - (this.fontRendererObj.getStringWidth("Hold Left Alt to decrease value") / 2), this.height - 60, 16777215);
        }
        this.fontRendererObj.drawString("Hytils version " + Hytils.VERSION, this.width / 2 - (this.fontRendererObj.getStringWidth("Hytils version " + Hytils.VERSION) / 2), this.height - 40, 16777215);
        this.fontRendererObj.drawString("Page " + page + "/" + maxPages, this.width / 2 - (this.fontRendererObj.getStringWidth("Page " + page + "/" + maxPages) / 2), this.height - 20, 16777215);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

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

        //page 3
        GuiButton buttonEquipmentHelperEnabled = new GuiButton(13, this.width / 2 - 205, (this.height / 6) + 10, "Enabled: " + (Hytils.instance.getConfig().equipmentHelperEnabled ? EnumChatFormatting.GREEN + "True" : EnumChatFormatting.RED + "False"));
        GuiButton buttonEquipmentHelperOnlyBestMode = new GuiButton(14, this.width / 2 + 5, (this.height / 6) + 10, "Only Best Mode: " + (Hytils.instance.getConfig().equipmentHelperOnlyBestMode ? EnumChatFormatting.GREEN + "True" : EnumChatFormatting.RED + "False"));

        GuiButton buttonEquipmentHelperWeaponDamageCheck = new GuiButton(15, this.width / 2 - 305, (this.height / 6) + 40, "Weapon Damage Check: " + (Hytils.instance.getConfig().equipmentHelperWeaponDamageCheck ? EnumChatFormatting.GREEN + "True" : EnumChatFormatting.RED + "False"));
        GuiButton buttonEquipmentHelperWeaponDamageFavor = new GuiButton(16, this.width / 2 - 305, (this.height / 6) + 65, "Weapon Damage Overall Favor: " + EnumChatFormatting.YELLOW + Hytils.instance.getConfig().equipmentHelperWeaponDamageFavor);
        GuiButton buttonEquipmentHelperWeaponDamageTierFavor = new GuiButton(17, this.width / 2 - 305, (this.height / 6) + 90, "Weapon Damage Tier Favor: " + EnumChatFormatting.YELLOW + Hytils.instance.getConfig().equipmentHelperWeaponDamageTierFavor);
        GuiButton buttonEquipmentHelperWeaponDamageLevelFavor = new GuiButton(18, this.width / 2 - 305, (this.height / 6) + 115, "Weapon Damage Level Favor: " + EnumChatFormatting.YELLOW + Hytils.instance.getConfig().equipmentHelperWeaponDamageLevelFavor);

        GuiButton buttonEquipmentHelperWeaponDurabilityCheck = new GuiButton(19, this.width / 2 - 305, (this.height / 6) + 140, "Weapon Durability Check: " + (Hytils.instance.getConfig().equipmentHelperWeaponDurabilityCheck ? EnumChatFormatting.GREEN + "True" : EnumChatFormatting.RED + "False"));
        GuiButton buttonEquipmentHelperWeaponDurabilityFavor = new GuiButton(20, this.width / 2 - 305, (this.height / 6) + 165, "Weapon Durability Overall Favor: " + EnumChatFormatting.YELLOW + Hytils.instance.getConfig().equipmentHelperWeaponDurabilityFavor);
        GuiButton buttonEquipmentHelperWeaponDurabilityTierFavor = new GuiButton(21, this.width / 2 - 305, (this.height / 6) + 190, "Weapon Durability Tier Favor: " + EnumChatFormatting.YELLOW + Hytils.instance.getConfig().equipmentHelperWeaponDurabilityTierFavor);
        GuiButton buttonEquipmentHelperWeaponDurabilityLevelFavor = new GuiButton(22, this.width / 2 - 305, (this.height / 6) + 215, "Weapon Durability Level Favor: " + EnumChatFormatting.YELLOW + Hytils.instance.getConfig().equipmentHelperWeaponDurabilityLevelFavor);

        GuiButton buttonEquipmentHelperWeaponKnockbackCheck = new GuiButton(23, this.width / 2 - 305, (this.height / 6) + 240, "Weapon Knockback Check: " + (Hytils.instance.getConfig().equipmentHelperWeaponKnockbackCheck ? EnumChatFormatting.GREEN + "True" : EnumChatFormatting.RED + "False"));
        GuiButton buttonEquipmentHelperWeaponKnockbackFavor = new GuiButton(24, this.width / 2 - 305, (this.height / 6) + 265, "Weapon Knockback Overall Favor: " + EnumChatFormatting.YELLOW + Hytils.instance.getConfig().equipmentHelperWeaponKnockbackFavor);

        GuiButton buttonEquipmentHelperArmorResCheck = new GuiButton(25, this.width / 2 - 100, (this.height / 6) + 40, "Armor Resistance Check: " + (Hytils.instance.getConfig().equipmentHelperArmorResCheck ? EnumChatFormatting.GREEN + "True" : EnumChatFormatting.RED + "False"));
        GuiButton buttonEquipmentHelperArmorResFavor = new GuiButton(26, this.width / 2 - 100, (this.height / 6) + 65, "Armor Resistance Overall Favor: " + EnumChatFormatting.YELLOW + Hytils.instance.getConfig().equipmentHelperArmorResFavor);
        GuiButton buttonEquipmentHelperArmorResTierFavor = new GuiButton(27, this.width / 2 - 100, (this.height / 6) + 90, "Armor Resistance Tier Favor: " + EnumChatFormatting.YELLOW + Hytils.instance.getConfig().equipmentHelperArmorResTierFavor);
        GuiButton buttonEquipmentHelperArmorResLevelFavor = new GuiButton(28, this.width / 2 - 100, (this.height / 6) + 115, "Armor Resistance Level Favor: " + EnumChatFormatting.YELLOW + Hytils.instance.getConfig().equipmentHelperArmorResLevelFavor);

        GuiButton buttonEquipmentHelperArmorDurabilityCheck = new GuiButton(29, this.width / 2 - 100, (this.height / 6) + 140, "Armor Durability Check: " + (Hytils.instance.getConfig().equipmentHelperArmorDurabilityCheck ? EnumChatFormatting.GREEN + "True" : EnumChatFormatting.RED + "False"));
        GuiButton buttonEquipmentHelperArmorDurabilityFavor = new GuiButton(30, this.width / 2 - 100, (this.height / 6) + 165, "Armor Durability Overall Favor: " + EnumChatFormatting.YELLOW + Hytils.instance.getConfig().equipmentHelperArmorDurabilityFavor);
        GuiButton buttonEquipmentHelperArmorDurabilityTierFavor = new GuiButton(31, this.width / 2 - 100, (this.height / 6) + 190, "Armor Durability Tier Favor: " + EnumChatFormatting.YELLOW + Hytils.instance.getConfig().equipmentHelperArmorDurabilityTierFavor);
        GuiButton buttonEquipmentHelperArmorDurabilityLevelFavor = new GuiButton(32, this.width / 2 - 100, (this.height / 6) + 215, "Armor Durability Level Favor: " + EnumChatFormatting.YELLOW + Hytils.instance.getConfig().equipmentHelperArmorDurabilityLevelFavor);

        GuiButton buttonEquipmentHelperRodDurabilityCheck = new GuiButton(33, this.width / 2 + 105, (this.height / 6) + 40, "Fishing Rod Durability Check: " + (Hytils.instance.getConfig().equipmentHelperRodDurabilityCheck ? EnumChatFormatting.GREEN + "True" : EnumChatFormatting.RED + "False"));
        GuiButton buttonEquipmentHelperRodDurabilityFavor = new GuiButton(34, this.width / 2 + 105, (this.height / 6) + 65, "Fishing Rod Durability Favor: " + EnumChatFormatting.YELLOW + Hytils.instance.getConfig().equipmentHelperRodDurabilityFavor);

        GuiButton buttonEquipmentHelperRodKnockbackCheck = new GuiButton(35, this.width / 2 + 105, (this.height / 6) + 90, "Fishing Rod Knockback Check: " + (Hytils.instance.getConfig().equipmentHelperRodKnockbackCheck ? EnumChatFormatting.GREEN + "True" : EnumChatFormatting.RED + "False"));
        GuiButton buttonEquipmentHelperRodKnockbackFavor = new GuiButton(36, this.width / 2 + 105, (this.height / 6) + 115, "Fishing Rod Knockback Favor: " + EnumChatFormatting.YELLOW + Hytils.instance.getConfig().equipmentHelperRodKnockbackFavor);

        GuiButton buttonPrevious = new GuiButton(-1, 10, this.height - 30, 100, 20, "Previous");
        GuiButton buttonNext = new GuiButton(-2, this.width - 110, this.height - 30, 100, 20, "Next");

        buttonTeammateHUDEnabled.enabled = !Hytils.instance.getConfig().teammateHUDCheckGame;

        buttonEquipmentHelperWeaponDamageCheck.enabled = Hytils.instance.getConfig().equipmentHelperEnabled;
        buttonEquipmentHelperWeaponDamageFavor.enabled = Hytils.instance.getConfig().equipmentHelperEnabled && Hytils.instance.getConfig().equipmentHelperWeaponDamageCheck;
        buttonEquipmentHelperWeaponDamageTierFavor.enabled = Hytils.instance.getConfig().equipmentHelperEnabled && Hytils.instance.getConfig().equipmentHelperWeaponDamageCheck;
        buttonEquipmentHelperWeaponDamageLevelFavor.enabled = Hytils.instance.getConfig().equipmentHelperEnabled && Hytils.instance.getConfig().equipmentHelperWeaponDamageCheck;
        buttonEquipmentHelperWeaponDurabilityCheck.enabled = Hytils.instance.getConfig().equipmentHelperEnabled;
        buttonEquipmentHelperWeaponDurabilityFavor.enabled = Hytils.instance.getConfig().equipmentHelperEnabled && Hytils.instance.getConfig().equipmentHelperWeaponDurabilityCheck;
        buttonEquipmentHelperWeaponDurabilityTierFavor.enabled = Hytils.instance.getConfig().equipmentHelperEnabled && Hytils.instance.getConfig().equipmentHelperWeaponDurabilityCheck;
        buttonEquipmentHelperWeaponDurabilityLevelFavor.enabled = Hytils.instance.getConfig().equipmentHelperEnabled && Hytils.instance.getConfig().equipmentHelperWeaponDurabilityCheck;
        buttonEquipmentHelperWeaponKnockbackCheck.enabled = Hytils.instance.getConfig().equipmentHelperEnabled;
        buttonEquipmentHelperWeaponKnockbackFavor.enabled = Hytils.instance.getConfig().equipmentHelperEnabled && Hytils.instance.getConfig().equipmentHelperWeaponKnockbackCheck;

        buttonEquipmentHelperArmorResCheck.enabled = Hytils.instance.getConfig().equipmentHelperEnabled;
        buttonEquipmentHelperArmorResFavor.enabled = Hytils.instance.getConfig().equipmentHelperEnabled && Hytils.instance.getConfig().equipmentHelperArmorResCheck;
        buttonEquipmentHelperArmorResTierFavor.enabled = Hytils.instance.getConfig().equipmentHelperEnabled && Hytils.instance.getConfig().equipmentHelperArmorResCheck;
        buttonEquipmentHelperArmorResLevelFavor.enabled = Hytils.instance.getConfig().equipmentHelperEnabled && Hytils.instance.getConfig().equipmentHelperArmorResCheck;
        buttonEquipmentHelperArmorDurabilityCheck.enabled = Hytils.instance.getConfig().equipmentHelperEnabled;
        buttonEquipmentHelperArmorDurabilityFavor.enabled = Hytils.instance.getConfig().equipmentHelperEnabled && Hytils.instance.getConfig().equipmentHelperArmorDurabilityCheck;
        buttonEquipmentHelperArmorDurabilityTierFavor.enabled = Hytils.instance.getConfig().equipmentHelperEnabled && Hytils.instance.getConfig().equipmentHelperArmorDurabilityCheck;
        buttonEquipmentHelperArmorDurabilityLevelFavor.enabled = Hytils.instance.getConfig().equipmentHelperEnabled && Hytils.instance.getConfig().equipmentHelperArmorDurabilityCheck;

        buttonEquipmentHelperRodKnockbackCheck.enabled = Hytils.instance.getConfig().equipmentHelperEnabled;
        buttonEquipmentHelperRodKnockbackFavor.enabled = Hytils.instance.getConfig().equipmentHelperEnabled && Hytils.instance.getConfig().equipmentHelperRodKnockbackCheck;
        buttonEquipmentHelperRodDurabilityCheck.enabled = Hytils.instance.getConfig().equipmentHelperEnabled;
        buttonEquipmentHelperRodDurabilityFavor.enabled = Hytils.instance.getConfig().equipmentHelperEnabled && Hytils.instance.getConfig().equipmentHelperRodDurabilityCheck;

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
        } else if (page == 3) {
            this.buttonList.add(buttonEquipmentHelperEnabled);
            this.buttonList.add(buttonEquipmentHelperOnlyBestMode);
            this.buttonList.add(buttonEquipmentHelperWeaponDamageCheck);
            this.buttonList.add(buttonEquipmentHelperWeaponDamageFavor);
            this.buttonList.add(buttonEquipmentHelperWeaponDamageTierFavor);
            this.buttonList.add(buttonEquipmentHelperWeaponDamageLevelFavor);
            this.buttonList.add(buttonEquipmentHelperWeaponDurabilityCheck);
            this.buttonList.add(buttonEquipmentHelperWeaponDurabilityFavor);
            this.buttonList.add(buttonEquipmentHelperWeaponDurabilityTierFavor);
            this.buttonList.add(buttonEquipmentHelperWeaponDurabilityLevelFavor);
            this.buttonList.add(buttonEquipmentHelperWeaponKnockbackCheck);
            this.buttonList.add(buttonEquipmentHelperWeaponKnockbackFavor);

            this.buttonList.add(buttonEquipmentHelperArmorResCheck);
            this.buttonList.add(buttonEquipmentHelperArmorResFavor);
            this.buttonList.add(buttonEquipmentHelperArmorResTierFavor);
            this.buttonList.add(buttonEquipmentHelperArmorResLevelFavor);
            this.buttonList.add(buttonEquipmentHelperArmorDurabilityCheck);
            this.buttonList.add(buttonEquipmentHelperArmorDurabilityFavor);
            this.buttonList.add(buttonEquipmentHelperArmorDurabilityTierFavor);
            this.buttonList.add(buttonEquipmentHelperArmorDurabilityLevelFavor);

            this.buttonList.add(buttonEquipmentHelperRodDurabilityCheck);
            this.buttonList.add(buttonEquipmentHelperRodDurabilityFavor);
            this.buttonList.add(buttonEquipmentHelperRodKnockbackCheck);
            this.buttonList.add(buttonEquipmentHelperRodKnockbackFavor);
        }

        this.buttonList.add(buttonPrevious);
        this.buttonList.add(buttonNext);
    }

    @Override
    public void onGuiClosed() {
        Hytils.instance.getConfig().saveConfig();
        page = 1;
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if (button.enabled) {
            switch (button.id) {
                case -1:
                    page -= 1;
                    break;
                case -2:
                    page += 1;
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
                case 13:
                    Hytils.instance.getConfig().equipmentHelperEnabled = !Hytils.instance.getConfig().equipmentHelperEnabled;
                    break;
                case 14:
                    Hytils.instance.getConfig().equipmentHelperOnlyBestMode = !Hytils.instance.getConfig().equipmentHelperOnlyBestMode;
                    break;
                case 15:
                    Hytils.instance.getConfig().equipmentHelperWeaponDamageCheck = !Hytils.instance.getConfig().equipmentHelperWeaponDamageCheck;
                    break;
                case 16:
                    if (!Keyboard.isKeyDown(Keyboard.KEY_LMENU)) {
                        if (Hytils.instance.getConfig().equipmentHelperWeaponDamageFavor >= 2f) {
                            Hytils.instance.getConfig().equipmentHelperWeaponDamageFavor = 1f;
                        } else {
                            Hytils.instance.getConfig().equipmentHelperWeaponDamageFavor += 0.05D;
                        }
                    } else {
                        if (Hytils.instance.getConfig().equipmentHelperWeaponDamageFavor <= 1f) {
                            Hytils.instance.getConfig().equipmentHelperWeaponDamageFavor = 2f;
                        } else {
                            Hytils.instance.getConfig().equipmentHelperWeaponDamageFavor -= 0.05D;
                        }
                    }
                    Hytils.instance.getConfig().equipmentHelperWeaponDamageFavor = round(Hytils.instance.getConfig().equipmentHelperWeaponDamageFavor, 2);
                    break;
                case 17:
                    if (!Keyboard.isKeyDown(Keyboard.KEY_LMENU)) {
                        if (Hytils.instance.getConfig().equipmentHelperWeaponDamageTierFavor >= 2f) {
                            Hytils.instance.getConfig().equipmentHelperWeaponDamageTierFavor = 1f;
                        } else {
                            Hytils.instance.getConfig().equipmentHelperWeaponDamageTierFavor += 0.05D;
                        }
                    } else {
                        if (Hytils.instance.getConfig().equipmentHelperWeaponDamageTierFavor <= 1f) {
                            Hytils.instance.getConfig().equipmentHelperWeaponDamageTierFavor = 2f;
                        } else {
                            Hytils.instance.getConfig().equipmentHelperWeaponDamageTierFavor -= 0.05D;
                        }
                    }
                    Hytils.instance.getConfig().equipmentHelperWeaponDamageTierFavor = round(Hytils.instance.getConfig().equipmentHelperWeaponDamageTierFavor, 2);
                    break;
                case 18:
                    if (!Keyboard.isKeyDown(Keyboard.KEY_LMENU)) {
                        if (Hytils.instance.getConfig().equipmentHelperWeaponDamageLevelFavor >= 2f) {
                            Hytils.instance.getConfig().equipmentHelperWeaponDamageLevelFavor = 1f;
                        } else {
                            Hytils.instance.getConfig().equipmentHelperWeaponDamageLevelFavor += 0.05D;
                        }
                    } else {
                        if (Hytils.instance.getConfig().equipmentHelperWeaponDamageLevelFavor <= 1f) {
                            Hytils.instance.getConfig().equipmentHelperWeaponDamageLevelFavor = 2f;
                        } else {
                            Hytils.instance.getConfig().equipmentHelperWeaponDamageLevelFavor -= 0.05D;
                        }
                    }
                    Hytils.instance.getConfig().equipmentHelperWeaponDamageLevelFavor = round(Hytils.instance.getConfig().equipmentHelperWeaponDamageLevelFavor, 2);
                    break;
                case 19:
                    Hytils.instance.getConfig().equipmentHelperWeaponDurabilityCheck = !Hytils.instance.getConfig().equipmentHelperWeaponDurabilityCheck;
                    break;
                case 20:
                    if (!Keyboard.isKeyDown(Keyboard.KEY_LMENU)) {
                        if (Hytils.instance.getConfig().equipmentHelperWeaponDurabilityFavor >= 2f) {
                            Hytils.instance.getConfig().equipmentHelperWeaponDurabilityFavor = 1f;
                        } else {
                            Hytils.instance.getConfig().equipmentHelperWeaponDurabilityFavor += 0.05D;
                        }
                    } else {
                        if (Hytils.instance.getConfig().equipmentHelperWeaponDurabilityFavor <= 1f) {
                            Hytils.instance.getConfig().equipmentHelperWeaponDurabilityFavor = 2f;
                        } else {
                            Hytils.instance.getConfig().equipmentHelperWeaponDurabilityFavor -= 0.05D;
                        }
                    }
                    Hytils.instance.getConfig().equipmentHelperWeaponDurabilityFavor = round(Hytils.instance.getConfig().equipmentHelperWeaponDurabilityFavor, 2);
                    break;
                case 21:
                    if (!Keyboard.isKeyDown(Keyboard.KEY_LMENU)) {
                        if (Hytils.instance.getConfig().equipmentHelperWeaponDurabilityTierFavor >= 2f) {
                            Hytils.instance.getConfig().equipmentHelperWeaponDurabilityTierFavor = 1f;
                        } else {
                            Hytils.instance.getConfig().equipmentHelperWeaponDurabilityTierFavor += 0.05D;
                        }
                    } else {
                        if (Hytils.instance.getConfig().equipmentHelperWeaponDurabilityTierFavor <= 1f) {
                            Hytils.instance.getConfig().equipmentHelperWeaponDurabilityTierFavor = 2f;
                        } else {
                            Hytils.instance.getConfig().equipmentHelperWeaponDurabilityTierFavor -= 0.05D;
                        }
                    }
                    Hytils.instance.getConfig().equipmentHelperWeaponDurabilityTierFavor = round(Hytils.instance.getConfig().equipmentHelperWeaponDurabilityTierFavor, 2);
                    break;

                case 22:
                    if (!Keyboard.isKeyDown(Keyboard.KEY_LMENU)) {
                        if (Hytils.instance.getConfig().equipmentHelperWeaponDurabilityLevelFavor >= 2f) {
                            Hytils.instance.getConfig().equipmentHelperWeaponDurabilityLevelFavor = 1f;
                        } else {
                            Hytils.instance.getConfig().equipmentHelperWeaponDurabilityLevelFavor += 0.05D;
                        }
                    } else {
                        if (Hytils.instance.getConfig().equipmentHelperWeaponDurabilityLevelFavor <= 1f) {
                            Hytils.instance.getConfig().equipmentHelperWeaponDurabilityLevelFavor = 2f;
                        } else {
                            Hytils.instance.getConfig().equipmentHelperWeaponDurabilityLevelFavor -= 0.05D;
                        }
                    }
                    Hytils.instance.getConfig().equipmentHelperWeaponDurabilityLevelFavor = round(Hytils.instance.getConfig().equipmentHelperWeaponDurabilityLevelFavor, 2);
                    break;
                case 23:
                    Hytils.instance.getConfig().equipmentHelperWeaponKnockbackCheck = !Hytils.instance.getConfig().equipmentHelperWeaponKnockbackCheck;
                    break;
                case 24:
                    if (!Keyboard.isKeyDown(Keyboard.KEY_LMENU)) {
                        if (Hytils.instance.getConfig().equipmentHelperWeaponKnockbackFavor >= 2f) {
                            Hytils.instance.getConfig().equipmentHelperWeaponKnockbackFavor = 1f;
                        } else {
                            Hytils.instance.getConfig().equipmentHelperWeaponKnockbackFavor += 0.05D;
                        }
                    } else {
                        if (Hytils.instance.getConfig().equipmentHelperWeaponKnockbackFavor <= 1D) {
                            Hytils.instance.getConfig().equipmentHelperWeaponKnockbackFavor = 2f;
                        } else {
                            Hytils.instance.getConfig().equipmentHelperWeaponKnockbackFavor -= 0.05D;
                        }
                    }
                    Hytils.instance.getConfig().equipmentHelperWeaponKnockbackFavor = round(Hytils.instance.getConfig().equipmentHelperWeaponKnockbackFavor, 2);
                    break;
                case 25:
                    Hytils.instance.getConfig().equipmentHelperArmorResCheck = !Hytils.instance.getConfig().equipmentHelperArmorResCheck;
                    break;
                case 26:
                    if (!Keyboard.isKeyDown(Keyboard.KEY_LMENU)) {
                        if (Hytils.instance.getConfig().equipmentHelperArmorResFavor >= 2f) {
                            Hytils.instance.getConfig().equipmentHelperArmorResFavor = 1f;
                        } else {
                            Hytils.instance.getConfig().equipmentHelperArmorResFavor += 0.05D;
                        }
                    } else {
                        if (Hytils.instance.getConfig().equipmentHelperArmorResFavor <= 1f) {
                            Hytils.instance.getConfig().equipmentHelperArmorResFavor = 2f;
                        } else {
                            Hytils.instance.getConfig().equipmentHelperArmorResFavor -= 0.05D;
                        }
                    }
                    Hytils.instance.getConfig().equipmentHelperArmorResFavor = round(Hytils.instance.getConfig().equipmentHelperArmorResFavor, 2);
                    break;
                case 27:
                    if (!Keyboard.isKeyDown(Keyboard.KEY_LMENU)) {
                        if (Hytils.instance.getConfig().equipmentHelperArmorResTierFavor >= 2f) {
                            Hytils.instance.getConfig().equipmentHelperArmorResTierFavor = 1f;
                        } else {
                            Hytils.instance.getConfig().equipmentHelperArmorResTierFavor += 0.05D;
                        }
                    } else {
                        if (Hytils.instance.getConfig().equipmentHelperArmorResTierFavor <= 1f) {
                            Hytils.instance.getConfig().equipmentHelperArmorResTierFavor = 2f;
                        } else {
                            Hytils.instance.getConfig().equipmentHelperArmorResTierFavor -= 0.05D;
                        }
                    }
                    Hytils.instance.getConfig().equipmentHelperArmorResTierFavor = round(Hytils.instance.getConfig().equipmentHelperArmorResTierFavor, 2);
                    break;
                case 28:
                    if (!Keyboard.isKeyDown(Keyboard.KEY_LMENU)) {
                        if (Hytils.instance.getConfig().equipmentHelperArmorResLevelFavor >= 2f) {
                            Hytils.instance.getConfig().equipmentHelperArmorResLevelFavor = 1f;
                        } else {
                            Hytils.instance.getConfig().equipmentHelperArmorResLevelFavor += 0.05D;
                        }
                    } else {
                        if (Hytils.instance.getConfig().equipmentHelperArmorResLevelFavor <= 1f) {
                            Hytils.instance.getConfig().equipmentHelperArmorResLevelFavor = 2f;
                        } else {
                            Hytils.instance.getConfig().equipmentHelperArmorResLevelFavor -= 0.05D;
                        }
                    }
                    Hytils.instance.getConfig().equipmentHelperArmorResLevelFavor = round(Hytils.instance.getConfig().equipmentHelperArmorResLevelFavor, 2);
                    break;
                case 29:
                    Hytils.instance.getConfig().equipmentHelperArmorDurabilityCheck = !Hytils.instance.getConfig().equipmentHelperArmorDurabilityCheck;
                    break;
                case 30:
                    if (!Keyboard.isKeyDown(Keyboard.KEY_LMENU)) {
                        if (Hytils.instance.getConfig().equipmentHelperArmorDurabilityFavor >= 2f) {
                            Hytils.instance.getConfig().equipmentHelperArmorDurabilityFavor = 1f;
                        } else {
                            Hytils.instance.getConfig().equipmentHelperArmorDurabilityFavor += 0.05D;
                        }
                    } else {
                        if (Hytils.instance.getConfig().equipmentHelperArmorDurabilityFavor <= 1f) {
                            Hytils.instance.getConfig().equipmentHelperArmorDurabilityFavor = 2f;
                        } else {
                            Hytils.instance.getConfig().equipmentHelperArmorDurabilityFavor -= 0.05D;
                        }
                    }
                    Hytils.instance.getConfig().equipmentHelperArmorDurabilityFavor = round(Hytils.instance.getConfig().equipmentHelperArmorDurabilityFavor, 2);
                    break;
                case 31:
                    if (!Keyboard.isKeyDown(Keyboard.KEY_LMENU)) {
                        if (Hytils.instance.getConfig().equipmentHelperArmorDurabilityTierFavor >= 2f) {
                            Hytils.instance.getConfig().equipmentHelperArmorDurabilityTierFavor = 1f;
                        } else {
                            Hytils.instance.getConfig().equipmentHelperArmorDurabilityTierFavor += 0.05D;
                        }
                    } else {
                        if (Hytils.instance.getConfig().equipmentHelperArmorDurabilityTierFavor <= 1f) {
                            Hytils.instance.getConfig().equipmentHelperArmorDurabilityTierFavor = 2f;
                        } else {
                            Hytils.instance.getConfig().equipmentHelperArmorDurabilityTierFavor -= 0.05D;
                        }
                    }
                    Hytils.instance.getConfig().equipmentHelperArmorDurabilityTierFavor = round(Hytils.instance.getConfig().equipmentHelperArmorDurabilityTierFavor, 2);
                    break;

                case 32:
                    if (!Keyboard.isKeyDown(Keyboard.KEY_LMENU)) {
                        if (Hytils.instance.getConfig().equipmentHelperArmorDurabilityLevelFavor >= 2f) {
                            Hytils.instance.getConfig().equipmentHelperArmorDurabilityLevelFavor = 1f;
                        } else {
                            Hytils.instance.getConfig().equipmentHelperArmorDurabilityLevelFavor += 0.05D;
                        }
                    } else {
                        if (Hytils.instance.getConfig().equipmentHelperArmorDurabilityLevelFavor <= 1f) {
                            Hytils.instance.getConfig().equipmentHelperArmorDurabilityLevelFavor = 2f;
                        } else {
                            Hytils.instance.getConfig().equipmentHelperArmorDurabilityLevelFavor -= 0.05D;
                        }
                    }
                    Hytils.instance.getConfig().equipmentHelperArmorDurabilityLevelFavor = round(Hytils.instance.getConfig().equipmentHelperArmorDurabilityLevelFavor, 2);
                    break;
                case 33:
                    Hytils.instance.getConfig().equipmentHelperRodDurabilityCheck = !Hytils.instance.getConfig().equipmentHelperRodDurabilityCheck;
                    break;
                case 34:
                    if (!Keyboard.isKeyDown(Keyboard.KEY_LMENU)) {
                        if (Hytils.instance.getConfig().equipmentHelperRodDurabilityFavor >= 2f) {
                            Hytils.instance.getConfig().equipmentHelperRodDurabilityFavor = 1f;
                        } else {
                            Hytils.instance.getConfig().equipmentHelperRodDurabilityFavor += 0.05D;
                        }
                    } else {
                        if (Hytils.instance.getConfig().equipmentHelperRodDurabilityFavor <= 1f) {
                            Hytils.instance.getConfig().equipmentHelperRodDurabilityFavor = 2f;
                        } else {
                            Hytils.instance.getConfig().equipmentHelperRodDurabilityFavor -= 0.05D;
                        }
                    }
                    Hytils.instance.getConfig().equipmentHelperRodDurabilityFavor = round(Hytils.instance.getConfig().equipmentHelperRodDurabilityFavor, 2);
                    break;
                case 35:
                    Hytils.instance.getConfig().equipmentHelperRodKnockbackCheck = !Hytils.instance.getConfig().equipmentHelperRodKnockbackCheck;
                    break;
                case 36:
                    if (!Keyboard.isKeyDown(Keyboard.KEY_LMENU)) {
                        if (Hytils.instance.getConfig().equipmentHelperRodKnockbackFavor >= 2f) {
                            Hytils.instance.getConfig().equipmentHelperRodKnockbackFavor = 1f;
                        } else {
                            Hytils.instance.getConfig().equipmentHelperRodKnockbackFavor += 0.05D;
                        }
                    } else {
                        if (Hytils.instance.getConfig().equipmentHelperRodKnockbackFavor <= 1f) {
                            Hytils.instance.getConfig().equipmentHelperRodKnockbackFavor = 2f;
                        } else {
                            Hytils.instance.getConfig().equipmentHelperRodKnockbackFavor -= 0.05D;
                        }
                    }
                    Hytils.instance.getConfig().equipmentHelperRodKnockbackFavor = round(Hytils.instance.getConfig().equipmentHelperRodKnockbackFavor, 2);
                    break;
            }
            initGui();
        }
    }
}
