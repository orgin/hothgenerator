package biz.orgin.minecraft.hothgenerator;

import org.bukkit.Material;

/**
 * Handles converting materials between ID's an instances of the Material class.
 * Since all type ID functions have been deprecated in bukkit then this
 * plugin must handle ID's on its own.
 * Unknown ID's and Materials are treated with the deprecated functions for now.
 * Unfortunately any new block must be added to this class..
 * @author orgin
 *
 */
public class MaterialManager
{
	public static void main(String[] args)
	{
		// Generate code for toID() function
		System.out.println("/*");
		System.out.println(" * Returns a classic block type id related to the material");
		System.out.println(" */");
		System.out.println("@SuppressWarnings(\"deprecation\")");
		System.out.println("public static int toID(Material material)");
		System.out.println("{");
		for(int i=0;i<1024;i++)
		{
			@SuppressWarnings("deprecation")
			Material material = Material.getMaterial(i);
			if(material!=null)
			{
				String name = material.name();
				String suffix = "";
				if(i!=0)
				{
					suffix = "else ";
				}
				System.out.println("\t" + suffix + "if(material.equals(Material." + name + "))");
				System.out.println("\t{");
				System.out.println("\t\treturn " + i + ";");
				System.out.println("\t}");
			}
		}
		System.out.println("\treturn material.getId();");
		System.out.println("}");
		
		System.out.println();
		
		// Generate code for toMaterial() function
		System.out.println("/*");
		System.out.println(" * Returns a classic block type id related to the material");
		System.out.println(" */");
		System.out.println("@SuppressWarnings(\"deprecation\")");
		System.out.println("public static Material toMaterial(int id)");
		System.out.println("{");
		for(int i=0;i<1024;i++)
		{
			@SuppressWarnings("deprecation")
			Material material = Material.getMaterial(i);
			if(material!=null)
			{
				String name = material.name();
				String suffix = "";
				if(i!=0)
				{
					suffix = "else ";
				}
				System.out.println("\t" + suffix + "if(id==" + i + ")");
				System.out.println("\t{");
				System.out.println("\t\treturn Material." + name + ";");
				System.out.println("\t}");
			}
		}
		System.out.println("\treturn Material.getMaterial(id);");
		System.out.println("}");
	}

	// Generated code below

