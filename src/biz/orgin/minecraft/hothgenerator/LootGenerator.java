package biz.orgin.minecraft.hothgenerator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Random;
import java.util.Vector;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

public class LootGenerator
{
	private String name;
	private Loot[] loot;
	
	private static Random random = new Random();
	
	private static Hashtable<String, LootGenerator> generators = new Hashtable<String, LootGenerator>();
	
	private static LootGenerator defLoot = new LootGenerator(new Loot[]
		{
			// Material, data, min, max %
			new Loot(Material.SAPLING,         (byte)0x02, 1, 4, 10), //Birch sapling
			new Loot(Material.BOOK,            (byte)0x00, 1, 2,  5), //Books
			new Loot(Material.BREAD,           (byte)0x00, 1, 3, 75), //Bread
			new Loot(Material.BUCKET,          (byte)0x00, 1, 1, 30), //Bucket
			new Loot(Material.COAL,            (byte)0x00, 1, 1, 20), //Coal
			new Loot(Material.INK_SACK,        (byte)0x03, 1, 2,  5), //Cocoa Beans
			new Loot(Material.COMPASS,         (byte)0x00, 1, 1,  5), //Compasses
			new Loot(Material.DIAMOND,         (byte)0x00, 1, 1,  5), //Diamond
			new Loot(Material.ENDER_PEARL,     (byte)0x00, 1, 1,  5), //Ender Pearls
			new Loot(Material.GOLD_INGOT,      (byte)0x00, 1, 1,  5), //Gold Ingot
			new Loot(Material.GOLDEN_APPLE,    (byte)0x00, 1, 1,  5), //Golden Apple (Normal)
			new Loot(Material.SULPHUR,         (byte)0x00, 1, 4,  5), //Gunpowder
			new Loot(Material.IRON_BOOTS,      (byte)0x00, 1, 1, 25), //Iron Boots
			new Loot(Material.IRON_CHESTPLATE, (byte)0x00, 1, 1, 25), //Iron Chestplates
			new Loot(Material.IRON_HELMET,     (byte)0x00, 1, 1, 25), //Iron Helmets
			new Loot(Material.IRON_INGOT,      (byte)0x00, 1, 2, 10), //Iron Ingot
			new Loot(Material.IRON_PICKAXE,    (byte)0x00, 1, 1, 25), //Iron Pickaxe
			new Loot(Material.IRON_SWORD,      (byte)0x00, 1, 1, 25), //Iron Swords
			new Loot(Material.SAPLING,         (byte)0x03, 1, 4,  5), //Jungle sapling
			new Loot(Material.INK_SACK,        (byte)0x04, 1, 8, 15), //Lapis Lazuli
			new Loot(Material.MELON_SEEDS,     (byte)0x00, 1, 2,  5), //Melon Seeds
			new Loot(Material.GREEN_RECORD,    (byte)0x00, 1, 1,  1), //Melon Seeds
			new Loot(Material.RECORD_3,        (byte)0x00, 1, 1,  1), //Music Discs
			new Loot(Material.RECORD_4,        (byte)0x00, 1, 1,  1), //Music Discs
			new Loot(Material.RECORD_5,        (byte)0x00, 1, 1,  1), //Music Discs
			new Loot(Material.RECORD_6,        (byte)0x00, 1, 1,  1), //Music Discs
			new Loot(Material.RECORD_7,        (byte)0x00, 1, 1,  1), //Music Discs
			new Loot(Material.RECORD_8,        (byte)0x00, 1, 1,  1), //Music Discs
			new Loot(Material.RECORD_9,        (byte)0x00, 1, 1,  1), //Music Discs
			new Loot(Material.RECORD_10,       (byte)0x00, 1, 1,  1), //Music Discs
			new Loot(Material.RECORD_11,       (byte)0x00, 1, 1,  1), //Music Discs
			new Loot(Material.SAPLING,         (byte)0x00, 1, 4,  5), //Oak sapling
			new Loot(Material.OBSIDIAN,        (byte)0x00, 1, 2,  4), //Obsidian
			new Loot(Material.PAPER,           (byte)0x00, 1, 3, 10), //Paper
			new Loot(Material.SAPLING,         (byte)0x01, 1, 4,  5), //Pine sapling
			new Loot(Material.PUMPKIN_SEEDS,   (byte)0x00, 1, 2,  5), //Pumpkin Seeds
			new Loot(Material.RAILS,           (byte)0x00, 1,10, 25), //Rails
			new Loot(Material.APPLE,           (byte)0x00, 1, 5, 50), //Red Apples
			new Loot(Material.REDSTONE,        (byte)0x00, 1, 4, 15), //Redstone
			new Loot(Material.SEEDS,           (byte)0x00, 1, 2, 25), //Seeds
			new Loot(Material.SADDLE,          (byte)0x00, 1, 1,  5), //Saddle
			new Loot(Material.STRING,          (byte)0x00, 1, 3, 50), //String
			new Loot(Material.SUGAR_CANE,      (byte)0x00, 1, 3,  5), //Sugar cane
			new Loot(Material.WATER_LILY,      (byte)0x00, 1, 5,  5), //Water lily
			new Loot(Material.WHEAT,           (byte)0x00, 1, 4,  5), //Wheat
			new Loot(Material.POTATO_ITEM,     (byte)0x00, 1, 2,  5), //Potato
			new Loot(Material.CARROT_ITEM,     (byte)0x00, 1, 2,  5), //Carrot
			
			new Loot(Material.MONSTER_EGG,     (byte)90, 1, 4,  3),  //Pig egg
			new Loot(Material.MONSTER_EGG,     (byte)91, 1, 4,  3),  //Sheep
			new Loot(Material.MONSTER_EGG,     (byte)92, 1, 4,  3),  //Cow
			new Loot(Material.MONSTER_EGG,     (byte)93, 1, 4,  3),  //Chicken
			new Loot(Material.MONSTER_EGG,     (byte)95, 1, 4,  3),  //Wolf
			new Loot(Material.MONSTER_EGG,     (byte)98, 1, 4,  3)   //Ocelot
		}
	);
	
