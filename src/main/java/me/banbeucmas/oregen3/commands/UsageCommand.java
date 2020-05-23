package me.banbeucmas.oregen3.commands;

import me.banbeucmas.oregen3.Oregen3;
import me.banbeucmas.oregen3.utils.StringUtils;
import org.bukkit.command.CommandSender;

public class UsageCommand extends AbstractCommand {
    UsageCommand(final CommandSender sender, final String label) {
        super(null, sender, label, null);
    }

    @Override
    protected ExecutionResult run() {
        final CommandSender sender = getSender();
        sender.sendMessage(StringUtils.getString("§7§m-------------§f[Oregen3§f]§7-------------", getPlayer()));
        sender.sendMessage("");
        sender.sendMessage(StringUtils.getString("       §fPlugin made by §e§oBanbeucmas§f, updated by §e§oxHexed", getPlayer()));
        sender.sendMessage(StringUtils.getString("       §f§oVersion: §e" + Oregen3.getPlugin().getDescription().getVersion(), getPlayer()));
        sender.sendMessage(StringUtils.getString("       §f§o/" + getLabel() + " help §efor more info", getPlayer()));
        sender.sendMessage("");
        sender.sendMessage(StringUtils.getString("------------------------------------", getPlayer()));
        return ExecutionResult.SUCCESS;
    }
}
