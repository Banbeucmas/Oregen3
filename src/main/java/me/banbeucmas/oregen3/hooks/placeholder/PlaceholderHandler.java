package me.banbeucmas.oregen3.hooks.placeholder;

import me.banbeucmas.oregen3.data.Generator;
import me.banbeucmas.oregen3.utils.PluginUtils;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class PlaceholderHandler extends PlaceholderExpansion {
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
        switch (params[0].toLowerCase()) {
            case "generator": {
                final Generator chooser = PluginUtils.getChooser(player.getUniqueId());
                return chooser != null ? chooser.getName() : "";
            }
            case "random": {
                if (params.length < 2) return "0";
                final Material material = Material.matchMaterial(params[1]);
                if (material == null) return "0";
                final Generator chooser = PluginUtils.getChooser(player.getUniqueId());
                if (chooser == null)
                    return "0";
                final Map<Material, Double> chances = chooser.getChances();
                return chances.containsKey(material) ? String.valueOf(chances.get(material)) : "0";
            }
            default:
                return "";
        }
    }
}
