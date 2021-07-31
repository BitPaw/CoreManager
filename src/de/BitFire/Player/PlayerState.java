package de.BitFire.Player;

public enum PlayerState 
{
	/**
	 * Player is <b>not</b> properly set up.<br> Use this as <u>initialization</u>.
	 */
	Undefined,
	
	/**
	 * Player is <b>online</b> but the database has <b>not</b> registered yet.
	 */
	Unregistered,
	
	/**
	 * Player is <b>online</b> and currently tries <b>logging in</b>.
	 */
	LoggingIn,
	
	/**
	 * Player is registered but not online.
	 */
	Offline,
	
	/**
	 * Player is currently <b>online</b>.
	 */
	Online,
	
	/**
	 * Player is currently <b>not</b> active.
	 */
	AFK, 
	
	/**
	 * Player is in a PvP Game.
	 */
	InGame
}
