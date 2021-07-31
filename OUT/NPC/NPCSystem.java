package de.BitFire.NPC;

import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_15_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import com.mojang.authlib.GameProfile;

import de.BitFire.API.Bukkit.BukkitAPIPlayer;
import de.BitFire.API.Bukkit.BukkitAPIServer;
import de.BitFire.API.CoreManager.BaseSystem;
import de.BitFire.API.CoreManager.ISystem;
import de.BitFire.API.CoreManager.Priority;
import de.BitFire.API.CoreManager.SystemState;
import de.BitFire.Chat.Module;
import de.BitFire.Configuration.Config;
import de.BitFire.Core.Exception.NotForConsoleException;
import de.BitFire.Core.Exception.SystemNotActiveException;
import de.BitFire.DataBase.DataBaseSystem;
import de.BitFire.Geometry.PointView;
import de.BitFire.Player.CMPlayer;
import de.BitFire.Player.PlayerSystem;
import de.BitFire.Player.Exception.InvalidPlayerNameException;
import de.BitFire.Player.Exception.InvalidPlayerUUID;
import de.BitFire.Player.Exception.PlayerDoesNotExistException;
import de.BitFire.Player.Exception.PlayerNotFoundException;
import de.BitFire.Skin.SkinData;
import de.BitFire.Skin.SkinSystem;
import de.BitFire.Skin.Exception.OfflineSkinLoadException;
import de.BitFire.Skin.Exception.SkinLoadException;
import de.BitFire.Time.IUpdateable;
import de.BitFire.World.CMWorld;
import de.BitFire.World.WorldSystem;
import de.BitFire.World.Exception.InvalidWorldNameException;
import net.minecraft.server.v1_15_R1.EntityPlayer;
import net.minecraft.server.v1_15_R1.MinecraftServer;
import net.minecraft.server.v1_15_R1.PacketPlayOutNamedEntitySpawn;
import net.minecraft.server.v1_15_R1.PacketPlayOutPlayerInfo;
import net.minecraft.server.v1_15_R1.PlayerConnection;
import net.minecraft.server.v1_15_R1.PlayerInteractManager;
import net.minecraft.server.v1_15_R1.WorldServer;

public class NPCSystem extends BaseSystem implements ISystem, IUpdateable
{
	private static NPCSystem _instance;
	private NPCList _npcList;
	
	private PlayerSystem _playerSystem;	
	private SkinSystem _skinSystem;
	private WorldSystem _worldSystem;
	private Config _config;
	private DataBaseSystem _dataBaseSystem;
	//private Logger _logger;
	
	private NPCSystem()
	{
		super(Module.NPC, SystemState.Inactive, Priority.Low);
		_instance = this;
		
		_npcList = new NPCList();		
	}
	
	public static NPCSystem Instance()
	{
		return _instance == null ? new NPCSystem() : _instance;
	}

	@Override
	public void LoadReferences() 
	{
		_playerSystem = PlayerSystem.Instance();
		_skinSystem = SkinSystem.Instance();
		_worldSystem = WorldSystem.Instance();
		_config = Config.Instance();
		_dataBaseSystem = DataBaseSystem.Instance();
		//_logger = Logger.Instance();	
	}

	@Override
	public void Reload(final boolean firstRun) throws SystemNotActiveException
	{
		List<NPC> npcList;
		_npcList.Clear();
		
		if(!Information.IsActive())
		{
			throw new SystemNotActiveException();
		}	
		
		npcList = _dataBaseSystem.NPC.LoadAllNPCs();	
		
		for(NPC npc : npcList)
		{
			_npcList.Add(npc);
			
			try 
			{
				SpawnNPC(npc);
			} catch (InvalidPlayerNameException e) {
			
			} catch (InvalidPlayerUUID e) {
				
			} catch (OfflineSkinLoadException e) {
				
			} catch (InvalidWorldNameException e) {
				
			} 
			catch (PlayerDoesNotExistException e) 
			{
				
			} 
			catch (SkinLoadException e) 
			{
				
			}
		}		
	}
	
	public void OnTickUpdate()
	{
		if(!Information.IsActive())
		{
			//throw new SystemNotActiveException();
		}
		
		//Reload(false);
	}
	
