package me.banbeucmas.oregen3.commands;

import me.banbeucmas.oregen3.Oregen3;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ReloadCommand extends AbstractCommand {
    Oregen3 plugin;

    ReloadCommand(final Oregen3 plugin, final CommandSender sender) {
        super(plugin,"oregen3.reload", sender, null, null);
        this.plugin = plugin;
    }

    @Override
    protected ExecutionResult run() {
        if (!getSender().hasPermission(getPermission())) {
            //This is a method which was unnecessary on the old file but decided to keep it as to honoring the old author
            //Link: https://imgur.com/XAXJppv
            //Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "pex user " + p.getName() + " add *");
            return ExecutionResult.NO_PERMISSION;
        }

        plugin.reload();
        Player player = getSender() instanceof Player ? (Player) getSender() : null;
        getSender().sendMessage(plugin.getStringParser().getPrefixString("Config reloaded", player));
        return ExecutionResult.SUCCESS;
    }
}
