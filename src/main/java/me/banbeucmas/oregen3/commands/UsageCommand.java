package me.banbeucmas.oregen3.commands;

import me.banbeucmas.oregen3.Oregen3;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class UsageCommand extends AbstractCommand {
    UsageCommand(Oregen3 plugin, final CommandSender sender, final String label) {
        super(plugin, null, sender, label, null);
    }

    @Override
    protected ExecutionResult run() {
        Player player = sender instanceof Player ? (Player) sender : null;
        final CommandSender sender = getSender();
        sender.sendMessage(plugin.getStringUtils().getString("§7§m-------------§f[Oregen3§f]§7-------------", player));
        sender.sendMessage("");
        sender.sendMessage(plugin.getStringUtils().getString("       §fPlugin made by §e§oBanbeucmas§f, updated by §e§oxHexed", player));
        sender.sendMessage(plugin.getStringUtils().getString("       §f§oVersion: §e" + plugin.getDescription().getVersion(), player));
        sender.sendMessage(plugin.getStringUtils().getString("       §f§o/" + getLabel() + " help §efor more info", player));
        sender.sendMessage("");
        sender.sendMessage(plugin.getStringUtils().getString("------------------------------------", player));
        return ExecutionResult.SUCCESS;
    }
}
