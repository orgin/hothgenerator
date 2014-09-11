package biz.orgin.minecraft.hothgenerator;

import org.bukkit.Location;
import org.bukkit.World;

public class ConfigManager
{
	
	public static boolean isDebug(HothGeneratorPlugin plugin)
	{
		return plugin.getConfig().getBoolean("hoth.debug", false);
	}
	
	public static boolean isItemInfoTool(HothGeneratorPlugin plugin)
	{
		return plugin.getConfig().getBoolean("hoth.iteminfotool", false);
	}
	
	public static boolean isSmoothSnow(HothGeneratorPlugin plugin)
	{
		return plugin.getConfig().getBoolean("hoth.smoothsnow", true);
	}
	
	
	private static int getConfigInt(HothGeneratorPlugin plugin, World world, String tag, int def)
	{
		String name = world.getName();
		String worldPath = "hothworldsdata." + name + "." + tag;
		String defaultPath = "hoth." + tag;

		if(plugin.getConfig().isSet(worldPath))
		{
			return plugin.getConfig().getInt(worldPath, def);
		}
		else
		{
			return plugin.getConfig().getInt(defaultPath, def);
		}
	}
	
	private static boolean getConfigBoolean(HothGeneratorPlugin plugin, World world, String tag, boolean def)
	{
		String name = world.getName();
		String worldPath = "hothworldsdata." + name + "." + tag;
		String defaultPath = "hoth." + tag;

		if(plugin.getConfig().isSet(worldPath))
		{
			return plugin.getConfig().getBoolean(worldPath, def);
		}
		else
		{
			return plugin.getConfig().getBoolean(defaultPath, def);
		}
	}

	private static String getConfigString(HothGeneratorPlugin plugin, World world, String tag, String def)
	{
		String name = world.getName();
		String worldPath = "hothworldsdata." + name + "." + tag;
		String defaultPath = "hoth." + tag;

		if(plugin.getConfig().isSet(worldPath))
		{
			return plugin.getConfig().getString(worldPath, def);
		}
		else
		{
			return plugin.getConfig().getString(defaultPath, def);
		}
	}

	public static int getWorldSurfaceoffset(HothGeneratorPlugin plugin, World world)
	{
		int result = ConfigManager.getConfigInt(plugin, world, "world.surfaceoffset", 0);
		
		if(result<0)
		{
			result = 0;
		}
		else if(result>127)
		{
			result = 127;
		}
		return result;
	}


	
	public static int getStructureSpikesRarity(HothGeneratorPlugin plugin, World world)
	{
		int result = ConfigManager.getConfigInt(plugin, world, "structure.spikes.rarity", 2);
		
		if(result<0 || result>10)
		{
			result = 2;
		}
		return result;
	}

	public static int getStructureGardensRarity(HothGeneratorPlugin plugin, World world)
	{
		int result = ConfigManager.getConfigInt(plugin, world, "structure.gardens.rarity", 2);
		if(result<0 || result>10)
		{
			result = 2;
		}
		return result;
	}

	public static int getStructureDomesRarity(HothGeneratorPlugin plugin, World world)
	{
		int result = ConfigManager.getConfigInt(plugin, world, "structure.domes.rarity", 3);
		if(result<0 || result>10)
		{
			result = 2;
		}
		return result;
	}

	public static int getStructureDomesPlantstem(HothGeneratorPlugin plugin, World world)
	{
		return ConfigManager.getConfigInt(plugin, world, "structure.domes.plantstem", 19);
	}

	public static int getStructureDomesPlanttop(HothGeneratorPlugin plugin, World world)
	{
		return ConfigManager.getConfigInt(plugin, world, "structure.domes.planttop", 89);
	}
	
	public static int getStructureDomesFloor(HothGeneratorPlugin plugin, World world)
	{
		return ConfigManager.getConfigInt(plugin, world, "structure.domes.floor", 3);
	}

	public static int getStructureDomesFloorrandom(HothGeneratorPlugin plugin, World world)
	{
		return ConfigManager.getConfigInt(plugin, world, "structure.domes.floorrandom", 89);
	}
	
	public static boolean isStructureDomesPlaceminidome(HothGeneratorPlugin plugin, World world)
	{
		return ConfigManager.getConfigBoolean(plugin, world, "structure.domes.placeminidome", true);
	}

	public static int getStructureBasesRarity(HothGeneratorPlugin plugin, World world)
	{
		int result = ConfigManager.getConfigInt(plugin, world, "structure.bases.rarity", 2);
		if(result<0 || result>10)
		{
			result = 2;
		}
		return result;
	}

