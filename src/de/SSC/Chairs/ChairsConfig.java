package de.SSC.Chairs;

import java.util.EnumMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.bukkit.Material;

public class ChairsConfig 
{
	    protected static final String sitConfigSectionPath = "sit-config";
	    protected static final String sitConfigDisabledWorldsPath = "disabled-worlds";
	    protected static final String sitConfigDistancePath = "distance";
	    protected static final String sitConfigRequireEmptyHandPath = "require-empty-hand";
	    protected static final String sitConfigStairsSectionPath = "stairs";
	    protected static final String sitConfigStairsEnabledPath = "enabled";
	    protected static final String sitConfigStairsRotatePath = "rotate";
	    protected static final String sitConfigStairsMaxWidthPath = "max-width";
	    protected static final String sitConfigStairsSpecialEndPath = "special-end";
	    protected static final String sitConfigStairsSpecialEndSignPath = "sign";
	    protected static final String sitConfigStairsSpecialEndCornerStairsPath = "corner-stairs";
	    protected static final String sitConfigAdditionalChairsPath = "additional-blocks";
	    protected static final String sitEffectsSectionPath = "sit-effects";
	    protected static final String sitEffectsHealingSectionPath = "healing";
	    protected static final String sitEffectsHealingEnabledPath = "enabled";
	    protected static final String sitEffectsHealingMaxPercentPath = "max-percent";
	    protected static final String sitEffectsHealingIntervalPath = "interval";
	    protected static final String sitEffectsHealingAmountPath = "amount";
	    protected static final String sitEffectsItempickupPath = "itempickup";
	    protected static final String sitEffectsItempickupEnabledPath = "enabled";
	    protected static final String sitRestrictionsSectionPath = "sit-restrictions";
	    protected static final String sitRestricitonsCommandsSectionPath = "commands";
	    protected static final String sitRestrictionsCommandsBlockAllPath = "all";
	    protected static final String sitRestrictionsCommandsBlockListPath = "list";
	    protected static final String msgSectionPath = "messages";
	    protected static final String msgEnabledPath = "enabled";
	    protected static final String msgSitSectionPath = "sit";
	    protected static final String msgSitEnterPath = "enter";
	    protected static final String msgSitLeavePath = "leave";
	    protected static final String msgSitEnabledPath = "enabled";
	    protected static final String msgSitDisabledPath = "disabled";
	    protected static final String msgSitCommandRestrictedPath = "commandrestricted";
	    public final Set<String> sitDisabledWorlds;
	    public boolean sitRequireEmptyHand;
	    public double sitMaxDistance;
	    public boolean stairsEnabled;
	    public boolean stairsAutoRotate;
	    public int stairsMaxWidth;
	    public boolean stairsSpecialEndEnabled;
	    public boolean stairsSpecialEndSign;
	    public boolean stairsSpecialEndCornerStairs;
	    public final Map<Material, Double> additionalChairs;
	    public boolean effectsHealEnabled;
	    public int effectsHealMaxHealth;
	    public int effectsHealInterval;
	    public int effectsHealHealthPerInterval;
	    public boolean effectsItemPickupEnabled;
	    public boolean restrictionsDisableAllCommands;
	    public final Set<String> restrictionsDisabledCommands;
	    public boolean msgEnabled;
	    public String msgSitEnter;
	    public String msgSitLeave;
	    public String msgSitDisabled;
	    public String msgSitEnabled;
	    public String msgSitCommandRestricted;
	    
	    protected ChairsConfig() {
	        this.sitDisabledWorlds = new HashSet<String>();
	        this.sitRequireEmptyHand = false;
	        this.sitMaxDistance = 2.0;
	        this.stairsEnabled = true;
	        this.stairsAutoRotate = true;
	        this.stairsMaxWidth = 16;
	        this.stairsSpecialEndEnabled = false;
	        this.stairsSpecialEndSign = true;
	        this.stairsSpecialEndCornerStairs = true;
	        this.additionalChairs = new EnumMap<Material, Double>(Material.class);
	        this.effectsHealEnabled = false;
	        this.effectsHealMaxHealth = 100;
	        this.effectsHealInterval = 20;
	        this.effectsHealHealthPerInterval = 1;
	        this.effectsItemPickupEnabled = false;
	        this.restrictionsDisableAllCommands = false;
	        this.restrictionsDisabledCommands = new HashSet<String>();
	        this.msgEnabled = true;
	        this.msgSitEnter = "&7You are now sitting.";
	        this.msgSitLeave = "&7You are no longer sitting.";
	        this.msgSitDisabled = "&7You have disabled chairs for yourself!";
	        this.msgSitEnabled = "&7You have enabled chairs for yourself!";
	        this.msgSitCommandRestricted = "&7You can't issue this command while sitting";
	    }
}
