package de.SSC.CoreManager.Addons.DropHead;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;

public class DropHeadConfig 
{
	  boolean playerKillsOnly = true;
	  boolean allowIndirectKills =  false;
	  boolean allowProjectileKills = false;
	  boolean playerHeadsOnly =  false;
	  boolean useTaylorModifiers =  true;
	  boolean chargedCreepers =  true;
	  double lootingBonus =  0.4D;
	  Random rand = new Random();
	  HashSet<Material> mustUseTools = new HashSet<Material>();
	  HashSet<EntityType> noLootingEffectMobs;
	  HashMap<EntityType, Double> mobChances;
	  HashMap<Material, Double> toolBonuses;
	  double DEFAULT_CHANCE;
	  
	  public DropHeadConfig()
	  {
		  
	  }
}
