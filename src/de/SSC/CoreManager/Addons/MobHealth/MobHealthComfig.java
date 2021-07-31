package de.SSC.CoreManager.Addons.MobHealth;

public class MobHealthComfig 
{	  
	 public boolean IsActive;
	 public boolean Debug;
	 public boolean UserPermission;
	 public boolean ShowPlayerHealth;
	 public boolean ShowMobHealth;
	 public boolean ShowInSpectatorMode;
	 public boolean DontShowIfInvisible;
	 public boolean OnlyShowOnLook;
	 public boolean DisableInNonPvPArea;
	 public int ShowDistance;
	 public String FullHealthIcon;
	 public String HalfHealthIcon;
	 public String EmptyHealthIcon;
	 public String HealthMessage;
	 
	 public MobHealthComfig()
	 {
		 IsActive = true;
		 Debug = false;
		 UserPermission = true;
		 ShowPlayerHealth = true;
		 ShowMobHealth = true;
		 ShowInSpectatorMode = true;
		 DontShowIfInvisible = false;
		 DisableInNonPvPArea = true;
		 OnlyShowOnLook = true;		 
		 ShowDistance = 10;		 
		 FullHealthIcon = "&4\u2764";
		 HalfHealthIcon = "&c\u2764";
		 EmptyHealthIcon = "&7\u2764";
		 HealthMessage = "&7&l{name}: {usestyle}";
	 }
}
