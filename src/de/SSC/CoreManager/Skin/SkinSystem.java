package de.SSC.CoreManager.Skin;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.UUID;

import org.bukkit.command.CommandSender;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import de.SSC.BukkitAPI.BukkitAPISystem;
import de.SSC.CoreManager.Chat.Logger;
import de.SSC.CoreManager.Chat.MessageType;
import de.SSC.CoreManager.Chat.Module;
import de.SSC.CoreManager.Player.Exception.PlayerDoesNotExistException;
import de.SSC.CoreManager.Skin.Exception.OfflineSkinLoadException;
import de.SSC.CoreManager.Skin.Exception.SkinLoadException;
import de.SSC.CoreManager.System.BaseSystem;
import de.SSC.CoreManager.System.ISystem;
import de.SSC.CoreManager.System.SystemPriority;
import de.SSC.CoreManager.System.SystemState;

public class SkinSystem extends BaseSystem implements ISystem
{
	private static SkinSystem _instance;
	private boolean _debug;
	private SkinList _skinList;
	private BukkitAPISystem _bukkitAPISystem;
	private Logger _logger;

    private SkinSystem()
    {
    	super(Module.SkinSystem, SystemState.Active, SystemPriority.Low);
    	_instance = this;
 
    	_debug = true;    	
    	_skinList = new SkinList();
    }
    
    public static SkinSystem Instance()
    {
    	return _instance == null ? new SkinSystem() : _instance;
    }

	@Override
	public void LoadReferences() 
	{
    	_bukkitAPISystem = BukkitAPISystem.Instance();
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
    
	public SkinData GetSkin(UUID playerUUID) throws OfflineSkinLoadException, 
													PlayerDoesNotExistException, 
													SkinLoadException 
	{
		final boolean doesSkinExist = _skinList.IsSkinCached(playerUUID);
		SkinData skinData; 
		
		if(doesSkinExist)
		{
			skinData = _skinList.Get(playerUUID);
		}
		else
		{
			skinData = LoadSkinFromMojang(playerUUID);
			
			_skinList.Add(skinData); 
		}
		
		return skinData;
	}
	
	private SkinData LoadSkinFromMojang(UUID playerUUID) throws OfflineSkinLoadException, 
																PlayerDoesNotExistException, 
																SkinLoadException 
    {		
		final boolean isServerCracked = _bukkitAPISystem.Server.IsServerCracked();
				
		if(isServerCracked)
		{
			throw new OfflineSkinLoadException();
		}
		else
		{
			try 
			{
				final String uuidString =  playerUUID.toString().replace("-", ""); 
				final URL url = new URL("https://sessionserver.mojang.com/session/minecraft/profile/" + uuidString + "?unsigned=false");
				URLConnection urlConnection = url.openConnection();
				
				urlConnection.setUseCaches(false);
				urlConnection.setDefaultUseCaches(false);
				urlConnection.addRequestProperty("User-Agent", "Mozilla/5.0");
				urlConnection.addRequestProperty("Cache-Control", "no-cache, no-store, must-revalidate");
				urlConnection.addRequestProperty("Pragma", "no-cache");				
		
				@SuppressWarnings("resource")
				final String json = new Scanner(urlConnection.getInputStream(), "UTF-8").useDelimiter("\\A").next();
				final JSONParser parser = new JSONParser();
		        final JSONObject jsonObject = (JSONObject) parser.parse(json);	        
		        final JSONArray properties = (JSONArray) jsonObject.get("properties");
		         		        
		        final JSONObject property = (JSONObject) properties.get(0);
            	final String name = (String) property.get("name");
            	final String value = (String) property.get("value");
            	final String signature = property.containsKey("signature") ? (String) property.get("signature") : null;
            
            	final SkinData skinData = new SkinData(playerUUID, name, value, signature);
            	
            	if(_debug)
  		        {
            		final String message =	"&7Skin &aloaded &7with data: \n" + 
            								"&eUUID &6: &d" + skinData.UUID + "\n" +
            								"&eName &6: &d" + skinData.Name + "\n" +
            								"&eValue &6: &d" + skinData.Value + "\n" +
            								"&eSignatur &6: &d" + skinData.Signatur + "\n";
            		
  		        	_logger.SendToConsole(Module.SkinSystem, MessageType.Loading, message);
  		        }
            	  
            	return skinData;
		        
		      		       
		        
		     
			} 
			catch (MalformedURLException e) 
			{
				e.printStackTrace();
			}
			catch (IOException e) 
			{
				e.printStackTrace();
			} 
			catch (ParseException e) 
			{
				e.printStackTrace();
			}  
			catch(NoSuchElementException noSuchElementException)
			{											
				throw new PlayerDoesNotExistException(playerUUID);
			}
			
			throw new SkinLoadException();
		}		
    }
}