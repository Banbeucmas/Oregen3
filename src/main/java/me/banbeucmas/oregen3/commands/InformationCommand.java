package me.banbeucmas.oregen3.commands;

import me.banbeucmas.oregen3.Oregen3;
import me.banbeucmas.oregen3.gui.GeneratorMaterialList;
import me.banbeucmas.oregen3.utils.PluginUtils;
import me.banbeucmas.oregen3.utils.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.UUID;
import java.util.regex.Matcher;

import static me.banbeucmas.oregen3.utils.StringUtils.PLAYER;

public class InformationCommand extends AbstractCommand {
    InformationCommand(final CommandSender sender, final String label, final String[] args) {
        super("oregen3.information", sender, label, args);
    }

    @Override
    protected ExecutionResult run() {
        final CommandSender sender = getSender();
        if (!(sender instanceof Player)) {
            return ExecutionResult.NON_PLAYER;
        }

        if (!sender.hasPermission(getPermission())) {
            return ExecutionResult.NO_PERMISSION;
        }

        final Player p = getPlayer();
        final String[] args = getArgs();
        final FileConfiguration config = Oregen3.getPlugin().getConfig();

        if (args.length > 1) {
            @SuppressWarnings("deprecation") final OfflinePlayer player = Bukkit.getOfflinePlayer(args[1]);
            if (!player.hasPlayedBefore()) {
                return ExecutionResult.NO_PLAYER;
            }
            final UUID uuid = player.getUniqueId();

            if (!Oregen3.getPlugin().hasDependency() || PluginUtils.getOwner(uuid) == null) {
                sender.sendMessage(PLAYER.matcher(StringUtils.getColoredPrefixString(config.getString("messages.noIslandOthers"), getPlayer())).replaceAll(Matcher.quoteReplacement(player.getName())));
                return ExecutionResult.SUCCESS;
            }

            p.openInventory(new GeneratorMaterialList(uuid, p).getInventory());
            return ExecutionResult.SUCCESS;
        }

        if (!Oregen3.getPlugin().hasDependency() || PluginUtils.getOwner(p.getLocation()) == null) {
            p.sendMessage(StringUtils.getColoredPrefixString(config.getString("messages.noIsland"), getPlayer()));
            return ExecutionResult.SUCCESS;
        }
        p.openInventory(new GeneratorMaterialList(p.getLocation(), p).getInventory());

        return ExecutionResult.SUCCESS;
    }
}
