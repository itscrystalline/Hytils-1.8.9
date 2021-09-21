package com.iwant2tryhard.hytils.commands;

import com.iwant2tryhard.hytils.Hytils;
import com.iwant2tryhard.hytils.core.Utils;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

import java.util.List;

public class PlayerCheckCommand extends CommandBase {

    @Override
    public String getCommandName() {
        return "checkTeammates";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return null;
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        if (Hytils.instance.mc.theWorld != null && Hytils.instance.mc.thePlayer != null){
            List<EntityPlayer> players = Hytils.instance.mc.theWorld.playerEntities;
            Utils.sendMessage(new ChatComponentText(EnumChatFormatting.GOLD + "[Hytils] Player list:"));
            for (EntityPlayer player: players) {
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
                if (helmet != null && helmet.getItem() != null){
                    if (helmet.getItem() instanceof ItemArmor){
                        helmetColor = ((ItemArmor) helmet.getItem()).getColor(helmet);
                    }
                }
                if (chestplate != null && chestplate.getItem() != null){
                    if (chestplate.getItem() instanceof ItemArmor){
                        chestplateColor = ((ItemArmor) chestplate.getItem()).getColor(chestplate);
                    }
                }
                if (leggings != null && leggings.getItem() != null){
                    if (leggings.getItem() instanceof ItemArmor){
                        leggingsColor = ((ItemArmor) leggings.getItem()).getColor(leggings);
                    }
                }
                if (boots != null && boots.getItem() != null){
                    if (boots.getItem() instanceof ItemArmor){
                        bootsColor = ((ItemArmor) boots.getItem()).getColor(boots);
                    }
                }
                if (Hytils.instance.mc.thePlayer.getCurrentArmor(2) != null && Hytils.instance.mc.thePlayer.getCurrentArmor(2).getItem() != null) {
                    if (Hytils.instance.mc.thePlayer.getCurrentArmor(2).getItem() instanceof ItemArmor){
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
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return true;
    }
}
