package de.BitFire.File;

/**
 * Used to mark regions with specific data.
 * @author BitPaw
 */
public enum FlagType 
{	
	/**
	 * Does a player need to have a higher permission level to use this?<br>
	 * Tag: <b>P</b>
	 */
	PermissionToUse,
	
	/**
	 * Has area infinite length and width?<br>
	 * Tag: <b>S</b>
	 */
	InfinitySize,
	
	/**
	 * Has area infinite height?<br>
	 * Tag: <b>H</b>
	 */
	InfinityHeight,
	
	/**
	 * Can a user modify blocks?<br>
	 * Tag: <b>M</b>
	 */
	PermissionToModify,
	
	/**
	 * Can a player move into a region?<br>
	 * Tag: <b>T</b>
	 */
	PermissionToTrespass,
	
	/**
	 * Can a player leave a region?<br>
	 * Tag: <b>L</b>
	 */
	PermissionToLeave
}
