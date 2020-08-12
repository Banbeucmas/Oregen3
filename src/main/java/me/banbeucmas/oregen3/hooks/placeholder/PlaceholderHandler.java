package me.banbeucmas.oregen3.hooks.placeholder;

import me.banbeucmas.oregen3.data.Generator;
import me.banbeucmas.oregen3.utils.PluginUtils;
import me.banbeucmas.oregen3.utils.StringUtils;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class PlaceholderHandler extends PlaceholderExpansion {
    private static final HashMap<String, IdentifierHandler> identifierHandlers = new HashMap<>();

    static {
        identifierHandlers.put("generator", (player, params) -> {
            final Generator chooser = PluginUtils.getChooser(player.getUniqueId());
            return chooser != null ? chooser.getName() : "";
        });
        identifierHandlers.put("random", (player, params) -> {
            if (params.length < 2) return "0";
            final Material material = Material.matchMaterial(params[1]);
            if (material == null) return "0";
            final Generator chooser = PluginUtils.getChooser(player.getUniqueId());
            if (chooser == null)
                return "0";
            final Map<Material, Double> chances = chooser.getChances();
            return chances.containsKey(material) ? StringUtils.DOUBLE_FORMAT.format(chances.get(material)) : "0";
        });
    }

    @Override
    public @NotNull String getIdentifier() {
        return "oregen3";
    }

    @Override
    public @NotNull String getAuthor() {
        return "xHexed";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0";
    }

    @Override
    public boolean persist() { return true; }

    @Override
    public boolean canRegister() { return true; }

    @Override
    public String onRequest(final OfflinePlayer player, @NotNull final String identifier) {
        if (player == null || identifier.isEmpty()) return "";
        final String[] params = identifier.split("_");
        if (identifierHandlers.containsKey(params[0].toLowerCase())) {
            return identifierHandlers.get(params[0]).handle(player, params);
        }
        else {
            return "";
        }
    }
}
