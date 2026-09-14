package me.banbeucmas.oregen3.commands;

import me.banbeucmas.oregen3.Oregen3;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static me.banbeucmas.oregen3.util.StringParser.PLACEHOLDER_LABEL_PATTERN;

public class HelpCommand extends AbstractCommand {
    HelpCommand(Oregen3 plugin, final CommandSender sender, final String label) {
        super(plugin, "oregen3.help", sender, label, null);
    }

    @Override
    protected ExecutionResult run() {
        if (!(getSender() instanceof Player)) {
            return ExecutionResult.NON_PLAYER;
        }

        final Player sender = (Player) getSender();
        sender.sendMessage(plugin.getStringParser().getColoredPrefixString(PLACEHOLDER_LABEL_PATTERN.matcher(plugin.getConfig().getString("messages.commands.help", "")).replaceAll(getLabel()), sender));
        if (sender.hasPermission("oregen3.reload")) {
            sender.sendMessage(plugin.getStringParser().getColoredPrefixString(PLACEHOLDER_LABEL_PATTERN.matcher(plugin.getConfig().getString("messages.commands.reload", "")).replaceAll(getLabel()), sender));
        }
        if (sender.hasPermission("oregen3.information")) {
            sender.sendMessage(plugin.getStringParser().getColoredPrefixString(PLACEHOLDER_LABEL_PATTERN.matcher(plugin.getConfig().getString("messages.commands.info", "")).replaceAll(getLabel()), sender));
        }
        if (sender.hasPermission("oregen3.edit")) {
            sender.sendMessage(plugin.getStringParser().getColoredPrefixString(PLACEHOLDER_LABEL_PATTERN.matcher(plugin.getConfig().getString("messages.commands.edit", "")).replaceAll(getLabel()), sender));
        }
        return ExecutionResult.SUCCESS;
    }
}
