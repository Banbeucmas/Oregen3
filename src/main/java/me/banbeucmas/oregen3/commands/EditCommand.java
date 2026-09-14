package me.banbeucmas.oregen3.commands;

import me.banbeucmas.oregen3.Oregen3;
import me.banbeucmas.oregen3.gui.editor.EditorGUI;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class EditCommand extends AbstractCommand {
    EditCommand(Oregen3 plugin, final CommandSender sender, final String[] args) {
        super(plugin, "oregen3.check", sender, null, args);
    }

    @Override
    protected ExecutionResult run() {
        final CommandSender sender = getSender();

        if (!sender.hasPermission(getPermission())) {
            return ExecutionResult.NO_PERMISSION;
        }

        final String[] args = getArgs();
        final int length = args.length;

        if (length == 1) {
            if (!(sender instanceof Player)) {
                return ExecutionResult.NON_PLAYER;
            }

            ((Player) sender).openInventory(new EditorGUI(plugin).getInventory());
        } else if (length > 1) {
            //TODO: Edit generators using commands
            return ExecutionResult.SUCCESS;
        }

        return ExecutionResult.SUCCESS;
    }
}