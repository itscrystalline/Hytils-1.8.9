package com.iwant2tryhard.hytils.commands;

import com.iwant2tryhard.hytils.Hytils;
import com.iwant2tryhard.hytils.core.GameTypes;
import com.iwant2tryhard.hytils.core.Utils;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

import java.util.Collections;
import java.util.List;

public class ViewTeammatesCommand extends CommandBase {
    @Override
    public String getCommandName() {
        return "viewteammates";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return null;
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        StringBuilder text = new StringBuilder(EnumChatFormatting.GOLD + "[Hytils] " + EnumChatFormatting.AQUA + "Teammate list: ");
        for (String player: Hytils.instance.getUtils().teammates) {
            text.append(player).append(", ");
        }
        Utils.sendMessage(new ChatComponentText(text.toString()));
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return true;
    }

    @Override
    public List<String> getCommandAliases() {
        return Collections.singletonList("vtm");
    }
}
