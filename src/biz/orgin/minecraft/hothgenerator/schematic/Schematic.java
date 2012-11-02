package biz.orgin.minecraft.hothgenerator.schematic;

public interface Schematic
{
	public int[][][] getMatrix();
	public int getWidth();
	public int getLength();
	public int getHeight();
	public String getName();
	public Schematic rotate(int direction);
}
