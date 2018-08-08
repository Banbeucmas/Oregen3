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
        sender.sendMessage(StringUtils.getColoredString("&7&m-------------&f[Oregen3&f]&7-------------"));
        sender.sendMessage("");
        sender.sendMessage(StringUtils.getColoredString("       &fPlugin made by &e&oBanbeucmas"));
        sender.sendMessage(StringUtils.getColoredString("       &f&o/help &efor more info"));
        sender.sendMessage("");
        sender.sendMessage(StringUtils.getColoredString("------------------------------------"));

        return ExecutionResult.DONT_CARE;
    }
}
