package de.SSC.CoreManager.Utility;

import java.util.Random;

import org.bukkit.Color;
import org.bukkit.Effect;
import org.bukkit.FireworkEffect;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;

public class FireWork
{
	private static FireWork _instance;
	
	public static FireWork Instance()
	{
		if(_instance == null)
		{
			_instance = new  FireWork();		
		}
		
		return _instance;
	}
	
    @SuppressWarnings("deprecation")
	public void PlayEffectOnPlayer(Player player, Effect effect,  int power)
    {
        // player.playEffect(player.getLocation(), Effect.SMOKE,2004);
        player.playEffect(player.getLocation(), effect,power);
    }

    public void CreateExplosion(Player player)
    {
        int randomeType;
        Color colorA;
        Color colorB;
        Firework firework = (Firework)player.getWorld().spawnEntity(player.getLocation(), EntityType.FIREWORK);
        FireworkMeta fireworkMeta = firework.getFireworkMeta();
        Random randomValue = new Random();

        FireworkEffect.Type type = FireworkEffect.Type.BALL;
        FireworkEffect effect;

        randomeType = randomValue.nextInt(4) + 1;

        switch(randomeType)
        {
            case 1 : type = FireworkEffect.Type.BALL; break;
            case 2 : type = FireworkEffect.Type.BALL_LARGE; break;
            case 3 : type = FireworkEffect.Type.BURST; break;
            case 4 : type = FireworkEffect.Type.CREEPER; break;
            case 5 : type = FireworkEffect.Type.STAR; break;
        }

        colorA = getColor(randomValue.nextInt(16) + 1);
        colorB = getColor(randomValue.nextInt(16) + 1);

        effect = FireworkEffect.builder().flicker(randomValue.nextBoolean()).withColor(colorA).withFade(colorB).with(type).trail(randomValue.nextBoolean()).build();

        fireworkMeta.addEffect(effect);
        fireworkMeta.setPower(randomValue.nextInt(2) + 1);

        firework.setFireworkMeta(fireworkMeta);
    }

    private Color getColor(int colorCode)
    {
        Color color;

        switch (colorCode)
        {
            case  1 :  color = Color.AQUA; break;
            case  2 :  color = Color.BLACK; break;
            case  3 :  color = Color.BLUE; break;
            case  4 :  color = Color.FUCHSIA; break;
            case  5:  color = Color.GRAY; break;
            case  6:  color = Color.GREEN; break;
            case  7:  color = Color.LIME; break;
            case  8:  color = Color.MAROON; break;
            case  9:  color = Color.NAVY; break;
            case 10:  color = Color.OLIVE; break;
            case 11:  color = Color.ORANGE; break;
            case 12:  color = Color.PURPLE; break;
            case 13:  color = Color.RED; break;
            case 14:  color = Color.SILVER; break;
            case 15:  color = Color.TEAL; break;
            case 16:  color = Color.WHITE; break;
            case 17 :  color = Color.YELLOW; break;
            default :  color = Color.WHITE;
        }
        return color;
    }

}
