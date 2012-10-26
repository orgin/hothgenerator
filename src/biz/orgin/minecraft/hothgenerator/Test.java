package biz.orgin.minecraft.hothgenerator;

import java.util.Random;

import biz.orgin.minecraft.hothgenerator.schematic.CorridorNS;
import biz.orgin.minecraft.hothgenerator.schematic.Schematic;

public class Test
{
	public static boolean done(int[] buffer)
	{
		return !Test.hasVal(buffer, -1);
	}
	
	public static boolean hasVal(int[] buffer, int val)
	{
		for(int i=0;i<6;i++)
		{
			if(buffer[i]==val)
			{
				return true;
			}
		}
		
		return false;
	}
	
	public static void add(int[] buffer, int val)
	{
		for(int i=0;i<6;i++)
		{
			if(buffer[i]==-1)
			{
				buffer[i]=val;
				return;
			}
		}
	}
	
	public static void print(int[] buffer)
	{
		StringBuffer mySB = new StringBuffer("{");
		for(int i=0;i<6;i++)
		{
			if(i!=0) mySB.append(",");
			mySB.append(buffer[i]);
		}
		mySB.append("},");
		System.out.println(mySB);
	}

	
	public static void main(String[] args)
	{
		Random random = new Random(1765);

		for(int j=0;j<64;j++)
		{
			int buffer[] = new int[]{-1,-1,-1,-1,-1,-1};
			while(!Test.done(buffer))
			{
				int val = random.nextInt(6);
				if(!Test.hasVal(buffer, val)) Test.add(buffer, val);
			}
			Test.print(buffer);
		}
		
		
		
		//Test test = new Test();
		StringBuffer mySB = new StringBuffer();
		
		Schematic schematic = CorridorNS.instance;
		
		int height = schematic.getHeight();
		int length = schematic.getLength();
		int width = schematic.getWidth();
		int[][][] matrix = schematic.getMatrix();
		
		System.out.println("Placing " + schematic.getName() + "  h=" + height + " l=" + length + " w="+ width);
		System.out.println("Was " + schematic.getName() + "  h=" + matrix.length + " l=" + matrix[0].length + " w=" + matrix[0][0].length);
		

		for(int yy=0;yy<height;yy++)
		{
			for(int zz=0;zz<length;zz++)
			{
				for(int xx=0;xx<width;xx++)
				{
					int type = matrix[yy][zz][xx];
					byte data = (byte)matrix[yy][zz][xx+width];
					
					mySB.append(type+"("+data+") ");
					

				}
				
				System.out.println(mySB);
				mySB.setLength(0);
			}
			System.out.println("");
		}
		
		
		/*
		NoiseGenerator generator = new NoiseGenerator(764523);
		
		double max = 0;
		double min = 0;

		for(int z=-20;z<20;z++)
		{
			StringBuffer mySB = new StringBuffer();
			for(int x=-20;x<20;x++)
			{
				double noice = generator.noise(x, z, 8, 11);
				int val = (int)(Math.round(noice*5));
				if(noice>max) max=noice;
				if(noice<min) min=noice;
				mySB.append(val).append(",");
			}
			
			System.out.println(mySB);
		}
		
		System.out.println("min = " + min + " max = " + max + " diff " + (max - min));
		
		*/
	}
	
	public Test(Random ran)
	{

	}
	
	public Test()
	{
	}
	
}
