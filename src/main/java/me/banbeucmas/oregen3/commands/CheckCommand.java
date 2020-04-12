package me.banbeucmas.oregen3.commands;

import me.banbeucmas.oregen3.Oregen3;
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
            Oregen3.getPermissionManager().checkPerms(player);
        }
        return ExecutionResult.DONT_CARE;
    }
}
