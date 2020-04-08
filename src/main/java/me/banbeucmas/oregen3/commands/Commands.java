package me.banbeucmas.oregen3.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.List;

public class Commands implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        if (args.length == 0) {
            new UsageCommand(sender, label).execute();
            return true;
        }
        switch (args[0].toLowerCase()) {
            case "reload":
                new ReloadCommand(sender).execute();
                break;
            case "help":
                new HelpCommand(sender, label).execute();
                break;
            case "info":
                new InformationCommand(sender, label, args).execute();
                break;
            case "debug":
                new DebugCommand(sender).execute();
                break;
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(final CommandSender sender, final Command command, final String alias, final String[] args) {
        return null;
    }
}