	/*
	 * Returns a classic block type id related to the material
	 */
	@SuppressWarnings("deprecation")
	public static int toID(Material material)
	{
		if(material.equals(Material.AIR))
		{
			return 0;
		}
		else if(material.equals(Material.STONE))
		{
			return 1;
		}
		else if(material.equals(Material.GRASS))
		{
			return 2;
		}
		else if(material.equals(Material.DIRT))
		{
			return 3;
		}
		else if(material.equals(Material.COBBLESTONE))
		{
			return 4;
		}
		else if(material.equals(Material.WOOD))
		{
			return 5;
		}
		else if(material.equals(Material.SAPLING))
		{
			return 6;
		}
		else if(material.equals(Material.BEDROCK))
		{
			return 7;
		}
		else if(material.equals(Material.WATER))
		{
			return 8;
		}
		else if(material.equals(Material.STATIONARY_WATER))
		{
			return 9;
		}
		else if(material.equals(Material.LAVA))
		{
			return 10;
		}
		else if(material.equals(Material.STATIONARY_LAVA))
		{
			return 11;
		}
		else if(material.equals(Material.SAND))
		{
			return 12;
		}
		else if(material.equals(Material.GRAVEL))
		{
			return 13;
		}
		else if(material.equals(Material.GOLD_ORE))
		{
			return 14;
		}
		else if(material.equals(Material.IRON_ORE))
		{
			return 15;
		}
		else if(material.equals(Material.COAL_ORE))
		{
			return 16;
		}
		else if(material.equals(Material.LOG))
		{
			return 17;
		}
		else if(material.equals(Material.LEAVES))
		{
			return 18;
		}
		else if(material.equals(Material.SPONGE))
		{
			return 19;
		}
		else if(material.equals(Material.GLASS))
		{
			return 20;
		}
		else if(material.equals(Material.LAPIS_ORE))
		{
			return 21;
		}
		else if(material.equals(Material.LAPIS_BLOCK))
		{
			return 22;
		}
		else if(material.equals(Material.DISPENSER))
		{
			return 23;
		}
		else if(material.equals(Material.SANDSTONE))
		{
			return 24;
		}
		else if(material.equals(Material.NOTE_BLOCK))
		{
			return 25;
		}
		else if(material.equals(Material.BED_BLOCK))
		{
			return 26;
		}
		else if(material.equals(Material.POWERED_RAIL))
		{
			return 27;
		}
		else if(material.equals(Material.DETECTOR_RAIL))
		{
			return 28;
		}
		else if(material.equals(Material.PISTON_STICKY_BASE))
		{
			return 29;
		}
		else if(material.equals(Material.WEB))
		{
			return 30;
		}
		else if(material.equals(Material.LONG_GRASS))
		{
			return 31;
		}
		else if(material.equals(Material.DEAD_BUSH))
		{
			return 32;
		}
		else if(material.equals(Material.PISTON_BASE))
		{
			return 33;
		}
		else if(material.equals(Material.PISTON_EXTENSION))
		{
			return 34;
		}
		else if(material.equals(Material.WOOL))
		{
			return 35;
		}
		else if(material.equals(Material.PISTON_MOVING_PIECE))
		{
			return 36;
		}
		else if(material.equals(Material.YELLOW_FLOWER))
		{
			return 37;
		}
		else if(material.equals(Material.RED_ROSE))
		{
			return 38;
		}
		else if(material.equals(Material.BROWN_MUSHROOM))
		{
			return 39;
		}
		else if(material.equals(Material.RED_MUSHROOM))
		{
			return 40;
		}
		else if(material.equals(Material.GOLD_BLOCK))
		{
			return 41;
		}
		else if(material.equals(Material.IRON_BLOCK))
		{
			return 42;
		}
		else if(material.equals(Material.DOUBLE_STEP))
		{
			return 43;
		}
		else if(material.equals(Material.STEP))
		{
			return 44;
		}
		else if(material.equals(Material.BRICK))
		{
			return 45;
		}
		else if(material.equals(Material.TNT))
		{
			return 46;
		}
		else if(material.equals(Material.BOOKSHELF))
		{
			return 47;
		}
		else if(material.equals(Material.MOSSY_COBBLESTONE))
		{
			return 48;
		}
		else if(material.equals(Material.OBSIDIAN))
		{
			return 49;
		}
		else if(material.equals(Material.TORCH))
		{
			return 50;
		}
		else if(material.equals(Material.FIRE))
		{
			return 51;
		}
		else if(material.equals(Material.MOB_SPAWNER))
		{
			return 52;
		}
		else if(material.equals(Material.WOOD_STAIRS))
		{
			return 53;
		}
		else if(material.equals(Material.CHEST))
		{
			return 54;
		}
		else if(material.equals(Material.REDSTONE_WIRE))
		{
			return 55;
		}
		else if(material.equals(Material.DIAMOND_ORE))
		{
			return 56;
		}
		else if(material.equals(Material.DIAMOND_BLOCK))
		{
			return 57;
		}
		else if(material.equals(Material.WORKBENCH))
		{
			return 58;
		}
		else if(material.equals(Material.CROPS))
		{
			return 59;
		}
		else if(material.equals(Material.SOIL))
		{
			return 60;
		}
		else if(material.equals(Material.FURNACE))
		{
			return 61;
		}
		else if(material.equals(Material.BURNING_FURNACE))
		{
			return 62;
		}
		else if(material.equals(Material.SIGN_POST))
		{
			return 63;
		}
		else if(material.equals(Material.WOODEN_DOOR))
		{
			return 64;
		}
		else if(material.equals(Material.LADDER))
		{
			return 65;
		}
		else if(material.equals(Material.RAILS))
		{
			return 66;
		}
		else if(material.equals(Material.COBBLESTONE_STAIRS))
		{
			return 67;
		}
		else if(material.equals(Material.WALL_SIGN))
		{
			return 68;
		}
		else if(material.equals(Material.LEVER))
		{
			return 69;
		}
		else if(material.equals(Material.STONE_PLATE))
		{
			return 70;
		}
		else if(material.equals(Material.IRON_DOOR_BLOCK))
		{
			return 71;
		}
		else if(material.equals(Material.WOOD_PLATE))
		{
			return 72;
		}
		else if(material.equals(Material.REDSTONE_ORE))
		{
			return 73;
		}
		else if(material.equals(Material.GLOWING_REDSTONE_ORE))
		{
			return 74;
		}
		else if(material.equals(Material.REDSTONE_TORCH_OFF))
		{
			return 75;
		}
		else if(material.equals(Material.REDSTONE_TORCH_ON))
		{
			return 76;
		}
		else if(material.equals(Material.STONE_BUTTON))
		{
			return 77;
		}
		else if(material.equals(Material.SNOW))
		{
			return 78;
		}
		else if(material.equals(Material.ICE))
		{
			return 79;
		}
		else if(material.equals(Material.SNOW_BLOCK))
		{
			return 80;
		}
		else if(material.equals(Material.CACTUS))
		{
			return 81;
		}
		else if(material.equals(Material.CLAY))
		{
			return 82;
		}
		else if(material.equals(Material.SUGAR_CANE_BLOCK))
		{
			return 83;
		}
		else if(material.equals(Material.JUKEBOX))
		{
			return 84;
		}
		else if(material.equals(Material.FENCE))
		{
			return 85;
		}
		else if(material.equals(Material.PUMPKIN))
		{
			return 86;
		}
		else if(material.equals(Material.NETHERRACK))
		{
			return 87;
		}
		else if(material.equals(Material.SOUL_SAND))
		{
			return 88;
		}
		else if(material.equals(Material.GLOWSTONE))
		{
			return 89;
		}
		else if(material.equals(Material.PORTAL))
		{
			return 90;
		}
		else if(material.equals(Material.JACK_O_LANTERN))
		{
			return 91;
		}
		else if(material.equals(Material.CAKE_BLOCK))
		{
			return 92;
		}
		else if(material.equals(Material.DIODE_BLOCK_OFF))
		{
			return 93;
		}
		else if(material.equals(Material.DIODE_BLOCK_ON))
		{
			return 94;
		}
		else if(material.equals(Material.STAINED_GLASS))
		{
			return 95;
		}
		else if(material.equals(Material.TRAP_DOOR))
		{
			return 96;
		}
		else if(material.equals(Material.MONSTER_EGGS))
		{
			return 97;
		}
		else if(material.equals(Material.SMOOTH_BRICK))
		{
			return 98;
		}
		else if(material.equals(Material.HUGE_MUSHROOM_1))
		{
			return 99;
		}
		else if(material.equals(Material.HUGE_MUSHROOM_2))
		{
			return 100;
		}
		else if(material.equals(Material.IRON_FENCE))
		{
			return 101;
		}
		else if(material.equals(Material.THIN_GLASS))
		{
			return 102;
		}
		else if(material.equals(Material.MELON_BLOCK))
		{
			return 103;
		}
		else if(material.equals(Material.PUMPKIN_STEM))
		{
			return 104;
		}
		else if(material.equals(Material.MELON_STEM))
		{
			return 105;
		}
		else if(material.equals(Material.VINE))
		{
			return 106;
		}
		else if(material.equals(Material.FENCE_GATE))
		{
			return 107;
		}
		else if(material.equals(Material.BRICK_STAIRS))
		{
			return 108;
		}
		else if(material.equals(Material.SMOOTH_STAIRS))
		{
			return 109;
		}
		else if(material.equals(Material.MYCEL))
		{
			return 110;
		}
		else if(material.equals(Material.WATER_LILY))
		{
			return 111;
		}
		else if(material.equals(Material.NETHER_BRICK))
		{
			return 112;
		}
		else if(material.equals(Material.NETHER_FENCE))
		{
			return 113;
		}
		else if(material.equals(Material.NETHER_BRICK_STAIRS))
		{
			return 114;
		}
		else if(material.equals(Material.NETHER_WARTS))
		{
			return 115;
		}
		else if(material.equals(Material.ENCHANTMENT_TABLE))
		{
			return 116;
		}
		else if(material.equals(Material.BREWING_STAND))
		{
			return 117;
		}
		else if(material.equals(Material.CAULDRON))
		{
			return 118;
		}
		else if(material.equals(Material.ENDER_PORTAL))
		{
			return 119;
		}
		else if(material.equals(Material.ENDER_PORTAL_FRAME))
		{
			return 120;
		}
		else if(material.equals(Material.ENDER_STONE))
		{
			return 121;
		}
		else if(material.equals(Material.DRAGON_EGG))
		{
			return 122;
		}
		else if(material.equals(Material.REDSTONE_LAMP_OFF))
		{
			return 123;
		}
		else if(material.equals(Material.REDSTONE_LAMP_ON))
		{
			return 124;
		}
		else if(material.equals(Material.WOOD_DOUBLE_STEP))
		{
			return 125;
		}
		else if(material.equals(Material.WOOD_STEP))
		{
			return 126;
		}
		else if(material.equals(Material.COCOA))
		{
			return 127;
		}
		else if(material.equals(Material.SANDSTONE_STAIRS))
		{
			return 128;
		}
		else if(material.equals(Material.EMERALD_ORE))
		{
			return 129;
		}
		else if(material.equals(Material.ENDER_CHEST))
		{
			return 130;
		}
		else if(material.equals(Material.TRIPWIRE_HOOK))
		{
			return 131;
		}
		else if(material.equals(Material.TRIPWIRE))
		{
			return 132;
		}
		else if(material.equals(Material.EMERALD_BLOCK))
		{
			return 133;
		}
		else if(material.equals(Material.SPRUCE_WOOD_STAIRS))
		{
			return 134;
		}
		else if(material.equals(Material.BIRCH_WOOD_STAIRS))
		{
			return 135;
		}
		else if(material.equals(Material.JUNGLE_WOOD_STAIRS))
		{
			return 136;
		}
		else if(material.equals(Material.COMMAND))
		{
			return 137;
		}
		else if(material.equals(Material.BEACON))
		{
			return 138;
		}
		else if(material.equals(Material.COBBLE_WALL))
		{
			return 139;
		}
		else if(material.equals(Material.FLOWER_POT))
		{
			return 140;
		}
		else if(material.equals(Material.CARROT))
		{
			return 141;
		}
		else if(material.equals(Material.POTATO))
		{
			return 142;
		}
		else if(material.equals(Material.WOOD_BUTTON))
		{
			return 143;
		}
		else if(material.equals(Material.SKULL))
		{
			return 144;
		}
		else if(material.equals(Material.ANVIL))
		{
			return 145;
		}
		else if(material.equals(Material.TRAPPED_CHEST))
		{
			return 146;
		}
		else if(material.equals(Material.GOLD_PLATE))
		{
			return 147;
		}
		else if(material.equals(Material.IRON_PLATE))
		{
			return 148;
		}
		else if(material.equals(Material.REDSTONE_COMPARATOR_OFF))
		{
			return 149;
		}
		else if(material.equals(Material.REDSTONE_COMPARATOR_ON))
		{
			return 150;
		}
		else if(material.equals(Material.DAYLIGHT_DETECTOR))
		{
			return 151;
		}
		else if(material.equals(Material.REDSTONE_BLOCK))
		{
			return 152;
		}
		else if(material.equals(Material.QUARTZ_ORE))
		{
			return 153;
		}
		else if(material.equals(Material.HOPPER))
		{
			return 154;
		}
		else if(material.equals(Material.QUARTZ_BLOCK))
		{
			return 155;
		}
		else if(material.equals(Material.QUARTZ_STAIRS))
		{
			return 156;
		}
		else if(material.equals(Material.ACTIVATOR_RAIL))
		{
			return 157;
		}
		else if(material.equals(Material.DROPPER))
		{
			return 158;
		}
		else if(material.equals(Material.STAINED_CLAY))
		{
			return 159;
		}
		else if(material.equals(Material.STAINED_GLASS_PANE))
		{
			return 160;
		}
		else if(material.equals(Material.LEAVES_2))
		{
			return 161;
		}
		else if(material.equals(Material.LOG_2))
		{
			return 162;
		}
		else if(material.equals(Material.ACACIA_STAIRS))
		{
			return 163;
		}
		else if(material.equals(Material.DARK_OAK_STAIRS))
		{
			return 164;
		}
		else if(material.equals(Material.SLIME_BLOCK))
		{
			return 165;
		}
		else if(material.equals(Material.BARRIER))
		{
			return 166;
		}
		else if(material.equals(Material.IRON_TRAPDOOR))
		{
			return 167;
		}
		else if(material.equals(Material.PRISMARINE))
		{
			return 168;
		}
		else if(material.equals(Material.SEA_LANTERN))
		{
			return 169;
		}
		else if(material.equals(Material.HAY_BLOCK))
		{
			return 170;
		}
		else if(material.equals(Material.CARPET))
		{
			return 171;
		}
		else if(material.equals(Material.HARD_CLAY))
		{
			return 172;
		}
		else if(material.equals(Material.COAL_BLOCK))
		{
			return 173;
		}
		else if(material.equals(Material.PACKED_ICE))
		{
			return 174;
		}
		else if(material.equals(Material.DOUBLE_PLANT))
		{
			return 175;
		}
		else if(material.equals(Material.STANDING_BANNER))
		{
			return 176;
		}
		else if(material.equals(Material.WALL_BANNER))
		{
			return 177;
		}
		else if(material.equals(Material.DAYLIGHT_DETECTOR_INVERTED))
		{
			return 178;
		}
		else if(material.equals(Material.RED_SANDSTONE))
		{
			return 179;
		}
		else if(material.equals(Material.RED_SANDSTONE_STAIRS))
		{
			return 180;
		}
		else if(material.equals(Material.DOUBLE_STONE_SLAB2))
		{
			return 181;
		}
		else if(material.equals(Material.STONE_SLAB2))
		{
			return 182;
		}
		else if(material.equals(Material.SPRUCE_FENCE_GATE))
		{
			return 183;
		}
		else if(material.equals(Material.BIRCH_FENCE_GATE))
		{
			return 184;
		}
		else if(material.equals(Material.JUNGLE_FENCE_GATE))
		{
			return 185;
		}
		else if(material.equals(Material.DARK_OAK_FENCE_GATE))
		{
			return 186;
		}
		else if(material.equals(Material.ACACIA_FENCE_GATE))
		{
			return 187;
		}
		else if(material.equals(Material.SPRUCE_FENCE))
		{
			return 188;
		}
		else if(material.equals(Material.BIRCH_FENCE))
		{
			return 189;
		}
		else if(material.equals(Material.JUNGLE_FENCE))
		{
			return 190;
		}
		else if(material.equals(Material.DARK_OAK_FENCE))
		{
			return 191;
		}
		else if(material.equals(Material.ACACIA_FENCE))
		{
			return 192;
		}
		else if(material.equals(Material.SPRUCE_DOOR))
		{
			return 193;
		}
		else if(material.equals(Material.BIRCH_DOOR))
		{
			return 194;
		}
		else if(material.equals(Material.JUNGLE_DOOR))
		{
			return 195;
		}
		else if(material.equals(Material.ACACIA_DOOR))
		{
			return 196;
		}
		else if(material.equals(Material.DARK_OAK_DOOR))
		{
			return 197;
		}
		else if(material.equals(Material.END_ROD))
		{
			return 198;
		}
		else if(material.equals(Material.CHORUS_PLANT))
		{
			return 199;
		}
		else if(material.equals(Material.CHORUS_FLOWER))
		{
			return 200;
		}
		else if(material.equals(Material.PURPUR_BLOCK))
		{
			return 201;
		}
		else if(material.equals(Material.PURPUR_PILLAR))
		{
			return 202;
		}
		else if(material.equals(Material.PURPUR_STAIRS))
		{
			return 203;
		}
		else if(material.equals(Material.PURPUR_DOUBLE_SLAB))
		{
			return 204;
		}
		else if(material.equals(Material.PURPUR_SLAB))
		{
			return 205;
		}
		else if(material.equals(Material.END_BRICKS))
		{
			return 206;
		}
		else if(material.equals(Material.BEETROOT_BLOCK))
		{
			return 207;
		}
		else if(material.equals(Material.GRASS_PATH))
		{
			return 208;
		}
		else if(material.equals(Material.END_GATEWAY))
		{
			return 209;
		}
		else if(material.equals(Material.COMMAND_REPEATING))
		{
			return 210;
		}
		else if(material.equals(Material.COMMAND_CHAIN))
		{
			return 211;
		}
		else if(material.equals(Material.FROSTED_ICE))
		{
			return 212;
		}
		else if(material.equals(Material.STRUCTURE_BLOCK))
		{
			return 255;
		}
		else if(material.equals(Material.IRON_SPADE))
		{
			return 256;
		}
		else if(material.equals(Material.IRON_PICKAXE))
		{
			return 257;
		}
		else if(material.equals(Material.IRON_AXE))
		{
			return 258;
		}
		else if(material.equals(Material.FLINT_AND_STEEL))
		{
			return 259;
		}
		else if(material.equals(Material.APPLE))
		{
			return 260;
		}
		else if(material.equals(Material.BOW))
		{
			return 261;
		}
		else if(material.equals(Material.ARROW))
		{
			return 262;
		}
		else if(material.equals(Material.COAL))
		{
			return 263;
		}
		else if(material.equals(Material.DIAMOND))
		{
			return 264;
		}
		else if(material.equals(Material.IRON_INGOT))
		{
			return 265;
		}
		else if(material.equals(Material.GOLD_INGOT))
		{
			return 266;
		}
		else if(material.equals(Material.IRON_SWORD))
		{
			return 267;
		}
		else if(material.equals(Material.WOOD_SWORD))
		{
			return 268;
		}
		else if(material.equals(Material.WOOD_SPADE))
		{
			return 269;
		}
		else if(material.equals(Material.WOOD_PICKAXE))
		{
			return 270;
		}
		else if(material.equals(Material.WOOD_AXE))
		{
			return 271;
		}
		else if(material.equals(Material.STONE_SWORD))
		{
			return 272;
		}
		else if(material.equals(Material.STONE_SPADE))
		{
			return 273;
		}
		else if(material.equals(Material.STONE_PICKAXE))
		{
			return 274;
		}
		else if(material.equals(Material.STONE_AXE))
		{
			return 275;
		}
		else if(material.equals(Material.DIAMOND_SWORD))
		{
			return 276;
		}
		else if(material.equals(Material.DIAMOND_SPADE))
		{
			return 277;
		}
		else if(material.equals(Material.DIAMOND_PICKAXE))
		{
			return 278;
		}
		else if(material.equals(Material.DIAMOND_AXE))
		{
			return 279;
		}
		else if(material.equals(Material.STICK))
		{
			return 280;
		}
		else if(material.equals(Material.BOWL))
		{
			return 281;
		}
		else if(material.equals(Material.MUSHROOM_SOUP))
		{
			return 282;
		}
		else if(material.equals(Material.GOLD_SWORD))
		{
			return 283;
		}
		else if(material.equals(Material.GOLD_SPADE))
		{
			return 284;
		}
		else if(material.equals(Material.GOLD_PICKAXE))
		{
			return 285;
		}
		else if(material.equals(Material.GOLD_AXE))
		{
			return 286;
		}
		else if(material.equals(Material.STRING))
		{
			return 287;
		}
		else if(material.equals(Material.FEATHER))
		{
			return 288;
		}
		else if(material.equals(Material.SULPHUR))
		{
			return 289;
		}
		else if(material.equals(Material.WOOD_HOE))
		{
			return 290;
		}
		else if(material.equals(Material.STONE_HOE))
		{
			return 291;
		}
		else if(material.equals(Material.IRON_HOE))
		{
			return 292;
		}
		else if(material.equals(Material.DIAMOND_HOE))
		{
			return 293;
		}
		else if(material.equals(Material.GOLD_HOE))
		{
			return 294;
		}
		else if(material.equals(Material.SEEDS))
		{
			return 295;
		}
		else if(material.equals(Material.WHEAT))
		{
			return 296;
		}
		else if(material.equals(Material.BREAD))
		{
			return 297;
		}
		else if(material.equals(Material.LEATHER_HELMET))
		{
			return 298;
		}
		else if(material.equals(Material.LEATHER_CHESTPLATE))
		{
			return 299;
		}
		else if(material.equals(Material.LEATHER_LEGGINGS))
		{
			return 300;
		}
		else if(material.equals(Material.LEATHER_BOOTS))
		{
			return 301;
		}
		else if(material.equals(Material.CHAINMAIL_HELMET))
		{
			return 302;
		}
		else if(material.equals(Material.CHAINMAIL_CHESTPLATE))
		{
			return 303;
		}
		else if(material.equals(Material.CHAINMAIL_LEGGINGS))
		{
			return 304;
		}
		else if(material.equals(Material.CHAINMAIL_BOOTS))
		{
			return 305;
		}
		else if(material.equals(Material.IRON_HELMET))
		{
			return 306;
		}
		else if(material.equals(Material.IRON_CHESTPLATE))
		{
			return 307;
		}
		else if(material.equals(Material.IRON_LEGGINGS))
		{
			return 308;
		}
		else if(material.equals(Material.IRON_BOOTS))
		{
			return 309;
		}
		else if(material.equals(Material.DIAMOND_HELMET))
		{
			return 310;
		}
		else if(material.equals(Material.DIAMOND_CHESTPLATE))
		{
			return 311;
		}
		else if(material.equals(Material.DIAMOND_LEGGINGS))
		{
			return 312;
		}
		else if(material.equals(Material.DIAMOND_BOOTS))
		{
			return 313;
		}
		else if(material.equals(Material.GOLD_HELMET))
		{
			return 314;
		}
		else if(material.equals(Material.GOLD_CHESTPLATE))
		{
			return 315;
		}
		else if(material.equals(Material.GOLD_LEGGINGS))
		{
			return 316;
		}
		else if(material.equals(Material.GOLD_BOOTS))
		{
			return 317;
		}
		else if(material.equals(Material.FLINT))
		{
			return 318;
		}
		else if(material.equals(Material.PORK))
		{
			return 319;
		}
		else if(material.equals(Material.GRILLED_PORK))
		{
			return 320;
		}
		else if(material.equals(Material.PAINTING))
		{
			return 321;
		}
		else if(material.equals(Material.GOLDEN_APPLE))
		{
			return 322;
		}
		else if(material.equals(Material.SIGN))
		{
			return 323;
		}
		else if(material.equals(Material.WOOD_DOOR))
		{
			return 324;
		}
		else if(material.equals(Material.BUCKET))
		{
			return 325;
		}
		else if(material.equals(Material.WATER_BUCKET))
		{
			return 326;
		}
		else if(material.equals(Material.LAVA_BUCKET))
		{
			return 327;
		}
		else if(material.equals(Material.MINECART))
		{
			return 328;
		}
		else if(material.equals(Material.SADDLE))
		{
			return 329;
		}
		else if(material.equals(Material.IRON_DOOR))
		{
			return 330;
		}
		else if(material.equals(Material.REDSTONE))
		{
			return 331;
		}
		else if(material.equals(Material.SNOW_BALL))
		{
			return 332;
		}
		else if(material.equals(Material.BOAT))
		{
			return 333;
		}
		else if(material.equals(Material.LEATHER))
		{
			return 334;
		}
		else if(material.equals(Material.MILK_BUCKET))
		{
			return 335;
		}
		else if(material.equals(Material.CLAY_BRICK))
		{
			return 336;
		}
		else if(material.equals(Material.CLAY_BALL))
		{
			return 337;
		}
		else if(material.equals(Material.SUGAR_CANE))
		{
			return 338;
		}
		else if(material.equals(Material.PAPER))
		{
			return 339;
		}
		else if(material.equals(Material.BOOK))
		{
			return 340;
		}
		else if(material.equals(Material.SLIME_BALL))
		{
			return 341;
		}
		else if(material.equals(Material.STORAGE_MINECART))
		{
			return 342;
		}
		else if(material.equals(Material.POWERED_MINECART))
		{
			return 343;
		}
		else if(material.equals(Material.EGG))
		{
			return 344;
		}
		else if(material.equals(Material.COMPASS))
		{
			return 345;
		}
		else if(material.equals(Material.FISHING_ROD))
		{
			return 346;
		}
		else if(material.equals(Material.WATCH))
		{
			return 347;
		}
		else if(material.equals(Material.GLOWSTONE_DUST))
		{
			return 348;
		}
		else if(material.equals(Material.RAW_FISH))
		{
			return 349;
		}
		else if(material.equals(Material.COOKED_FISH))
		{
			return 350;
		}
		else if(material.equals(Material.INK_SACK))
		{
			return 351;
		}
		else if(material.equals(Material.BONE))
		{
			return 352;
		}
		else if(material.equals(Material.SUGAR))
		{
			return 353;
		}
		else if(material.equals(Material.CAKE))
		{
			return 354;
		}
		else if(material.equals(Material.BED))
		{
			return 355;
		}
		else if(material.equals(Material.DIODE))
		{
			return 356;
		}
		else if(material.equals(Material.COOKIE))
		{
			return 357;
		}
		else if(material.equals(Material.MAP))
		{
			return 358;
		}
		else if(material.equals(Material.SHEARS))
		{
			return 359;
		}
		else if(material.equals(Material.MELON))
		{
			return 360;
		}
		else if(material.equals(Material.PUMPKIN_SEEDS))
		{
			return 361;
		}
		else if(material.equals(Material.MELON_SEEDS))
		{
			return 362;
		}
		else if(material.equals(Material.RAW_BEEF))
		{
			return 363;
		}
		else if(material.equals(Material.COOKED_BEEF))
		{
			return 364;
		}
		else if(material.equals(Material.RAW_CHICKEN))
		{
			return 365;
		}
		else if(material.equals(Material.COOKED_CHICKEN))
		{
			return 366;
		}
		else if(material.equals(Material.ROTTEN_FLESH))
		{
			return 367;
		}
		else if(material.equals(Material.ENDER_PEARL))
		{
			return 368;
		}
		else if(material.equals(Material.BLAZE_ROD))
		{
			return 369;
		}
		else if(material.equals(Material.GHAST_TEAR))
		{
			return 370;
		}
		else if(material.equals(Material.GOLD_NUGGET))
		{
			return 371;
		}
		else if(material.equals(Material.NETHER_STALK))
		{
			return 372;
		}
		else if(material.equals(Material.POTION))
		{
			return 373;
		}
		else if(material.equals(Material.GLASS_BOTTLE))
		{
			return 374;
		}
		else if(material.equals(Material.SPIDER_EYE))
		{
			return 375;
		}
		else if(material.equals(Material.FERMENTED_SPIDER_EYE))
		{
			return 376;
		}
		else if(material.equals(Material.BLAZE_POWDER))
		{
			return 377;
		}
		else if(material.equals(Material.MAGMA_CREAM))
		{
			return 378;
		}
		else if(material.equals(Material.BREWING_STAND_ITEM))
		{
			return 379;
		}
		else if(material.equals(Material.CAULDRON_ITEM))
		{
			return 380;
		}
		else if(material.equals(Material.EYE_OF_ENDER))
		{
			return 381;
		}
		else if(material.equals(Material.SPECKLED_MELON))
		{
			return 382;
		}
		else if(material.equals(Material.MONSTER_EGG))
		{
			return 383;
		}
		else if(material.equals(Material.EXP_BOTTLE))
		{
			return 384;
		}
		else if(material.equals(Material.FIREBALL))
		{
			return 385;
		}
		else if(material.equals(Material.BOOK_AND_QUILL))
		{
			return 386;
		}
		else if(material.equals(Material.WRITTEN_BOOK))
		{
			return 387;
		}
		else if(material.equals(Material.EMERALD))
		{
			return 388;
		}
		else if(material.equals(Material.ITEM_FRAME))
		{
			return 389;
		}
		else if(material.equals(Material.FLOWER_POT_ITEM))
		{
			return 390;
		}
		else if(material.equals(Material.CARROT_ITEM))
		{
			return 391;
		}
		else if(material.equals(Material.POTATO_ITEM))
		{
			return 392;
		}
		else if(material.equals(Material.BAKED_POTATO))
		{
			return 393;
		}
		else if(material.equals(Material.POISONOUS_POTATO))
		{
			return 394;
		}
		else if(material.equals(Material.EMPTY_MAP))
		{
			return 395;
		}
		else if(material.equals(Material.GOLDEN_CARROT))
		{
			return 396;
		}
		else if(material.equals(Material.SKULL_ITEM))
		{
			return 397;
		}
		else if(material.equals(Material.CARROT_STICK))
		{
			return 398;
		}
		else if(material.equals(Material.NETHER_STAR))
		{
			return 399;
		}
		else if(material.equals(Material.PUMPKIN_PIE))
		{
			return 400;
		}
		else if(material.equals(Material.FIREWORK))
		{
			return 401;
		}
		else if(material.equals(Material.FIREWORK_CHARGE))
		{
			return 402;
		}
		else if(material.equals(Material.ENCHANTED_BOOK))
		{
			return 403;
		}
		else if(material.equals(Material.REDSTONE_COMPARATOR))
		{
			return 404;
		}
		else if(material.equals(Material.NETHER_BRICK_ITEM))
		{
			return 405;
		}
		else if(material.equals(Material.QUARTZ))
		{
			return 406;
		}
		else if(material.equals(Material.EXPLOSIVE_MINECART))
		{
			return 407;
		}
		else if(material.equals(Material.HOPPER_MINECART))
		{
			return 408;
		}
		else if(material.equals(Material.PRISMARINE_SHARD))
		{
			return 409;
		}
		else if(material.equals(Material.PRISMARINE_CRYSTALS))
		{
			return 410;
		}
		else if(material.equals(Material.RABBIT))
		{
			return 411;
		}
		else if(material.equals(Material.COOKED_RABBIT))
		{
			return 412;
		}
		else if(material.equals(Material.RABBIT_STEW))
		{
			return 413;
		}
		else if(material.equals(Material.RABBIT_FOOT))
		{
			return 414;
		}
		else if(material.equals(Material.RABBIT_HIDE))
		{
			return 415;
		}
		else if(material.equals(Material.ARMOR_STAND))
		{
			return 416;
		}
		else if(material.equals(Material.IRON_BARDING))
		{
			return 417;
		}
		else if(material.equals(Material.GOLD_BARDING))
		{
			return 418;
		}
		else if(material.equals(Material.DIAMOND_BARDING))
		{
			return 419;
		}
		else if(material.equals(Material.LEASH))
		{
			return 420;
		}
		else if(material.equals(Material.NAME_TAG))
		{
			return 421;
		}
		else if(material.equals(Material.COMMAND_MINECART))
		{
			return 422;
		}
		else if(material.equals(Material.MUTTON))
		{
			return 423;
		}
		else if(material.equals(Material.COOKED_MUTTON))
		{
			return 424;
		}
		else if(material.equals(Material.BANNER))
		{
			return 425;
		}
		else if(material.equals(Material.END_CRYSTAL))
		{
			return 426;
		}
		else if(material.equals(Material.SPRUCE_DOOR_ITEM))
		{
			return 427;
		}
		else if(material.equals(Material.BIRCH_DOOR_ITEM))
		{
			return 428;
		}
		else if(material.equals(Material.JUNGLE_DOOR_ITEM))
		{
			return 429;
		}
		else if(material.equals(Material.ACACIA_DOOR_ITEM))
		{
			return 430;
		}
		else if(material.equals(Material.DARK_OAK_DOOR_ITEM))
		{
			return 431;
		}
		else if(material.equals(Material.CHORUS_FRUIT))
		{
			return 432;
		}
		else if(material.equals(Material.CHORUS_FRUIT_POPPED))
		{
			return 433;
		}
		else if(material.equals(Material.BEETROOT))
		{
			return 434;
		}
		else if(material.equals(Material.BEETROOT_SEEDS))
		{
			return 435;
		}
		else if(material.equals(Material.BEETROOT_SOUP))
		{
			return 436;
		}
		else if(material.equals(Material.DRAGONS_BREATH))
		{
			return 437;
		}
		else if(material.equals(Material.SPLASH_POTION))
		{
			return 438;
		}
		else if(material.equals(Material.SPECTRAL_ARROW))
		{
			return 439;
		}
		else if(material.equals(Material.TIPPED_ARROW))
		{
			return 440;
		}
		else if(material.equals(Material.LINGERING_POTION))
		{
			return 441;
		}
		else if(material.equals(Material.SHIELD))
		{
			return 442;
		}
		else if(material.equals(Material.ELYTRA))
		{
			return 443;
		}
		else if(material.equals(Material.BOAT_SPRUCE))
		{
			return 444;
		}
		else if(material.equals(Material.BOAT_BIRCH))
		{
			return 445;
		}
		else if(material.equals(Material.BOAT_JUNGLE))
		{
			return 446;
		}
		else if(material.equals(Material.BOAT_ACACIA))
		{
			return 447;
		}
		else if(material.equals(Material.BOAT_DARK_OAK))
		{
			return 448;
		}
		return material.getId();
	}

