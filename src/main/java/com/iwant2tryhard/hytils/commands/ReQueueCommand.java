package com.iwant2tryhard.hytils.commands;

import com.iwant2tryhard.hytils.Hytils;
import com.iwant2tryhard.hytils.core.GameTypes;
import com.iwant2tryhard.hytils.core.Utils;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

import java.util.Collections;
import java.util.List;

public class ReQueueCommand extends CommandBase {
    @Override
    public String getCommandName() {
        return "requeue";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/rq or /requeue";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        if (Hytils.instance.getUtils().isOnHypixel()){
            if (!(Hytils.instance.getUtils().previousGame == GameTypes.NOT_IN_GAME)) {
                Utils.sendMessage(new ChatComponentText(EnumChatFormatting.GOLD + "[Hytils] " + EnumChatFormatting.GREEN + "Attempting to requeue into " + Hytils.instance.getUtils().previousGame.userFriendlyName + " (" + Hytils.instance.getUtils().previousGame + ")."));
                Hytils.instance.mc.thePlayer.sendChatMessage("/play " + Hytils.instance.getUtils().previousGame.command);
            }else{
                Utils.sendMessage(new ChatComponentText(EnumChatFormatting.GOLD + "[Hytils] " + EnumChatFormatting.RED + "Cannot find game to requeue into."));
            }
        }else{
            Utils.sendMessage(new ChatComponentText(EnumChatFormatting.GOLD + "[Hytils] " + EnumChatFormatting.RED + "You are not on Hypixel! Join Hypixel to use this command."));
        }
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return true;
    }

    @Override
    public List<String> getCommandAliases() {
        return Collections.singletonList("rq");
    }
}