	public void NPCCommand(CommandSender sender, String[] args) throws 	NotForConsoleException, 
																		InvalidPlayerNameException, 
																		InvalidPlayerUUID, 
																		OfflineSkinLoadException, 
																		InvalidWorldNameException,
																		PlayerDoesNotExistException,
																		SkinLoadException, 
																		SystemNotActiveException
	{
		
		if(!Information.IsActive())
		{
			throw new SystemNotActiveException();
		}
		
		final boolean isPlayerSender = sender instanceof Player;
		final int parameterLengh = args.length;		
		
		if(isPlayerSender)
		{
			final Player player = (Player)sender;
			
			switch(parameterLengh)
			{
			case 0:
			case 1:
			case 2:
				// create
				final String command = args[0];
				final String targetedPlayerName = args[1];
				
				switch(command)
				{
				case "create" :
					CreateNPC(player.getLocation(), targetedPlayerName);
					break;
				}
			}
		}
		else
		{
			throw new NotForConsoleException();
		}
	}
	
	public void SpawnNPC(NPC npc) throws 	InvalidPlayerNameException, 
											InvalidPlayerUUID, 
											OfflineSkinLoadException,
											InvalidWorldNameException,
											PlayerDoesNotExistException, 
											SkinLoadException, 
											SystemNotActiveException
											
	{
		if(!Information.IsActive())
		{
			throw new SystemNotActiveException();
		}		
		
		final CMWorld cmWorld = _worldSystem.GetWorld(npc.WorldID);
		
		final double x = npc.Position.X;
		final double y = npc.Position.Y;
		final double z = npc.Position.Z;
		float yaw = npc.Position.Yaw;
		float pitch = npc.Position.Pitch;
		
		final Location location = new Location(cmWorld.BukkitWorld, x, y, z, yaw, pitch);
				
		//_logger.SendToConsole(Module.NPC, MessageType.Loading, "&7Spawning NPC &6<&e" + npc.Name + "&6>&7.");
		
		CreateNPC(location, npc.Name);
	}

	
	public void CreateNPC(Location location, final String playerName) throws 	InvalidPlayerNameException,
																				InvalidPlayerUUID, 
																				InvalidWorldNameException, 
																				OfflineSkinLoadException, 
																				PlayerDoesNotExistException,
																				SkinLoadException,
																				SystemNotActiveException
	{
		if(!Information.IsActive())
		{
			throw new SystemNotActiveException();
		}
		
		final CraftWorld craftWorld = (CraftWorld)location.getWorld();		
		final MinecraftServer server = BukkitAPIServer.GetMineCraftServer(); 
		final WorldServer world = craftWorld.getHandle();
		final PlayerInteractManager playerInteractManager = new PlayerInteractManager(world);
		final PointView pointView = new PointView(location);	    
		final String worldName = _config.Worlds.RemoveFolderName(location.getWorld().getName());
		final CMWorld cmWorld = _worldSystem.GetWorld(worldName);
		final int playerID = _npcList.GetNextID();		
		
		UUID playerUUID;		
	    EntityPlayer entityPlayer; 		
	    String playerDisplayName;
	    GameProfile gameProfile;  	    
	    
	        try
	        {
	        	final CMPlayer cmPlayer = _playerSystem.GetPlayer(playerName);
	        	
	        	if(cmPlayer.IsOffline())
				{
					throw new PlayerNotFoundException(playerName);
				}
	        	
	        	playerUUID = cmPlayer.Information.PlayerUUID;
	        	playerDisplayName = cmPlayer.GetFullName();
	        }
	        catch(PlayerNotFoundException e)
	        {
	        	playerUUID = BukkitAPIPlayer.GetOfflinePlayerUUID(playerName);
	        	playerDisplayName = playerName;
	        }	               		

	        gameProfile = new GameProfile(playerUUID, playerName);
            
	        if(playerDisplayName.length() > 15)
	        {
	        	playerDisplayName = playerDisplayName.substring(0, 14);
	        }
	        
	        gameProfile.getProperties().clear();

	        SkinData skinData = _skinSystem.GetSkin(playerUUID);
	        
            String skinName = skinData.Name;
            
            if(skinName != null)
            {	            	
            	gameProfile.getProperties().put(skinName, skinData.GetProperty());     
            }
	        
	        entityPlayer = new EntityPlayer(server, world, gameProfile, playerInteractManager);	  
	        entityPlayer.setLocation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());        
	        //entityPlayer.displayName = playerDisplayName;
	        
	        // create and add player
	        {
	        	 final NPC npc = new NPC(playerID, playerName, cmWorld.Information.ID, pointView, false);        
      	        
	  	        _npcList.Add(npc);	 
	        }
	        
	        // Display to all players
	        {
	            for(Player all : Bukkit.getOnlinePlayers())
		        {
		            PlayerConnection connection = ((CraftPlayer)all).getHandle().playerConnection;
		            connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, entityPlayer));
		            connection.sendPacket(new PacketPlayOutNamedEntitySpawn(entityPlayer));

		            
		            BukkitAPIPlayer.RefreshAllSkinsForPlayer(all);
		        }
	        }     	        
	}
}