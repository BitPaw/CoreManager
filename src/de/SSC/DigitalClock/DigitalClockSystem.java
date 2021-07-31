package de.SSC.DigitalClock;

import java.time.LocalTime;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import de.SSC.CoreManager.Chat.Logger;
import de.SSC.CoreManager.Chat.MessageType;
import de.SSC.CoreManager.Chat.Module;
import de.SSC.CoreManager.Player.PlayerSystem;
import de.SSC.CoreManager.Player.ViewDirection;
import de.SSC.CoreManager.Player.Exception.PlayerNotFacingDirectionException;
import de.SSC.CoreManager.Sound.SoundSystem;
import de.SSC.CoreManager.System.BaseSystem;
import de.SSC.CoreManager.System.IRunnableSystem;
import de.SSC.CoreManager.System.ISystem;
import de.SSC.CoreManager.System.SystemPriority;
import de.SSC.CoreManager.System.SystemState;
import de.SSC.CoreManager.System.Exception.NotForConsoleException;

public class DigitalClockSystem extends BaseSystem implements ISystem, IRunnableSystem
{
	private static DigitalClockSystem _instance;
	private ClockList _clockList;
	private SoundSystem _soundSystem;
	private Logger _logger;
	private PlayerSystem _playerSystem;
	
	
    private DigitalClockSystem() 	    
    {
    	super(Module.DigtalClock, SystemState.Inactive, SystemPriority.Low);
    	_instance = this;
    	
    	_clockList = new ClockList();  
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
	}

	@Override
	public void Reload(final boolean firstRun) 
	{		
		
	}
	
	@Override
	public void PrintData(CommandSender sender) 
	{
		
	}
    
    public void DigitalClockCommand(CommandSender sender, final String[] args) throws 	NotForConsoleException, 
    																					PlayerNotFacingDirectionException 
    {      
    	boolean isPlayerSender = sender instanceof Player;
    	int parameterLengh = args.length;    
    	String command;    	
    	String wantedClockName;
    	String wantedBaseMaterialText;
    	String wantedFillMaterialText;  	
    	Player player;
    	
    	
    	switch(parameterLengh)
    	{
    	case 0:
    		break;
    		
    	case 1:
    		command = args[0].toLowerCase();
    		break;
    	
    	case 2:   		
    		
    		//dc create <name>
    		command = args[0].toLowerCase();
    		//clockName = args[1].toLowerCase();
    		
    		switch(command)
    		{   
    		case "delete":
    			DeleteClock();
    			break;
    			
    		case "rename":
    			RenameClock();
    			break;
    		}
    		break;
    		
    	case 4:
    		//dc create <name> <> <>		    		
    		command = args[0].toLowerCase();
    		
    		switch(command)
    		{
    		case "create":
    			if(isPlayerSender)
    			{
    				player = (Player)sender;       				
    				    				
    				wantedClockName = args[1].toLowerCase();
    	    		wantedBaseMaterialText = args[2];
    	    		wantedFillMaterialText = args[3];
    				
    				CreateClock(player, wantedClockName, wantedBaseMaterialText, wantedFillMaterialText);
    			
    				try {
						Update();
					} catch (Exception e) {
						// TODO Auto-generated catch block
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
    	}   
    }
    

    
    private void CreateClock(Player player, String clockName, String baseMaterial, String fillMaterial) throws PlayerNotFacingDirectionException
    {  
    	ViewDirection viewDirection = _playerSystem.GetViewDirection(player);  
    	boolean isFacingAWay = _playerSystem.IsLookingInADirection(viewDirection);
    	Material base = Material.getMaterial(baseMaterial);
    	Material fill = Material.getMaterial(fillMaterial);	
    	Clock clock; 
    	
		if(!isFacingAWay)
		{
			throw new PlayerNotFacingDirectionException(viewDirection);
		}
		
		clock = new Clock(_clockList.Size(), clockName, base, fill, player.getLocation(), viewDirection); 
    	
		_clockList.Add(clock);
		
		_logger.SendToSender(Module.System, MessageType.Info, player, "&7Clock &6<&e" + clockName + "&6> &7created. Direction &8:&7 " + viewDirection);
    }
    
    private void DeleteClock()
    {
    	
    }
    
    private void RenameClock()
    {
    	
    }   
    
    private void UpdateClock(Clock clock, ClockTimeMap clockTimeMap)
    {       	
    	final int height = 5;
    	final int width = 3;    
    	    
    	
    	Location startLocation = clock.AncerLocation;
    	int xPosition = startLocation.getBlockX();
    	int yPosition = startLocation.getBlockY();
    	int zPosition = startLocation.getBlockZ();
    	
    	World world = clock.AncerLocation.getWorld();
    	   	
    	
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
    
	private void PlaceBlock(World world, Material material, int x, int y, int z, ViewDirection viewDirection)
    {
		Block block = null;
		
		switch(viewDirection)
		{
		case South:
			block  = world.getBlockAt(z,y,x);
			break;
		
		case West:
			block  = world.getBlockAt(x,y,z);
			break;
		
		case East:
			block  = world.getBlockAt(x,y,z);
			break;
			
		case North:
			block  = world.getBlockAt(x,y,z);
			break;
			
		case NorthEast:		
		case NorthWest:	
		case SouthEast:		
		case SouthWest:	
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

    public void Update()
    {
    	ClockTimeMap clockTimeMap = LoadClockTimeMap();		
		List<Clock> clockList = _clockList.GetClocks();
		
		for(Clock clock : clockList)
		{
			_soundSystem.PlaySound(clock.AncerLocation, Sound.BLOCK_NOTE_BLOCK_IRON_XYLOPHONE, 0);
			
			UpdateClock(clock, clockTimeMap);
		}
    }
}