	private LootGenerator()
	{
		
	}
	
	private LootGenerator(Loot[] loot)
	{
		this.loot = loot;
		this.name = "default";
	}
	
	public static void load(HothGeneratorPlugin plugin)
	{
		File dataFolder = plugin.getDataFolder();
		String path = dataFolder.getAbsolutePath() + "/custom";
		File customFolder = new File(path);
		if(!customFolder.exists())
		{
			customFolder.mkdir();
		}
		else
		{
			File[] files = customFolder.listFiles();
			if(files!=null)
			{
				for(int i=0;i<files.length;i++)
				{
					File file = files[i];
					if(file.isFile() && file.getName().endsWith(".ll")  && !file.getName().equals("example.ll"))
					{
						try
						{
							LootGenerator generator = LootGenerator.loadLootGenerator(file);
							LootGenerator.generators.put(file.getName(), generator);
							plugin.getLogger().info("Loaded custom loot list: " + file.getName());

						}
						catch(Exception e)
						{
							plugin.getLogger().info("ERROR: Failed to load " + file.getName() + " " + e.getMessage());
						}
					}
				}
			}
		}
	}
	public static LootGenerator getLootGenerator(String name)
	{
		return LootGenerator.generators.get(name);
		
	}

	private static LootGenerator loadLootGenerator(File lootFile) throws IOException
	{
		LootGenerator generator = new LootGenerator();
		generator.name = lootFile.getName();
		
		
		BufferedReader reader = new BufferedReader(new FileReader(lootFile));
		
		String line;
		
		Vector<Loot> lootVector = new Vector<Loot>();
		
		while( (line=reader.readLine())!=null)
		{
			String row = line.trim();
			if(row.length()>0 &&  row.charAt(0)!=';' && row.charAt(0)!='#')
			{
				// Name, Material, data, min, max, %
				String[] data = row.split(",");
				if(data.length!=6)
				{
					throw new IOException("Error reading from " + generator.name + " wrong number of parameters: " + row);
				}
				
				int materialID,dataVal,min,max,probability;
				
				try
				{
					materialID = Integer.parseInt(data[1]);
					dataVal = Integer.parseInt(data[2]);
					min = Integer.parseInt(data[3]);
					max = Integer.parseInt(data[4]);
					probability = Integer.parseInt(data[5]);
				}
				catch(Exception e)
				{
					throw new IOException("Error reading from " + generator.name + " invalid parameters: " + row);
				}
				
				Material material = Material.getMaterial(materialID);
				if(material==null)
				{
					throw new IOException("Error reading from " + generator.name + " Unknow materialID: " + row);
				}
				
				if(dataVal<0)
				{
					throw new IOException("Error reading from " + generator.name + " data value below 0: " + row);
				}
				
				if(min<0)
				{
					throw new IOException("Error reading from " + generator.name + " min value below 0: " + row);
				}
				
				if(max<min)
				{
					throw new IOException("Error reading from " + generator.name + " max value below min: " + row);
				}
				
				if(probability>100 || probability<1)
				{
					throw new IOException("Error reading from " + generator.name + " invalid probability: " + row);
				}
				
				Loot newLoot = new Loot(material, (byte)dataVal, min, max, probability);
				lootVector.add(newLoot);
			}
		}
		
		generator.loot = lootVector.toArray(new Loot[lootVector.size()]);
		return generator;
	}

	public static LootGenerator getLootGenerator()
	{
		return LootGenerator.defLoot;
	}
	
	public Inventory getLootInventory(Inventory inv, int min, int max)
	{
		Random random = LootGenerator.random;
		
		int items = min + random.nextInt(max-min);
		
		for(int i=0;i<items;i++)
		{
			boolean found = false;
			while(!found)
			{
				Loot randLoot = this.loot[random.nextInt(this.loot.length)];
				
				int prob = random.nextInt(100);
				if(randLoot.chance >= prob)
				{
					int cnt = randLoot.min, rand = randLoot.max - randLoot.min;
					if(rand>0)
					{
						cnt = cnt + random.nextInt(rand);
					}
					

					MaterialData mdata = new MaterialData(randLoot.material, randLoot.data);
					ItemStack stack = mdata.toItemStack(cnt);

					inv.addItem(stack);
					
					found = true;
				}
			}
		}
		
		return inv;
	}

	public String getName() {
		return name;
	}
}
