package me.banbeucmas.oregen3.commands;

import me.banbeucmas.oregen3.Oregen3;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.regex.Matcher;

import static me.banbeucmas.oregen3.util.StringParser.*;

abstract class AbstractCommand {
	Oregen3 plugin;
	String permission;
	CommandSender sender;
	String label;
	String[] args;

	AbstractCommand(final Oregen3 plugin, final String permission, final CommandSender sender, final String label, final String[] args) {
		this.plugin = plugin;
		this.permission = permission;
		this.label      = label;
		this.args       = args;
		this.sender     = sender;
	}

	/**
	 * @return an {@link ExecutionResult}, should never be null
	 */
	abstract ExecutionResult run();

	void execute() {
		Player player = sender instanceof Player ? (Player) sender : null;
		switch (run()) {
			case MISSING_ARGS:
				sender.sendMessage(plugin.getStringParser().getColoredPrefixString(plugin.getConfig().getString("messages.missingArgs"), player));
				break;
			case NO_PERMISSION:
				sender.sendMessage(plugin.getStringParser().getColoredPrefixString(PLACEHOLDER_PERM_PATTERN.matcher(plugin.getConfig().getString("messages.noPermission", "")).replaceAll(Matcher.quoteReplacement(permission)), player));
				break;
			case NO_PLAYER:
				sender.sendMessage(plugin.getStringParser().getColoredPrefixString(PLACEHOLDER_PLAYER_PATTERN.matcher(plugin.getConfig().getString("messages.noPlayer", "")).replaceAll(Matcher.quoteReplacement(player != null ? player.getName() : "")), player));
				break;
			case NON_PLAYER:
				sender.sendMessage(plugin.getStringParser().getColoredPrefixString(plugin.getConfig().getString("messages.notPlayer"), player));
		}
	}

	/* Getters */
	String getPermission() {
		return permission;
	}

	String[] getArgs() {
		return args;
	}

	String getLabel() {
		return label;
	}

	CommandSender getSender() {
		return sender;
	}
}
