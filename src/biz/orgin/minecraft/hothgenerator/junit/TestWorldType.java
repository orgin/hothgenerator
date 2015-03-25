package biz.orgin.minecraft.hothgenerator.junit;

import static org.junit.Assert.*;

import org.junit.Test;

import biz.orgin.minecraft.hothgenerator.WorldType;
import biz.orgin.minecraft.hothgenerator.WorldType.InvalidWorldTypeException;

public class TestWorldType
{

	@Test
	public void testEquals()
	{
		try
		{
			WorldType wt = WorldType.getType("dagobah");
			assertTrue(wt.equals(WorldType.DAGOBAH));
			assertTrue(wt == WorldType.DAGOBAH);
			assertTrue(wt.equals("dagobah"));
		}
		catch(InvalidWorldTypeException e)
		{
			fail("dagobah is not an invalid WorldType");
		}
		
		System.out.println("End of line.");
	}
	
	@Test
	public void testGetType()
	{
		try
		{
			WorldType wt = WorldType.getType("hoth");
			assertTrue(wt == WorldType.HOTH);
			wt = WorldType.getType("tatooine");
			assertTrue(wt == WorldType.TATOOINE);
			wt = WorldType.getType("dagobah");
			assertTrue(wt == WorldType.DAGOBAH);
			wt = WorldType.getType("mustafar");
			assertTrue(wt == WorldType.MUSTAFAR);
		}
		catch(InvalidWorldTypeException e)
		{
			fail("dagobah is not an invalid WorldType");
		}

		try
		{
			WorldType.getType("earth");
			fail("earth is not an valid WorldType");
		}
		catch(InvalidWorldTypeException e)
		{
		}
		
		System.out.println("End of line.");
	}

}
