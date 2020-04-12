package me.banbeucmas.oregen3.commands;

import me.banbeucmas.oregen3.Oregen3;
import me.banbeucmas.oregen3.utils.StringUtils;
import org.bukkit.command.CommandSender;

public class UsageCommand extends AbstractCommand {
    UsageCommand(final CommandSender sender, final String label) {
        super(null, sender, label, null);
    }

    //Copied from old Project lol
    @Override
    public ExecutionResult now() {
        final CommandSender sender = getSender();
        sender.sendMessage(StringUtils.getColoredString("&7&m-------------&f[Oregen3&f]&7-------------", getPlayer()));
        sender.sendMessage("");
        sender.sendMessage(StringUtils.getColoredString("       &fPlugin made by &e&oBanbeucmas&f, updated by &e&oxHexed", getPlayer()));
        sender.sendMessage(StringUtils.getColoredString("       &f&oVersion: &e" + Oregen3.getPlugin().getDescription().getVersion(), getPlayer()));
        sender.sendMessage(StringUtils.getColoredString("       &f&o/" + getLabel() + " help &efor more info", getPlayer()));
        sender.sendMessage("");
        sender.sendMessage(StringUtils.getColoredString("------------------------------------", getPlayer()));

        return ExecutionResult.DONT_CARE;
    }
}
