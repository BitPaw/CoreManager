package de.SSC.CoreManager.Utility;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import de.SSC.CoreManager.Config.Config;
import de.SSC.CoreManager.DataBase.DataTypes.CMPlayer;
import de.SSC.CoreManager.DataBase.DataTypes.CMPlayerList;
import de.SSC.CoreManager.DataBase.DataTypes.CMRank;
import de.SSC.CoreManager.DataBase.DataTypes.CMWorld;

public class MessageTags
{   
	private static MessageTags _Instance;
	
	private final String _world;
	private final String _warp;
	private final String _rank;
	private final String _rankColor;
	private final String _name;
	private final String _gameModeOld;
	private final String _gameModeNew;
	private final String _gameMode;
	private final String _value;
	private final String _player;
	private final String _op;
	private final String _message;
	private final String _ping;
	
	private Config _config; 
	
	private MessageTags()
	{
		_Instance = this;
		
		_world = "{WORLD}";	
		_warp = "{WARP}";
		_rank = "{RANK}";
		_rankColor = "{RANKCOLOR}";
		_player = "{PLAYER}";
		_name = "{NAME}";
		_gameMode = "{GAMEMODE}";
		_gameModeNew = "{GAMEMODENEW}";
		_gameModeOld = "{GAMEMODEOLD}";
		_value = "{VALUE}";			 
		_op = "{OP}";
		_message = "{MESSAGE}";	
		_ping = "{PING}";
		
		_config = Config.Instance();
	}
	
	public static MessageTags Instance()
	{
		return _Instance == null ? new MessageTags() : _Instance;
	}	
	
	  private String GetGameModeName(GameMode gamemode)
	  {
	    	String gameModeString = "Error";
	    	
	        switch(gamemode)
	        {
				case ADVENTURE:
					gameModeString = _config.Messages.Player.Adventure;
					break;
					
				case CREATIVE:
					gameModeString = _config.Messages.Player.Creative;
					break;
				
				case SPECTATOR:
					gameModeString = _config.Messages.Player.Spectator;
					break;
				
				case SURVIVAL:
					gameModeString = _config.Messages.Player.Survival;
					break;  
				
	        }   
	    	
	    	return gameModeString;
	    }
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	
	public String ReplaceWorldTag(String message, CMWorld cmWorld)
	{
		return message.replace(_world, cmWorld.CustomName);
	}
	
	public String ReplaceWorldTag(String message, String world)
	{
		return message.replace(_world, world);
	}
	
	public String ReplaceRankTag(String message, CMPlayer cmPlayer)
	{
		return message.replace(_rank, cmPlayer.RankGroup.ColorTag);
	}
	
	public String ReplaceOPTag(String message, boolean isOP)
	{   
		  String replacement = "";
		  
		  if(isOP)
		  {
			  replacement = _config.Chat.OPPermissionSymbol;
		  } 
		  
		  message = message.replace(_op, replacement);
		  
		  return message;
	  }
	  
	public String ReplacePingTag(String message, int ping)
	{
		return message.replace(_ping, Integer.toString(ping));
	}
	
	  public String ReplaceRankTag(String message, CMRank cmRank) 
	  {   			 
		  message = message.replace(_rank, cmRank.ColorTag);
		  message = message.replace(_rankColor, cmRank.PlayerColor);
		  
		  return message;
	  }
	  
	  public String ReplacePlayerTag(String text, CMPlayer cmPlayer)
	  {   		  
		  return text.replace(_player, cmPlayer.GetPlayerCustomName());
	  }
	  
	  public String ReplacePlayerTag(String text, String playerName)
	  {   		  
		  return text.replace(_player, playerName);
	  }
	  
	  public String ReplacePlayerTag(String text, Player player)
	  {   
		  CMPlayerList cmPlayerList = CMPlayerList.Instance();
		  CMPlayer cmPlayer = cmPlayerList.GetPlayer(player);		  
		  
		  return ReplacePlayerTag(text, cmPlayer);
	  }
	  
	  public String ReplaceMessageTag(String text, String message) 
	  {   
		  return text.replace(_message, message);
	  }

	public String ReplaceNameTag(String message, String name) 
	{
		return message.replace(_name, name);
	}

	public String ReplaceValueTag(String message, String value) 
	{		
		return message.replace(_value, value);
	}
	
	public String ReplaceValueTag(String message, int value) 
	{		
	String valueText = Integer.toString(value);
		
		return message.replace(_value, valueText);
	}
	
	public String ReplaceValueTag(String message, double value) 
	{		
		String valueText = Double.toString(value);
		
		return message.replace(_value, valueText);
	}
	
	public String ReplaceValueTag(String message, float value) 
	{		
		String valueText = Float.toString(value);
		
		return message.replace(_value, valueText);
	}

	public String ReplaceGameMode(String message, GameMode gameMode) 
	{
		String gameModeText = GetGameModeName(gameMode);		
		
		return message.replace(_gameMode, gameModeText);
	}
	
	public String ReplaceGameModeOld(String message, GameMode gameMode) 
	{
		String gameModeText = GetGameModeName(gameMode);		
		
		return message.replace(_gameModeOld, gameModeText);
	}

	public String ReplaceGameModeNew(String message, GameMode gameMode) 
	{
		String gameModeText = GetGameModeName(gameMode);		
		
		return message.replace(_gameModeNew, gameModeText);
	}

	public String ReplaceWarpTag(String message, String warpName) 
	{
		return message.replace(_warp, warpName);
	}
}
