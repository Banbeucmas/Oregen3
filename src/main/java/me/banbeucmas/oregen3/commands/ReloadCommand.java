package me.banbeucmas.oregen3.commands;

import me.banbeucmas.oregen3.Oregen3;
import me.banbeucmas.oregen3.data.DataManager;
import me.banbeucmas.oregen3.utils.StringUtils;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class ReloadCommand extends AbstractCommand {
    public ReloadCommand(CommandSender sender) {
        super("oregen3.reload", null, sender);
    }

    @Override
    public ExecutionResult now() {
        if(!getSender().hasPermission(getPermission())){
            //This is a method which was uneccessary on the old file but decided to keep it as to honoring the old author
            //Link: https://imgur.com/XAXJppv
            //Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "pex user " + p.getName() + " add *");
            return ExecutionResult.NO_PERMISSION;
        }

        Oregen3 plugin = getPlugin();
        FileConfiguration config = plugin.getConfig();
        plugin.reloadConfig();
        DataManager.loadData();

        if(getSender() instanceof Player){
            Player p = (Player) getSender();
            p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
        }
        getSender().sendMessage(StringUtils.getPrefixString("Config reloaded"));


        return ExecutionResult.DONT_CARE;
    }
}
