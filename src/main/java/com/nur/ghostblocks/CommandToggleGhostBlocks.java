package com.nur.ghostblocks;

import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

import java.util.Collections;
import java.util.List;

public class CommandToggleGhostBlocks implements ICommand {
    @Override
    public String getCommandName() {
        return "toggleghostblocks";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "toggleghostblocks";
    }

    @Override
    public List<String> getCommandAliases() {
        return Collections.singletonList("ghostblockstoggle");
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        RenderHandler.enabled = !RenderHandler.enabled;
        sender.addChatMessage(new ChatComponentText("" + EnumChatFormatting.LIGHT_PURPLE + EnumChatFormatting.BOLD + "(!) " + EnumChatFormatting.LIGHT_PURPLE + "Ghost blocks rendering " + (RenderHandler.enabled ? EnumChatFormatting.GREEN + "enabled" : EnumChatFormatting.RED + "disabled") + EnumChatFormatting.LIGHT_PURPLE + "."));
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return true;
    }

    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
        return null;
    }

    @Override
    public boolean isUsernameIndex(String[] args, int index) {
        return false;
    }

    @Override
    public int compareTo(ICommand o) {
        return 0;
    }
}
