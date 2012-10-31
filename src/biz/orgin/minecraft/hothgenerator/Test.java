package biz.orgin.minecraft.hothgenerator;

import java.util.Random;

/**
 * Just a test class. Has no meaning for the world generator itself
 * @author orgin
 *
 */
public class Test
{
	public static void main(String[] args)
	{
		int x = -5;
		int z = -5;
		
		double angle = Math.atan2(z, x);
		
		double fx = Math.cos(angle);
		double fz = Math.sin(angle);
		
		System.out.println("Angle = " + angle * 180/Math.PI);
		System.out.println("fx = " + fx + " fz = " + fz);
		
	}
	
	public Test(Random ran)
	{

	}
	
	public Test()
	{
	}
	
}
