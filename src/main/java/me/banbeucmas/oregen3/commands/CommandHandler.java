package me.banbeucmas.oregen3.commands;

import me.banbeucmas.oregen3.Oregen3;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CommandHandler implements CommandExecutor, TabCompleter {
    private Oregen3 plugin;

    public CommandHandler(Oregen3 plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
        if (args.length == 0) {
            new UsageCommand(plugin, sender, label).execute();
            return true;
        }
        switch (args[0].toLowerCase()) {
            case "reload":
                new ReloadCommand(plugin, sender).execute();
                break;
            case "help":
                new HelpCommand(plugin, sender, label).execute();
                break;
            case "info":
                new InformationCommand(plugin, sender, label, args).execute();
                break;
            case "edit":
                new EditCommand(plugin, sender, args).execute();
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, final String[] args) {
        if (args.length > 1) {
            return null;
        }
        final List<String> completions = new ArrayList<>();
        StringUtil.copyPartialMatches(args[0], Arrays.asList("reload", "help", "info", "edit"), completions);
        Collections.sort(completions);
        return completions;
    }
}
