package de.SSC.CoreManager.Config.Messages;

import de.SSC.CoreManager.Config.IConfig;

public class MessagesPlayer implements IConfig
{
	public String GameModeTagOld;
	public String GameModeTagNew;
	public String GameModeTag;
	public String ValueTag;
	
	public String Creative;
	public String Survival;
	public String Adventure;
	public String Spectator;
	
	public String GameModeChanged;
	public String GameModeChangeError;
	public String HealedByAmount;
	
	public String YouAreAlreadyInThisGameMode;
	
	public String RegisteringNewPlayer;
	public String NewPlayerRegistrationError;
	public String FailedToLoadPlayer;
	public String PlayerParameterWasNull;
	public String PlayerTag;
	public String FailedToGetPlayerInformation;
	public String UserAdded;
	public String PlayerNotFound;	


	public void LoadDefaults() 
	{
		PlayerTag = "{Player}";
		GameModeTag = "{GameMode}";
		GameModeTagNew = "{GameModeNew}";
		GameModeTagOld = "{GameModeOld}";
		ValueTag = "{value}";
		
		Creative = "Creative";
		Survival = "Survival";
		Adventure = "Adventure";
		Spectator = "Spectator";
		
		GameModeChanged = "&6Gamemode changed from &e<&f{GameModeOld}&e> &6to &e<&f{GameModeNew}&e>";
		GameModeChangeError = "GameModeChangeError";
		
		HealedByAmount = "Healed by {value}";
		
		YouAreAlreadyInThisGameMode = "&6You are already in &e<&f{GameMode}&e>&6.";		
		  
		RegisteringNewPlayer = "&aRegister new Player &2<&f{Player}&2>&a...";		 
		NewPlayerRegistrationError = "&cRegisteration of a new player failed!"; 
		FailedToLoadPlayer = "&cFailed to load player!";   
		PlayerParameterWasNull = "&cThe player parameter was null!";		 
		FailedToGetPlayerInformation = "&cFailed to load the player information!";
		UserAdded = "&aUser &2<&f{Player}&2> &aadded!";
		PlayerNotFound = "&cUser &4<&f{Player}&4> &cwas not found!";
	}	
}
