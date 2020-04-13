package me.banbeucmas.oregen3.commands;

import me.banbeucmas.oregen3.gui.EditGUI;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class EditCommand extends AbstractCommand {
    EditCommand(final CommandSender sender, final String[] args) {
        super("oregen3.check", sender, null, args);
    }

    @Override
    protected ExecutionResult now() {
        final CommandSender sender = getSender();
        if (!(sender instanceof Player)) {
            return ExecutionResult.NOT_PLAYER;
        }

        if (!sender.hasPermission(getPermission())) {
            return ExecutionResult.NO_PERMISSION;
        }

        final Player p = getPlayer();
        final String[] args = getArgs();
        final int length = args.length;

        if (length == 1) {
            p.openInventory(new EditGUI(p).getInventory());
        } else if (length > 1) {
            //TODO: Edit the generator using commands
            return ExecutionResult.DONT_CARE;
        }

        return ExecutionResult.DONT_CARE;
    }
}
