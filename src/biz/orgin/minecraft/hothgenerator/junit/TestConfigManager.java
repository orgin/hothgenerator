package biz.orgin.minecraft.hothgenerator.junit;

import static org.junit.Assert.*;

import org.junit.Test;

import biz.orgin.minecraft.hothgenerator.ConfigManager;

public class TestConfigManager {

	@Test
	public void testFlagValue() 
	{
		assertTrue(ConfigManager.isValidWorldFlagValue("world.surfaceoffset", "10"));
		assertTrue(ConfigManager.isValidWorldFlagValue("world.surfaceoffset", ""));
		assertFalse(ConfigManager.isValidWorldFlagValue("world.surfaceoffset", "sfdbjh kasfjh 10"));
		assertFalse(ConfigManager.isValidWorldFlagValue("world.surfaceoffset", "-1"));
		assertTrue(ConfigManager.isValidWorldFlagValue("world.surfaceoffset", "0"));
		assertFalse(ConfigManager.isValidWorldFlagValue("world.surfaceoffset", "5000"));
		System.out.println("End of line.");
	}

}
