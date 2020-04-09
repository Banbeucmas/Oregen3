package me.banbeucmas.oregen3.commands;

import me.banbeucmas.oregen3.Oregen3;
import me.banbeucmas.oregen3.utils.StringUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.regex.Matcher;

import static me.banbeucmas.oregen3.utils.StringUtils.*;

abstract class AbstractCommand {
	private final String permission;
	private final CommandSender sender;
	private final Player player;
	private final String label;
	private final String[] args;

	AbstractCommand(final String permission, final CommandSender sender, final String label, final String[] args) {
		this.permission = permission;
		this.label      = label;
		this.args       = args;
		this.sender     = sender;
		if (sender instanceof Player) {
			player = (Player) sender;
		}
		else
			player = null;
	}

	/*
	AbstractCommand(final String permission, final CommandSender sender, final String[] args) {
		this.permission = permission;
		label           = null;
		this.args       = args;
		this.sender     = sender;
		if (sender instanceof Player) {
			player = (Player) sender;
		}
		else
			player = null;
	}
	*/

	AbstractCommand(final String permission, final CommandSender sender, final String label) {
		this.permission = permission;
		this.label      = label;
		args            = null;
		this.sender     = sender;
		if (sender instanceof Player) {
			player = (Player) sender;
		}
		else
			player = null;
	}

	AbstractCommand(final String permission, final CommandSender sender) {
		this.permission = permission;
		label           = null;
		args            = null;
		this.sender     = sender;
		if (sender instanceof Player) {
			player = (Player) sender;
		}
		else
			player = null;
	}

	/**
	 * @return an {@link ExecutionResult}, should never be null
	 */
	protected abstract ExecutionResult now();

	void execute() {
		switch (now()) {
			case MISSING_ARGS:
				if (getFormat() != null) {
					sender.sendMessage(StringUtils.getPrefixString(FORMAT.matcher(Oregen3.getPlugin().getConfig().getString("messages.missingArgs")).replaceAll(Matcher.quoteReplacement(getFormat())), player));
				}
				break;
			case NO_PERMISSION:
				sender.sendMessage(StringUtils.getPrefixString(PERM.matcher(Oregen3.getPlugin().getConfig().getString("messages.noPermission")).replaceAll(Matcher.quoteReplacement(permission)), player));
				break;
			case NO_PLAYER:
				assert player != null;
				sender.sendMessage(StringUtils.getPrefixString(PLAYER.matcher(Oregen3.getPlugin().getConfig().getString("messages.noPlayer")).replaceAll(Matcher.quoteReplacement(player.getName())), player));
				break;
			case NOT_PLAYER:
				sender.sendMessage(StringUtils.getPrefixString(Oregen3.getPlugin().getConfig().getString("messages.notPlayer"), player));
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

	private String getFormat() {
		return null;
	}

	CommandSender getSender() {
		return sender;
	}

	Player getPlayer() {
		return player;
	}
}
