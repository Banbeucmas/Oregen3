package me.banbeucmas.oregen3.commands;

import me.banbeucmas.oregen3.Oregen3;
import me.banbeucmas.oregen3.utils.StringUtils;
import org.bukkit.command.CommandSender;


public abstract class AbstractCommand {

	private String permission;
	private String[] args;
	private CommandSender sender;
	private Oregen3 plugin = Oregen3.getPlugin();


	public AbstractCommand(String permission, String[] args, CommandSender sender) {
		this.permission = permission;
		this.args = args;
		this.sender = sender;
	}

	/**
	 * @return an {@link ExecutionResult}, should never be null
	 */
	public abstract ExecutionResult now();

	public void execute() {
		switch (now()) {
		case DONT_CARE:
			break;
		case MISSING_ARGS:
			if(getFormat() != null){
				sender.sendMessage(StringUtils.getColoredString("&cFormat: &f" + getFormat()));
			}
			break;
		case NO_PERMISSION:
			sender.sendMessage(StringUtils.getColoredString("&4Missing Permission: &c" + permission));
			break;
		case NO_PLAYER:
			sender.sendMessage(StringUtils.getColoredString("&4Player is not excist or isn't online"));
			break;
		case CONSOLE_NOT_PERMITTED:
			sender.sendMessage(StringUtils.getColoredString("&4This command is not available to console"));
			break;
		default:
			break;
		}
	}

	/* Getters */
	public String getPermission() {
		return permission;
	}

	public String[] getArgs() {
		return args;
	}

	public String getFormat() {
		return null;
	}

	public CommandSender getSender() {
		return sender;
	}

	public Oregen3 getPlugin() {
		return plugin;
	}
}
