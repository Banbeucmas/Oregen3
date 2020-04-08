package me.banbeucmas.oregen3.commands;

import me.banbeucmas.oregen3.utils.StringUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HelpCommand extends AbstractCommand {
    HelpCommand(final CommandSender sender, final String label) {
        super("oregen3.help", sender, label);
    }

    @Override
    public ExecutionResult now() {
        if (!(getSender() instanceof Player)) {
            return ExecutionResult.NO_PERMISSION;
        }
        sendHelp(getSender(), getLabel());
        return ExecutionResult.DONT_CARE;
    }

    private void sendHelp(final CommandSender sender, final String label) {
        sender.sendMessage(StringUtils.getPrefixString("&6&o/" + label + " help &f» Open help pages", getPlayer()));
        if (sender.hasPermission("oregen3.reload")) {
            sender.sendMessage(StringUtils.getPrefixString("&6&o/" + label + " reload &f» Reload config", getPlayer()));
        }
        if (sender.hasPermission("oregen3.information")) {
            sender.sendMessage(StringUtils.getPrefixString("&6&o/" + label + " info &f» Getting ore spawning chance of the island you are standing", getPlayer()));
        }
        if (sender.hasPermission("oregen3.debug")) {
            sender.sendMessage(StringUtils.getPrefixString("&6&o/" + label + " debug &f» Enable debugging", getPlayer()));
        }
    }
}
