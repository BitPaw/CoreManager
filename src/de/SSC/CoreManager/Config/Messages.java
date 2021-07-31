package de.SSC.CoreManager.Config;

public class Messages 
{
	// Tags
	public String TagPlayer = "{Player}";
	public String TagWorld = "{World}";
	public String TagOP = "{OP}";

	public String TagPing = "{Ping}";

	public String TagGameMode = "{GameMode}";

	public String TagValue = "{Value}";
	public String TagMessage = "{Message}";

	public String TagRank = "{Rank}";
	public String TagOldRank = "{OldRank}";

	// Chat Syntax
	public String CharSyntax = "&7[&r" + TagWorld + "&7][&r" + TagRank + "&7][&4" + TagOP + "&r" + TagPlayer + "&7]&r " + TagMessage;

	// Console I/O	
	public String DataBase = "&7[&eDataBase&7]";
	public String CoreManager = "&7[&bCoreManager&7]";
	public String System = "&7[&3System&7]";
	public String TeleportSystem = "&7[&6TPS&7]";
	public String MultiverseSystem = "&7[&aMVS&7]";
	public String PermissionSystem = "&7[&cRMS&7]";

	public String Shutdown = "This plugin will be disabled do to an critical error!";
	
	public String On = "&7[&aOnline&7]";
	public String Off = "&7[&cOffline&7]";
	public String Err = "&7[&cError&7]";
	public String Null = "&7[&fNull&7]";

	public String Command = "&7[&a#&7]&a ";
	public String Info = "&7[&ei&7]&e ";
	public String Question = "&7[&b?&7]&b ";
	public String Warning = "&7[&6!&7]&6 ";
	public String Error = "&7[&c!&7]&c ";
	
	public String NotForConsole = "&cThe Console can't execute this command!";
	
	// CoreSystem
	public String StartUpError = "There was an error while creating objects!\nThe plugin will be disabled!";

	// ChatManager
	public String FirstJoin = "&3Welcome &b%player% &3to our Server!\n You are &bUser Nr. %nr%&3.";
	public String ReJoin = "&3Welcome back!";
	public String Join = "&7[&a+&7]&r&a ";
	public String Quit = "&7[&c-&7]&r&c ";
	public String PermissionChangeName = "changename.change";
	public String NameChanged = "You are now disguised as ";
	public String NameChangesWrongCommand = "Wrong usage! /changename <name>";
	public String AFK = "%prefix&%player% ist nun abwesend";
	public String UnAFK = "%prefix&%player% ist wieder da";
	
	// TeleportManager
	public String Teleporting = "&6Teleporting...";
	public String CouldNotTeleport = "&cCould not Teleport";
	public String WarpDeleted = "&7WarpPoint <&e\"+ TagWorld +\"&7>&c deleted.";
	public String WarpCreated = "&7WarpPoint <&e"+ TagWorld +"&7>&a created.";
	public String SpawnUpdated = "&7World spawnpoint &aupdated";
	public String WorldDoesntExist = "&7The world <&e" + TagWorld + "&7> &4doesn't exist!";

	// MySQL
	public String SQLConnectionSuccessfully = "connection successfully.";
	public String SQLConnectionFailed = "connection failed!";
	public String SQLStatementError = "statement error!";
	public String SQLClosingError = "closing error!";
	public String SQLMessageHeader = "&5----------[&dMessage&5]----------&d";
	public String SQLMessageFooter = "&5-----------------------------";
	
	public String SQLRegisteringNewPlayer = "Registing new Player %player%";
	public String SQLRegistrationError = "RegisterNewPlayer Error!";
	
	// Sign
	public String SignLine = "&l&1-=-=-=-=-=-=-=-";

	// Vanilla
	public String Day = "&7The &6sun&7 is now shining.";
	public String Night = "&7Embrace the &adarkness&7.";
	public String Rain = "&3Rainfall&7 toggled.";

	// PlayerEditor
	public String HealedBy = "&aHealed &7by <&2" + TagValue+ "&7> health.";
	public String GameModeChanged = "&6GameMode &7changed to &6<&e" + TagGameMode + "&6>&7.";
	public String GameModeChangeError = Error + "&c Invalid GameMode!";

	// Permission Manager
	public String ChangeRankToLessParameters = "&cYou need to add a parameter! Try to add a rank and/or player";
	public String ChangeRankToManyParameters = "&cYou have to many parameters!";
	public String ChangeRankPlayerNotFound = "&cPlayer &7<&e" + TagPlayer + "&7> &cnot found!";
	public String ChangeRankPlayerChanged = "&6Rank &7changed from <&e" + TagOldRank + "&7> to <&e" + TagRank + "&7>";
	public String ChangeRankOtherPlayerChanged = "&6Rank &7from <&e" + TagPlayer + "&7> changed from <&e" + TagOldRank + "&7> to <&e" + TagRank + "&7>";
}