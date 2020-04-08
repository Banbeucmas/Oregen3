package me.banbeucmas.oregen3.commands;

import me.banbeucmas.oregen3.utils.StringUtils;
import org.bukkit.command.CommandSender;

public class UsageCommand extends AbstractCommand {
    UsageCommand(final CommandSender sender, final String label) {
        super(null, sender, label);
    }

    //Copied from old Project lol
    @Override
    public ExecutionResult now() {
        final CommandSender sender = getSender();
        sender.sendMessage(StringUtils.getColoredString("&7&m-------------&f[Oregen3&f]&7-------------", null));
        sender.sendMessage("");
        sender.sendMessage(StringUtils.getColoredString("       &fPlugin made by &e&oBanbeucmas, updated by xHexed", null));
        sender.sendMessage(StringUtils.getColoredString("       &f&o/" + getLabel() + " help &efor more info", null));
        sender.sendMessage("");
        sender.sendMessage(StringUtils.getColoredString("------------------------------------", null));

        return ExecutionResult.DONT_CARE;
    }
}
