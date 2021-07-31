package de.SSC.CoreManager.Ranks;

import java.util.List;

public class RankCredentials
{
    public boolean IsDefault;
    public String RankName;
    public String ColorCode;
    public String PlayerColor;
    public List<String> Permissions;

    public RankCredentials(String groupRank, String tag, String playerColor, List<String> groupPermissions, boolean isDefault)
    {
        RankName = groupRank;
        ColorCode = tag;
        PlayerColor = playerColor;
        Permissions = groupPermissions;
        IsDefault = isDefault;
    }
}
