package biz.orgin.minecraft.hothgenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;

/**
 * Generates the terrain
 * @author orgin
 *
 */
public class HothGenerator extends ChunkGenerator
{
	HothGeneratorPlugin plugin; 
	
	private int height;
	
	private NoiseGenerator noiseGenerator;
	
	public HothGenerator(HothGeneratorPlugin plugin)
	{
		this.plugin = plugin;
		this.height = 256;
		this.noiseGenerator = null;
	}
	
	public HothGenerator(HothGeneratorPlugin plugin, int height)
	{
		this.plugin = plugin;
		this.height = height;
		this.noiseGenerator = null;
	}
	
	@Override
	public byte[][] generateBlockSections(World world, Random random, int chunkx, int chunkz, BiomeGrid biomes)
	{
		if(this.noiseGenerator==null)
		{
			this.noiseGenerator = new NoiseGenerator(world);
		}
		
		Random localRand = new Random(chunkx*chunkz);
		Position[][] snowcover = new Position[16][16];
		
		int vsegs = HothGeneratorPlugin.maxHeight(world, height) / 16;
		byte[][] chunk = new byte[vsegs][];
		
		for(int z=0;z<16;z++)
		{
			for(int x=0;x<16;x++)
			{
				int rx = chunkx*16 + x;
				int rz = chunkz*16 + z;
				
				Biome biome = biomes.getBiome(x, z);
				float factor = 1.0f;
				if(biome.equals(Biome.DESERT_HILLS))
				{
					factor = 2.0f;
				}
				else if(biome.equals(Biome.EXTREME_HILLS))
				{
					factor = 8.0f;
				}
				else if(biome.equals(Biome.FOREST_HILLS))
				{
					factor = 3.0f;
				}
				else if(biome.equals(Biome.ICE_MOUNTAINS))
				{
					factor = 5.0f;
				}
				else if(biome.equals(Biome.ICE_PLAINS))
				{
					factor = 0.5f;
				}
				else if(biome.equals(Biome.FROZEN_OCEAN))
				{
					factor = 0.3f;
				}
				else if(biome.equals(Biome.FROZEN_RIVER))
				{
					factor = 0.3f;
				}
				else if(biome.equals(Biome.JUNGLE_HILLS))
				{
					factor = 3.0f;
				}
				else if(biome.equals(Biome.MUSHROOM_ISLAND))
				{
					factor = 1.5f;
				}
				else if(biome.equals(Biome.PLAINS))
				{
					factor = 0.5f;
				}
				else if(biome.equals(Biome.RIVER))
				{
					factor = 0.3f;
				}
				else if(biome.equals(Biome.SMALL_MOUNTAINS))
				{
					factor = 5.0f;
				}
				else if(biome.equals(Biome.TAIGA_HILLS))
				{
					factor = 3.0f;
				}
				// BEDROCK Layer
				int y = 0;
				HothUtils.setPos(chunk, x,y,z,Material.BEDROCK);
				HothUtils.setPos(chunk, x,y+1,z, getBedrockMaterial(localRand, (int)(256*0.9f))); // 90%
				HothUtils.setPos(chunk, x,y+2,z, getBedrockMaterial(localRand, (int)(256*0.7f))); // 70%
				HothUtils.setPos(chunk, x,y+3,z, getBedrockMaterial(localRand, (int)(256*0.5f))); // 50%
				HothUtils.setPos(chunk, x,y+4,z, getBedrockMaterial(localRand, (int)(256*0.3f))); // 30%
				HothUtils.setPos(chunk, x,y+5,z, getBedrockMaterial(localRand, (int)(256*0.2f))); // 20%
				
				// STONE Layer, solid (Use Populator for ores, lava and such?)
				for(y=6	;y<27;y++)
				{
					HothUtils.setPos(chunk, x,y,z, Material.STONE);
				}
				
				// STONE Layer
				double stone = this.noiseGenerator.noise(rx, rz, 8, 16)*3;
				for(int i=0;i<(int)(stone);i++)
				{
					HothUtils.setPos(chunk, x,y,z, Material.STONE);
					y++;
				}
				
				// DIRT Layer
				double dirt = this.noiseGenerator.noise(rx, rz, 8, 11)*5;
				for(int i=2;i< (int)(dirt);i++)
				{
					HothUtils.setPos(chunk, x,y,z, Material.DIRT);
					y++;
				}

				// GRAVEL Layer
				double gravel = this.noiseGenerator.noise(rx, rz, 7, 16)*5;
				for(int i=2;i< (int)(gravel);i++)
				{
					HothUtils.setPos(chunk, x,y,z, Material.GRAVEL);
					y++;
				}

				// SANDSTONE Layer
				double sandstone = this.noiseGenerator.noise(rx, rz, 8, 23)*4;
				for(int i=1;i< (int)(sandstone);i++)
				{
					HothUtils.setPos(chunk, x,y,z, Material.SANDSTONE);
					y++;
				}

				// SAND Layer
				double sand = 1+this.noiseGenerator.noise(rx, rz, 8, 43)*4;
				for(int i=0;i< (int)(sand);i++)
				{
					HothUtils.setPos(chunk, x,y,z, Material.SAND);
					y++;
				}

				// CLAY Layer
				double clay = 1+this.noiseGenerator.noise(rx, rz, 3, 9)*5;
				for(int i=3;i< (int)(clay);i++)
				{
					HothUtils.setPos(chunk, x,y,z, Material.CLAY);
					y++;
				}
				

				
				// ice Layer
				while(y<34)
				{
					HothUtils.setPos(chunk, x,y,z, Material.ICE);
					y++;
				}
				
				double icel = this.noiseGenerator.noise(rx, rz, 3, 68)*8;
				for(int i=3;i< (int)(icel);i++)
				{
					HothUtils.setPos(chunk, x,y,z, Material.ICE);
					y++;
				}
				
				double iceh = this.noiseGenerator.noise(rx, rz, 3, 7)*15;
				
				// ICE mountains and hills
				double ice = factor * (this.noiseGenerator.noise(rx, rz, 4, 63)*2 + 
						      this.noiseGenerator.noise(rx, rz, 10, 12)) * 2.5;
				
				int icey = 64+(int)(ice);
				double dicey = 64+ice;
				for(;y<(icey-iceh);y++)
				{
					HothUtils.setPos(chunk, x,y,z, Material.SNOW_BLOCK);
				}

				for(;y<(icey);y++)
				{
					HothUtils.setPos(chunk, x,y,z, Material.ICE);
				}
				
				
				// Inject stone mountains
				double domountain = this.noiseGenerator.noise(rx, rz, 4, 236)*20;
				double mfactor = 0.0f;
				if(domountain>18)
				{
					mfactor = 1.0f;
				}
				else if (domountain>16)
				{
					mfactor = (domountain-16)*0.5;
				}
					
				if(mfactor>0)
				{
					double mountain = this.noiseGenerator.noise(rx, rz, 4, 27)*84; // bulk of the mountain
					mountain = mountain + this.noiseGenerator.noise(rx, rz, 8, 3)*5; // Add a bit more noise
					for(int i=0;i<(int)(mountain*mfactor);i++)
					{
						HothUtils.setPos(chunk, x,i+26,z, Material.STONE);
						
						if(i+26>y)
						{
							y = i+26;
						}
					}
				}
				
				// snowblock cover
				double snowblocks = 1+this.noiseGenerator.noise(rx, rz, 8, 76)*2;

				for(int i = 0;i<(int)(snowblocks + (dicey - (int)dicey)); i++)
				{
					HothUtils.setPos(chunk, x,y,z, Material.SNOW_BLOCK);
					y++;
				}				
				
				// snow cover
				double dval = snowblocks+dicey;
				snowcover[z][x] = new Position(rx, y, rz, (int) (8.0*(dval-(int)(dval))));
				HothUtils.setPos(chunk, x,y,z, Material.SNOW);


				
				
				// LAVA Layer
				double dolava = this.noiseGenerator.noise(rx, rz, 4, 71)*10;
				if(dolava>7)
				{
					double lava = this.noiseGenerator.noise(rx, rz, 4, 7)*21;
					int lavay = (int)lava-18;
					if(lavay>-2)
					{
						int start = 8-(2+lavay)/2;
						
						for(int i=-1;i<lavay;i++)
						{
							if(start+i>6)
							{
								HothUtils.setPos(chunk, x,start+i,z, Material.AIR);
							}
							else
							{
								HothUtils.setPos(chunk, x,start+i,z, Material.LAVA);
							}
						}
					}
				}

				
				// WATER Layer
				double dowater = this.noiseGenerator.noise(rx, rz, 4, 91)*10;
				if(dowater>7)
				{
					double water = this.noiseGenerator.noise(rx, rz, 4, 8)*21;
					int watery = (int)water-18;
					if(watery>-2)
					{
						int start = 23-(2+watery)/2;
						
						for(int i=-1;i<watery;i++)
						{
							if(start+i>21)
							{
								HothUtils.setPos(chunk, x,start+i,z, Material.AIR);
							}
							else
							{
								HothUtils.setPos(chunk, x,start+i,z, Material.WATER);
							}
							
						}
					}
				}
				
			}
		}
		
		GardenGenerator.generateGarden(this.plugin, world, new Random(random.nextLong()), chunkx, chunkz);
		RoomGenerator.generateRooms(world, this.plugin, new Random(random.nextLong()), chunkx, chunkz);
		OreGenerator.generateOres(chunk, new Random(random.nextLong()));
		DomeGenerator.generateDome(plugin, world, new Random(random.nextLong()), chunkx, chunkz);
		BaseGenerator.generateBase(plugin, world, new Random(random.nextLong()), chunkx, chunkz);
		if(plugin.isSmoothSnow())
		{
			SnowGenerator.generateSnowCover(plugin, world, snowcover);
		}

		return chunk;
	}
	
	private Material getBedrockMaterial(Random localRand, int limit)
	{
		int ran = localRand.nextInt() & 0xff;
		if(ran>limit)
		{
			return Material.STONE;
		}
		return Material.BEDROCK;
	}
	
	@Override
	public List<BlockPopulator> getDefaultPopulators(World world)
	{
			List<BlockPopulator> list = new ArrayList<BlockPopulator>(1);
			list.add(new CavePopulator(this.plugin));
			list.add(new HothPopulator(this.height));
			return list;
	}

	@Override
	public Location getFixedSpawnLocation(World world, Random random)
	{
		
		int y = 65;
		if(this.height<=66)
		{
			y = (this.height/2)+1;
		}
		
		return new Location(world,8,y,8);
	}
	

}