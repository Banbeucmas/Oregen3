package me.banbeucmas.oregen3.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class AdminCommands implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(args.length == 0){
            new UsageCommand(sender).execute();
            return true;
        }
        switch (args[0].toLowerCase()){
            case "reload":
                new ReloadCommand(sender).execute();
                break;
            case "help":
                new HelpCommand(sender).execute();
                break;
            case "info":
                new InformationCommand(sender).execute();
        }
        return true;
    }
}
