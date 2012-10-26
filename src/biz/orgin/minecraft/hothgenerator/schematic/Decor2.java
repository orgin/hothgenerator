package biz.orgin.minecraft.hothgenerator.schematic;

public class Decor2 implements Schematic
{
	public static Schematic instance = new Decor2();
	private static int WIDTH = 3;
	private static int LENGTH = 3;
	private static int HEIGHT = 3;
	private static String name = "Decor2";
	
	private final int[][][] matrix = new int[][][]
	{
		    //   TYPEID                       DATAID
		{   // Layer 0
			{ -1, 44, -1,   0, 0, 0},
			{ 44, 10, 44,   0, 0, 0},
			{ -1, 44, -1,   0, 0, 0}
		},
		{   // Layer 1
			{ -1, 44, -1,   0, 0, 0},
			{ 44, 10, 44,   0, 0, 0},
			{ -1, 44, -1,   0, 0, 0}
		},
		{   // Layer 2
			{ -1, 44, -1,   0, 0, 0},
			{ 44, 10, 44,   0, 0, 0},
			{ -1, 44, -1,   0, 0, 0}
		}
	};
	
	private Decor2()
	{
	}
	
	public int getWidth() // Inner
	{
		return Decor2.WIDTH;
	}
	public int getLength() // Middle
	{
		return Decor2.LENGTH;
	}
	public int getHeight() // Outer
	{
		return Decor2.HEIGHT;
	}
	
	public int[][][] getMatrix()
	{
		return this.matrix;
	}

	@Override
	public String getName()
	{
		return Decor2.name;
	}
}