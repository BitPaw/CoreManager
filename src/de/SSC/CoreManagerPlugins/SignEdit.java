package de.SSC.CoreManagerPlugins;

import de.SSC.CoreManager.Config.Messages;
import de.SSC.CoreManager.CoreController;
import de.SSC.CoreManager.Main;
import org.bukkit.event.block.SignChangeEvent;

public class SignEdit
{
	private BukkitHook _bukkitHook;

	public SignEdit()
	{
		_bukkitHook =  CoreController.GetInstance()._BukkitHook;
	}
	
	public void OnSignChangeEvent(SignChangeEvent e)
	{   
		for (int i = 0; i < 4; i++) 
		{
			String line = e.getLine(i);
			String coloredLine = "";

			if(line.contains(":l:"))
			{
				Messages messages = Main.messages;
				coloredLine = messages.SignLine;
			}
			else
			{
				coloredLine = line;
			}	

			e.setLine(i,  _bukkitHook.TransformColor(coloredLine));
		}
	}
}