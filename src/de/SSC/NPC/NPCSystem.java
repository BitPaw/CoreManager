package de.SSC.NPC;

import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_15_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import com.mojang.authlib.GameProfile;

import de.SSC.BukkitAPI.BukkitAPISystem;
import de.SSC.CoreManager.Chat.Module;
import de.SSC.CoreManager.Config.Config;
import de.SSC.CoreManager.DataBase.DataBaseSystem;
import de.SSC.CoreManager.Geometry.PointView;
import de.SSC.CoreManager.Player.CMPlayer;
import de.SSC.CoreManager.Player.PlayerSystem;
import de.SSC.CoreManager.Player.Exception.InvalidPlayerNameException;
import de.SSC.CoreManager.Player.Exception.InvalidPlayerUUID;
import de.SSC.CoreManager.Player.Exception.PlayerDoesNotExistException;
import de.SSC.CoreManager.Player.Exception.PlayerNotFoundException;
import de.SSC.CoreManager.Skin.SkinData;
import de.SSC.CoreManager.Skin.SkinSystem;
import de.SSC.CoreManager.Skin.Exception.OfflineSkinLoadException;
import de.SSC.CoreManager.Skin.Exception.SkinLoadException;
import de.SSC.CoreManager.System.BaseSystem;
import de.SSC.CoreManager.System.IRunnableSystem;
import de.SSC.CoreManager.System.ISystem;
import de.SSC.CoreManager.System.SystemPriority;
import de.SSC.CoreManager.System.SystemState;
import de.SSC.CoreManager.System.Exception.NotForConsoleException;
import de.SSC.CoreManager.System.Exception.SystemNotActiveException;
import de.SSC.CoreManager.World.CMWorld;
import de.SSC.CoreManager.World.WorldSystem;
import de.SSC.CoreManager.World.Exception.InvalidWorldNameException;
import net.minecraft.server.v1_15_R1.EntityPlayer;
import net.minecraft.server.v1_15_R1.MinecraftServer;
import net.minecraft.server.v1_15_R1.PacketPlayOutNamedEntitySpawn;
import net.minecraft.server.v1_15_R1.PacketPlayOutPlayerInfo;
import net.minecraft.server.v1_15_R1.PlayerConnection;
import net.minecraft.server.v1_15_R1.PlayerInteractManager;
import net.minecraft.server.v1_15_R1.WorldServer;

public class NPCSystem extends BaseSystem implements ISystem, IRunnableSystem
{
	private static NPCSystem _instance;
	private NPCList _npcList;
	
	private PlayerSystem _playerSystem;	
	private BukkitAPISystem _bukkitAPISystem;
	private SkinSystem _skinSystem;
	private WorldSystem _worldSystem;
	private Config _config;
	private DataBaseSystem _dataBaseSystem;
	//private Logger _logger;
	
	private NPCSystem()
	{
		super(Module.NPC, SystemState.Inactive, SystemPriority.Low);
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
		_bukkitAPISystem = BukkitAPISystem.Instance();
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
	
	@Override
	public void Update() throws SystemNotActiveException 
	{
		if(!Information.IsActive())
		{
			throw new SystemNotActiveException();
		}
		
		Reload(false);
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
		final MinecraftServer server = _bukkitAPISystem.Server.GetMineCraftServer(); 
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
	        	
	        	playerUUID = cmPlayer.PlayerUUID;
	        	playerDisplayName = cmPlayer.GetFullName();
	        }
	        catch(PlayerNotFoundException e)
	        {
	        	playerUUID = _bukkitAPISystem.Player.GetOfflinePlayerUUID(playerName);
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

		            
		            _bukkitAPISystem.Player.RefreshAllSkinsForPlayer(all);
		        }
	        }     	        
	}
}