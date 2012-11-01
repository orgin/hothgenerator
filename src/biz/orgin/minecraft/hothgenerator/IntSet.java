package biz.orgin.minecraft.hothgenerator;

import java.util.HashSet;

public class IntSet extends HashSet<Integer>
{
	private static final long serialVersionUID = -5198684673071092609L;

	public static void main(String[] args)
	{
		IntSet set = new IntSet(new int[] {12,45,67,1,35});
		
		for(int i=0;i<100;i++)
		{
			if(set.contains(i))
			{
				System.out.println("Set contains " + i);
			}
			else
			{
				System.out.println("Set does not contain " + i);
			}
		}
	}
	
	
	public IntSet(int[] values)
	{
		for(int i=0;i<values.length;i++)
		{
			this.add(new Integer(values[i]));
		}
	}
	
	public boolean contains(int value)
	{
		return this.contains(new Integer(value));
	}
}
