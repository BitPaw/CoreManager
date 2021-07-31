package de.BitFire.Time.Clock;

import java.time.LocalTime;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.BitFire.API.CommandCredentials;
import de.BitFire.API.Bukkit.BukkitAPIPlayer;
import de.BitFire.API.CoreManager.BaseSystem;
import de.BitFire.API.CoreManager.ISystem;
import de.BitFire.API.CoreManager.Priority;
import de.BitFire.API.CoreManager.SystemState;
import de.BitFire.Chat.Logger;
import de.BitFire.Chat.MessageType;
import de.BitFire.Chat.Module;
import de.BitFire.Core.Exception.NotForConsoleException;
import de.BitFire.Core.Exception.SystemHasNoDataToPrintException;
import de.BitFire.Core.Exception.TooFewParameterException;
import de.BitFire.Core.Exception.TooManyParameterException;
import de.BitFire.Player.PlayerSystem;
import de.BitFire.Player.Exception.PlayerDirectionAmbiguous;
import de.BitFire.Sound.SoundSystem;
import de.BitFire.Teleport.TeleportSystem;
import de.BitFire.Time.IUpdateable;

public class DigitalClockSystem extends BaseSystem implements ISystem, IUpdateable
{
	private static DigitalClockSystem _instance;
	private DigitalClockList _clockList;
	
	private SoundSystem _soundSystem;	
	private Logger _logger;
	private PlayerSystem _playerSystem;	
	private TeleportSystem _teleportSystem;
	
    private DigitalClockSystem() 	    
    {
    	super(Module.DigtalClock, SystemState.Active, Priority.Low);
    	_instance = this;
    	
    	_clockList = new DigitalClockList();  
    }
    
    public static DigitalClockSystem Instance()
    {
    	return _instance == null ? new DigitalClockSystem() : _instance;
    }
    
	@Override
	public void LoadReferences() 
	{
    	_playerSystem = PlayerSystem.Instance();    	
    	_soundSystem = SoundSystem.Instance();
    	_logger = Logger.Instance();		
    	_teleportSystem = TeleportSystem.Instance();
	}
	
	
	public void PrintData(final CommandSender sender) throws SystemHasNoDataToPrintException 
	{
		List<DigitalClock> clocks = _clockList.GetClocks();
		String message = "";
		
		for(DigitalClock clock : clocks)
		{
			message += "ID:" + clock.ID + " Name:" + clock.Name + "\n";
		}
		
		_logger.SendToSender(Information.GetSystemModule(), MessageType.None, sender, message);
	}
    
	public void Command(CommandCredentials commandCredentials) throws 	NotForConsoleException, 
    																 	TooFewParameterException, 
    																 	TooManyParameterException, 
    																 	SystemHasNoDataToPrintException, PlayerDirectionAmbiguous 
    {       
		
		
    	switch(commandCredentials.ParameterLengh)
    	{
    	case 0:
    		throw new TooFewParameterException();
    		
    	case 1:
    		// dc <command>    	
    		final String commandP1 = commandCredentials.Parameter[0].toLowerCase();
    		
    		switch(commandP1)
    		{
    		case "list":
    			PrintData(commandCredentials.Sender);
    			break;
    			
    		}
    		
    		break;
    		
    	case 2:   		
    		
    		//dc create <name>
    		final String commandP2 = commandCredentials.Parameter[0].toLowerCase();
    		final String clockName = commandCredentials.Parameter[1].toLowerCase();
    		
    		switch(commandP2)
    		{   
    		case "c":
    		case "create":
    			
    			if(commandCredentials.IsSenderPlayer)
    			{
    			    final Player player = commandCredentials.TryGetPlayer();
    				
    				CreateClock(player, clockName);
    			}
    			else
    			{
    				throw new NotForConsoleException();
    			}    			
    		
    			break;
    		
    		case "delete":
    			//DeleteClock();
    			break;
    			
    		case "rename":
    			//RenameClock();
    			break;
    			
    		case "tp":	
    			
    			if(commandCredentials.IsSenderPlayer)
    			{
    				final DigitalClock clock = _clockList.GetClock(clockName);
        			final Player player = commandCredentials.TryGetPlayer();
    				
        			_teleportSystem.TeleportToLocation(player, clock.AncerLocation);
    			}
    			else
    			{
    				throw new NotForConsoleException();
    			}
    			
    		
    			break;
    		}
    		break;
    		
    	case 4:
    		//dc create <name> <> <>	
    		final String command = commandCredentials.Parameter[0].toLowerCase();
        	final String wantedClockName = commandCredentials.Parameter[1].toLowerCase();
        	final String wantedBaseMaterialText = commandCredentials.Parameter[2].toLowerCase();;
        	final String wantedFillMaterialText = commandCredentials.Parameter[3].toLowerCase();;      		        	
    		
    		switch(command)
    		{
    		case "create":
    			if(commandCredentials.IsSenderPlayer)
    			{
    				final Player player = commandCredentials.TryGetPlayer();       				
    				
    				CreateClock(player, wantedClockName, wantedBaseMaterialText, wantedFillMaterialText);
    			
    				try 
    				{
    					//Update();
					} 
    				catch (Exception e) 
    				{
						e.printStackTrace();
					}
    			}
    			else
    			{
    				throw new NotForConsoleException();
    			}    		
    			break;
    		}
    		break;
    		
    		default:
    			throw new TooManyParameterException();
    	}   
    }    