	public static boolean isStructureBasesSpawner(HothGeneratorPlugin plugin, World world)
	{
		return ConfigManager.getConfigBoolean(plugin, world, "structure.bases.spawner", true);
	}

	public static int getStructureMazesRarity(HothGeneratorPlugin plugin, World world)
	{
		int result = ConfigManager.getConfigInt(plugin, world, "structure.mazes.rarity", 2);
		if(result<0 || result>10)
		{
			result = 2;
		}
		return result;
	}
	
	public static int getStructureMazesMinrooms(HothGeneratorPlugin plugin, World world)
	{
		int min = ConfigManager.getConfigInt(plugin, world, "structure.mazes.minrooms", 8);
		int max = ConfigManager.getConfigInt(plugin, world, "structure.mazes.maxrooms", 32);
		
		if(min>max || min<1 || max<1 || max>100)
		{
			min = 8;
		}
		
		return min;
	}

	public static int getStructureMazesMaxrooms(HothGeneratorPlugin plugin, World world)
	{
		int min = ConfigManager.getConfigInt(plugin, world, "structure.mazes.minrooms", 8);
		int max = ConfigManager.getConfigInt(plugin, world, "structure.mazes.maxrooms", 32);
		
		if(min>max || min<1 || max<1 || max>100)
		{
			max = 32;
		}
		
		return max;
	}
	
	public static boolean isStructureMazesSpawner(HothGeneratorPlugin plugin, World world)
	{
		return ConfigManager.getConfigBoolean(plugin, world, "structure.mazes.spawner", true);
	}

	public static int getStructureSkeletonsRarity(HothGeneratorPlugin plugin, World world)
	{
		int result = ConfigManager.getConfigInt(plugin, world, "structure.skeletons.rarity", 2);
		if(result<0 || result>10)
		{
			result = 2;
		}
		return result;
	}
	
	public static int getStructureOasisRarity(HothGeneratorPlugin plugin, World world)
	{
		int result = ConfigManager.getConfigInt(plugin, world, "structure.oasis.rarity", 2);
		if(result<0 || result>10)
		{
			result = 2;
		}
		return result;
	}
	
	public static int getStructureSandCastleRarity(HothGeneratorPlugin plugin, World world)
	{
		int result = ConfigManager.getConfigInt(plugin, world, "structure.sandcastle.rarity", 2);
		if(result<0 || result>10)
		{
			result = 2;
		}
		return result;
	}

	public static int getStructureVillageRarity(HothGeneratorPlugin plugin, World world)
	{
		int result = ConfigManager.getConfigInt(plugin, world, "structure.village.rarity", 2);
		if(result<0 || result>10)
		{
			result = 2;
		}
		return result;
	}

	public static int getStructureSuperGardenRarity(HothGeneratorPlugin plugin, World world)
	{
		int result = ConfigManager.getConfigInt(plugin, world, "structure.supergarden.rarity", 2);
		if(result<0 || result>10)
		{
			result = 2;
		}
		return result;
	}
	
	public static int getStructureSarlaccRarity(HothGeneratorPlugin plugin, World world)
	{
		int result = ConfigManager.getConfigInt(plugin, world, "structure.sarlacc.rarity", 2);
		if(result<0 || result>10)
		{
			result = 2;
		}
		return result;
	}

	public static int getStructureHugeTreeRarity(HothGeneratorPlugin plugin, World world)
	{
		int result = ConfigManager.getConfigInt(plugin, world, "structure.hugetree.rarity", 2);
		if(result<0 || result>10)
		{
			result = 2;
		}
		return result;
	}

	public static int getStructureSpiderForestRarity(HothGeneratorPlugin plugin, World world)
	{
		int result = ConfigManager.getConfigInt(plugin, world, "structure.spiderforest.rarity", 2);
		if(result<0 || result>10)
		{
			result = 2;
		}
		return result;
	}

	public static int getStructureSwampTempleRarity(HothGeneratorPlugin plugin, World world)
	{
		int result = ConfigManager.getConfigInt(plugin, world, "structure.swamptemple.rarity", 2);
		if(result<0 || result>10)
		{
			result = 2;
		}
		return result;
	}

	public static int getStructureTreeHutRarity(HothGeneratorPlugin plugin, World world)
	{
		int result = ConfigManager.getConfigInt(plugin, world, "structure.treehut.rarity", 2);
		if(result<0 || result>10)
		{
			result = 2;
		}
		return result;
	}

	public static boolean isGenerateLogs(HothGeneratorPlugin plugin, World world)
	{
		return ConfigManager.getConfigBoolean(plugin, world, "generate.logs", true);
	}

