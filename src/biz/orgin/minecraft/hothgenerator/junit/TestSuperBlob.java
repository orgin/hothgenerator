package biz.orgin.minecraft.hothgenerator.junit;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.Test;

import biz.orgin.minecraft.hothgenerator.Position;
import biz.orgin.minecraft.hothgenerator.SuperBlob;
import biz.orgin.minecraft.hothgenerator.SuperBlobType;

public class TestSuperBlob {

	@Test
	public void test()
	{
		SuperBlob superBlob = new SuperBlob(SuperBlobType.GENERIC);
		
		
		superBlob.addPosition(new Position(0,0,0,10));
		superBlob.addPosition(new Position(1,0,1,10));
		superBlob.addPosition(new Position(0,0,2,10));
		superBlob.addPosition(new Position(3,0,0,10));
		superBlob.addPosition(new Position(19,0,3,10));
		
		superBlob.addPosition(new Position(5,0,-4,40));
		superBlob.addPosition(new Position(6,0,-1,40));
		superBlob.addPosition(new Position(3,0,-2,40));
		superBlob.addPosition(new Position(8,0,-6,40));
		superBlob.addPosition(new Position(9,0,-3,40));

		superBlob.addPosition(new Position(-5,0,-16,10));
		superBlob.addPosition(new Position(-6,0,-18,10));
		superBlob.addPosition(new Position(-3,0,-20,40));
		superBlob.addPosition(new Position(-8,0,-26,40));
		superBlob.addPosition(new Position(-9,0,-23,40));
		
		assertTrue(superBlob.getChunkKeys().size() == 5);
		
		Set<Position> p0 = superBlob.getPrimaryForChunk(0, 0);
		Set<Position> s0 = superBlob.getSecondaryForChunk(0, 0);
		Set<Position> p1 = superBlob.getPrimaryForChunk(16, 0);
		Set<Position> s1 = superBlob.getSecondaryForChunk(16, 0);
		Set<Position> p2 = superBlob.getPrimaryForChunk(0, -16);
		Set<Position> s2 = superBlob.getSecondaryForChunk(0, -16);
		Set<Position> p3 = superBlob.getPrimaryForChunk(-16, -32);
		Set<Position> s3 = superBlob.getSecondaryForChunk(-16, -32);
		Set<Position> p4 = superBlob.getPrimaryForChunk(-16, -16);
		Set<Position> s4 = superBlob.getSecondaryForChunk(-16, -16);
		
		assertTrue(p0.size()==4);
		assertTrue(s0.size()==0);
		assertTrue(p1.size()==1);
		assertTrue(s1.size()==0);
		assertTrue(p2.size()==0);
		assertTrue(s2.size()==5);
		assertTrue(p3.size()==1);
		assertTrue(s3.size()==3);
		assertTrue(p4.size()==1);
		assertTrue(s4.size()==0);
		System.out.println("End of line.");
	}

}
