package com.iwant2tryhard.hytils.commands;

import com.iwant2tryhard.hytils.Hytils;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;

public class GamesMenuCommand extends CommandBase {

    @Override
    public String getCommandName() {
        return "hygames";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return null;
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        Hytils.instance.setShowGamesMenuScreen(true);
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return true;
    }
}