	public static int getGenerateCavesRarity(HothGeneratorPlugin plugin, World world)
	{
		int result = ConfigManager.getConfigInt(plugin, world, "generate.caves.rarity", 2);
		if(result<0 || result>10)
		{
			result = 2;
		}
		return result;
	}
	
	public static boolean isGenerateOres(HothGeneratorPlugin plugin, World world)
	{
		return ConfigManager.getConfigBoolean(plugin, world, "generate.ores", true);
	}

	public static boolean isGenerateExtendedOre(HothGeneratorPlugin plugin)
	{
		return plugin.getConfig().getBoolean("hoth.generate.extendedore", false);
	}

	public static boolean isGenerateCactuses(HothGeneratorPlugin plugin, World world)
	{
		return ConfigManager.getConfigBoolean(plugin, world, "generate.cactuses", true);
	}

	public static boolean isGenerateShrubs(HothGeneratorPlugin plugin, World world)
	{
		return ConfigManager.getConfigBoolean(plugin, world, "generate.shrubs", true);
	}

	public static boolean isGenerateMushroomHuts(HothGeneratorPlugin plugin, World world)
	{
		return ConfigManager.getConfigBoolean(plugin, world, "generate.mushroomhuts", true);
	}

	public static boolean isRulesDropice(HothGeneratorPlugin plugin, Location location)
	{
		return plugin.getRegionManager().getBoolean("dropice", location, ConfigManager.getConfigBoolean(plugin, location.getWorld(), "rules.dropice", true));
	}

	public static boolean isRulesDroppackedice(HothGeneratorPlugin plugin, Location location)
	{
		return plugin.getRegionManager().getBoolean("droppackedice", location, ConfigManager.getConfigBoolean(plugin, location.getWorld(), "rules.droppackedice", true));
	}

	public static boolean isRulesDropsnow(HothGeneratorPlugin plugin, Location location)
	{
		return plugin.getRegionManager().getBoolean("dropsnow", location, ConfigManager.getConfigBoolean(plugin, location.getWorld(), "rules.dropsnow", true));
	}

	public static boolean isRulesFreezewater(HothGeneratorPlugin plugin, Location location)
	{
		return plugin.getRegionManager().getBoolean("freezewater", location, ConfigManager.getConfigBoolean(plugin, location.getWorld(), "rules.freezewater", true));
	}
	
	public static boolean isRulesFreezelava(HothGeneratorPlugin plugin, Location location)
	{
		return plugin.getRegionManager().getBoolean("freezelava", location, ConfigManager.getConfigBoolean(plugin, location.getWorld(), "rules.freezelava", true));
	}
	
	public static boolean isRulesPlantsgrow(HothGeneratorPlugin plugin, Location location)
	{
		return plugin.getRegionManager().getBoolean("plantsgrow", location, ConfigManager.getConfigBoolean(plugin, location.getWorld(), "rules.plantsgrow", false));
	}

	public static boolean isRulesGrassspread(HothGeneratorPlugin plugin, Location location)
	{
		return plugin.getRegionManager().getBoolean("grassspread", location, ConfigManager.getConfigBoolean(plugin, location.getWorld(), "rules.grassspread", false));
	}
	
	public static boolean isRulesStopmelt(HothGeneratorPlugin plugin, Location location)
	{
		return plugin.getRegionManager().getBoolean("stopmelt", location, ConfigManager.getConfigBoolean(plugin, location.getWorld(), "rules.stopmelt", true));
	}

	public static boolean isRulesLimitslime(HothGeneratorPlugin plugin, Location location)
	{
		return plugin.getRegionManager().getBoolean("limitslime", location, ConfigManager.getConfigBoolean(plugin, location.getWorld(), "rules.limitslime", true));
	}
	
	public static boolean isRulesSnowgravity(HothGeneratorPlugin plugin, Location location)
	{
		return plugin.getRegionManager().getBoolean("snowgravity", location, ConfigManager.getConfigBoolean(plugin, location.getWorld(), "rules.snowgravity", false));
	}

	public static int getRulesEnvironmentPeriod(HothGeneratorPlugin plugin)
	{
	    int period = plugin.getConfig().getInt("hoth.rules.environment.period", 0);

	    if(period<0)
	    {
	    	period = 5;
	    }
	    return period;
	}

	public static int getRulesFreezeDamage(HothGeneratorPlugin plugin, Location location)
	{
	    int damage = plugin.getRegionManager().getInt("freeze.damage", location, ConfigManager.getConfigInt(plugin, location.getWorld(), "rules.freeze.damage", 2));
	    if(damage<0)
	    {
	    	damage = 2;
	    }
	    return damage;
	}

