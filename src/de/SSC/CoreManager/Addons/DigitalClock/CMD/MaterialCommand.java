// 
// Decompiled by Procyon v0.5.36
// 

package de.SSC.CoreManager.Addons.DigitalClock.CMD;

import org.bukkit.Material;

public class MaterialCommand
{
    protected boolean isUsableNumber(final String s) {
        return s.matches("^[0-9]*([,]{1}[0-9]{0,2}){0,1}$");
    }    
}
