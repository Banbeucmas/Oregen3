package me.banbeucmas.oregen3.commands;

import me.banbeucmas.oregen3.utils.StringUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HelpCommand extends AbstractCommand {
    HelpCommand(final CommandSender sender) {
        super("oregen3.help", sender);
    }

    @Override
    public ExecutionResult now() {
        if (!(getSender() instanceof Player)) {
            return ExecutionResult.NO_PERMISSION;
        }
        sendHelp(getSender());
        return ExecutionResult.DONT_CARE;
    }

    private void sendHelp(final CommandSender sender) {
        sender.sendMessage(StringUtils.getPrefixString("&6&o/og3 help &f» Open help pages"));
        if (sender.hasPermission("oregen3.reload")) {
            sender.sendMessage(StringUtils.getPrefixString("&6&o/og3 reload &f» Reload config"));
        }
        if (sender.hasPermission("oregen3.information")) {
            sender.sendMessage(StringUtils.getPrefixString("&6&o/og3 info &f» Getting ore spawning chance of the island you are standing"));
        }
        if (sender.hasPermission("oregen3.debug")) {
            sender.sendMessage(StringUtils.getPrefixString("&6&o/og3 debug &f» Enable debugging"));
        }
    }
}
