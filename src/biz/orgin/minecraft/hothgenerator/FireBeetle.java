package biz.orgin.minecraft.hothgenerator;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Endermite;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

public class FireBeetle
{
	public static Endermite spawn(Location location, FireBeetleType fireBeetleType)
	{
		World world = location.getWorld();
		Entity mite = world.spawnEntity(location, EntityType.ENDERMITE);
		Endermite m = (Endermite)mite;
		
		switch(fireBeetleType)
		{
		case PRIME:
			m.setCustomName(FireBeetleType.PRIME.getName());
			m.setMaxHealth(128.0);
			m.setHealth(128.0);
			break;
		case REGULAR:
			m.setCustomName(FireBeetleType.REGULAR.getName());
			m.setMaxHealth(32.0);
			m.setHealth(32);
			break;
		case HATCHLING:
			m.setCustomName(FireBeetleType.HATCHLING.getName());
			m.setMaxHealth(8.0);
			m.setHealth(8);
			break;
		}

		m.setCustomNameVisible(true);

		return m;
	}
	
	public static FireBeetleType getFireBeetleType(String type)
	{
		 for(FireBeetleType t : FireBeetleType.values())
		 {
			 if(t.name.equals(type))
			 {
				 return t;
			 }
		 }
		 
		 return null;
	}
	
	public enum FireBeetleType {
		 PRIME ("Prime fire beetle"),
		 REGULAR ("Fire beetle"),
		 HATCHLING ("Fire beetle hatchling");
		 
		 private final String name;
		 
		 private FireBeetleType(String name)
		 {
			 this.name = name;
		 }
		 
		 public String getName()
		 {
			 return this.name;
		 }
		 
		 public String toString()
		 {
			 return this.name;
		 }
	}

}
