package com.iwant2tryhard.hytils.commands;

import com.iwant2tryhard.hytils.Hytils;
import com.iwant2tryhard.hytils.core.Utils;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

import java.text.MessageFormat;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class HytilsDebugCommand extends CommandBase {
    @Override
    public String getCommandName() {
        return "hytilsdebug";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return null;
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        if (args.length > 0)
            if (args[0].equalsIgnoreCase("state")) {
                Utils.sendMessage(new ChatComponentText(EnumChatFormatting.GOLD + "[Hytils] " + EnumChatFormatting.GREEN + "Mod Global values:"));
                Utils.sendMessage(new ChatComponentText(EnumChatFormatting.GOLD + "[Hytils]     " + EnumChatFormatting.GREEN + "onHypixel: " + Hytils.instance.getUtils().isOnHypixel()));
                Utils.sendMessage(new ChatComponentText(EnumChatFormatting.GOLD + "[Hytils]     " + EnumChatFormatting.GREEN + "onHypixelLobby: " + Hytils.instance.getUtils().isInHypixelLobby()));
                Utils.sendMessage(new ChatComponentText(EnumChatFormatting.GOLD + "[Hytils]     " + EnumChatFormatting.GREEN + "currentGame: " + (Hytils.instance.getUtils()).currentGame));
                Utils.sendMessage(new ChatComponentText(EnumChatFormatting.GOLD + "[Hytils]     " + EnumChatFormatting.GREEN + "previousGame: " + (Hytils.instance.getUtils()).previousGame));
                Utils.sendMessage(new ChatComponentText(EnumChatFormatting.GOLD + "[Hytils]     " + EnumChatFormatting.GREEN + "prevPreviousGame: " + (Hytils.instance.getUtils()).prevPreviousGame));
                Utils.sendMessage(new ChatComponentText(EnumChatFormatting.GOLD + "[Hytils]     " + EnumChatFormatting.GREEN + "checkStep: " + (Hytils.instance.getUtils()).displayTimes));
                Utils.sendMessage(new ChatComponentText(EnumChatFormatting.GOLD + "[Hytils]     " + EnumChatFormatting.GREEN + "lastPlayerInteractedTimer: " + (Hytils.instance.getUtils()).lastPlayerInteractedTimer));
                Utils.sendMessage(new ChatComponentText(EnumChatFormatting.GOLD + "[Hytils]     " + EnumChatFormatting.GREEN + "lastPlayerInteracted: " + (Hytils.instance.getUtils()).lastPlayerNameInteracted));
            } else if (args[0].equalsIgnoreCase("tntstate")) {
                if (args.length > 1) {
                    if ("alltnt".equalsIgnoreCase(args[1])) {
                        Utils.sendMessage(new ChatComponentText(EnumChatFormatting.GOLD + "[Hytils] " + EnumChatFormatting.GREEN + "All TNT's: "));
                        for (EntityTNTPrimed tnt : Hytils.instance.getUtils().tntList) {
                            Utils.sendMessage(new ChatComponentText(EnumChatFormatting.GOLD + "[Hytils] " + EnumChatFormatting.GREEN + "    " + tnt + " at: (" + tnt.posX + "," + tnt.posY + "," + tnt.posZ + ")"));
                        }
                    } else if ("tntglow".equalsIgnoreCase(args[1])) {
                        Utils.sendMessage(new ChatComponentText(EnumChatFormatting.GOLD + "[Hytils] " + EnumChatFormatting.GREEN + "TNT Glow List: "));
                        for (int i = 0; i < Hytils.instance.getUtils().highlightList.size(); i++) {
                            Utils.sendMessage(new ChatComponentText(EnumChatFormatting.GOLD + "[Hytils] " + EnumChatFormatting.GREEN + "    index " + i + ": "));
                            for (Map.Entry<Integer, BlockPos> entry : Hytils.instance.getUtils().highlightList.get(i).entrySet()) {
                                Utils.sendMessage(new ChatComponentText(EnumChatFormatting.GOLD + "[Hytils] " + EnumChatFormatting.GREEN + "        distance: " + entry.getKey() + " pos: (" + entry.getValue().getX() + "," + entry.getValue().getY() + "," + entry.getValue().getZ() + ")"));
                            }
                        }
                    }
                }
            } else if (args[0].equalsIgnoreCase("checkteammates") | args[0].equalsIgnoreCase("ctm")) {
                if (Hytils.instance.mc.theWorld != null && Hytils.instance.mc.thePlayer != null) {
                    List<EntityPlayer> players = Hytils.instance.mc.theWorld.playerEntities;
                    Utils.sendMessage(new ChatComponentText(EnumChatFormatting.GOLD + "[Hytils] Player list:"));
                    for (EntityPlayer player : players) {
                        ItemStack helmet = player.getCurrentArmor(3);
                        ItemStack chestplate = player.getCurrentArmor(2);
                        ItemStack leggings = player.getCurrentArmor(1);
                        ItemStack boots = player.getCurrentArmor(0);
                        int helmetColor = 0;
                        int chestplateColor = 0;
                        int leggingsColor = 0;
                        int bootsColor = 0;
                        boolean shouldBeTeammate = false;
                        boolean scoreboardTeammate = false;
                        if (helmet != null && helmet.getItem() != null) {
                            if (helmet.getItem() instanceof ItemArmor) {
                                helmetColor = ((ItemArmor) helmet.getItem()).getColor(helmet);
                            }
                        }
                        if (chestplate != null && chestplate.getItem() != null) {
                            if (chestplate.getItem() instanceof ItemArmor) {
                                chestplateColor = ((ItemArmor) chestplate.getItem()).getColor(chestplate);
                            }
                        }
                        if (leggings != null && leggings.getItem() != null) {
                            if (leggings.getItem() instanceof ItemArmor) {
                                leggingsColor = ((ItemArmor) leggings.getItem()).getColor(leggings);
                            }
                        }
                        if (boots != null && boots.getItem() != null) {
                            if (boots.getItem() instanceof ItemArmor) {
                                bootsColor = ((ItemArmor) boots.getItem()).getColor(boots);
                            }
                        }
                        if (Hytils.instance.mc.thePlayer.getCurrentArmor(2) != null && Hytils.instance.mc.thePlayer.getCurrentArmor(2).getItem() != null) {
                            if (Hytils.instance.mc.thePlayer.getCurrentArmor(2).getItem() instanceof ItemArmor) {
                                int myColor = ((ItemArmor) Hytils.instance.mc.thePlayer.getCurrentArmor(2).getItem()).getColor(Hytils.instance.mc.thePlayer.getCurrentArmor(2));
                                shouldBeTeammate = chestplateColor == myColor;
                            }
                        }

                        scoreboardTeammate = Hytils.instance.mc.thePlayer.isOnSameTeam(player);
                        Utils.sendMessage(new ChatComponentText(EnumChatFormatting.GOLD + "[Hytils]     " +
                                EnumChatFormatting.GREEN + player.getDisplayNameString() + ": " +
                                EnumChatFormatting.RED + player.getHealth() + "/" + player.getMaxHealth() +
                                EnumChatFormatting.BLUE + " Helmet: " + helmet + " " + helmetColor +
                                EnumChatFormatting.BLUE + " Chestplate: " + chestplate + " " + chestplateColor +
                                EnumChatFormatting.BLUE + " Leggings: " + leggings + " " + leggingsColor +
                                EnumChatFormatting.BLUE + " Boots: " + boots + " " + bootsColor +
                                EnumChatFormatting.BLUE + " Should be teammate: " + shouldBeTeammate +
                                EnumChatFormatting.BLUE + " Scoreboard Teammates: " + scoreboardTeammate));
                    }
                }
            } else if (args[0].equalsIgnoreCase("viewteammates") | args[0].equalsIgnoreCase("vtm")) {
                StringBuilder text = new StringBuilder(EnumChatFormatting.GOLD + "[Hytils] " + EnumChatFormatting.AQUA + "Teammate list: ");
                for (String player : (Hytils.instance.getUtils()).teammates)
                    text.append(player).append(", ");
                Utils.sendMessage(new ChatComponentText(text.toString()));
            } else if (args[0].equalsIgnoreCase("checkitem")) {
                if (args.length > 1) {
                    if (args[1].equalsIgnoreCase("heads")) {
                        ConcurrentHashMap<Integer, Double> heads = new ConcurrentHashMap<>(Hytils.instance.getUtils().headScoreMap);
                        Utils.sendMessage(new ChatComponentText(EnumChatFormatting.GOLD + "[Hytils] " +
                                EnumChatFormatting.GREEN + "Head Score Map: "));
                        for (Map.Entry<Integer, Double> entry : heads.entrySet()) {
                            Utils.sendMessage(new ChatComponentText(EnumChatFormatting.GOLD + "[Hytils]     " + EnumChatFormatting.GREEN + MessageFormat.format("Item Location: {0}, Score: {1}", entry.getKey(), entry.getValue())));
                        }
                    } else if (args[1].equalsIgnoreCase("chests")) {
                        ConcurrentHashMap<Integer, Double> chests = new ConcurrentHashMap<>(Hytils.instance.getUtils().chestScoreMap);
                        Utils.sendMessage(new ChatComponentText(EnumChatFormatting.GOLD + "[Hytils] " +
                                EnumChatFormatting.GREEN + "Chest Score Map: "));
                        for (Map.Entry<Integer, Double> entry : chests.entrySet()) {
                            Utils.sendMessage(new ChatComponentText(EnumChatFormatting.GOLD + "[Hytils]     " + EnumChatFormatting.GREEN + MessageFormat.format("Item Location: {0}, Score: {1}", entry.getKey(), entry.getValue())));
                        }
                    } else if (args[1].equalsIgnoreCase("legs")) {
                        ConcurrentHashMap<Integer, Double> legs = new ConcurrentHashMap<>(Hytils.instance.getUtils().legsScoreMap);
                        Utils.sendMessage(new ChatComponentText(EnumChatFormatting.GOLD + "[Hytils] " +
                                EnumChatFormatting.GREEN + "Legs Score Map: "));
                        for (Map.Entry<Integer, Double> entry : legs.entrySet()) {
                            Utils.sendMessage(new ChatComponentText(EnumChatFormatting.GOLD + "[Hytils]     " + EnumChatFormatting.GREEN + MessageFormat.format("Item Location: {0}, Score: {1}", entry.getKey(), entry.getValue())));
                        }
                    } else if (args[1].equalsIgnoreCase("feet")) {
                        ConcurrentHashMap<Integer, Double> feet = new ConcurrentHashMap<>(Hytils.instance.getUtils().feetScoreMap);
                        Utils.sendMessage(new ChatComponentText(EnumChatFormatting.GOLD + "[Hytils] " +
                                EnumChatFormatting.GREEN + "Feet Score Map: "));
                        for (Map.Entry<Integer, Double> entry : feet.entrySet()) {
                            Utils.sendMessage(new ChatComponentText(EnumChatFormatting.GOLD + "[Hytils]     " + EnumChatFormatting.GREEN + MessageFormat.format("Item Location: {0}, Score: {1}", entry.getKey(), entry.getValue())));
                        }
                    } else if (args[1].equalsIgnoreCase("swords")) {
                        ConcurrentHashMap<Integer, Double> swords = new ConcurrentHashMap<>(Hytils.instance.getUtils().swordScoreMap);
                        Utils.sendMessage(new ChatComponentText(EnumChatFormatting.GOLD + "[Hytils] " +
                                EnumChatFormatting.GREEN + "Swords Score Map: "));
                        for (Map.Entry<Integer, Double> entry : swords.entrySet()) {
                            Utils.sendMessage(new ChatComponentText(EnumChatFormatting.GOLD + "[Hytils]     " + EnumChatFormatting.GREEN + MessageFormat.format("Item Location: {0}, Score: {1}", entry.getKey(), entry.getValue())));
                        }
                    } else if (args[1].equalsIgnoreCase("bows")) {
                        ConcurrentHashMap<Integer, Double> bows = new ConcurrentHashMap<>(Hytils.instance.getUtils().bowScoreMap);
                        Utils.sendMessage(new ChatComponentText(EnumChatFormatting.GOLD + "[Hytils] " +
                                EnumChatFormatting.GREEN + "Bows Score Map: "));
                        for (Map.Entry<Integer, Double> entry : bows.entrySet()) {
                            Utils.sendMessage(new ChatComponentText(EnumChatFormatting.GOLD + "[Hytils]     " + EnumChatFormatting.GREEN + MessageFormat.format("Item Location: {0}, Score: {1}", entry.getKey(), entry.getValue())));
                        }
                    } else if (args[1].equalsIgnoreCase("rods")) {
                        ConcurrentHashMap<Integer, Double> rods = new ConcurrentHashMap<>(Hytils.instance.getUtils().rodScoreMap);
                        Utils.sendMessage(new ChatComponentText(EnumChatFormatting.GOLD + "[Hytils] " +
                                EnumChatFormatting.GREEN + "Rods Score Map: "));
                        for (Map.Entry<Integer, Double> entry : rods.entrySet()) {
                            Utils.sendMessage(new ChatComponentText(EnumChatFormatting.GOLD + "[Hytils]     " + EnumChatFormatting.GREEN + MessageFormat.format("Item Location: {0}, Score: {1}", entry.getKey(), entry.getValue())));
                        }
                    }
                } else {
                    if (Hytils.instance.mc.theWorld != null && Hytils.instance.mc.thePlayer != null) {
                        getScoreMapForArmor(Hytils.instance.mc.thePlayer.getHeldItem());
                    }
                }
            } else {
                Utils.sendMessage(new ChatComponentText(EnumChatFormatting.GOLD + "[Hytils] " + EnumChatFormatting.RED + "Provide an argument!"));
            }
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return true;
    }

    @Override
    public List<String> getCommandAliases() {
        return Collections.singletonList("hydbg");
    }

    public void getScoreMapForArmor(ItemStack itemstack) {
        double maxResistanceScore = (4.0F * (Hytils.instance.getConfig()).equipmentHelperArmorResTierFavor + 8.0F * (Hytils.instance.getConfig()).equipmentHelperArmorResLevelFavor + 4.0F * (Hytils.instance.getConfig()).equipmentHelperArmorResLevelFavor) * (Hytils.instance.getConfig()).equipmentHelperArmorResFavor;
        double maxDurabilityScore = (6.0F * (Hytils.instance.getConfig()).equipmentHelperArmorDurabilityTierFavor + 6.0F * (Hytils.instance.getConfig()).equipmentHelperArmorDurabilityLevelFavor) * (Hytils.instance.getConfig()).equipmentHelperArmorDurabilityFavor * 0.75F;
        Item item = itemstack.getItem();
        double protectionScore = 0.0F;
        double durabilityScore = 0.0F;
        int protectionLevel = Utils.getEnchantmentLevel(itemstack, Enchantment.protection);
        int otherprotectionLevel = Utils.getEnchantmentLevel(itemstack, Enchantment.fireProtection) + Utils.getEnchantmentLevel(itemstack, Enchantment.blastProtection) + Utils.getEnchantmentLevel(itemstack, Enchantment.projectileProtection);
        int unbreakingLevel = Utils.getEnchantmentLevel(itemstack, Enchantment.unbreaking);
        if ((item == Items.leather_helmet) | (item == Items.leather_chestplate) | (item == Items.leather_leggings) | (item == Items.leather_boots)) {
            double scoreProtectionRaw = (1.0D * (Hytils.instance.getConfig()).equipmentHelperArmorResTierFavor + (protectionLevel * 2) * (Hytils.instance.getConfig()).equipmentHelperArmorResLevelFavor + otherprotectionLevel * (Hytils.instance.getConfig()).equipmentHelperArmorResLevelFavor) * (Hytils.instance.getConfig()).equipmentHelperArmorResFavor;
            double scoreDurabilityRaw = (2.0D * (Hytils.instance.getConfig()).equipmentHelperArmorDurabilityTierFavor + (unbreakingLevel * 2) * (Hytils.instance.getConfig()).equipmentHelperArmorDurabilityLevelFavor) * (Hytils.instance.getConfig()).equipmentHelperArmorDurabilityFavor * 0.5D;
            protectionScore = scoreProtectionRaw / maxResistanceScore * 100.0D;
            durabilityScore = scoreDurabilityRaw / maxDurabilityScore * 50.0D;
        } else if ((item == Items.golden_helmet) | (item == Items.golden_chestplate) | (item == Items.golden_leggings) | (item == Items.golden_boots)) {
            double scoreProtectionRaw = (1.0D * (Hytils.instance.getConfig()).equipmentHelperArmorResTierFavor + (protectionLevel * 2) * (Hytils.instance.getConfig()).equipmentHelperArmorResLevelFavor + otherprotectionLevel * (Hytils.instance.getConfig()).equipmentHelperArmorResLevelFavor) * (Hytils.instance.getConfig()).equipmentHelperArmorResFavor;
            double scoreDurabilityRaw = (1.0D * (Hytils.instance.getConfig()).equipmentHelperArmorDurabilityTierFavor + (unbreakingLevel * 2) * (Hytils.instance.getConfig()).equipmentHelperArmorDurabilityLevelFavor) * (Hytils.instance.getConfig()).equipmentHelperArmorDurabilityFavor * 0.5D;
            protectionScore = scoreProtectionRaw / maxResistanceScore * 100.0D;
            durabilityScore = scoreDurabilityRaw / maxDurabilityScore * 50.0D;
        } else if ((item == Items.chainmail_helmet) | (item == Items.chainmail_chestplate) | (item == Items.chainmail_leggings) | (item == Items.chainmail_boots)) {
            double scoreProtectionRaw = (2.0D * (Hytils.instance.getConfig()).equipmentHelperArmorResTierFavor + (protectionLevel * 2) * (Hytils.instance.getConfig()).equipmentHelperArmorResLevelFavor + otherprotectionLevel * (Hytils.instance.getConfig()).equipmentHelperArmorResLevelFavor) * (Hytils.instance.getConfig()).equipmentHelperArmorResFavor;
            double scoreDurabilityRaw = (3.0D * (Hytils.instance.getConfig()).equipmentHelperArmorDurabilityTierFavor + (unbreakingLevel * 2) * (Hytils.instance.getConfig()).equipmentHelperArmorDurabilityLevelFavor) * (Hytils.instance.getConfig()).equipmentHelperArmorDurabilityFavor * 0.5D;
            protectionScore = scoreProtectionRaw / maxResistanceScore * 100.0D;
            durabilityScore = scoreDurabilityRaw / maxDurabilityScore * 50.0D;
        } else if ((item == Items.iron_helmet) | (item == Items.iron_chestplate) | (item == Items.iron_leggings) | (item == Items.iron_boots)) {
            double scoreProtectionRaw = (2.0D * (Hytils.instance.getConfig()).equipmentHelperArmorResTierFavor + (protectionLevel * 2) * (Hytils.instance.getConfig()).equipmentHelperArmorResLevelFavor + otherprotectionLevel * (Hytils.instance.getConfig()).equipmentHelperArmorResLevelFavor) * (Hytils.instance.getConfig()).equipmentHelperArmorResFavor;
            double scoreDurabilityRaw = (3.0D * (Hytils.instance.getConfig()).equipmentHelperArmorDurabilityTierFavor + (unbreakingLevel * 2) * (Hytils.instance.getConfig()).equipmentHelperArmorDurabilityLevelFavor) * (Hytils.instance.getConfig()).equipmentHelperArmorDurabilityFavor * 0.5D;
            protectionScore = scoreProtectionRaw / maxResistanceScore * 100.0D;
            durabilityScore = scoreDurabilityRaw / maxDurabilityScore * 50.0D;
        } else if ((item == Items.diamond_helmet) | (item == Items.diamond_chestplate) | (item == Items.diamond_leggings) | (item == Items.diamond_boots)) {
            double scoreProtectionRaw = (3.0D * (Hytils.instance.getConfig()).equipmentHelperArmorResTierFavor + (protectionLevel * 2) * (Hytils.instance.getConfig()).equipmentHelperArmorResLevelFavor + otherprotectionLevel * (Hytils.instance.getConfig()).equipmentHelperArmorResLevelFavor) * (Hytils.instance.getConfig()).equipmentHelperArmorResFavor;
            double scoreDurabilityRaw = (4.0D * (Hytils.instance.getConfig()).equipmentHelperArmorDurabilityTierFavor + (unbreakingLevel * 2) * (Hytils.instance.getConfig()).equipmentHelperArmorDurabilityLevelFavor) * (Hytils.instance.getConfig()).equipmentHelperArmorDurabilityFavor * 0.5D;
            protectionScore = scoreProtectionRaw / maxResistanceScore * 100.0F;
            durabilityScore = scoreDurabilityRaw / maxDurabilityScore * 50.0F;
        }
        float sumScore = 0.0F;
        float divisor = 0.0F;
        if ((Hytils.instance.getConfig()).equipmentHelperArmorResCheck) {
            sumScore += protectionScore;
            divisor++;
        }
        if ((Hytils.instance.getConfig()).equipmentHelperArmorDurabilityCheck) {
            sumScore += durabilityScore;
            divisor += 0.5F;
        }
        Utils.sendMessage(new ChatComponentText(EnumChatFormatting.GOLD + "[Hytils] " + EnumChatFormatting.GREEN + MessageFormat.format("The item in hand ({0}) has a score of {1}", item.getRegistryName(), sumScore / divisor)));
    }
}
