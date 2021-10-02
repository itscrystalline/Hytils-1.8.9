package com.iwant2tryhard.hytils.commands;

import com.iwant2tryhard.hytils.Hytils;
import com.iwant2tryhard.hytils.core.Utils;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

import java.util.Collections;
import java.util.List;

public class HytilsConfigCommand extends CommandBase {
    @Override
    public String getCommandName() {
        return "hytilsconfig";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return null;
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        if (args.length > 0){
            if ("teammatehud".equalsIgnoreCase(args[0]) || "thud".equalsIgnoreCase(args[0])) {
                if (args.length > 1) {
                    if ("enabled".equalsIgnoreCase(args[1])) {
                        Hytils.instance.getConfig().teammateHUDEnabled = !Hytils.instance.getConfig().teammateHUDEnabled;
                        Utils.sendMessage(new ChatComponentText(EnumChatFormatting.GOLD + "[Hytils] " + EnumChatFormatting.GREEN + "Updated Teammate HUD Enabled to " + Hytils.instance.getConfig().teammateHUDEnabled));
                        Hytils.instance.getConfig().saveConfig();
                    } else if ("checkgame".equalsIgnoreCase(args[1]) || "cg".equalsIgnoreCase(args[1])) {
                        Hytils.instance.getConfig().teammateHUDCheckGame = !Hytils.instance.getConfig().teammateHUDCheckGame;
                        Utils.sendMessage(new ChatComponentText(EnumChatFormatting.GOLD + "[Hytils] " + EnumChatFormatting.GREEN + "Updated Teammate HUD Check Game to " + Hytils.instance.getConfig().teammateHUDCheckGame));
                        Hytils.instance.getConfig().saveConfig();
                    } else if ("healthbar".equalsIgnoreCase(args[1])) {
                        Hytils.instance.getConfig().teammateHUDHealthBar = !Hytils.instance.getConfig().teammateHUDHealthBar;
                        Utils.sendMessage(new ChatComponentText(EnumChatFormatting.GOLD + "[Hytils] " + EnumChatFormatting.GREEN + "Updated Teammate HUD Health bar to " + Hytils.instance.getConfig().teammateHUDHealthBar));
                        Hytils.instance.getConfig().saveConfig();
                    } else if ("hungerbar".equalsIgnoreCase(args[1])) {
                        Hytils.instance.getConfig().teammateHUDHungerBar = !Hytils.instance.getConfig().teammateHUDHungerBar;
                        Utils.sendMessage(new ChatComponentText(EnumChatFormatting.GOLD + "[Hytils] " + EnumChatFormatting.GREEN + "Updated Teammate HUD Hunger bar to " + Hytils.instance.getConfig().teammateHUDHungerBar));
                        Hytils.instance.getConfig().saveConfig();
                    } else if ("armorbar".equalsIgnoreCase(args[1])) {
                        Hytils.instance.getConfig().teammateHUDArmorBar = !Hytils.instance.getConfig().teammateHUDArmorBar;
                        Utils.sendMessage(new ChatComponentText(EnumChatFormatting.GOLD + "[Hytils] " + EnumChatFormatting.GREEN + "Updated Teammate HUD Armor bar to " + Hytils.instance.getConfig().teammateHUDArmorBar));
                        Hytils.instance.getConfig().saveConfig();
                    } else if ("airbar".equalsIgnoreCase(args[1])) {
                        Hytils.instance.getConfig().teammateHUDAirBar = !Hytils.instance.getConfig().teammateHUDAirBar;
                        Utils.sendMessage(new ChatComponentText(EnumChatFormatting.GOLD + "[Hytils] " + EnumChatFormatting.GREEN + "Updated Teammate HUD Air bar to " + Hytils.instance.getConfig().teammateHUDAirBar));
                        Hytils.instance.getConfig().saveConfig();
                    } else if ("showundetected".equalsIgnoreCase(args[1])) {
                        Hytils.instance.getConfig().teammateHUDShowUndetected = !Hytils.instance.getConfig().teammateHUDShowUndetected;
                        Utils.sendMessage(new ChatComponentText(EnumChatFormatting.GOLD + "[Hytils] " + EnumChatFormatting.GREEN + "Updated Teammate HUD Show Undetected to " + Hytils.instance.getConfig().teammateHUDShowUndetected));
                        Hytils.instance.getConfig().saveConfig();
                    } else if ("smst".equalsIgnoreCase(args[1]) | "smdt".equalsIgnoreCase(args[1]) | "setmaxshowteammates".equalsIgnoreCase(args[1]) | "setmaxdisplayteammates".equalsIgnoreCase(args[1])) {
                        if (args.length > 2) {
                            try {
                                int num = Integer.parseInt(args[2]);
                                if (num > 0) {
                                    Hytils.instance.getConfig().maxTeammateDisplay = num;
                                    Utils.sendMessage(new ChatComponentText(EnumChatFormatting.GOLD + "[Hytils] " + EnumChatFormatting.GREEN + "Set max display teammates to " + num));
                                    Hytils.instance.getConfig().saveConfig();
                                } else {
                                    Utils.sendMessage(new ChatComponentText(EnumChatFormatting.GOLD + "[Hytils] " + EnumChatFormatting.RED + "Number must be more than 0!"));
                                }
                            } catch (NumberFormatException e) {
                                Utils.sendMessage(new ChatComponentText(EnumChatFormatting.GOLD + "[Hytils] " + EnumChatFormatting.RED + "Invalid Number '" + args[2] + "'"));
                            }
                        }
                    } else {
                        Utils.sendMessage(new ChatComponentText(EnumChatFormatting.GOLD + "[Hytils] " + EnumChatFormatting.RED + "Invalid argument '" + args[1] + "'"));
                    }
                }
            } else if ("onscreenhud".equalsIgnoreCase(args[0]) || "oshud".equalsIgnoreCase(args[0])) {
                if (args.length > 1) {
                    if ("healthbar".equalsIgnoreCase(args[1])) {
                        Hytils.instance.getConfig().showCustomHealthBar = !Hytils.instance.getConfig().showCustomHealthBar;
                        Utils.sendMessage(new ChatComponentText(EnumChatFormatting.GOLD + "[Hytils] " + EnumChatFormatting.GREEN + "Updated On screen HUD's custom health bar to " + Hytils.instance.getConfig().showCustomHealthBar));
                        Hytils.instance.getConfig().saveConfig();
                    } else if ("armorbar".equalsIgnoreCase(args[1])) {
                        Hytils.instance.getConfig().showCustomArmorBar = !Hytils.instance.getConfig().showCustomArmorBar;
                        Utils.sendMessage(new ChatComponentText(EnumChatFormatting.GOLD + "[Hytils] " + EnumChatFormatting.GREEN + "Updated On screen HUD's custom armor bar to " + Hytils.instance.getConfig().showCustomArmorBar));
                        Hytils.instance.getConfig().saveConfig();
                    } else if ("airbar".equalsIgnoreCase(args[1])) {
                        Hytils.instance.getConfig().showCustomAirBar = !Hytils.instance.getConfig().showCustomAirBar;
                        Utils.sendMessage(new ChatComponentText(EnumChatFormatting.GOLD + "[Hytils] " + EnumChatFormatting.GREEN + "Updated On screen HUD's custom air bar to " + Hytils.instance.getConfig().showCustomAirBar));
                        Hytils.instance.getConfig().saveConfig();
                    } else if ("hungerbar".equalsIgnoreCase(args[1])) {
                        Hytils.instance.getConfig().showCustomHungerBar = !Hytils.instance.getConfig().showCustomHungerBar;
                        Utils.sendMessage(new ChatComponentText(EnumChatFormatting.GOLD + "[Hytils] " + EnumChatFormatting.GREEN + "Updated On screen HUD's custom hunger bar to " + Hytils.instance.getConfig().showCustomHungerBar));
                        Hytils.instance.getConfig().saveConfig();
                    } else {
                        Utils.sendMessage(new ChatComponentText(EnumChatFormatting.GOLD + "[Hytils] " + EnumChatFormatting.RED + "Invalid argument '" + args[1] + "'"));
                    }
                } else {
                    Utils.sendMessage(new ChatComponentText(EnumChatFormatting.GOLD + "[Hytils] " + EnumChatFormatting.RED + "Provide an argument!"));
                }
            }
        }else{
            Hytils.instance.setShowConfigScreen(true);
        }
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return true;
    }

    @Override
    public List<String> getCommandAliases() {
        return Collections.singletonList("hycfg");
    }

    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
        return super.addTabCompletionOptions(sender, args, pos);
    }
}
