package me.banbeucmas.oregen3.commands;

import me.banbeucmas.oregen3.utils.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class UsageCommand extends AbstractCommand {
    public UsageCommand(CommandSender sender) {
        super(null, null, sender);
    }

    //Copied from old Project lol
    @Override
    public ExecutionResult now() {
        CommandSender sender = getSender();
        sender.sendMessage(ChatColor.GRAY + "" + ChatColor.STRIKETHROUGH + "-------------" + ChatColor.WHITE + "[" + ChatColor.YELLOW + "Oregen3" + ChatColor.WHITE + "]" + ChatColor.GRAY + "" + ChatColor.STRIKETHROUGH + "-------------");
        sender.sendMessage("");
        sender.sendMessage("       " +ChatColor.WHITE+ "" + ChatColor.ITALIC + "Plugin made by " + ChatColor.YELLOW + "" + ChatColor.ITALIC + "Banbeucmas");
        sender.sendMessage("       " +ChatColor.WHITE+ "" + ChatColor.ITALIC + "/help" + ChatColor.YELLOW + " for more info");
        sender.sendMessage("");
        sender.sendMessage(ChatColor.GRAY + "" + ChatColor.STRIKETHROUGH + "------------------------------------");

        return ExecutionResult.DONT_CARE;
    }
}
