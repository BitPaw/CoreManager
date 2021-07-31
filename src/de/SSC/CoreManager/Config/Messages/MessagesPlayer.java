package de.SSC.CoreManager.Config.Messages;

import de.SSC.CoreManager.Config.IConfig;

public class MessagesPlayer implements IConfig
{
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
	public String FailedToGetPlayerInformation;
	public String UserAdded;
	public String PlayerNotFound;	


	public void LoadDefaults() 
	{

		
		Creative = "Creative";
		Survival = "Survival";
		Adventure = "Adventure";
		Spectator = "Spectator";
		
		GameModeChanged = "&6Gamemode changed from &e<&f{GAMEMODEOLD}&e> &6to &e<&f{GAMEMODENEW}&e>";
		GameModeChangeError = "GameModeChangeError";
		
		HealedByAmount = "Healed by {VALUE}";
		
		YouAreAlreadyInThisGameMode = "&6You are already in &e<&f{GAMEMODE}&e>&6.";		
		  
		RegisteringNewPlayer = "&aRegister new Player &2<&f{PLAYER}&2>&a...";		 
		NewPlayerRegistrationError = "&cRegisteration of a new player failed!"; 
		FailedToLoadPlayer = "&cFailed to load player!";   
		PlayerParameterWasNull = "&cThe player parameter was null!";		 
		FailedToGetPlayerInformation = "&cFailed to load the player information!";
		UserAdded = "&aUser &2<&f{PLAYER}&2> &aadded!";
		PlayerNotFound = "&cUser &4<&f{PLAYER}&4> &cwas not found!";
	}	
}
