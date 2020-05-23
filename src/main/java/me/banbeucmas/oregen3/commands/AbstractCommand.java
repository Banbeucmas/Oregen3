package me.banbeucmas.oregen3.commands;

import me.banbeucmas.oregen3.Oregen3;
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

	/**
	 * @return an {@link ExecutionResult}, should never be null
	 */
	abstract ExecutionResult run();

	void execute() {
		switch (run()) {
			case MISSING_ARGS:
				sender.sendMessage(getPrefixString(Oregen3.getPlugin().getConfig().getString("messages.missingArgs"), player));
				break;
			case NO_PERMISSION:
				sender.sendMessage(getPrefixString(PERM.matcher(Oregen3.getPlugin().getConfig().getString("messages.noPermission")).replaceAll(Matcher.quoteReplacement(permission)), player));
				break;
			case NO_PLAYER:
				sender.sendMessage(getPrefixString(PLAYER.matcher(Oregen3.getPlugin().getConfig().getString("messages.noPlayer")).replaceAll(Matcher.quoteReplacement(player != null ? player.getName() : "")), player));
				break;
			case NON_PLAYER:
				sender.sendMessage(getPrefixString(Oregen3.getPlugin().getConfig().getString("messages.notPlayer"), player));
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

	Player getPlayer() {
		return player;
	}
}
