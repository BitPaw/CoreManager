package de.SSC.DropHead;

import org.bukkit.DyeColor;
import org.bukkit.entity.TropicalFish;

public class CCP 
{
	public DyeColor bodyColor;
	public DyeColor patternColor;
	public TropicalFish.Pattern pattern;	
	
	public CCP(DyeColor color, DyeColor pColor, TropicalFish.Pattern p) 
	{
	      bodyColor = color;
	      patternColor = pColor;
	      pattern = p;
	}
	    
	public boolean equals(Object o)
	{
		if (o == this) 
		{
			return true;
		}
		if ((o == null) || (o.getClass() != getClass()))
		{
			return false;
		}
		CCP ccp = (CCP)o;
		
		return (ccp.bodyColor == this.bodyColor) && (ccp.patternColor == this.patternColor) && (ccp.pattern == this.pattern);
	}
	    
	public int hashCode()
	{
		return this.bodyColor.hashCode() + 16 * (this.patternColor.hashCode() + 16 * this.pattern.hashCode());
	}	  
}