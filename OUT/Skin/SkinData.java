package de.BitFire.Skin;

import java.util.UUID;

import com.mojang.authlib.properties.Property;

public class SkinData 
{
	public final UUID UUID;
    public final String Name;
    public final String Value;
    public final String Signatur;
    
    public SkinData(UUID uuid)
    {
    	this(uuid, null,null,null);
    }
    
    public SkinData(UUID uuid, String name, String value, String signatur)
    {
    	UUID = uuid;
    	Name = name;
    	Value = value;
    	Signatur = signatur;
    }
    
    public Property GetProperty()
    {
    	return new Property(Name, Value, Signatur);
    }
}
