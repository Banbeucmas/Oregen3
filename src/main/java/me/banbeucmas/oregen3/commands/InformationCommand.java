package me.banbeucmas.oregen3.commands;

import me.banbeucmas.oregen3.Oregen3;
import me.banbeucmas.oregen3.gui.OreListGUI;
import me.banbeucmas.oregen3.utils.PluginUtils;
import me.banbeucmas.oregen3.utils.StringUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;

public class InformationCommand extends AbstractCommand {
    InformationCommand(final CommandSender sender) {
        super("oregen3.information", sender);
    }

    @Override
    public ExecutionResult now() {
        if (!(getSender() instanceof Player)) {
            return ExecutionResult.NOT_PLAYER;
        }
        if (!getSender().hasPermission(getPermission())) {
            return ExecutionResult.NO_PERMISSION;
        }
        final HumanEntity p = (Player) getSender();
        if((!Oregen3.getHook().isOnIsland(p.getLocation())
                || PluginUtils.getOwner(p.getLocation()) == null)
                && Oregen3.getPlugin().getConfig().getBoolean("enableDependency")){
            p.sendMessage(StringUtils.getPrefixString("&cYou has to be on an island to view this"));
            return ExecutionResult.DONT_CARE;
        }

        final OreListGUI gui = new OreListGUI(p.getLocation());
        p.openInventory(gui.getInventory());

        return ExecutionResult.DONT_CARE;
    }
}
