package de.SSC.CoreManager.Systems.SkinChanger;

import org.bukkit.Bukkit;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;
import java.util.UUID;
import java.util.logging.Level;

public class SkinLoader
{
	public SkinData Data;

    public SkinLoader(UUID uuid)
    {
    	Data = new SkinData(uuid);

        LoadSkin();
    }

	@SuppressWarnings("resource")
	private void LoadSkin()
    {
		URL url;
		String json;
		JSONParser parser;
		JSONArray properties;
		Object obj;		
		String uuidString =  Data.UUID.toString().replace("-", ""); 
		
        try
        {
            // Get the name from SwordPVP
            url = new URL("https://sessionserver.mojang.com/session/minecraft/profile/" + uuidString + "?unsigned=false");
            URLConnection uc = url.openConnection();
            uc.setUseCaches(false);
            uc.setDefaultUseCaches(false);
            uc.addRequestProperty("User-Agent", "Mozilla/5.0");
            uc.addRequestProperty("Cache-Control", "no-cache, no-store, must-revalidate");
            uc.addRequestProperty("Pragma", "no-cache");

            // Parse it
			json = new Scanner(uc.getInputStream(), "UTF-8").useDelimiter("\\A").next();
            parser = new JSONParser();
            obj = parser.parse(json);
            
            properties = (JSONArray) ((JSONObject) obj).get("properties");
             
            for (int i = 0; i < properties.size(); i++)
            {
                try
                {
                    JSONObject property = (JSONObject) properties.get(i);
                    String name = (String) property.get("name");
                    String value = (String) property.get("value");
                    String signature = property.containsKey("signature") ? (String) property.get("signature") : null;
                    
                    Data = new SkinData(Data.UUID, name, value, signature);
                } 
                catch (Exception e)
                {
                    Bukkit.getLogger().log(Level.WARNING, "Failed to apply auth property", e);
                }
            }
        }
        catch (Exception e) 
        {
            //throw new SkinLoadException(null);
        }
    }
}
