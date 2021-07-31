package de.SSC.CoreManager.Config;

import de.SSC.CoreManager.DataBase.DataTypes.CMPlayer;
import de.SSC.CoreManager.DataBase.DataTypes.CMRank;

public class ChatConfig implements IConfig
{
	
	  
	public char ColorCombineChar;
	public String OPPermissionSymbol; 
	 
	  public boolean DebugMode;
	  public boolean ColorLogs;
		public boolean ShowWorld;
		public boolean ShowPrefx;
		public boolean ShowSuffix;
		
		public String WorldSyntax;
		public String PrefixSyntax;
		public String PlayerNameSyntax;
		public String SuffixSyntax;
		public String MessageSyntax;	
		 public String OPPermissionSyntax;
		
		 public  String OPPermissionSyntaxTag;
		public String  WorldSyntaxTag;
		public String  PrefixSyntaxTag;
		public String  PlayerNameSyntaxTag;
		public String  SuffixSyntaxTag;
		public String  MessageSyntaxTag;	
		
	  
	  public ChatConfig()
	  {
		  
	  }
	  
	  public void LoadDefaults()
	  {
		  ColorCombineChar = '&';
		  OPPermissionSymbol = "$";		  

		  DebugMode = true;
		  ColorLogs = true;
		  
			 ShowWorld = true;
			 ShowPrefx = true;
			 ShowSuffix = false;
			
			 WorldSyntax = "&7[&e{WORLD}&7]";
			 PrefixSyntax = "&7[&6{PREFIX}&7]";
			 PlayerNameSyntax= "&7[&4{OP}&e{NAME}&7]";
			 SuffixSyntax = "&7[&6{SUFFIX}&7]";
			 MessageSyntax = " &r&f{MESSAGE}";			 
			 OPPermissionSyntax = "&c{OP}&r";
			 
			 OPPermissionSyntaxTag = "{OP}";
			 WorldSyntaxTag = "{WORLD}";
			 PrefixSyntaxTag = "{PREFIX}";
			 PlayerNameSyntaxTag= "{NAME}";
			 SuffixSyntaxTag = "{SUFFIX}";
			 MessageSyntaxTag = "{MESSAGE}";	
	  }
	  
	  public String SetOPTag(boolean isOP, String text)
	  {   
		  if(isOP)
		  {
			  text = text.replace(OPPermissionSyntaxTag, OPPermissionSymbol);
		  }
		  else
		  {
			  text = text.replace(OPPermissionSyntaxTag, "");
		  }		  
		  
		  return text;
	  }
	  
	  public String SetRankTag(CMRank rank, String text)
	  {   
			  return text.replace(PrefixSyntaxTag, rank.ColorTag);
	  }
	  
	  public String SetNameTag(CMPlayer cmPlayer, String text)
	  {   
		  return text.replace(PlayerNameSyntaxTag, cmPlayer.RankGroup.PlayerColor + cmPlayer.BukkitPlayer.getDisplayName());
	  }
	  
	  public String SetWorldTag(String worldName, String text)
	  {   
		  return text.replace(WorldSyntaxTag, worldName);
	  }
	  
	  public String SetMessageTag(String message, String text)
	  {   
		  return text.replace(MessageSyntaxTag, message);
	  }
}