	public static int getRulesFreezeStormdamage(HothGeneratorPlugin plugin, Location location)
	{
	    int damage = plugin.getRegionManager().getInt("freeze.stormdamage", location, ConfigManager.getConfigInt(plugin, location.getWorld(), "rules.freeze.stormdamage", 1));
	    if(damage<0)
	    {
	    	damage = 2;
	    }
	    return damage;
	}

	public static String getRulesFreezeMessage(HothGeneratorPlugin plugin, Location location)
	{
		return plugin.getRegionManager().get("freeze.message", location, ConfigManager.getConfigString(plugin, location.getWorld(), "rules.freeze.message", "&bYou are freezing. Find shelter!"));
	}
	
	
	public static int getRulesHeatDamage(HothGeneratorPlugin plugin, Location location)
	{
	    int damage = plugin.getRegionManager().getInt("heat.damage", location, ConfigManager.getConfigInt(plugin, location.getWorld(), "rules.heat.damage", 2));
	    if(damage<0)
	    {
	    	damage = 2;
	    }
	    return damage;
	}

	public static String getRulesHeatMessage1(HothGeneratorPlugin plugin, Location location)
	{
		return plugin.getRegionManager().get("heat.message1", location, ConfigManager.getConfigString(plugin, location.getWorld(), "rules.heat.message1", "&6The water removes your thirst."));
	}

	public static String getRulesHeatMessage2(HothGeneratorPlugin plugin, Location location)
	{
		return plugin.getRegionManager().get("heat.message2", location, ConfigManager.getConfigString(plugin, location.getWorld(), "rules.heat.message2", "&6Your are starting to feel thirsty."));
	}

	public static String getRulesHeatMessage3(HothGeneratorPlugin plugin, Location location)
	{
		return plugin.getRegionManager().get("heat.message3", location, ConfigManager.getConfigString(plugin, location.getWorld(), "rules.heat.message3", "&6Your feel very thirsty."));
	}

	public static String getRulesHeatMessage4(HothGeneratorPlugin plugin, Location location)
	{
		return plugin.getRegionManager().get("heat.message4", location, ConfigManager.getConfigString(plugin, location.getWorld(), "rules.heat.message4", "&6You are exhausted from the heat. Find water or shelter!"));
	}

	public static int getRulesMosquitoDamage(HothGeneratorPlugin plugin, Location location)
	{
	    int damage = plugin.getRegionManager().getInt("mosquito.damage", location, ConfigManager.getConfigInt(plugin, location.getWorld(), "rules.mosquito.damage", 1));
	    if(damage<0)
	    {
	    	damage = 1;
	    }
	    return damage;
	}
	
	public static int getRulesMosquitoRarity(HothGeneratorPlugin plugin, Location location)
	{
		int result = plugin.getRegionManager().getInt("mosquito.rarity", location, ConfigManager.getConfigInt(plugin, location.getWorld(), "rules.mosquito.rarity", 5));
		if(result<1 || result>10)
		{
			result = 2;
		}
		return result;
	}

	public static int getRulesMosquitoRunFree(HothGeneratorPlugin plugin, Location location)
	{
		int result = plugin.getRegionManager().getInt("mosquito.runfree", location, ConfigManager.getConfigInt(plugin, location.getWorld(), "rules.mosquito.runfree", 10));
		if(result<10 || result>100)
		{
			result = 10;
		}
		return result;
	}

	public static String getRulesMosquitoMessage1(HothGeneratorPlugin plugin, Location location)
	{
		return plugin.getRegionManager().get("mosquito.message1", location, ConfigManager.getConfigString(plugin, location.getWorld(), "rules.mosquito.message1", "&2You seem to have lost the mosquito swarm."));
	}

	public static String getRulesMosquitoMessage2(HothGeneratorPlugin plugin, Location location)
	{
		return plugin.getRegionManager().get("mosquito.message2", location, ConfigManager.getConfigString(plugin, location.getWorld(), "rules.mosquito.message2", "&2You hear some buzzing nearby."));
	}

	public static String getRulesMosquitoMessage3(HothGeneratorPlugin plugin, Location location)
	{
		return plugin.getRegionManager().get("mosquito.message3", location, ConfigManager.getConfigString(plugin, location.getWorld(), "rules.mosquito.message3", "&2You can see a swarm of huge mosquitos nearby."));
	}