	/*
	 * Returns a classic block type id related to the material
	 */
	@SuppressWarnings("deprecation")
	public static Material toMaterial(int id)
	{
		if(id==0)
		{
			return Material.AIR;
		}
		else if(id==1)
		{
			return Material.STONE;
		}
		else if(id==2)
		{
			return Material.GRASS;
		}
		else if(id==3)
		{
			return Material.DIRT;
		}
		else if(id==4)
		{
			return Material.COBBLESTONE;
		}
		else if(id==5)
		{
			return Material.WOOD;
		}
		else if(id==6)
		{
			return Material.SAPLING;
		}
		else if(id==7)
		{
			return Material.BEDROCK;
		}
		else if(id==8)
		{
			return Material.WATER;
		}
		else if(id==9)
		{
			return Material.STATIONARY_WATER;
		}
		else if(id==10)
		{
			return Material.LAVA;
		}
		else if(id==11)
		{
			return Material.STATIONARY_LAVA;
		}
		else if(id==12)
		{
			return Material.SAND;
		}
		else if(id==13)
		{
			return Material.GRAVEL;
		}
		else if(id==14)
		{
			return Material.GOLD_ORE;
		}
		else if(id==15)
		{
			return Material.IRON_ORE;
		}
		else if(id==16)
		{
			return Material.COAL_ORE;
		}
		else if(id==17)
		{
			return Material.LOG;
		}
		else if(id==18)
		{
			return Material.LEAVES;
		}
		else if(id==19)
		{
			return Material.SPONGE;
		}
		else if(id==20)
		{
			return Material.GLASS;
		}
		else if(id==21)
		{
			return Material.LAPIS_ORE;
		}
		else if(id==22)
		{
			return Material.LAPIS_BLOCK;
		}
		else if(id==23)
		{
			return Material.DISPENSER;
		}
		else if(id==24)
		{
			return Material.SANDSTONE;
		}
		else if(id==25)
		{
			return Material.NOTE_BLOCK;
		}
		else if(id==26)
		{
			return Material.BED_BLOCK;
		}
		else if(id==27)
		{
			return Material.POWERED_RAIL;
		}
		else if(id==28)
		{
			return Material.DETECTOR_RAIL;
		}
		else if(id==29)
		{
			return Material.PISTON_STICKY_BASE;
		}
		else if(id==30)
		{
			return Material.WEB;
		}
		else if(id==31)
		{
			return Material.LONG_GRASS;
		}
		else if(id==32)
		{
			return Material.DEAD_BUSH;
		}
		else if(id==33)
		{
			return Material.PISTON_BASE;
		}
		else if(id==34)
		{
			return Material.PISTON_EXTENSION;
		}
		else if(id==35)
		{
			return Material.WOOL;
		}
		else if(id==36)
		{
			return Material.PISTON_MOVING_PIECE;
		}
		else if(id==37)
		{
			return Material.YELLOW_FLOWER;
		}
		else if(id==38)
		{
			return Material.RED_ROSE;
		}
		else if(id==39)
		{
			return Material.BROWN_MUSHROOM;
		}
		else if(id==40)
		{
			return Material.RED_MUSHROOM;
		}
		else if(id==41)
		{
			return Material.GOLD_BLOCK;
		}
		else if(id==42)
		{
			return Material.IRON_BLOCK;
		}
		else if(id==43)
		{
			return Material.DOUBLE_STEP;
		}
		else if(id==44)
		{
			return Material.STEP;
		}
		else if(id==45)
		{
			return Material.BRICK;
		}
		else if(id==46)
		{
			return Material.TNT;
		}
		else if(id==47)
		{
			return Material.BOOKSHELF;
		}
		else if(id==48)
		{
			return Material.MOSSY_COBBLESTONE;
		}
		else if(id==49)
		{
			return Material.OBSIDIAN;
		}
		else if(id==50)
		{
			return Material.TORCH;
		}
		else if(id==51)
		{
			return Material.FIRE;
		}
		else if(id==52)
		{
			return Material.MOB_SPAWNER;
		}
		else if(id==53)
		{
			return Material.WOOD_STAIRS;
		}
		else if(id==54)
		{
			return Material.CHEST;
		}
		else if(id==55)
		{
			return Material.REDSTONE_WIRE;
		}
		else if(id==56)
		{
			return Material.DIAMOND_ORE;
		}
		else if(id==57)
		{
			return Material.DIAMOND_BLOCK;
		}
		else if(id==58)
		{
			return Material.WORKBENCH;
		}
		else if(id==59)
		{
			return Material.CROPS;
		}
		else if(id==60)
		{
			return Material.SOIL;
		}
		else if(id==61)
		{
			return Material.FURNACE;
		}
		else if(id==62)
		{
			return Material.BURNING_FURNACE;
		}
		else if(id==63)
		{
			return Material.SIGN_POST;
		}
		else if(id==64)
		{
			return Material.WOODEN_DOOR;
		}
		else if(id==65)
		{
			return Material.LADDER;
		}
		else if(id==66)
		{
			return Material.RAILS;
		}
		else if(id==67)
		{
			return Material.COBBLESTONE_STAIRS;
		}
		else if(id==68)
		{
			return Material.WALL_SIGN;
		}
		else if(id==69)
		{
			return Material.LEVER;
		}
		else if(id==70)
		{
			return Material.STONE_PLATE;
		}
		else if(id==71)
		{
			return Material.IRON_DOOR_BLOCK;
		}
		else if(id==72)
		{
			return Material.WOOD_PLATE;
		}
		else if(id==73)
		{
			return Material.REDSTONE_ORE;
		}
		else if(id==74)
		{
			return Material.GLOWING_REDSTONE_ORE;
		}
		else if(id==75)
		{
			return Material.REDSTONE_TORCH_OFF;
		}
		else if(id==76)
		{
			return Material.REDSTONE_TORCH_ON;
		}
		else if(id==77)
		{
			return Material.STONE_BUTTON;
		}
		else if(id==78)
		{
			return Material.SNOW;
		}
		else if(id==79)
		{
			return Material.ICE;
		}
		else if(id==80)
		{
			return Material.SNOW_BLOCK;
		}
		else if(id==81)
		{
			return Material.CACTUS;
		}
		else if(id==82)
		{
			return Material.CLAY;
		}
		else if(id==83)
		{
			return Material.SUGAR_CANE_BLOCK;
		}
		else if(id==84)
		{
			return Material.JUKEBOX;
		}
		else if(id==85)
		{
			return Material.FENCE;
		}
		else if(id==86)
		{
			return Material.PUMPKIN;
		}
		else if(id==87)
		{
			return Material.NETHERRACK;
		}
		else if(id==88)
		{
			return Material.SOUL_SAND;
		}
		else if(id==89)
		{
			return Material.GLOWSTONE;
		}
		else if(id==90)
		{
			return Material.PORTAL;
		}
		else if(id==91)
		{
			return Material.JACK_O_LANTERN;
		}
		else if(id==92)
		{
			return Material.CAKE_BLOCK;
		}
		else if(id==93)
		{
			return Material.DIODE_BLOCK_OFF;
		}
		else if(id==94)
		{
			return Material.DIODE_BLOCK_ON;
		}
		else if(id==95)
		{
			return Material.STAINED_GLASS;
		}
		else if(id==96)
		{
			return Material.TRAP_DOOR;
		}
		else if(id==97)
		{
			return Material.MONSTER_EGGS;
		}
		else if(id==98)
		{
			return Material.SMOOTH_BRICK;
		}
		else if(id==99)
		{
			return Material.HUGE_MUSHROOM_1;
		}
		else if(id==100)
		{
			return Material.HUGE_MUSHROOM_2;
		}
		else if(id==101)
		{
			return Material.IRON_FENCE;
		}
		else if(id==102)
		{
			return Material.THIN_GLASS;
		}
		else if(id==103)
		{
			return Material.MELON_BLOCK;
		}
		else if(id==104)
		{
			return Material.PUMPKIN_STEM;
		}
		else if(id==105)
		{
			return Material.MELON_STEM;
		}
		else if(id==106)
		{
			return Material.VINE;
		}
		else if(id==107)
		{
			return Material.FENCE_GATE;
		}
		else if(id==108)
		{
			return Material.BRICK_STAIRS;
		}
		else if(id==109)
		{
			return Material.SMOOTH_STAIRS;
		}
		else if(id==110)
		{
			return Material.MYCEL;
		}
		else if(id==111)
		{
			return Material.WATER_LILY;
		}
		else if(id==112)
		{
			return Material.NETHER_BRICK;
		}
		else if(id==113)
		{
			return Material.NETHER_FENCE;
		}
		else if(id==114)
		{
			return Material.NETHER_BRICK_STAIRS;
		}
		else if(id==115)
		{
			return Material.NETHER_WARTS;
		}
		else if(id==116)
		{
			return Material.ENCHANTMENT_TABLE;
		}
		else if(id==117)
		{
			return Material.BREWING_STAND;
		}
		else if(id==118)
		{
			return Material.CAULDRON;
		}
		else if(id==119)
		{
			return Material.ENDER_PORTAL;
		}
		else if(id==120)
		{
			return Material.ENDER_PORTAL_FRAME;
		}
		else if(id==121)
		{
			return Material.ENDER_STONE;
		}
		else if(id==122)
		{
			return Material.DRAGON_EGG;
		}
		else if(id==123)
		{
			return Material.REDSTONE_LAMP_OFF;
		}
		else if(id==124)
		{
			return Material.REDSTONE_LAMP_ON;
		}
		else if(id==125)
		{
			return Material.WOOD_DOUBLE_STEP;
		}
		else if(id==126)
		{
			return Material.WOOD_STEP;
		}
		else if(id==127)
		{
			return Material.COCOA;
		}
		else if(id==128)
		{
			return Material.SANDSTONE_STAIRS;
		}
		else if(id==129)
		{
			return Material.EMERALD_ORE;
		}
		else if(id==130)
		{
			return Material.ENDER_CHEST;
		}
		else if(id==131)
		{
			return Material.TRIPWIRE_HOOK;
		}
		else if(id==132)
		{
			return Material.TRIPWIRE;
		}
		else if(id==133)
		{
			return Material.EMERALD_BLOCK;
		}
		else if(id==134)
		{
			return Material.SPRUCE_WOOD_STAIRS;
		}
		else if(id==135)
		{
			return Material.BIRCH_WOOD_STAIRS;
		}
		else if(id==136)
		{
			return Material.JUNGLE_WOOD_STAIRS;
		}
		else if(id==137)
		{
			return Material.COMMAND;
		}
		else if(id==138)
		{
			return Material.BEACON;
		}
		else if(id==139)
		{
			return Material.COBBLE_WALL;
		}
		else if(id==140)
		{
			return Material.FLOWER_POT;
		}
		else if(id==141)
		{
			return Material.CARROT;
		}
		else if(id==142)
		{
			return Material.POTATO;
		}
		else if(id==143)
		{
			return Material.WOOD_BUTTON;
		}
		else if(id==144)
		{
			return Material.SKULL;
		}
		else if(id==145)
		{
			return Material.ANVIL;
		}
		else if(id==146)
		{
			return Material.TRAPPED_CHEST;
		}
		else if(id==147)
		{
			return Material.GOLD_PLATE;
		}
		else if(id==148)
		{
			return Material.IRON_PLATE;
		}
		else if(id==149)
		{
			return Material.REDSTONE_COMPARATOR_OFF;
		}
		else if(id==150)
		{
			return Material.REDSTONE_COMPARATOR_ON;
		}
		else if(id==151)
		{
			return Material.DAYLIGHT_DETECTOR;
		}
		else if(id==152)
		{
			return Material.REDSTONE_BLOCK;
		}
		else if(id==153)
		{
			return Material.QUARTZ_ORE;
		}
		else if(id==154)
		{
			return Material.HOPPER;
		}
		else if(id==155)
		{
			return Material.QUARTZ_BLOCK;
		}
		else if(id==156)
		{
			return Material.QUARTZ_STAIRS;
		}
		else if(id==157)
		{
			return Material.ACTIVATOR_RAIL;
		}
		else if(id==158)
		{
			return Material.DROPPER;
		}
		else if(id==159)
		{
			return Material.STAINED_CLAY;
		}
		else if(id==160)
		{
			return Material.STAINED_GLASS_PANE;
		}
		else if(id==161)
		{
			return Material.LEAVES_2;
		}
		else if(id==162)
		{
			return Material.LOG_2;
		}
		else if(id==163)
		{
			return Material.ACACIA_STAIRS;
		}
		else if(id==164)
		{
			return Material.DARK_OAK_STAIRS;
		}
		else if(id==165)
		{
			return Material.SLIME_BLOCK;
		}
		else if(id==166)
		{
			return Material.BARRIER;
		}
		else if(id==167)
		{
			return Material.IRON_TRAPDOOR;
		}
		else if(id==168)
		{
			return Material.PRISMARINE;
		}
		else if(id==169)
		{
			return Material.SEA_LANTERN;
		}
		else if(id==170)
		{
			return Material.HAY_BLOCK;
		}
		else if(id==171)
		{
			return Material.CARPET;
		}
		else if(id==172)
		{
			return Material.HARD_CLAY;
		}
		else if(id==173)
		{
			return Material.COAL_BLOCK;
		}
		else if(id==174)
		{
			return Material.PACKED_ICE;
		}
		else if(id==175)
		{
			return Material.DOUBLE_PLANT;
		}
		else if(id==176)
		{
			return Material.STANDING_BANNER;
		}
		else if(id==177)
		{
			return Material.WALL_BANNER;
		}
		else if(id==178)
		{
			return Material.DAYLIGHT_DETECTOR_INVERTED;
		}
		else if(id==179)
		{
			return Material.RED_SANDSTONE;
		}
		else if(id==180)
		{
			return Material.RED_SANDSTONE_STAIRS;
		}
		else if(id==181)
		{
			return Material.DOUBLE_STONE_SLAB2;
		}
		else if(id==182)
		{
			return Material.STONE_SLAB2;
		}
		else if(id==183)
		{
			return Material.SPRUCE_FENCE_GATE;
		}
		else if(id==184)
		{
			return Material.BIRCH_FENCE_GATE;
		}
		else if(id==185)
		{
			return Material.JUNGLE_FENCE_GATE;
		}
		else if(id==186)
		{
			return Material.DARK_OAK_FENCE_GATE;
		}
		else if(id==187)
		{
			return Material.ACACIA_FENCE_GATE;
		}
		else if(id==188)
		{
			return Material.SPRUCE_FENCE;
		}
		else if(id==189)
		{
			return Material.BIRCH_FENCE;
		}
		else if(id==190)
		{
			return Material.JUNGLE_FENCE;
		}
		else if(id==191)
		{
			return Material.DARK_OAK_FENCE;
		}
		else if(id==192)
		{
			return Material.ACACIA_FENCE;
		}
		else if(id==193)
		{
			return Material.SPRUCE_DOOR;
		}
		else if(id==194)
		{
			return Material.BIRCH_DOOR;
		}
		else if(id==195)
		{
			return Material.JUNGLE_DOOR;
		}
		else if(id==196)
		{
			return Material.ACACIA_DOOR;
		}
		else if(id==197)
		{
			return Material.DARK_OAK_DOOR;
		}
		else if(id==198)
		{
			return Material.END_ROD;
		}
		else if(id==199)
		{
			return Material.CHORUS_PLANT;
		}
		else if(id==200)
		{
			return Material.CHORUS_FLOWER;
		}
		else if(id==201)
		{
			return Material.PURPUR_BLOCK;
		}
		else if(id==202)
		{
			return Material.PURPUR_PILLAR;
		}
		else if(id==203)
		{
			return Material.PURPUR_STAIRS;
		}
		else if(id==204)
		{
			return Material.PURPUR_DOUBLE_SLAB;
		}
		else if(id==205)
		{
			return Material.PURPUR_SLAB;
		}
		else if(id==206)
		{
			return Material.END_BRICKS;
		}
		else if(id==207)
		{
			return Material.BEETROOT_BLOCK;
		}
		else if(id==208)
		{
			return Material.GRASS_PATH;
		}
		else if(id==209)
		{
			return Material.END_GATEWAY;
		}
		else if(id==210)
		{
			return Material.COMMAND_REPEATING;
		}
		else if(id==211)
		{
			return Material.COMMAND_CHAIN;
		}
		else if(id==212)
		{
			return Material.FROSTED_ICE;
		}
		else if(id==255)
		{
			return Material.STRUCTURE_BLOCK;
		}
		else if(id==256)
		{
			return Material.IRON_SPADE;
		}
		else if(id==257)
		{
			return Material.IRON_PICKAXE;
		}
		else if(id==258)
		{
			return Material.IRON_AXE;
		}
		else if(id==259)
		{
			return Material.FLINT_AND_STEEL;
		}
		else if(id==260)
		{
			return Material.APPLE;
		}
		else if(id==261)
		{
			return Material.BOW;
		}
		else if(id==262)
		{
			return Material.ARROW;
		}
		else if(id==263)
		{
			return Material.COAL;
		}
		else if(id==264)
		{
			return Material.DIAMOND;
		}
		else if(id==265)
		{
			return Material.IRON_INGOT;
		}
		else if(id==266)
		{
			return Material.GOLD_INGOT;
		}
		else if(id==267)
		{
			return Material.IRON_SWORD;
		}
		else if(id==268)
		{
			return Material.WOOD_SWORD;
		}
		else if(id==269)
		{
			return Material.WOOD_SPADE;
		}
		else if(id==270)
		{
			return Material.WOOD_PICKAXE;
		}
		else if(id==271)
		{
			return Material.WOOD_AXE;
		}
		else if(id==272)
		{
			return Material.STONE_SWORD;
		}
		else if(id==273)
		{
			return Material.STONE_SPADE;
		}
		else if(id==274)
		{
			return Material.STONE_PICKAXE;
		}
		else if(id==275)
		{
			return Material.STONE_AXE;
		}
		else if(id==276)
		{
			return Material.DIAMOND_SWORD;
		}
		else if(id==277)
		{
			return Material.DIAMOND_SPADE;
		}
		else if(id==278)
		{
			return Material.DIAMOND_PICKAXE;
		}
		else if(id==279)
		{
			return Material.DIAMOND_AXE;
		}
		else if(id==280)
		{
			return Material.STICK;
		}
		else if(id==281)
		{
			return Material.BOWL;
		}
		else if(id==282)
		{
			return Material.MUSHROOM_SOUP;
		}
		else if(id==283)
		{
			return Material.GOLD_SWORD;
		}
		else if(id==284)
		{
			return Material.GOLD_SPADE;
		}
		else if(id==285)
		{
			return Material.GOLD_PICKAXE;
		}
		else if(id==286)
		{
			return Material.GOLD_AXE;
		}
		else if(id==287)
		{
			return Material.STRING;
		}
		else if(id==288)
		{
			return Material.FEATHER;
		}
		else if(id==289)
		{
			return Material.SULPHUR;
		}
		else if(id==290)
		{
			return Material.WOOD_HOE;
		}
		else if(id==291)
		{
			return Material.STONE_HOE;
		}
		else if(id==292)
		{
			return Material.IRON_HOE;
		}
		else if(id==293)
		{
			return Material.DIAMOND_HOE;
		}
		else if(id==294)
		{
			return Material.GOLD_HOE;
		}
		else if(id==295)
		{
			return Material.SEEDS;
		}
		else if(id==296)
		{
			return Material.WHEAT;
		}
		else if(id==297)
		{
			return Material.BREAD;
		}
		else if(id==298)
		{
			return Material.LEATHER_HELMET;
		}
		else if(id==299)
		{
			return Material.LEATHER_CHESTPLATE;
		}
		else if(id==300)
		{
			return Material.LEATHER_LEGGINGS;
		}
		else if(id==301)
		{
			return Material.LEATHER_BOOTS;
		}
		else if(id==302)
		{
			return Material.CHAINMAIL_HELMET;
		}
		else if(id==303)
		{
			return Material.CHAINMAIL_CHESTPLATE;
		}
		else if(id==304)
		{
			return Material.CHAINMAIL_LEGGINGS;
		}
		else if(id==305)
		{
			return Material.CHAINMAIL_BOOTS;
		}
		else if(id==306)
		{
			return Material.IRON_HELMET;
		}
		else if(id==307)
		{
			return Material.IRON_CHESTPLATE;
		}
		else if(id==308)
		{
			return Material.IRON_LEGGINGS;
		}
		else if(id==309)
		{
			return Material.IRON_BOOTS;
		}
		else if(id==310)
		{
			return Material.DIAMOND_HELMET;
		}
		else if(id==311)
		{
			return Material.DIAMOND_CHESTPLATE;
		}
		else if(id==312)
		{
			return Material.DIAMOND_LEGGINGS;
		}
		else if(id==313)
		{
			return Material.DIAMOND_BOOTS;
		}
		else if(id==314)
		{
			return Material.GOLD_HELMET;
		}
		else if(id==315)
		{
			return Material.GOLD_CHESTPLATE;
		}
		else if(id==316)
		{
			return Material.GOLD_LEGGINGS;
		}
		else if(id==317)
		{
			return Material.GOLD_BOOTS;
		}
		else if(id==318)
		{
			return Material.FLINT;
		}
		else if(id==319)
		{
			return Material.PORK;
		}
		else if(id==320)
		{
			return Material.GRILLED_PORK;
		}
		else if(id==321)
		{
			return Material.PAINTING;
		}
		else if(id==322)
		{
			return Material.GOLDEN_APPLE;
		}
		else if(id==323)
		{
			return Material.SIGN;
		}
		else if(id==324)
		{
			return Material.WOOD_DOOR;
		}
		else if(id==325)
		{
			return Material.BUCKET;
		}
		else if(id==326)
		{
			return Material.WATER_BUCKET;
		}
		else if(id==327)
		{
			return Material.LAVA_BUCKET;
		}
		else if(id==328)
		{
			return Material.MINECART;
		}
		else if(id==329)
		{
			return Material.SADDLE;
		}
		else if(id==330)
		{
			return Material.IRON_DOOR;
		}
		else if(id==331)
		{
			return Material.REDSTONE;
		}
		else if(id==332)
		{
			return Material.SNOW_BALL;
		}
		else if(id==333)
		{
			return Material.BOAT;
		}
		else if(id==334)
		{
			return Material.LEATHER;
		}
		else if(id==335)
		{
			return Material.MILK_BUCKET;
		}
		else if(id==336)
		{
			return Material.CLAY_BRICK;
		}
		else if(id==337)
		{
			return Material.CLAY_BALL;
		}
		else if(id==338)
		{
			return Material.SUGAR_CANE;
		}
		else if(id==339)
		{
			return Material.PAPER;
		}
		else if(id==340)
		{
			return Material.BOOK;
		}
		else if(id==341)
		{
			return Material.SLIME_BALL;
		}
		else if(id==342)
		{
			return Material.STORAGE_MINECART;
		}
		else if(id==343)
		{
			return Material.POWERED_MINECART;
		}
		else if(id==344)
		{
			return Material.EGG;
		}
		else if(id==345)
		{
			return Material.COMPASS;
		}
		else if(id==346)
		{
			return Material.FISHING_ROD;
		}
		else if(id==347)
		{
			return Material.WATCH;
		}
		else if(id==348)
		{
			return Material.GLOWSTONE_DUST;
		}
		else if(id==349)
		{
			return Material.RAW_FISH;
		}
		else if(id==350)
		{
			return Material.COOKED_FISH;
		}
		else if(id==351)
		{
			return Material.INK_SACK;
		}
		else if(id==352)
		{
			return Material.BONE;
		}
		else if(id==353)
		{
			return Material.SUGAR;
		}
		else if(id==354)
		{
			return Material.CAKE;
		}
		else if(id==355)
		{
			return Material.BED;
		}
		else if(id==356)
		{
			return Material.DIODE;
		}
		else if(id==357)
		{
			return Material.COOKIE;
		}
		else if(id==358)
		{
			return Material.MAP;
		}
		else if(id==359)
		{
			return Material.SHEARS;
		}
		else if(id==360)
		{
			return Material.MELON;
		}
		else if(id==361)
		{
			return Material.PUMPKIN_SEEDS;
		}
		else if(id==362)
		{
			return Material.MELON_SEEDS;
		}
		else if(id==363)
		{
			return Material.RAW_BEEF;
		}
		else if(id==364)
		{
			return Material.COOKED_BEEF;
		}
		else if(id==365)
		{
			return Material.RAW_CHICKEN;
		}
		else if(id==366)
		{
			return Material.COOKED_CHICKEN;
		}
		else if(id==367)
		{
			return Material.ROTTEN_FLESH;
		}
		else if(id==368)
		{
			return Material.ENDER_PEARL;
		}
		else if(id==369)
		{
			return Material.BLAZE_ROD;
		}
		else if(id==370)
		{
			return Material.GHAST_TEAR;
		}
		else if(id==371)
		{
			return Material.GOLD_NUGGET;
		}
		else if(id==372)
		{
			return Material.NETHER_STALK;
		}
		else if(id==373)
		{
			return Material.POTION;
		}
		else if(id==374)
		{
			return Material.GLASS_BOTTLE;
		}
		else if(id==375)
		{
			return Material.SPIDER_EYE;
		}
		else if(id==376)
		{
			return Material.FERMENTED_SPIDER_EYE;
		}
		else if(id==377)
		{
			return Material.BLAZE_POWDER;
		}
		else if(id==378)
		{
			return Material.MAGMA_CREAM;
		}
		else if(id==379)
		{
			return Material.BREWING_STAND_ITEM;
		}
		else if(id==380)
		{
			return Material.CAULDRON_ITEM;
		}
		else if(id==381)
		{
			return Material.EYE_OF_ENDER;
		}
		else if(id==382)
		{
			return Material.SPECKLED_MELON;
		}
		else if(id==383)
		{
			return Material.MONSTER_EGG;
		}
		else if(id==384)
		{
			return Material.EXP_BOTTLE;
		}
		else if(id==385)
		{
			return Material.FIREBALL;
		}
		else if(id==386)
		{
			return Material.BOOK_AND_QUILL;
		}
		else if(id==387)
		{
			return Material.WRITTEN_BOOK;
		}
		else if(id==388)
		{
			return Material.EMERALD;
		}
		else if(id==389)
		{
			return Material.ITEM_FRAME;
		}
		else if(id==390)
		{
			return Material.FLOWER_POT_ITEM;
		}
		else if(id==391)
		{
			return Material.CARROT_ITEM;
		}
		else if(id==392)
		{
			return Material.POTATO_ITEM;
		}
		else if(id==393)
		{
			return Material.BAKED_POTATO;
		}
		else if(id==394)
		{
			return Material.POISONOUS_POTATO;
		}
		else if(id==395)
		{
			return Material.EMPTY_MAP;
		}
		else if(id==396)
		{
			return Material.GOLDEN_CARROT;
		}
		else if(id==397)
		{
			return Material.SKULL_ITEM;
		}
		else if(id==398)
		{
			return Material.CARROT_STICK;
		}
		else if(id==399)
		{
			return Material.NETHER_STAR;
		}
		else if(id==400)
		{
			return Material.PUMPKIN_PIE;
		}
		else if(id==401)
		{
			return Material.FIREWORK;
		}
		else if(id==402)
		{
			return Material.FIREWORK_CHARGE;
		}
		else if(id==403)
		{
			return Material.ENCHANTED_BOOK;
		}
		else if(id==404)
		{
			return Material.REDSTONE_COMPARATOR;
		}
		else if(id==405)
		{
			return Material.NETHER_BRICK_ITEM;
		}
		else if(id==406)
		{
			return Material.QUARTZ;
		}
		else if(id==407)
		{
			return Material.EXPLOSIVE_MINECART;
		}
		else if(id==408)
		{
			return Material.HOPPER_MINECART;
		}
		else if(id==409)
		{
			return Material.PRISMARINE_SHARD;
		}
		else if(id==410)
		{
			return Material.PRISMARINE_CRYSTALS;
		}
		else if(id==411)
		{
			return Material.RABBIT;
		}
		else if(id==412)
		{
			return Material.COOKED_RABBIT;
		}
		else if(id==413)
		{
			return Material.RABBIT_STEW;
		}
		else if(id==414)
		{
			return Material.RABBIT_FOOT;
		}
		else if(id==415)
		{
			return Material.RABBIT_HIDE;
		}
		else if(id==416)
		{
			return Material.ARMOR_STAND;
		}
		else if(id==417)
		{
			return Material.IRON_BARDING;
		}
		else if(id==418)
		{
			return Material.GOLD_BARDING;
		}
		else if(id==419)
		{
			return Material.DIAMOND_BARDING;
		}
		else if(id==420)
		{
			return Material.LEASH;
		}
		else if(id==421)
		{
			return Material.NAME_TAG;
		}
		else if(id==422)
		{
			return Material.COMMAND_MINECART;
		}
		else if(id==423)
		{
			return Material.MUTTON;
		}
		else if(id==424)
		{
			return Material.COOKED_MUTTON;
		}
		else if(id==425)
		{
			return Material.BANNER;
		}
		else if(id==426)
		{
			return Material.END_CRYSTAL;
		}
		else if(id==427)
		{
			return Material.SPRUCE_DOOR_ITEM;
		}
		else if(id==428)
		{
			return Material.BIRCH_DOOR_ITEM;
		}
		else if(id==429)
		{
			return Material.JUNGLE_DOOR_ITEM;
		}
		else if(id==430)
		{
			return Material.ACACIA_DOOR_ITEM;
		}
		else if(id==431)
		{
			return Material.DARK_OAK_DOOR_ITEM;
		}
		else if(id==432)
		{
			return Material.CHORUS_FRUIT;
		}
		else if(id==433)
		{
			return Material.CHORUS_FRUIT_POPPED;
		}
		else if(id==434)
		{
			return Material.BEETROOT;
		}
		else if(id==435)
		{
			return Material.BEETROOT_SEEDS;
		}
		else if(id==436)
		{
			return Material.BEETROOT_SOUP;
		}
		else if(id==437)
		{
			return Material.DRAGONS_BREATH;
		}
		else if(id==438)
		{
			return Material.SPLASH_POTION;
		}
		else if(id==439)
		{
			return Material.SPECTRAL_ARROW;
		}
		else if(id==440)
		{
			return Material.TIPPED_ARROW;
		}
		else if(id==441)
		{
			return Material.LINGERING_POTION;
		}
		else if(id==442)
		{
			return Material.SHIELD;
		}
		else if(id==443)
		{
			return Material.ELYTRA;
		}
		else if(id==444)
		{
			return Material.BOAT_SPRUCE;
		}
		else if(id==445)
		{
			return Material.BOAT_BIRCH;
		}
		else if(id==446)
		{
			return Material.BOAT_JUNGLE;
		}
		else if(id==447)
		{
			return Material.BOAT_ACACIA;
		}
		else if(id==448)
		{
			return Material.BOAT_DARK_OAK;
		}
		return Material.getMaterial(id);
	}



}
