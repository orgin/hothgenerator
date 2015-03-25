package biz.orgin.minecraft.hothgenerator.junit;

import static org.junit.Assert.*;

import org.junit.Test;

import biz.orgin.minecraft.hothgenerator.FireBeetle;
import biz.orgin.minecraft.hothgenerator.FireBeetle.FireBeetleType;

public class TestFireBeetleType {

	@Test
	public void testGetFireBeetleType() {
		assertTrue(FireBeetle.getFireBeetleType(FireBeetleType.HATCHLING.getName())==FireBeetleType.HATCHLING);
		assertTrue(FireBeetle.getFireBeetleType(FireBeetleType.PRIME.getName())==FireBeetleType.PRIME);
		assertTrue(FireBeetle.getFireBeetleType(FireBeetleType.REGULAR.getName())==FireBeetleType.REGULAR);
		assertTrue(FireBeetle.getFireBeetleType("__fakename__")==null);
		System.out.println("End of line.");
	}

}
