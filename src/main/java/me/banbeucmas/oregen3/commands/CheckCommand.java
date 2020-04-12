package me.banbeucmas.oregen3.commands;

import me.banbeucmas.oregen3.data.permission.PermissionManager;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;

public class CheckCommand extends AbstractCommand {
    CheckCommand(final CommandSender sender, final String[] args) {
        super("oregen3.check", sender, null, args);
    }

    @Override
    protected ExecutionResult now() {
        if (!getSender().hasPermission(getPermission())) {
            return ExecutionResult.NO_PERMISSION;
        }

        final String[] args = getArgs();
        if (args.length > 1) {
            @SuppressWarnings("deprecation") final OfflinePlayer player = Bukkit.getOfflinePlayer(args[1]);
            if (!player.hasPlayedBefore()) {
                return ExecutionResult.NO_PLAYER;
            }
            PermissionManager.checkPerms(player);
        }
        return ExecutionResult.DONT_CARE;
    }
}
