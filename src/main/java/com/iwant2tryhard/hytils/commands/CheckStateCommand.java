package com.iwant2tryhard.hytils.commands;

import com.iwant2tryhard.hytils.Hytils;
import com.iwant2tryhard.hytils.core.Utils;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class CheckStateCommand extends CommandBase {
    @Override
    public String getCommandName() {
        return "hytilsstate";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return null;
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        Utils.sendMessage(new ChatComponentText(EnumChatFormatting.GOLD + "[Hytils] " + EnumChatFormatting.GREEN + "Mod Global values:"));
        Utils.sendMessage(new ChatComponentText(EnumChatFormatting.GOLD + "[Hytils]     " + EnumChatFormatting.GREEN + "onHypixel: " + Hytils.instance.getUtils().isOnHypixel()));
        Utils.sendMessage(new ChatComponentText(EnumChatFormatting.GOLD + "[Hytils]     " + EnumChatFormatting.GREEN + "onHypixelLobby: " + Hytils.instance.getUtils().isInHypixelLobby()));
        Utils.sendMessage(new ChatComponentText(EnumChatFormatting.GOLD + "[Hytils]     " + EnumChatFormatting.GREEN + "currentGame: " + Hytils.instance.getUtils().currentGame));
        Utils.sendMessage(new ChatComponentText(EnumChatFormatting.GOLD + "[Hytils]     " + EnumChatFormatting.GREEN + "previousGame: " + Hytils.instance.getUtils().previousGame));
        Utils.sendMessage(new ChatComponentText(EnumChatFormatting.GOLD + "[Hytils]     " + EnumChatFormatting.GREEN + "prevPreviousGame: " + Hytils.instance.getUtils().prevPreviousGame));
        Utils.sendMessage(new ChatComponentText(EnumChatFormatting.GOLD + "[Hytils]     " + EnumChatFormatting.GREEN + "checkStep: " + Hytils.instance.getUtils().displayTimes));
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return true;
    }

    @Override
    public List<String> getCommandAliases() {
        return Collections.singletonList("hystat");
    }
}
