package me.banbeucmas.oregen3.commands;

import me.banbeucmas.oregen3.Oregen3;
import me.banbeucmas.oregen3.utils.StringUtils;
import org.bukkit.command.CommandSender;

public class DebugCommand extends AbstractCommand {
    DebugCommand(final CommandSender sender) {
        super("oregen3.debug", sender, null, null);
    }

    @Override
    public ExecutionResult now() {
        if (!getSender().hasPermission("oregen3.debug"))
            return ExecutionResult.NO_PERMISSION;

        Oregen3.DEBUG = !Oregen3.DEBUG;
        getSender().sendMessage(StringUtils.getPrefixString("&eToggled Debug to " + Oregen3.DEBUG, getPlayer()));
        return ExecutionResult.DONT_CARE;
    }
}
