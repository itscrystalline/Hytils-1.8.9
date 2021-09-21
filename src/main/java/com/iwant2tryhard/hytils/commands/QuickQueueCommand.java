package com.iwant2tryhard.hytils.commands;

import com.iwant2tryhard.hytils.Hytils;
import com.iwant2tryhard.hytils.core.GameTypes;
import com.iwant2tryhard.hytils.core.Utils;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

import java.util.Collections;
import java.util.List;

public class QuickQueueCommand extends CommandBase {
    @Override
    public String getCommandName() {
        return "quickqueue";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/quickqueue <GameType> or /qq <GameType>";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        if (args.length > 0){
            String arg = args[0].toLowerCase();
            boolean isEqual = false;
            for (GameTypes gameType: GameTypes.values()) {
                if (arg.toLowerCase().equals(gameType.shortCommand)){
                    piecePlayCommand(gameType);
                    isEqual = true;
                }
            }
            if (!isEqual){
                Utils.sendMessage(new ChatComponentText(EnumChatFormatting.GOLD + "[Hytils] " + EnumChatFormatting.DARK_RED + "Cannot find Game Type '" + arg + "'"));
            }
        }else{
            //open quickqueue gui
        }
    }

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
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return true;
    }

    @Override
    public List<String> getCommandAliases() {
        return Collections.singletonList("qq");
    }
}
