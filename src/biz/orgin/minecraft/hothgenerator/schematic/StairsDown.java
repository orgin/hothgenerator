package biz.orgin.minecraft.hothgenerator.schematic;

public class StairsDown implements Schematic
{
	public static Schematic instance = new StairsDown();
	private static int WIDTH = 5;
	private static int LENGTH = 5;
	private static int HEIGHT = 2;
	private static String name = "StairsDown";
	
	private final int[][][] matrix = new int[][][]
	{
		    //   TYPEID            DATAID
		{   // Layer 0
			{ -1, -1, -1, -1, -1,   0, 0, 0, 0, 0},
			{ -1,  0,  0, 98, -1,   0, 0, 0, 0, 0},
			{ -1,  0, 98, 98, -1,   0, 0, 0, 0, 0},
			{ -1,109, 98, 98, -1,   0, 2, 0, 0, 0},
			{ -1, -1, -1, -1, -1,   0, 0, 0, 0, 0}
		},
		{   // Layer 1
			{ -1, 98, 98, 98, -1,   0, 0, 0, 0, 0},
			{ 98,  0,  0,  0, 98,   0, 0, 0, 0, 0},
			{ 98,109, 89, 98, 98,   0, 2, 0, 0, 0},
			{ 98,  0,  0,  0, 98,   0, 0, 0, 0, 0},
			{ -1, 98, 98, 98, -1,   0, 0, 0, 0, 0}
		},
	};
	
	
	private StairsDown()
	{
	}
	
	public int getWidth() // Inner
	{
		return StairsDown.WIDTH;
	}
	public int getLength() // Middle
	{
		return StairsDown.LENGTH;
	}
	public int getHeight() // Outer
	{
		return StairsDown.HEIGHT;
	}
	
	public int[][][] getMatrix()
	{
		return this.matrix;
	}

	@Override
	public String getName()
	{
		return StairsDown.name;
	}
}