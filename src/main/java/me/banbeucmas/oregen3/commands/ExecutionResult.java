package me.banbeucmas.oregen3.commands;

public enum ExecutionResult {

	/**
	 * Can be used either the command is successful or failed
	 */
	DONT_CARE, // Could be null, but that would throw a NPE if it wasn't checked
	MISSING_ARGS,
	NO_PERMISSION,
	CONSOLE_NOT_PERMITTED,
	NO_PLAYER

}
