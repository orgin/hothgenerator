package biz.orgin.minecraft.hothgenerator;

import java.util.Random;

import biz.orgin.minecraft.hothgenerator.schematic.BaseRoom1;
import biz.orgin.minecraft.hothgenerator.schematic.Schematic;

/**
 * Just a test class. Has no meaning for the world generator itself
 * @author orgin
 *
 */
public class Test
{
	public static void main(String[] args)
	{

		Schematic schematic = BaseRoom1.instance;
		Schematic rot = HothUtils.rotateSchematic(3, schematic);
		
		System.out.println(HothUtils.schematicToString(rot));
		
		
	}
	
	public Test(Random ran)
	{

	}
	
	public Test()
	{
	}
	
}