	public static String getRulesMosquitoMessage4(HothGeneratorPlugin plugin, Location location)
	{
		return plugin.getRegionManager().get("mosquito.message4", location, ConfigManager.getConfigString(plugin, location.getWorld(), "rules.mosquito.message4", "&2Mosquitos are attacking you! Run!!"));
	}
	
	public static int getRulesLeechDamage(HothGeneratorPlugin plugin, Location location)
	{
	    int damage = plugin.getRegionManager().getInt("leech.damage", location, ConfigManager.getConfigInt(plugin, location.getWorld(), "rules.leech.damage", 1));
	    if(damage<0)
	    {
	    	damage = 1;
	    }
	    return damage;
	}
	
	
	public static int getRulesLeechRarity(HothGeneratorPlugin plugin, Location location)
	{
		int result = plugin.getRegionManager().getInt("leech.rarity", location, ConfigManager.getConfigInt(plugin, location.getWorld(), "rules.leech.rarity", 5));
		if(result<1 || result>10)
		{
			result = 2;
		}
		return result;
	}

	public static String getRulesLeechMessage1(HothGeneratorPlugin plugin, Location location)
	{
		return plugin.getRegionManager().get("leech.message1", location, ConfigManager.getConfigString(plugin, location.getWorld(), "rules.leech.message1", "&2You manage to remove all leeches."));
	}

	public static String getRulesLeechMessage2(HothGeneratorPlugin plugin, Location location)
	{
		return plugin.getRegionManager().get("leech.message2", location, ConfigManager.getConfigString(plugin, location.getWorld(), "rules.leech.message2", "&2You notice something moving in the water nearby."));
	}

	public static String getRulesLeechMessage3(HothGeneratorPlugin plugin, Location location)
	{
		return plugin.getRegionManager().get("leech.message3", location, ConfigManager.getConfigString(plugin, location.getWorld(), "rules.leech.message3", "&2You can see leeches moving in the water."));
	}

	public static String getRulesLeechMessage4(HothGeneratorPlugin plugin, Location location)
	{
		return plugin.getRegionManager().get("leech.message4", location, ConfigManager.getConfigString(plugin, location.getWorld(), "rules.leech.message4", "&2Leeches are attacking you! Get out of the water!"));
	}

	public static String getRulesLeechMessage5(HothGeneratorPlugin plugin, Location location)
	{
		return plugin.getRegionManager().get("leech.message5", location, ConfigManager.getConfigString(plugin, location.getWorld(), "rules.leech.message5", "&2Leeches are sucking you dry, run to shake them off!"));
	}

	public static int getRulesSpawnNeutralRarity(HothGeneratorPlugin plugin, Location location)
	{
	    int rarity = plugin.getRegionManager().getInt("spawn.neutral.rarity", location, ConfigManager.getConfigInt(plugin, location.getWorld(), "rules.spawn.neutral.rarity", 2));
	    if(rarity<1 || rarity>10)
	    {
	    	rarity = 2;
	    }
	    return rarity;
	}
	
	public static String getRulesSpawnNeutralMobs(HothGeneratorPlugin plugin, Location location)
	{
		return plugin.getRegionManager().get("spawn.neutral.mobs", location, ConfigManager.getConfigString(plugin, location.getWorld(), "rules.spawn.neutral.mobs", "chicken,cow,mushroom_cow,ocelot,pig,sheep,wolf"));
	}
	
	public static boolean isRulesSpawnNeutralOn(HothGeneratorPlugin plugin, World world)
	{
		return ConfigManager.getConfigBoolean(plugin, world, "rules.spawn.neutral.on", true);
	}
	
	/*
	  +structure.spikes.rarity: 2
	  +structure.gardens.rarity: 2
	  +structure.domes.rarity: 2
	  +structure.domes.plantstem: 19
	  +structure.domes.planttop: 89
	  +structure.domes.floor: 3
	  +structure.domes.floorrandom: 89
	  +structure.domes.placeminidome: true
	  +structure.bases.rarity: 2
	  +structure.bases.spawner: true
	  +structure.roomcluster.rarity: 2
	  +structure.roomcluster.minrooms: 8
	  +structure.roomcluster.maxrooms: 32
	  +structure.roomcluster.spawner: true
	  +generate.logs: true
	  +generate.caves.rarity: 2
	  +generate.ores: true
	  +generate.extendedores: false
	  +rules.dropice: true
	  +rules.droppackedice: true
	  +rules.dropsnow: true
	  +rules.freezewater: true
	  +rules.freezelava: true
	  +rules.plantsgrow: false
	  +rules.grassspread: false
	  +rules.stopmelt: true
	  +rules.limitslime: true
	  */
	
	
	/* Config - End */

}
