package de.SSC.CoreManager.SignEdit;

import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;

public class EditableSignConfig
{
  String error_msg;
  EditableSign plugin;
  boolean useWorldGuard;
  boolean useRegionName;
  List<String> regions = new ArrayList<String>();
  int sign_cooldown;
  int write_cooldown;
  boolean logging;
  
  EditableSignConfig(EditableSign plugin, FileConfiguration config)
  {
    this.plugin = plugin;
    load(config);
  }
  
  void load(FileConfiguration config)
  {
    this.error_msg = config.getString("ErrorMessage", "&c [VK] : Some error occured.").replace('&', '�');
    this.useWorldGuard = config.getBoolean("WorldGuard", true);
    this.useRegionName = config.getBoolean("UseRegionName", true);
    this.sign_cooldown = config.getInt("SignCoolDown", 0);
    this.write_cooldown = config.getInt("WriteCoolDown", 0);
    this.regions = config.getStringList("Regions");
    this.logging = config.getBoolean("Logging", false);
  }
  
  void setErrorMsg(String msg)
  {
    this.error_msg = msg.replace('&', '�');
  }
  
  String getErrorMsg()
  {
    return this.error_msg;
  }
  
  void save(FileConfiguration config)
  {
    config.set("ErrorMessage", this.error_msg.replace('�', '&'));
    config.set("SignCoolDown", Integer.valueOf(this.sign_cooldown));
    config.set("WriteCoolDown", Integer.valueOf(this.write_cooldown));
    config.set("WorldGuard", Boolean.valueOf(this.useWorldGuard));
    config.set("UserRegionName", Boolean.valueOf(this.useRegionName));
    config.set("Regions", this.regions);
    config.set("Logging", Boolean.valueOf(this.logging));
  }
}
