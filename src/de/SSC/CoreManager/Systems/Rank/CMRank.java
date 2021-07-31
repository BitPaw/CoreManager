package de.SSC.CoreManager.Systems.Rank;

public class CMRank 
{
  public String RankName;
  public String ColorTag;
  public String PlayerColor;
  public boolean IsDefault;
  
  public CMRank(String name, String colorTag, String playerColor, boolean isDefault)
  {
	   RankName = name;
	    ColorTag = colorTag;
	    PlayerColor = playerColor;
	    IsDefault = isDefault;
  }
}
