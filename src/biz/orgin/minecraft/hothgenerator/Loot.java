package biz.orgin.minecraft.hothgenerator;

import org.bukkit.Material;

/**
 * Loot definition container
 * @author orgin
 *
 */
public class Loot
{
	public Material material;
	public byte data;
	public int min;
	public int max;
	public int chance;
	
	public Loot(Material material, byte data, int min, int max, int chance)
	{
		this.material = material;
		this.data = data;
		this.min = min;
		this.max = max;
		this.chance = chance;
	}
	
	
}
