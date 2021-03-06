package de.BitFire.Chat;

import java.util.UUID;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import de.BitFire.API.CoreManager.BaseSystem;
import de.BitFire.API.CoreManager.ISystem;
import de.BitFire.API.CoreManager.Priority;
import de.BitFire.API.CoreManager.SystemState;
import de.BitFire.Configuration.Config;
import de.BitFire.Player.CMPlayer;
import de.BitFire.Player.PlayerSystem;
import de.BitFire.Player.Exception.InvalidPlayerUUID;
import de.BitFire.Player.Exception.PlayerNotFoundException;
import de.BitFire.Rank.CMRank;
import de.BitFire.World.CMWorld;

public class MessageTags extends BaseSystem implements ISystem
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
	private final String _speedType;
	private final String _mapType;
	private final String _mapEnviroment;
	private final String _iD;

	private Config _config;
	private PlayerSystem _playerSystem;

	private MessageTags()
	{
		super(Module.Message, SystemState.Active, Priority.Essential);    	
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
		_speedType = "{SPEEDTYPE}";
		_mapType = "{MAPTYPE}";
		_mapEnviroment = "{MAPENVIROMENT}";
		_iD = "{ID}";
		
		_config = Config.Instance();
		_playerSystem = PlayerSystem.Instance();
	}

	public static MessageTags Instance()
	{
		return _Instance == null ? new MessageTags() : _Instance;
	}
		
	private String GetGameModeName(GameMode gamemode)
	{
		String gameModeString = "Error";

		switch (gamemode)
		{
		case ADVENTURE:
			gameModeString = _config.Message.Player.Adventure;
			break;

		case CREATIVE:
			gameModeString = _config.Message.Player.Creative;
			break;

		case SPECTATOR:
			gameModeString = _config.Message.Player.Spectator;
			break;

		case SURVIVAL:
			gameModeString = _config.Message.Player.Survival;
			break;

		}

		return gameModeString;
	}

	public String ReplaceWorldTag(String message, CMWorld cmWorld)
	{
		return message.replace(_world, cmWorld.Information.CustomName);
	}

	public String ReplaceWorldTag(String message, String world)
	{
		return message.replace(_world, world);
	}

	public String ReplaceRankTag(String message, CMPlayer cmPlayer)
	{
		return message.replace(_rank, cmPlayer.Information.RankGroup.Tag);
	}

	public String ReplaceOPTag(String message, boolean isOP)
	{
		String replacement = "";

		if (isOP)
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
		if(cmRank == null)
		{
			message = message.replace("[" + _rank + "]", "");
			message = message.replace(_rankColor, "&a");
		}
		else
		{
			message = message.replace(_rank, cmRank.Tag);
			message = message.replace(_rankColor, cmRank.PlayerColor);
		}		

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

	public String ReplacePlayerTag(String text, Player player) throws PlayerNotFoundException, InvalidPlayerUUID
	{
		UUID playerUUID = player.getUniqueId();
		CMPlayer cmPlayer = _playerSystem.GetPlayer(playerUUID);
		String message = text;
		
		if(cmPlayer == null)
		{
			message = ReplacePlayerTag(text, player.getName());
		}
		else
		{
			message = ReplacePlayerTag(text, cmPlayer);
		}
		
		return message;
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

	public String RemovePlayerTag(String text)
	{
		return text.replace(_player, "");
	}

	public String ReplaceSpeedTypeTag(String text, String speedType) 
	{
		return text.replace(_speedType, speedType);
	}

	public String MapType(String text, String mapType) 
	{
		return text.replace(_mapType, mapType);
	}
	
	public String MapEnviroment(String text, String mapEnviroment) 
	{
		return text.replace(_mapEnviroment, mapEnviroment);
	}

	public String ReplaceIDTag(String worldMessage, int iD) 
	{		
		return worldMessage.replace(_iD, Integer.toString(iD));
	}
}