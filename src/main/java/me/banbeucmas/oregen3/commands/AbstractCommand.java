package me.banbeucmas.oregen3.commands;

import me.banbeucmas.oregen3.Oregen3;
import me.banbeucmas.oregen3.utils.StringUtils;
import org.bukkit.command.CommandSender;


abstract class AbstractCommand {

	private final String permission;
	private final CommandSender sender;
	private final Oregen3 plugin = Oregen3.getPlugin();


	AbstractCommand(final String permission, final CommandSender sender) {
		this.permission = permission;
		//final String[] args = null;
		this.sender = sender;
	}

	/**
	 * @return an {@link ExecutionResult}, should never be null
	 */
	protected abstract ExecutionResult now();

	void execute() {
		switch (now()) {
			case MISSING_ARGS:
				if (getFormat() != null) {
					sender.sendMessage(StringUtils.getPrefixString("&cFormat: &f" + getFormat()));
				}
				break;
			case NO_PERMISSION:
				sender.sendMessage(StringUtils.getPrefixString("&4Missing Permission: &c" + permission));
				break;
			case NO_PLAYER:
			    sender.sendMessage(StringUtils.getPrefixString("&4Player is not excist or isn't online"));
			    break;
		    case NOT_PLAYER:
			    sender.sendMessage(StringUtils.getPrefixString("&4Only player can use this command"));
		    case CONSOLE_NOT_PERMITTED:
			    sender.sendMessage(StringUtils.getPrefixString("&4This command is not available to console"));
			    break;
			default:
				break;
		}
	}

	/* Getters */
	String getPermission() {
		return permission;
	}

// --Commented out by Inspection START (25/03/2020 8:27 SA):
//	public String[] getArgs() {
//		return args;
//	}
// --Commented out by Inspection STOP (25/03/2020 8:27 SA)

	private String getFormat() {
		return null;
	}

	CommandSender getSender() {
		return sender;
	}

	Oregen3 getPlugin() {
		return plugin;
	}
}
