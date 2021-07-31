package de.BitFire.CoreManager.Modules.Chat;

import org.bukkit.ChatColor;

public class ColorManager 
{
	  public final static String ColorBlack = "&0";
	  public final static String ColorDarkBlue = "&1";
	  public final static String ColorDarKGreen = "&2";
	  public final static String ColorDarkCyan = "&3";
	  public final static String ColorDarkRed = "&4";
	  public final static String ColorPurple = "&5";
	  public final static String ColorGold = "&6";
	  public final static String ColorLightGray = "&7";
	  public final static String ColorGray = "&8";
	  public final static String ColorAqua = "&9";
	  public final static String ColorLightGreen = "&a";
	  public final static String ColorLightBlue = "&b";
	  public final static String ColorLightRed = "&c";
	  public final static String ColorPink = "&d";
	  public final static String ColorYellow = "&e";
	  public final static String ColorWhite = "&f";

	  public final static String ColorBold = "&l";
	  public final static String ColorMagic = "&k";
	  public final static String ColorReset = "&r";
	  public final static String ColorItalic = "&o";
	  public final static String ColorUnderline = "&n";
	  public final static String ColorStrikethrough = "&m";
	  
	  public final static char ColorChar = '&';
	  
	    public static String AddColor(final String message)
	    {    	
	    	return ChatColor.translateAlternateColorCodes(ColorChar, message);
	    }
	  
	  public static String RemoveColor(final String message)
	  {
		  return ChatColor.stripColor(message);
	  }
	  
	  public static String GetColorCode(final ChatColor chatColor)
	  {	
		    switch(chatColor)
		    {
			case AQUA:			return ColorAqua;				
			case BLACK:			return ColorBlack;			
			case BLUE:			return ColorLightBlue;			
			case BOLD:			return ColorBold;			
			case DARK_AQUA:		return ColorDarkCyan;			
			case DARK_BLUE:		return ColorDarkBlue;			
			case DARK_GRAY:		return ColorGray;			
			case DARK_GREEN:	return ColorDarKGreen;			
			case DARK_PURPLE:	return ColorPurple;			
			case DARK_RED:		return ColorDarkRed;			
			case GOLD:			return ColorGold;			
			case GRAY:			return ColorLightGray;			
			case GREEN:			return ColorLightGreen;			
			case ITALIC:		return ColorItalic;			
			case LIGHT_PURPLE:	return ColorPink;			
			case MAGIC:			return ColorMagic;			
			case RED:			return ColorLightRed;
			case STRIKETHROUGH:	return ColorStrikethrough;			
			case UNDERLINE:		return ColorUnderline;			
			case WHITE:			return ColorWhite;			
			case YELLOW:		return ColorYellow;			
			
			case RESET:
			default:
				return ColorReset;			
		    
		    }
	  }
	  
	  public static ChatColor GetColorCode(final String colorTag)
	    {	    	
	    	switch(colorTag)
	    	{
	    	case ColorLightBlue: 
	    		return ChatColor.AQUA;	 
	    		
	    	case ColorBlack:  	
	    		return ChatColor.BLACK;
	    		
	    	case ColorAqua:	   
	    		return ChatColor.BLUE;	  
	    		
	    	case ColorBold:	
	    		return ChatColor.BOLD;
	    		
	    	case ColorDarkCyan:
	    		return ChatColor.DARK_AQUA;
	    		
	    	case ColorDarkBlue:
	    		return ChatColor.DARK_BLUE;	 
	    		
	    	case ColorGray:
	    		return ChatColor.DARK_GRAY;	
	    		
	    	case ColorDarKGreen:
	    		return ChatColor.DARK_GREEN;
	    		
	    	case ColorPurple:
	    		return ChatColor.DARK_PURPLE;
	    		
	    	case ColorDarkRed:
	    		return ChatColor.DARK_RED;
	    		
	    	case ColorGold:
	    		return ChatColor.GOLD;	
	    		
	    	case ColorLightGray:
	    		return ChatColor.GRAY;
	    		
	    	case ColorLightGreen:
	    		return ChatColor.GREEN;
	    		
	    	case ColorItalic:
	    		return ChatColor.ITALIC;
	    		
	    	case ColorPink:	
	    		return ChatColor.LIGHT_PURPLE;	
	    		
	    	case ColorMagic:	
	    		return ChatColor.MAGIC;	
	    		
	    	case ColorLightRed:
	    		return ChatColor.RED;	
	    		
	    	case ColorReset:
	    		return ChatColor.RESET;	  
	    		
	    	case ColorStrikethrough: 
	    		return ChatColor.STRIKETHROUGH;
	    		
	    	case ColorUnderline: 
	    		return ChatColor.UNDERLINE;	
	    		
	    	case ColorYellow: 
	    		return ChatColor.YELLOW;
	    		
	    	case ColorWhite:
	    	default:   
	    		return ChatColor.WHITE;
	    	
	    	}				 
	    }
	  
	  public static String PrintColors()
	    {
	    	String message = "";
	    	
	    	message += "&6===[&eColors&6]============================\n";	
	    	message += "&7[&r& 0&7] &0Black      &8| &7[&r& a&7] &aGreen\n";	
	    	message += "&7[&r& 1&7] &1Dark-Blue  &8| &7[&r& b&7] &bCyan\n";	
	    	message += "&7[&r& 2&7] &2Dark-Green &8| &7[&r& c&7] &cRed\n";	
	    	message += "&7[&r& 3&7] &3Dark-Cyan  &8| &7[&r& d&7] &dPink\n";	
	    	message += "&7[&r& 4&7] &4Dark-Red   &8| &7[&r& e&7] &eYellow\n";	
	    	message += "&7[&r& 5&7] &5Purple     &8| &7[&r& f&7] &rWhite\n";	
	    	message += "&7[&r& 6&7] &6Gold       &8| &7[&r& o&7] &oItalic\n";	
	    	message += "&7[&r& 7&7] &7Light-Gray &8| &7[&r& l&7] &lBold\n";	
	    	message += "&7[&r& 8&7] &8Gray       &8| &7[&r& m&7] &mStrikethrough\n";	
	    	message += "&7[&r& 9&7] &9Blue       &8| &7[&r& n&7] &nUnderline\n";	
	    	message	+= "&7[&r& r&7] &rReset      &8| &7[&r& k&7] &kRANDOME \n";			
	    	message += "&6=============================================";
	    	
	    	return message;
	    }
}
