package de.SSC.CoreManager.Addons.DigitalClock;

import java.util.HashMap;

public class ClockMap extends HashMap<Clock, Integer>
{
	  private static final long serialVersionUID = 1L;
	  
	  public Integer getByClockName(String name)
	  {
	    for (Clock c0 : keySet()) {
	      if (c0.getName().equals(name)) {
	        return (Integer)get(c0);
	      }
	    }
	    return Integer.valueOf(-1);
	  }
	  
	  public boolean containsKeyByClockName(String name)
	  {
	    for (Clock c0 : keySet()) {
	      if (c0.getName().equals(name)) {
	        return true;
	      }
	    }
	    return false;
	  }
	  
	  public Integer removeByClockName(String name)
	  {
	    for (Clock c0 : keySet()) {
	      if (c0.getName().equals(name)) {
	        return (Integer)remove(c0);
	      }
	    }
	    return Integer.valueOf(-1);
	  }
}
