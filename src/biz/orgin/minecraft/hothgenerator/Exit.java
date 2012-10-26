package biz.orgin.minecraft.hothgenerator;

import biz.orgin.minecraft.hothgenerator.schematic.Schematic;

public class Exit
{
	public int x;
	public int y;
	public int z;
	public Schematic schematic;
	
	public Exit(int x, int y, int z, Schematic schematic)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.schematic = schematic;
	}
}
