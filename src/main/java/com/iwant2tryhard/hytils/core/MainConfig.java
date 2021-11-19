package com.iwant2tryhard.hytils.core;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.*;

public class MainConfig {
    private final File configFile;
    private JsonObject loadedConfig = new JsonObject();

    public int maxTeammateDisplay = 4;
    public boolean teammateHUDCheckGame = true;
    public boolean teammateHUDEnabled = false;
    public boolean teammateHUDHealthBar = false;
    public boolean teammateHUDHungerBar = false;
    public boolean teammateHUDArmorBar = false;
    public boolean teammateHUDAirBar = false;
    public boolean teammateHUDShowUndetected = true;

    public boolean showCustomHealthBar = false;
    public boolean showCustomHungerBar = false;
    public boolean showCustomArmorBar = false;
    public boolean showCustomAirBar = false;
    public boolean showCustomLevelBar = false;

    public boolean equipmentHelperEnabled = false;

    public boolean equipmentHelperWeaponDamageCheck = true;
    public float equipmentHelperWeaponDamageFavor = 1f;
    public boolean equipmentHelperWeaponDurabilityCheck = true;
    public float equipmentHelperWeaponDurabilityFavor = 1f;
    public boolean equipmentHelperWeaponKnockbackCheck = true;
    public float equipmentHelperWeaponKnockbackFavor = 1f;

    public boolean equipmentHelperArmorResCheck = true;
    public float equipmentHelperArmorResFavor = 1f;
    public boolean equipmentHelperArmorDurabilityCheck = true;
    public float equipmentHelperArmorDurabilityFavor = 1f;

    public boolean equipmentHelperRodKnockbackCheck = true;
    public float equipmentHelperRodKnockbackFavor = 1f;
    public boolean equipmentHelperRodDurabilityCheck = true;
    public float equipmentHelperRodDurabilityFavor = 1f;

    public MainConfig(File configFile) {
        this.configFile = configFile;
    }

    public void loadConfig() {
        if (configFile.exists()) {
            try {
                FileReader reader = new FileReader(configFile);
                BufferedReader bufferedReader = new BufferedReader(reader);
                StringBuilder builder = new StringBuilder();
                String nextLine;
                while ((nextLine = bufferedReader.readLine()) != null) {
                    builder.append(nextLine);
                }
                String complete = builder.toString();
                loadedConfig = new JsonParser().parse(complete).getAsJsonObject();
                maxTeammateDisplay = loadedConfig.get("maxTeammateDisplay").getAsInt();
                teammateHUDCheckGame = loadedConfig.get("teammateHUDCheckGame").getAsBoolean();
                teammateHUDEnabled = loadedConfig.get("teammateHUDEnabled").getAsBoolean();
                teammateHUDHealthBar = loadedConfig.get("teammateHUDHealthBar").getAsBoolean();
                teammateHUDHungerBar = loadedConfig.get("teammateHUDHungerBar").getAsBoolean();
                teammateHUDArmorBar = loadedConfig.get("teammateHUDArmorBar").getAsBoolean();
                teammateHUDAirBar = loadedConfig.get("teammateHUDAirBar").getAsBoolean();
                teammateHUDShowUndetected = loadedConfig.get("teammateHUDShowUndetected").getAsBoolean();

                showCustomHealthBar = loadedConfig.get("showCustomHealthBar").getAsBoolean();
                showCustomHungerBar = loadedConfig.get("showCustomHungerBar").getAsBoolean();
                showCustomArmorBar = loadedConfig.get("showCustomArmorBar").getAsBoolean();
                showCustomAirBar = loadedConfig.get("showCustomAirBar").getAsBoolean();
                showCustomLevelBar = loadedConfig.get("showCustomLevelBar").getAsBoolean();

                equipmentHelperEnabled = loadedConfig.get("equipmentHelperEnabled").getAsBoolean();
                equipmentHelperWeaponDamageCheck = loadedConfig.get("equipmentHelperWeaponDamageCheck").getAsBoolean();
                equipmentHelperWeaponDamageFavor = loadedConfig.get("equipmentHelperWeaponDamageFavor").getAsFloat();
                equipmentHelperWeaponDurabilityCheck = loadedConfig.get("equipmentHelperWeaponDurabilityCheck").getAsBoolean();
                equipmentHelperWeaponDurabilityFavor = loadedConfig.get("equipmentHelperWeaponDurabilityFavor").getAsFloat();
                equipmentHelperWeaponKnockbackCheck = loadedConfig.get("equipmentHelperWeaponKnockbackCheck").getAsBoolean();
                equipmentHelperWeaponKnockbackFavor = loadedConfig.get("equipmentHelperWeaponKnockbackFavor").getAsFloat();

                equipmentHelperArmorResCheck = loadedConfig.get("equipmentHelperArmorResCheck").getAsBoolean();
                equipmentHelperArmorResFavor = loadedConfig.get("equipmentHelperArmorResFavor").getAsFloat();
                equipmentHelperArmorDurabilityCheck = loadedConfig.get("equipmentHelperArmorDurabilityCheck").getAsBoolean();
                equipmentHelperArmorDurabilityFavor = loadedConfig.get("equipmentHelperArmorDurabilityFavor").getAsFloat();

                equipmentHelperWeaponDurabilityCheck = loadedConfig.get("equipmentHelperWeaponDurabilityCheck").getAsBoolean();
                equipmentHelperWeaponDurabilityFavor = loadedConfig.get("equipmentHelperWeaponDurabilityFavor").getAsFloat();
                equipmentHelperWeaponKnockbackCheck = loadedConfig.get("equipmentHelperWeaponKnockbackCheck").getAsBoolean();
                equipmentHelperWeaponKnockbackFavor = loadedConfig.get("equipmentHelperWeaponKnockbackFavor").getAsFloat();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            saveConfig();
        }
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public void saveConfig() {
        loadedConfig = new JsonObject();
        try {
            configFile.createNewFile();
            FileWriter writer = new FileWriter(configFile);
            BufferedWriter bufferedWriter = new BufferedWriter(writer);
            loadedConfig.addProperty("maxTeammateDisplay", maxTeammateDisplay);
            loadedConfig.addProperty("teammateHUDCheckGame", teammateHUDCheckGame);
            loadedConfig.addProperty("teammateHUDEnabled", teammateHUDEnabled);
            loadedConfig.addProperty("teammateHUDHealthBar", teammateHUDHealthBar);
            loadedConfig.addProperty("teammateHUDHungerBar", teammateHUDHungerBar);
            loadedConfig.addProperty("teammateHUDArmorBar", teammateHUDArmorBar);
            loadedConfig.addProperty("teammateHUDAirBar", teammateHUDAirBar);
            loadedConfig.addProperty("teammateHUDShowUndetected", teammateHUDShowUndetected);
            loadedConfig.addProperty("showCustomHealthBar", showCustomHealthBar);
            loadedConfig.addProperty("showCustomHungerBar", showCustomHungerBar);
            loadedConfig.addProperty("showCustomArmorBar", showCustomArmorBar);
            loadedConfig.addProperty("showCustomAirBar", showCustomAirBar);
            loadedConfig.addProperty("showCustomLevelBar", showCustomLevelBar);
            bufferedWriter.write(loadedConfig.toString());
            bufferedWriter.close();
            writer.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Error while attempting to create/save the config...");
        }
    }
}
