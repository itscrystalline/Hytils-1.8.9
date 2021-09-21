package com.iwant2tryhard.hytils.commands;

import com.iwant2tryhard.hytils.Hytils;
import com.iwant2tryhard.hytils.core.Utils;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

import java.util.Collections;
import java.util.List;

public class ClearTeammatesCommand extends CommandBase {
    @Override
    public String getCommandName() {
        return "clearteammates";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return null;
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        Hytils.instance.getUtils().teammates.clear();
        Hytils.instance.getUtils().displayTeammates.clear();
        Hytils.instance.getUtils().prevInitialAbsorption.clear();
        Utils.sendMessage(new ChatComponentText(EnumChatFormatting.GOLD + "[Hytils] " + EnumChatFormatting.GREEN + "Cleared teammates list."));
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return true;
    }

    @Override
    public List<String> getCommandAliases() {
        return Collections.singletonList("ctm");
    }
}
