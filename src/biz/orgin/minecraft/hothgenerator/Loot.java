package biz.orgin.minecraft.hothgenerator;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

/**
 * Used to generate random loot for chests in underground mazes
 * @author orgin
 *
 */
public class Loot
{
	private static Loot[] loot = new Loot[]
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
			};
	
	public Material material;
	public byte data;
	public int min;
	public int max;
	public int chance;
	
	private Loot(Material material, byte data, int min, int max, int chance)
	{
		this.material = material;
		this.data = data;
		this.min = min;
		this.max = max;
		this.chance = chance;
	}
	
	public static Inventory getLoot(Inventory inv)
	{
		Random random = new Random(System.currentTimeMillis());
		
		int items = 2 + random.nextInt(8);
		
		for(int i=0;i<items;i++)
		{
			boolean found = false;
			while(!found)
			{
				Loot randLoot = Loot.loot[random.nextInt(Loot.loot.length)];
				
				int prob = random.nextInt(100);
				if(randLoot.chance > prob)
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
	
}
