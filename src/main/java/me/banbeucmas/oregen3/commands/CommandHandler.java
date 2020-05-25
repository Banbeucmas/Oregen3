package me.banbeucmas.oregen3.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CommandHandler implements CommandExecutor, TabCompleter {
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
            case "edit":
                new EditCommand(sender, args).execute();
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(final CommandSender sender, final Command command, final String alias, final String[] args) {
        if (args.length > 1) {
            return null;
        }
        final List<String> completions = new ArrayList<>();
        StringUtil.copyPartialMatches(args[0], Arrays.asList("reload", "help", "info", "edit"), completions);
        Collections.sort(completions);
        return completions;
    }
}
