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

public class QueuePreviousCommand extends CommandBase {

    private void piecePlayCommand(GameTypes gameType){
        if (Hytils.instance.getUtils().isOnHypixel()){
            Hytils.instance.getUtils().prevPreviousGame = Hytils.instance.getUtils().previousGame;
            Hytils.instance.getUtils().previousGame = gameType;
            Hytils.instance.getUtils().currentGame = gameType;
            Utils.sendMessage(new ChatComponentText(EnumChatFormatting.GOLD + "[Hytils] " + EnumChatFormatting.GREEN + "Attempting to queue into " + gameType.userFriendlyName + " (" + gameType + ")"));
            Hytils.instance.mc.thePlayer.sendChatMessage("/play " + gameType.command);
        }else{
            Utils.sendMessage(new ChatComponentText(EnumChatFormatting.GOLD + "[Hytils] " + EnumChatFormatting.RED + "You are not on Hypixel! Join Hypixel to use this command."));
        }
    }

    @Override
    public String getCommandName() {
        return "previousqueue";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return null;
    }

    @Override
    public List<String> getCommandAliases() {
        return Collections.singletonList("pq");
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        if (Hytils.instance.getUtils().isOnHypixel()){
            if (!(Hytils.instance.getUtils().prevPreviousGame == GameTypes.NOT_IN_GAME)) {
                Utils.sendMessage(new ChatComponentText(EnumChatFormatting.GOLD + "[Hytils] " + EnumChatFormatting.GREEN + "Attempting to requeue into " + Hytils.instance.getUtils().prevPreviousGame.userFriendlyName + " (" + Hytils.instance.getUtils().prevPreviousGame + ")."));
                piecePlayCommand(Hytils.instance.getUtils().prevPreviousGame);
            }else{
                Utils.sendMessage(new ChatComponentText(EnumChatFormatting.GOLD + "[Hytils] " + EnumChatFormatting.RED + "Cannot find game to requeue into."));
            }
        }else{
            Utils.sendMessage(new ChatComponentText(EnumChatFormatting.GOLD + "[Hytils] " + EnumChatFormatting.RED + "You are not on Hypixel! Join Hypixel to use this command."));
        }
    }
}
