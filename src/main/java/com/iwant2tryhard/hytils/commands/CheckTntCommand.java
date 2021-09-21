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

public class CheckTntCommand extends CommandBase {
    @Override
    public String getCommandName() {
        return "checktnt";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return null;
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        if (args.length > 0){
            if ("alltnt".equalsIgnoreCase(args[0])) {
                Utils.sendMessage(new ChatComponentText(EnumChatFormatting.GOLD + "[Hytils] " + EnumChatFormatting.GREEN + "All TNT's: "));
                for (EntityTNTPrimed tnt : Hytils.instance.getUtils().tntList) {
                    Utils.sendMessage(new ChatComponentText(EnumChatFormatting.GOLD + "[Hytils] " + EnumChatFormatting.GREEN + "    " + tnt + " at: (" + tnt.posX + "," + tnt.posY + "," + tnt.posZ + ")"));
                }
            } else if ("tntglow".equalsIgnoreCase(args[0])) {
                Utils.sendMessage(new ChatComponentText(EnumChatFormatting.GOLD + "[Hytils] " + EnumChatFormatting.GREEN + "TNT Glow List: "));
                for (int i = 0; i < Hytils.instance.getUtils().highlightList.size(); i++) {
                    Utils.sendMessage(new ChatComponentText(EnumChatFormatting.GOLD + "[Hytils] " + EnumChatFormatting.GREEN + "    index " + i + ": "));
                    for (Map.Entry<Integer, BlockPos> entry : Hytils.instance.getUtils().highlightList.get(i).entrySet()) {
                        Utils.sendMessage(new ChatComponentText(EnumChatFormatting.GOLD + "[Hytils] " + EnumChatFormatting.GREEN + "        distance: " + entry.getKey() + " pos: (" + entry.getValue().getX() + "," + entry.getValue().getY() + "," + entry.getValue().getZ() + ")"));
                    }
                }
            }
        }
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return true;
    }

    @Override
    public List<String> getCommandAliases() {
        return Collections.singletonList("ctnt");
    }
}
