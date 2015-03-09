package biz.orgin.minecraft.hothgenerator;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Endermite;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import biz.orgin.minecraft.hothgenerator.FireBeetle.FireBeetleType;

public class EntityDeathManager implements Listener
{
	private HothGeneratorPlugin plugin;
	private Random random;

	public EntityDeathManager(HothGeneratorPlugin plugin)
	{
		this.plugin = plugin;
		this.random = new Random(System.currentTimeMillis());
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onEntitydeath(EntityDeathEvent event)
	{
		Entity entity = event.getEntity();
		EntityType type = event.getEntityType();
		Location location = entity.getLocation();
		World world = location.getWorld();
		
		if(this.plugin.isHothWorld(world) && this.plugin.getWorldType(world)== WorldType.MUSTAFAR)
		{
			if(type.equals(EntityType.ENDERMITE))
			{
				Endermite em = (Endermite)entity;
				String name = em.getCustomName();
				FireBeetleType beetleType = FireBeetle.getFireBeetleType(name);
				
				if(beetleType!=null)
				{
					
					// Spawn some more beetles
					int prob = 2;
					switch(beetleType)
					{
					case PRIME:
						prob = 0;
						for(int i=0;i<this.random.nextInt(2);i++)
						{
							Location pl = location.clone();
							pl.add(this.random.nextDouble(), 0, this.random.nextDouble());
							FireBeetle.spawn(pl, FireBeetleType.REGULAR);
						}
						for(int i=0;i<this.random.nextInt(2);i++)
						{
							Location pl = location.clone();
							pl.add(this.random.nextDouble(), 0, this.random.nextDouble());
							FireBeetle.spawn(pl, FireBeetleType.HATCHLING);
						}
						break;
					case REGULAR:
						prob = 1;
						for(int i=0;i<this.random.nextInt(3);i++)
						{
							Location pl = location.clone();
							pl.add(this.random.nextDouble(), 0, this.random.nextDouble());
							FireBeetle.spawn(pl, FireBeetleType.HATCHLING);
						}
						break;
					case HATCHLING:
						prob = 2;
						break;
					default:
						break;
					}
					
					
					// Drop some loot
					if(prob==0 || this.random.nextInt(prob)==0)
					{
						ItemStack stack = null;
						switch(random.nextInt(10))
						{
						case 1:
							stack = new ItemStack(Material.GLOWSTONE_DUST,1);
							break;
						case 2:
							stack = new ItemStack(Material.FLINT,1);
							break;
						case 3:
							int rnd = random.nextInt(10);
							if(rnd==0)
							{
								stack = new ItemStack(Material.PRISMARINE_SHARD,1);
							}
							else if(rnd<4)
							{
								stack = new ItemStack(Material.PRISMARINE_CRYSTALS,1);
							}
							break;
						case 4:
							stack = new ItemStack(Material.QUARTZ,1);
							break;
						case 5:
							stack = new ItemStack(Material.SPIDER_EYE,1);
							break;

						default:
							break;
						}
						if(stack!=null)
						{
							world.dropItem(location, stack);
						}
					}

				}
				
			}
		}
		
	}
}
