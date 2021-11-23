package com.iwant2tryhard.hytils.core;

import com.iwant2tryhard.hytils.Hytils;
import com.iwant2tryhard.hytils.core.misc.BetterColor;
import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.common.MinecraftForge;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Utils {
    private static final String COMPASS_NAME = EnumChatFormatting.GREEN + "Game Menu " + EnumChatFormatting.GRAY + "(Right Click)";
    private static final String COMPASS_LORE = EnumChatFormatting.DARK_PURPLE.toString() + EnumChatFormatting.ITALIC + EnumChatFormatting.GRAY + "Right Click to bring up the Game Menu!";
    private boolean inHypixelLobby = false;
    private boolean onHypixel = false;

    public GameTypes currentGame;
    public GameTypes previousGame;
    public GameTypes prevPreviousGame;

    public List<String> teammates = new ArrayList<String>();
    public List<String> displayTeammates = new ArrayList<String>();

    public Map<Integer, Double> swordScoreMap = new HashMap<Integer, Double>();
    public Map<Integer, Double> bowScoreMap = new HashMap<Integer, Double>();

    public Map<Integer, Double> headScoreMap = new HashMap<Integer, Double>();
    public Map<Integer, Double> chestScoreMap = new HashMap<Integer, Double>();
    public Map<Integer, Double> legsScoreMap = new HashMap<Integer, Double>();
    public Map<Integer, Double> feetScoreMap = new HashMap<Integer, Double>();

    public Map<Integer, Double> rodScoreMap = new HashMap<Integer, Double>();

    public Map<String, Float> prevInitialAbsorption = new HashMap<String, Float>();

    public List<EntityTNTPrimed> tntList = new ArrayList<EntityTNTPrimed>();
    public List<Map<Integer, BlockPos>> highlightList = new ArrayList<Map<Integer, BlockPos>>();

    public int displayTimes = 0;

    private final Random rnd = new Random();

    public String lastPlayerNameInteracted = null;
    public int lastPlayerInteractedTimer = 0;

    public void runLobbyCheckerTimer() {
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                checkIP(Hytils.instance.mc);
                checkForItem(Hytils.instance.mc);
                detectTeammatesAuto();
                randomizeDisplayTeammates();
            }
        }, 1000, 1000);
    }

    public static void sendFormattedMessage(String message) {
        sendMessage(new ChatComponentText(EnumChatFormatting.GOLD + "[Hytils] " + EnumChatFormatting.RESET + message));
    }

    private void checkIP(Minecraft mc) {
        if (mc.getCurrentServerData() != null) {
            String ip = mc.getCurrentServerData().serverIP.toLowerCase();
            onHypixel = ip.equals("hypixel.net") || ip.endsWith(".hypixel.net") || ip.contains(".hypixel.net:") || ip.startsWith("hypixel.net:");
            return;
        }
        onHypixel = false;
    }

    private void detectTeammatesAuto() {
        if (isOnHypixel() && !isInHypixelLobby()) {
            if (teammates.isEmpty()) {
                detectTeammates();
            }
        }
    }

    private void checkForItem(Minecraft mc) {
        if (mc != null && mc.thePlayer != null && mc.thePlayer.inventory != null) {
            if (isOnHypixel()) {
                ItemStack item = mc.thePlayer.inventory.getStackInSlot(0);
                if (item != null && item.hasDisplayName() && item.getDisplayName().equals(COMPASS_NAME)) {
                    List<String> toolip = item.getTooltip(mc.thePlayer, false);
                    if (toolip.get(1).equals(COMPASS_LORE)) {
                        inHypixelLobby = true;
                        currentGame = GameTypes.NOT_IN_GAME;
                        teammates.clear();
                        displayTeammates.clear();
                        prevInitialAbsorption.clear();
                        return;
                    }
                }
            }
        }
        inHypixelLobby = false;
    }

    public void detectTNT() {
        if (Hytils.instance.mc != null && Hytils.instance.mc.theWorld != null) {
            tntList.clear();
            World wrd = Hytils.instance.mc.theWorld;
            List<Entity> allEntities = wrd.loadedEntityList;
            List<EntityTNTPrimed> allTnt = new ArrayList<EntityTNTPrimed>();
            for (Entity e : allEntities) {
                if (e instanceof EntityTNTPrimed) {
                    allTnt.add((EntityTNTPrimed) e);
                }
            }
            tntList = allTnt;
        }
    }

    public void detectTeammates() {
        Minecraft mc = Hytils.instance.mc;
        if (mc.theWorld != null && mc.thePlayer != null) {
            List<EntityPlayer> players = mc.theWorld.playerEntities;
            if (currentGame != null) {
                if (currentGame.gameGroup.equals("bedwars") | currentGame == GameTypes.ARCADE_CAPTURETHEWOOL) {
                    teammates.clear();
                    displayTeammates.clear();
                    prevInitialAbsorption.clear();
                    for (EntityPlayer player : players) {
                        String filteredPlayerName = player.getDisplayNameString();
                        if (filteredPlayerName.startsWith("\u00a7")) {
                            filteredPlayerName = filteredPlayerName.replace("\u00a7", "");
                            filteredPlayerName = filteredPlayerName.substring(1);
                        }
                        ItemStack chestplate = player.getCurrentArmor(3);
                        int chestplateColor = 0;
                        boolean shouldBeTeammate = false;
                        if (chestplate != null && chestplate.getItem() != null) {
                            if (chestplate.getItem() instanceof ItemArmor) {
                                chestplateColor = ((ItemArmor) chestplate.getItem()).getColor(chestplate);
                            }
                        }
                        if (mc.thePlayer.getCurrentArmor(3) != null && mc.thePlayer.getCurrentArmor(3).getItem() != null) {
                            if (mc.thePlayer.getCurrentArmor(3).getItem() instanceof ItemArmor) {
                                int myColor = ((ItemArmor) mc.thePlayer.getCurrentArmor(3).getItem()).getColor(mc.thePlayer.getCurrentArmor(3));
                                shouldBeTeammate = chestplateColor == myColor;
                            }
                        }
                        if (shouldBeTeammate && !teammates.contains(filteredPlayerName)) {
                            teammates.add(filteredPlayerName);
                        } else {
                            teammates.remove(filteredPlayerName);
                        }
                    }
                } else if (Hytils.instance.getUtils().currentGame == GameTypes.ARCADE_BLOCKINGDEAD ||
                        Hytils.instance.getUtils().currentGame == GameTypes.ARCADE_ZOMBIES_DEADEND ||
                        Hytils.instance.getUtils().currentGame == GameTypes.ARCADE_ZOMBIES_BADBLOOD ||
                        Hytils.instance.getUtils().currentGame == GameTypes.ARCADE_ZOMBIES_ALIENARCADIUM) {
                    teammates.clear();
                    List<EntityPlayer> players1 = mc.theWorld.playerEntities;
                    List<String> players2 = new ArrayList<String>();
                    for (EntityPlayer player : players1) {
                        if (!player.getDisplayNameString().startsWith("\u00a7c")) {
                            String playerName = player.getDisplayNameString();
                            if (playerName.startsWith("\u00a7")) {
                                playerName = playerName.replace("\u00a7", "");
                                playerName = playerName.substring(1);
                            }
                            players2.add(playerName);
                        }
                    }
                    teammates = players2;
                }
            }
        }
    }

    public boolean isInHypixelLobby() {
        return inHypixelLobby;
    }

    public boolean isOnHypixel() {
        return onHypixel;
    }

    public static void sendMessage(IChatComponent sendMessage) {
        ClientChatReceivedEvent event = new ClientChatReceivedEvent((byte) 1, sendMessage);
        MinecraftForge.EVENT_BUS.post(event);
        if (!event.isCanceled()) {
            Minecraft.getMinecraft().thePlayer.addChatMessage(event.message);
        }
    }

    public void randomizeDisplayTeammates() {
        //System.out.println("called");
        if (displayTimes > 4) {
            if (teammates.size() > 0) {
                if (teammates.size() <= Hytils.instance.getConfig().maxTeammateDisplay) {
                    displayTeammates = teammates;
                } else {
                    displayTeammates.clear();
                    displayTeammates = new ArrayList<String>();
                    prevInitialAbsorption.clear();
                    int times = Hytils.instance.getConfig().maxTeammateDisplay;
                    while (times > 0) {
                        int rand = MathHelper.getRandomIntegerInRange(rnd, 0, teammates.size() - 1);
                        displayTeammates.add(teammates.get(rand));
                        --times;
                    }
                    System.out.println("randomized display teammates list");
                }
            }
            displayTimes = 0;
        } else {
            ++displayTimes;
        }
    }

    /**
     * Returns all blocks inside a specified sphere.
     *
     * @param center    Center of the sphere or ellipsoid
     * @param diameterX The sphere/ellipsoid's largest north/south extent
     * @param diameterY The sphere/ellipsoid's largest up/down extent
     * @param diameterZ The sphere/ellipsoid's largest east/west extent
     * @param filled    If false, only a shell will be returned.
     * @return List of blocks in the sphere or ellipsoid
     */
    public static List<BlockPos> getSphere(BlockPos center, double diameterX, double diameterY, double diameterZ, boolean filled) {
        List<BlockPos> posList = new ArrayList<BlockPos>();

        diameterX += 0.5;
        diameterY += 0.5;
        diameterZ += 0.5;

        final double invRadiusX = 1 / diameterX;
        final double invRadiusY = 1 / diameterY;
        final double invRadiusZ = 1 / diameterZ;

        final int ceilRadiusX = (int) Math.ceil(diameterX);
        final int ceilRadiusY = (int) Math.ceil(diameterY);
        final int ceilRadiusZ = (int) Math.ceil(diameterZ);

        double nextXn = 0;
        forX:
        for (int x = 0; x <= ceilRadiusX; ++x) {
            final double xn = nextXn;
            nextXn = (x + 1) * invRadiusX;
            double nextYn = 0;
            forY:
            for (int y = 0; y <= ceilRadiusY; ++y) {
                final double yn = nextYn;
                nextYn = (y + 1) * invRadiusY;
                double nextZn = 0;
                forZ:
                for (int z = 0; z <= ceilRadiusZ; ++z) {
                    final double zn = nextZn;
                    nextZn = (z + 1) * invRadiusZ;

                    double distanceSq = lengthSq(xn, yn, zn);
                    if (distanceSq > 1) {
                        if (z == 0) {
                            if (y == 0) {
                                break forX;
                            }
                            break forY;
                        }
                        break forZ;
                    }

                    if (!filled) {
                        if (lengthSq(nextXn, yn, zn) <= 1 && lengthSq(xn, nextYn, zn) <= 1 && lengthSq(xn, yn, nextZn) <= 1) {
                            continue;
                        }
                    }

                    posList.add(new BlockPos(x, y, z));
                    posList.add(new BlockPos(-x, y, z));
                    posList.add(new BlockPos(x, -y, z));
                    posList.add(new BlockPos(x, y, -z));
                    posList.add(new BlockPos(-x, -y, z));
                    posList.add(new BlockPos(-x, y, -z));
                    posList.add(new BlockPos(x, -y, -z));
                    posList.add(new BlockPos(-x, -y, -z));
                }
            }
        }

        return posList;
    }

    /**
     * Makes a sphere or ellipsoid.
     *
     * @param center   Center of the sphere or ellipsoid
     * @param diameter The sphere's diameter
     * @param filled   If false, only a shell will be generated.
     * @return List of blocks in the sphere or ellipsoid
     */
    public static List<BlockPos> getSphere(BlockPos center, double diameter, boolean filled) {
        return getSphere(center, diameter, diameter, diameter, filled);
    }

    public static int getEnchantmentLevel(ItemStack stack, Enchantment enchant) {
        NBTTagList nbttaglist = stack.getEnchantmentTagList();

        if (nbttaglist != null) {
            for (int j = 0; j < nbttaglist.tagCount(); ++j) {
                int k = nbttaglist.getCompoundTagAt(j).getShort("id");
                int l = nbttaglist.getCompoundTagAt(j).getShort("lvl");

                if (Enchantment.getEnchantmentById(k) == enchant) {
                    return l;
                }
            }
        }

        return 0;
    }

    public void scoreInventory(Container inv) {
        getScoreMapForSwords(inv);
        getScoreMapForArmor(inv);
        getScoreMapForBows(inv);
        getScoreMapForRods(inv);
    }

    public static double distanceOf(Entity a, Entity b) {
        double x1 = a.posX;
        double y1 = a.posY;
        double z1 = a.posZ;
        double x2 = b.posX;
        double y2 = b.posY;
        double z2 = b.posZ;
        return distanceOf(x1, y1, z1, x2, y2, z2);
    }

    public static double distanceOf(Entity a, double x2, double y2, double z2) {
        double x1 = a.posX;
        double y1 = a.posY;
        double z1 = a.posZ;
        return distanceOf(x1, y1, z1, x2, y2, z2);
    }

    public void getScoreMapForArmor(Container inv) {
        headScoreMap.clear();
        chestScoreMap.clear();
        legsScoreMap.clear();
        feetScoreMap.clear();
        double maxResistanceScore = ((4 * (((1f - Hytils.instance.getConfig().equipmentHelperArmorResTierFavor) / 2f) + 1f)) +
                (8 * (((1f - Hytils.instance.getConfig().equipmentHelperArmorResLevelFavor) / 2f) + 1f)) +
                (4 * (((1f - Hytils.instance.getConfig().equipmentHelperArmorResLevelFavor) / 2f) + 1f))) *
                (((1f - Hytils.instance.getConfig().equipmentHelperArmorResFavor) / 2f) + 1f);
        double maxDurabilityScore = ((6 * (((1f - Hytils.instance.getConfig().equipmentHelperArmorDurabilityTierFavor) / 2f) + 1f)) +
                (6 * (((1f - Hytils.instance.getConfig().equipmentHelperArmorDurabilityLevelFavor) / 2f) + 1f))) *
                (((1f - Hytils.instance.getConfig().equipmentHelperArmorDurabilityFavor) / 2f) + 1f) * 0.75f;
        List<Slot> slots = inv.inventorySlots;
        for (int i = 0; i < slots.size(); i++) {
            if (slots.get(i).getStack() != null && slots.get(i).getStack().getItem() != null) {
                Item item = slots.get(i).getStack().getItem();
                ItemStack itemstack = slots.get(i).getStack();
                double protectionScore = 0;
                double durabilityScore = 0;
                int protectionLevel = getEnchantmentLevel(itemstack, Enchantment.protection);
                int otherprotectionLevel = getEnchantmentLevel(itemstack, Enchantment.projectileProtection) +
                        getEnchantmentLevel(itemstack, Enchantment.blastProtection) +
                        getEnchantmentLevel(itemstack, Enchantment.fireProtection);
                int unbreakingLevel = getEnchantmentLevel(itemstack, Enchantment.unbreaking);
                if (item == Items.leather_helmet |
                        item == Items.leather_chestplate |
                        item == Items.leather_leggings |
                        item == Items.leather_boots) {
                    double scoreProtectionRaw = ((1 * Hytils.instance.getConfig().equipmentHelperArmorResTierFavor) +
                            (protectionLevel * 2 * Hytils.instance.getConfig().equipmentHelperArmorResLevelFavor) +
                            (otherprotectionLevel * Hytils.instance.getConfig().equipmentHelperArmorResLevelFavor)) *
                            Hytils.instance.getConfig().equipmentHelperArmorResFavor;
                    double scoreDurabilityRaw = ((2 * Hytils.instance.getConfig().equipmentHelperArmorDurabilityTierFavor) +
                            (unbreakingLevel * 2 * Hytils.instance.getConfig().equipmentHelperArmorDurabilityLevelFavor)) *
                            Hytils.instance.getConfig().equipmentHelperArmorDurabilityFavor * 0.5f;
                    protectionScore = (scoreProtectionRaw / maxResistanceScore) * 100f;
                    durabilityScore = (scoreDurabilityRaw / maxDurabilityScore) * 50f;
                } else if (item == Items.golden_helmet |
                        item == Items.golden_chestplate |
                        item == Items.golden_leggings |
                        item == Items.golden_boots) {
                    double scoreProtectionRaw = ((1 * Hytils.instance.getConfig().equipmentHelperArmorResTierFavor) +
                            (protectionLevel * 2 * Hytils.instance.getConfig().equipmentHelperArmorResLevelFavor) +
                            (otherprotectionLevel * Hytils.instance.getConfig().equipmentHelperArmorResLevelFavor)) *
                            Hytils.instance.getConfig().equipmentHelperArmorResFavor;
                    double scoreDurabilityRaw = ((1 * Hytils.instance.getConfig().equipmentHelperArmorDurabilityTierFavor) +
                            (unbreakingLevel * 2 * Hytils.instance.getConfig().equipmentHelperArmorDurabilityLevelFavor)) *
                            Hytils.instance.getConfig().equipmentHelperArmorDurabilityFavor * 0.5f;
                    protectionScore = (scoreProtectionRaw / maxResistanceScore) * 100f;
                    durabilityScore = (scoreDurabilityRaw / maxDurabilityScore) * 50f;
                } else if (item == Items.iron_helmet |
                        item == Items.iron_chestplate |
                        item == Items.iron_leggings |
                        item == Items.iron_boots) {
                    double scoreProtectionRaw = ((2 * Hytils.instance.getConfig().equipmentHelperArmorResTierFavor) +
                            (protectionLevel * 2 * Hytils.instance.getConfig().equipmentHelperArmorResLevelFavor) +
                            (otherprotectionLevel * Hytils.instance.getConfig().equipmentHelperArmorResLevelFavor)) *
                            Hytils.instance.getConfig().equipmentHelperArmorResFavor;
                    double scoreDurabilityRaw = ((3 * Hytils.instance.getConfig().equipmentHelperArmorDurabilityTierFavor) +
                            (unbreakingLevel * 2 * Hytils.instance.getConfig().equipmentHelperArmorDurabilityLevelFavor)) *
                            Hytils.instance.getConfig().equipmentHelperArmorDurabilityFavor * 0.5f;
                    protectionScore = (scoreProtectionRaw / maxResistanceScore) * 100f;
                    durabilityScore = (scoreDurabilityRaw / maxDurabilityScore) * 50f;
                } else if (item == Items.chainmail_helmet |
                        item == Items.chainmail_chestplate |
                        item == Items.chainmail_leggings |
                        item == Items.chainmail_boots) {
                    double scoreProtectionRaw = ((2 * Hytils.instance.getConfig().equipmentHelperArmorResTierFavor) +
                            (protectionLevel * 2 * Hytils.instance.getConfig().equipmentHelperArmorResLevelFavor) +
                            (otherprotectionLevel * Hytils.instance.getConfig().equipmentHelperArmorResLevelFavor)) *
                            Hytils.instance.getConfig().equipmentHelperArmorResFavor;
                    double scoreDurabilityRaw = ((3 * Hytils.instance.getConfig().equipmentHelperArmorDurabilityTierFavor) +
                            (unbreakingLevel * 2 * Hytils.instance.getConfig().equipmentHelperArmorDurabilityLevelFavor)) *
                            Hytils.instance.getConfig().equipmentHelperArmorDurabilityFavor * 0.5f;
                    protectionScore = (scoreProtectionRaw / maxResistanceScore) * 100f;
                    durabilityScore = (scoreDurabilityRaw / maxDurabilityScore) * 50f;
                } else if (item == Items.diamond_helmet |
                        item == Items.diamond_chestplate |
                        item == Items.diamond_leggings |
                        item == Items.diamond_boots) {
                    double scoreProtectionRaw = ((3 * Hytils.instance.getConfig().equipmentHelperArmorResTierFavor) +
                            (protectionLevel * 2 * Hytils.instance.getConfig().equipmentHelperArmorResLevelFavor) +
                            (otherprotectionLevel * Hytils.instance.getConfig().equipmentHelperArmorResLevelFavor)) *
                            Hytils.instance.getConfig().equipmentHelperArmorResFavor;
                    double scoreDurabilityRaw = ((4 * Hytils.instance.getConfig().equipmentHelperArmorDurabilityTierFavor) +
                            (unbreakingLevel * 2 * Hytils.instance.getConfig().equipmentHelperArmorDurabilityLevelFavor)) *
                            Hytils.instance.getConfig().equipmentHelperArmorDurabilityFavor * 0.5f;
                    protectionScore = (scoreProtectionRaw / maxResistanceScore) * 100f;
                    durabilityScore = (scoreDurabilityRaw / maxDurabilityScore) * 50f;
                }
                double sumScore = 0;
                double divisor = 0;
                if (Hytils.instance.getConfig().equipmentHelperArmorResCheck) {
                    sumScore += protectionScore;
                    divisor += 1f;
                }
                if (Hytils.instance.getConfig().equipmentHelperArmorDurabilityCheck) {
                    sumScore += durabilityScore;
                    divisor += 0.5f;
                }

                if (item == Items.leather_helmet |
                        item == Items.golden_helmet |
                        item == Items.chainmail_helmet |
                        item == Items.iron_helmet |
                        item == Items.diamond_helmet) {
                    headScoreMap.put(i, sumScore / divisor);
                } else if (item == Items.leather_chestplate |
                        item == Items.golden_chestplate |
                        item == Items.chainmail_chestplate |
                        item == Items.iron_chestplate |
                        item == Items.diamond_chestplate) {
                    chestScoreMap.put(i, sumScore / divisor);
                } else if (item == Items.leather_leggings |
                        item == Items.golden_leggings |
                        item == Items.chainmail_leggings |
                        item == Items.iron_leggings |
                        item == Items.diamond_leggings) {
                    legsScoreMap.put(i, sumScore / divisor);
                } else if (item == Items.leather_boots |
                        item == Items.golden_boots |
                        item == Items.chainmail_boots |
                        item == Items.iron_boots |
                        item == Items.diamond_boots) {
                    feetScoreMap.put(i, sumScore / divisor);
                }
            }
        }
    }

    public static double distanceOf(double x1, double y1, double z1, double x2, double y2, double z2) {
        return Math.sqrt(Math.pow((x1 - x2), 2d) + Math.pow((y1 - y2), 2d) + Math.pow((z1 - z2), 2d));
    }

    public static BlockPos getNearestBlock(double x, double y, double z) {
        double nearestX = Math.round(x);
        double nearestY = Math.round(y);
        double nearestZ = Math.round(z);
        return new BlockPos(nearestX, nearestY, nearestZ);
    }

    private static double lengthSq(double x, double y, double z) {
        return (x * x) + (y * y) + (z * z);
    }

    private static double lengthSq(double x, double z) {
        return (x * x) + (z * z);
    }

    public static int GetColorIntValue(Color value) {
        return (value.getAlpha() << 24 | value.getRed() << 16 | value.getGreen() << 8 | value.getBlue());
    }

    public static BetterColor GetColorValue(int value) {
        int a = (value >> 24) & 0XFF;
        int r = (value >> 16) & 0XFF;
        int g = (value >> 8) & 0XFF;
        int b = value & 0XFF;
        return new BetterColor(r, g, b, a);
    }

    public void getScoreMapForSwords(Container inv) {
        swordScoreMap.clear();
        double maxDamageScore = ((4 * (((1f - Hytils.instance.getConfig().equipmentHelperWeaponDamageTierFavor) / 2f) + 1f)) +
                (10 * (((1f - Hytils.instance.getConfig().equipmentHelperWeaponDamageLevelFavor) / 2f) + 1f))) *
                (((1f - Hytils.instance.getConfig().equipmentHelperWeaponDamageFavor) / 2f) + 1f);
        double maxDurabilityScore = ((6 * (((1f - Hytils.instance.getConfig().equipmentHelperWeaponDurabilityTierFavor) / 2f) + 1f)) +
                (6 * (((1f - Hytils.instance.getConfig().equipmentHelperWeaponDurabilityLevelFavor) / 2f) + 1f))) *
                (((1f - Hytils.instance.getConfig().equipmentHelperWeaponDurabilityFavor) / 2f) + 1f) * 0.75f;
        double maxKnockbackScore = (2 * (((1f - Hytils.instance.getConfig().equipmentHelperWeaponKnockbackFavor) / 2f) + 1f)) * 0.75f;
        List<Slot> slots = inv.inventorySlots;
        for (int i = 0; i < slots.size(); i++) {
            if (slots.get(i).getStack() != null && slots.get(i).getStack().getItem() != null) {
                Item item = slots.get(i).getStack().getItem();
                ItemStack itemstack = slots.get(i).getStack();
                double damageScore = 0;
                double durabilityScore = 0;
                double knockbackScore = 0;
                int sharpnessLevel = getEnchantmentLevel(itemstack, Enchantment.sharpness);
                int unbreakingLevel = getEnchantmentLevel(itemstack, Enchantment.unbreaking);
                int knockbacklevel = getEnchantmentLevel(itemstack, Enchantment.knockback);
                if (item == Items.wooden_sword |
                        item == Items.stone_sword |
                        item == Items.golden_sword |
                        item == Items.iron_sword |
                        item == Items.diamond_sword) {
                    if (item == Items.wooden_sword) {
                        double scoreDamageRaw = ((1 * Hytils.instance.getConfig().equipmentHelperWeaponDamageTierFavor) +
                                (sharpnessLevel * 2 * Hytils.instance.getConfig().equipmentHelperWeaponDamageLevelFavor)) *
                                Hytils.instance.getConfig().equipmentHelperWeaponDamageFavor;
                        double scoreDurabilityRaw = ((2 * Hytils.instance.getConfig().equipmentHelperWeaponDurabilityTierFavor) +
                                (unbreakingLevel * 2 * Hytils.instance.getConfig().equipmentHelperWeaponDurabilityLevelFavor)) *
                                Hytils.instance.getConfig().equipmentHelperWeaponDurabilityFavor * 0.5f;
                        double scoreKnockbackRaw = (knockbacklevel * Hytils.instance.getConfig().equipmentHelperWeaponKnockbackFavor) * 0.5f;
                        damageScore = (scoreDamageRaw / maxDamageScore) * 100f;
                        durabilityScore = (scoreDurabilityRaw / maxDurabilityScore) * 50f;
                        knockbackScore = (scoreKnockbackRaw / maxKnockbackScore) * 50f;
                    } else if (item == Items.golden_sword) {
                        double scoreDamageRaw = ((1 * Hytils.instance.getConfig().equipmentHelperWeaponDamageTierFavor) +
                                (sharpnessLevel * 2 * Hytils.instance.getConfig().equipmentHelperWeaponDamageLevelFavor)) *
                                Hytils.instance.getConfig().equipmentHelperWeaponDamageFavor;
                        double scoreDurabilityRaw = ((1 * Hytils.instance.getConfig().equipmentHelperWeaponDurabilityTierFavor) +
                                (unbreakingLevel * 2 * Hytils.instance.getConfig().equipmentHelperWeaponDurabilityLevelFavor)) *
                                Hytils.instance.getConfig().equipmentHelperWeaponDurabilityFavor * 0.5f;
                        double scoreKnockbackRaw = (knockbacklevel * Hytils.instance.getConfig().equipmentHelperWeaponKnockbackFavor) * 0.5f;
                        damageScore = (scoreDamageRaw / maxDamageScore) * 100f;
                        durabilityScore = (scoreDurabilityRaw / maxDurabilityScore) * 50f;
                        knockbackScore = (scoreKnockbackRaw / maxKnockbackScore) * 50f;
                    } else if (item == Items.stone_sword) {
                        double scoreDamageRaw = ((2 * Hytils.instance.getConfig().equipmentHelperWeaponDamageTierFavor) +
                                (sharpnessLevel * 2 * Hytils.instance.getConfig().equipmentHelperWeaponDamageLevelFavor)) *
                                Hytils.instance.getConfig().equipmentHelperWeaponDamageFavor;
                        double scoreDurabilityRaw = ((3 * Hytils.instance.getConfig().equipmentHelperWeaponDurabilityTierFavor) +
                                (unbreakingLevel * 2 * Hytils.instance.getConfig().equipmentHelperWeaponDurabilityLevelFavor)) *
                                Hytils.instance.getConfig().equipmentHelperWeaponDurabilityFavor * 0.5f;
                        double scoreKnockbackRaw = (knockbacklevel * Hytils.instance.getConfig().equipmentHelperWeaponKnockbackFavor) * 0.5f;
                        damageScore = (scoreDamageRaw / maxDamageScore) * 100f;
                        durabilityScore = (scoreDurabilityRaw / maxDurabilityScore) * 50f;
                        knockbackScore = (scoreKnockbackRaw / maxKnockbackScore) * 50f;
                    } else if (item == Items.iron_sword) {
                        double scoreDamageRaw = ((3 * Hytils.instance.getConfig().equipmentHelperWeaponDamageTierFavor) +
                                (sharpnessLevel * 2 * Hytils.instance.getConfig().equipmentHelperWeaponDamageLevelFavor)) *
                                Hytils.instance.getConfig().equipmentHelperWeaponDamageFavor;
                        double scoreDurabilityRaw = ((4 * Hytils.instance.getConfig().equipmentHelperWeaponDurabilityTierFavor) +
                                (unbreakingLevel * 2 * Hytils.instance.getConfig().equipmentHelperWeaponDurabilityLevelFavor)) *
                                Hytils.instance.getConfig().equipmentHelperWeaponDurabilityFavor * 0.5f;
                        double scoreKnockbackRaw = (knockbacklevel * Hytils.instance.getConfig().equipmentHelperWeaponKnockbackFavor) * 0.5f;
                        damageScore = (scoreDamageRaw / maxDamageScore) * 100f;
                        durabilityScore = (scoreDurabilityRaw / maxDurabilityScore) * 50f;
                        knockbackScore = (scoreKnockbackRaw / maxKnockbackScore) * 50f;
                    } else if (item == Items.diamond_sword) {
                        double scoreDamageRaw = ((4 * Hytils.instance.getConfig().equipmentHelperWeaponDamageTierFavor) +
                                (sharpnessLevel * 2 * Hytils.instance.getConfig().equipmentHelperWeaponDamageLevelFavor)) *
                                Hytils.instance.getConfig().equipmentHelperWeaponDamageFavor;
                        double scoreDurabilityRaw = ((6 * Hytils.instance.getConfig().equipmentHelperWeaponDurabilityTierFavor) +
                                (unbreakingLevel * 2 * Hytils.instance.getConfig().equipmentHelperWeaponDurabilityLevelFavor)) *
                                Hytils.instance.getConfig().equipmentHelperWeaponDurabilityFavor * 0.5f;
                        double scoreKnockbackRaw = (knockbacklevel * Hytils.instance.getConfig().equipmentHelperWeaponKnockbackFavor) * 0.5f;
                        damageScore = (scoreDamageRaw / maxDamageScore) * 100f;
                        durabilityScore = (scoreDurabilityRaw / maxDurabilityScore) * 50f;
                        knockbackScore = (scoreKnockbackRaw / maxKnockbackScore) * 50f;
                    }
                    double sumScore = 0;
                    double divisor = 0;
                    if (Hytils.instance.getConfig().equipmentHelperWeaponDamageCheck) {
                        sumScore += damageScore;
                        divisor += 1f;
                    }
                    if (Hytils.instance.getConfig().equipmentHelperWeaponDurabilityCheck) {
                        sumScore += durabilityScore;
                        divisor += 0.5f;
                    }
                    if (Hytils.instance.getConfig().equipmentHelperWeaponKnockbackCheck) {
                        sumScore += knockbackScore;
                        divisor += 0.5f;
                    }
                    swordScoreMap.put(i, sumScore / divisor);
                }
            }
        }
    }

    public void getScoreMapForBows(Container inv) {
        bowScoreMap.clear();
        double maxDamageScore = (10 * (((1f - Hytils.instance.getConfig().equipmentHelperWeaponDamageLevelFavor) / 2f) + 1f)) *
                (((1f - Hytils.instance.getConfig().equipmentHelperWeaponDamageFavor) / 2f) + 1f);
        double maxDurabilityScore = (6 * (((1f - Hytils.instance.getConfig().equipmentHelperWeaponDurabilityLevelFavor) / 2f) + 1f)) *
                (((1f - Hytils.instance.getConfig().equipmentHelperWeaponDurabilityFavor) / 2f) + 1f) * 0.75f;
        double maxKnockbackScore = (2 * (((1f - Hytils.instance.getConfig().equipmentHelperWeaponKnockbackFavor) / 2f) + 1f)) * 0.75f;
        List<Slot> slots = inv.inventorySlots;
        for (int i = 0; i < slots.size(); i++) {
            if (slots.get(i).getStack() != null && slots.get(i).getStack().getItem() != null) {
                Item item = slots.get(i).getStack().getItem();
                ItemStack itemstack = slots.get(i).getStack();
                double damageScore = 0;
                double durabilityScore = 0;
                double knockbackScore = 0;
                int sharpnessLevel = getEnchantmentLevel(itemstack, Enchantment.power);
                int unbreakingLevel = getEnchantmentLevel(itemstack, Enchantment.unbreaking);
                int knockbacklevel = getEnchantmentLevel(itemstack, Enchantment.punch);
                if (item == Items.bow) {
                    double scoreDamageRaw = (sharpnessLevel * 2 * Hytils.instance.getConfig().equipmentHelperWeaponDamageLevelFavor) *
                            Hytils.instance.getConfig().equipmentHelperWeaponDamageFavor;
                    double scoreDurabilityRaw = (unbreakingLevel * 2 * Hytils.instance.getConfig().equipmentHelperWeaponDurabilityLevelFavor) *
                            Hytils.instance.getConfig().equipmentHelperWeaponDurabilityFavor * 0.5f;
                    double scoreKnockbackRaw = (knockbacklevel * Hytils.instance.getConfig().equipmentHelperWeaponKnockbackFavor) * 0.5f;
                    damageScore = (scoreDamageRaw / maxDamageScore) * 100f;
                    durabilityScore = (scoreDurabilityRaw / maxDurabilityScore) * 50f;
                    knockbackScore = (scoreKnockbackRaw / maxKnockbackScore) * 50f;
                    double sumScore = 0;
                    double divisor = 0;
                    if (Hytils.instance.getConfig().equipmentHelperWeaponDamageCheck) {
                        sumScore += damageScore;
                        divisor += 1f;
                    }
                    if (Hytils.instance.getConfig().equipmentHelperWeaponDurabilityCheck) {
                        sumScore += durabilityScore;
                        divisor += 0.5f;
                    }
                    if (Hytils.instance.getConfig().equipmentHelperWeaponKnockbackCheck) {
                        sumScore += knockbackScore;
                        divisor += 0.5f;
                    }
                    bowScoreMap.put(i, sumScore / divisor);
                }
            }
        }
    }

    public void getScoreMapForRods(Container inv) {
        rodScoreMap.clear();
        double maxDurabilityScore = (6 * (((1f - Hytils.instance.getConfig().equipmentHelperRodDurabilityLevelFavor) / 2f) + 1f)) *
                (((1f - Hytils.instance.getConfig().equipmentHelperRodDurabilityFavor) / 2f) + 1f) * 0.75f;
        double maxKnockbackScore = (4 * (((1f - Hytils.instance.getConfig().equipmentHelperRodKnockbackFavor) / 2f) + 1f));
        List<Slot> slots = inv.inventorySlots;
        for (int i = 0; i < slots.size(); i++) {
            if (slots.get(i).getStack() != null && slots.get(i).getStack().getItem() != null) {
                Item item = slots.get(i).getStack().getItem();
                ItemStack itemstack = slots.get(i).getStack();
                double durabilityScore = 0;
                double knockbackScore = 0;
                int unbreakingLevel = getEnchantmentLevel(itemstack, Enchantment.unbreaking);
                int knockbacklevel = getEnchantmentLevel(itemstack, Enchantment.knockback);
                if (item == Items.fishing_rod) {
                    double scoreDurabilityRaw = (unbreakingLevel * 2 * Hytils.instance.getConfig().equipmentHelperRodDurabilityLevelFavor) *
                            Hytils.instance.getConfig().equipmentHelperRodDurabilityFavor * 0.5f;
                    double scoreKnockbackRaw = (knockbacklevel * 2 * Hytils.instance.getConfig().equipmentHelperRodKnockbackFavor);
                    durabilityScore = (scoreDurabilityRaw / maxDurabilityScore) * 50f;
                    knockbackScore = (scoreKnockbackRaw / maxKnockbackScore) * 100f;
                    double sumScore = 0;
                    double divisor = 0;
                    if (Hytils.instance.getConfig().equipmentHelperRodDurabilityCheck) {
                        sumScore += durabilityScore;
                        divisor += 0.5f;
                    }
                    if (Hytils.instance.getConfig().equipmentHelperRodKnockbackCheck) {
                        sumScore += knockbackScore;
                        divisor += 1f;
                    }
                    rodScoreMap.put(i, sumScore / divisor);
                }
            }
        }
    }
}