	@Override
	public void OnTickUpdate()
    {
    	final ClockTimeMap clockTimeMap = LoadClockTimeMap();		
    	final List<DigitalClock> clockList = _clockList.GetClocks();
		
		_logger.SendToConsole(Information.GetSystemModule(), MessageType.None, "Refreshing clocks! Its : " + clockTimeMap.toString());
		
		for(final DigitalClock clock : clockList)
		{
			_soundSystem.PlaySound(clock.AncerLocation, Sound.BLOCK_NOTE_BLOCK_IRON_XYLOPHONE, 0);
			
			UpdateClock(clock, clockTimeMap);
		}
    }
	
	private void CreateClock(final Player player, final String clockName) throws PlayerDirectionAmbiguous
    {  
    	String materialBase = Material.RED_WOOL.toString();
    	String materialFill = Material.WHITE_WOOL.toString();
		
		CreateClock(player, clockName, materialBase, materialFill);
    }	
    
	
    private void CreateClock(Player player, String clockName, String baseMaterial, String fillMaterial) throws PlayerDirectionAmbiguous
    {  
    	final Material base = Material.getMaterial(baseMaterial);
    	final Material fill = Material.getMaterial(fillMaterial);	    	
    	final Location location = BukkitAPIPlayer.GetBlockInfrontOfPlayer(player);    			
    	final DigitalClock clock = new DigitalClock(_clockList.Size(), clockName, location, base, fill); 
    	
    	clock.ClockViewDirection = BukkitAPIPlayer.GetNarrowDirectionFromPlayer(player);
    	
		_clockList.Add(clock);
		
		_logger.SendToSender(Module.System, MessageType.Info, player, "&7Clock &6<&e" + clockName + "&6> &7created. Direction &8:&7 ?");
    }
        
    private void UpdateClock(DigitalClock clock, ClockTimeMap clockTimeMap)
    {       	
    	final int height = 5;
    	final int width = 3;  
    	final Location startLocation = clock.AncerLocation;    	  	
    	final World world = clock.AncerLocation.getWorld();
    	int xPosition = startLocation.getBlockX();
    	final int yPosition = startLocation.getBlockY();
    	final int zPosition = startLocation.getBlockZ();  
    	
    	for(int y = 0 ;  y < height ; y++)
    	{
    		for(int x = 0 ; x < width ; x++)
    		{    			
    			boolean isBlockNumberBlock = clockTimeMap.HourDigitOne[height-1 - y][x];
    			Material material = isBlockNumberBlock ? clock.NumberMaterial : clock.FillMaterial;    			
    			
    			PlaceBlock(world, material, xPosition + x, yPosition + y,zPosition, clock.ClockViewDirection);
    		}
    	}    
    	
    	xPosition += width +1;
    	
    	for(int y = 0 ;  y < height ; y++)
    	{
    		for(int x = 0 ; x < width ; x++)
    		{
    			boolean isBlockNumberBlock = clockTimeMap.HourDigitTwo[height-1 - y][x];
    			Material material = isBlockNumberBlock ? clock.NumberMaterial : clock.FillMaterial;
    		    	
    			PlaceBlock(world, material, xPosition + x, yPosition + y,zPosition, clock.ClockViewDirection);
    		}
    	}
    	
    	xPosition += width;
    	
    	for(int y = 0 ;  y < height ; y++)
    	{
    		for(int x = 0 ; x < width ; x++)
    		{
    			boolean isBlockNumberBlock = clockTimeMap.Divider[height-1 - y][x];
    			Material material = isBlockNumberBlock ? clock.NumberMaterial : clock.FillMaterial;
    		    	       			
    			PlaceBlock(world, material, xPosition + x, yPosition + y,zPosition, clock.ClockViewDirection);
    		}
    	}
    	
    	xPosition += width;
    	
    	for(int y = 0 ;  y < height ; y++)
    	{
    		for(int x = 0 ; x < width ; x++)
    		{
    			boolean isBlockNumberBlock = clockTimeMap.MinuteDigitOne[height-1 - y][x];
    			Material material = isBlockNumberBlock ? clock.NumberMaterial : clock.FillMaterial;
    
    			PlaceBlock(world, material, xPosition + x, yPosition + y,zPosition, clock.ClockViewDirection);
    		}
    	}     	
    	
    	xPosition += width +1;
    	
    	for(int y = 0 ;  y < height ; y++)
    	{
    		for(int x = 0 ; x < width ; x++)
    		{
    			boolean isBlockNumberBlock = clockTimeMap.MinuteDigitTwo[height-1 - y][x];
    			Material material = isBlockNumberBlock ? clock.NumberMaterial : clock.FillMaterial;
     			
    			PlaceBlock(world, material, xPosition + x, yPosition + y,zPosition, clock.ClockViewDirection);
    		}
    	}    
    }
    
	private void PlaceBlock(World world, Material material, int x, int y, int z, BlockFace viewDirection)
    {
		Block block = null;
		
		switch(viewDirection)
		{
		case SOUTH:
			block  = world.getBlockAt(z,y,x);
			break;
		
		case WEST:
			block  = world.getBlockAt(x,y,z);
			break;
		
		case EAST:
			block  = world.getBlockAt(x,y,z);
			break;
			
		case NORTH:
			block  = world.getBlockAt(x,y,z);
			break;
			
		case NORTH_EAST:		
		case NORTH_WEST:	
		case SOUTH_EAST:		
		case SOUTH_WEST:	
		default:
			break;
		
		}
		
		if(block != null)
		{
			block.setType(material);
		}		
    }
        
    private ClockTimeMap LoadClockTimeMap() 
    {
    	LocalTime localTime = GetCurrentTime();
    	
    	return new ClockTimeMap(localTime.getHour(), localTime.getMinute());
    }
    
    private LocalTime GetCurrentTime()
    {
    	return java.time.LocalTime.now(); 	
    }
}