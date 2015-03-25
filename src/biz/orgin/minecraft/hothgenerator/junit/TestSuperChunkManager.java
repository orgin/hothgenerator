package biz.orgin.minecraft.hothgenerator.junit;

import static org.junit.Assert.*;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import biz.orgin.minecraft.hothgenerator.Position;
import biz.orgin.minecraft.hothgenerator.SuperBlob;
import biz.orgin.minecraft.hothgenerator.SuperBlobType;
import biz.orgin.minecraft.hothgenerator.SuperChunk;
import biz.orgin.minecraft.hothgenerator.SuperChunkBlob;
import biz.orgin.minecraft.hothgenerator.SuperChunkBlob.ChunkKey;
import biz.orgin.minecraft.hothgenerator.SuperChunkManager;
import biz.orgin.minecraft.hothgenerator.SuperChunkStorage;
import biz.orgin.minecraft.hothgenerator.SuperChunkStorage.SuperChunkKey;

public class TestSuperChunkManager {

	@Test
	public void test()
	{
		SuperChunkManager scm = new SuperChunkManager();
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
		
		scm.addSuperBlob("mytestworld", superBlob);
		
		Map<String, SuperChunkStorage> map = scm.getSuperChunkStorageList();
		
		// Only one world added?
		Set<String> keys = map.keySet();
		assertTrue(keys.size()==1);

		// Correct name on world?
		Iterator<String> iter = keys.iterator();
		String worldName = iter.next();
		assertTrue(worldName.equals("mytestworld"));
		
		SuperChunkStorage scs = map.get(worldName);
		Map<SuperChunkKey, SuperChunk> scmap = scs.getSuperChunkMap();

		// Correct number of superchunks?
		Set<SuperChunkKey> keyset = scmap.keySet();
		assertTrue(keyset.size()==3);
		Iterator<SuperChunkKey> keyseti = keyset.iterator();
		
		boolean part1 = false;
		boolean part2 = false;
		boolean part3 = false;
		
		SuperChunk sc_1 = null;
		SuperChunk sc_2 = null;
		SuperChunk sc_3 = null;
		
		// Verify contents of all the superchunks
		while(keyseti.hasNext())
		{
			SuperChunkKey sckey = keyseti.next();
			SuperChunk sc = scmap.get(sckey);
			
			if(sckey.equals(new SuperChunkKey(0,0)))
			{
				sc_1 = sc;
				this.verifySuperChunk(sckey, sc, 2, 5, 0);
				part1 = true;
			}
			else if(sckey.equals(new SuperChunkKey(0,-256)))
			{
				sc_2 = sc;
				this.verifySuperChunk(sckey, sc, 1, 0, 5);
				part2 = true;
			}
			else if(sckey.equals(new SuperChunkKey(-256,-256)))
			{
				sc_3 = sc;
				this.verifySuperChunk(sckey, sc, 2, 2, 3);
				part3 = true;
			}
			else
			{
				fail("Invalid SuperChunk found. Wrong coordinates:" + sckey);
			}
			
		}
		
		// All superchunks found?
		assertTrue(part1 && part2 && part3);

		// Set up test directory
		File file = new File("test");
		file.mkdir();
		SuperChunkStorage.BASEPATH = "test";
		
		// Save the data
		scm.save();
		
		// Load data from storage
		SuperChunkKey sckey1 = new SuperChunkKey(0, 0);
		SuperChunk sc1 = scs.load(sckey1);
		this.verifySuperChunk(sckey1, sc1, 2, 5, 0);


		SuperChunkKey sckey2 = new SuperChunkKey(0, -256);
		SuperChunk sc2 = scs.load(sckey2);
		this.verifySuperChunk(sckey2, sc2, 1, 0, 5);

		SuperChunkKey sckey3 = new SuperChunkKey(-256, -256);
		SuperChunk sc3 = scs.load(sckey3);
		this.verifySuperChunk(sckey3, sc3, 2, 2, 3);
		
		// Data is the same after loading?
		assertTrue(sc1.toString().equals(sc_1.toString()));
		assertTrue(sc2.toString().equals(sc_2.toString()));
		assertTrue(sc3.toString().equals(sc_3.toString()));
		
		// Clean up file system
		this.wipeDirectory(file);
		
		System.out.println("End of line.");
	}
	
	private void verifySuperChunk(SuperChunkKey sckey, SuperChunk sc, int listCnt, int expectedPrimaryCount, int expectedSecondaryCount)
	{
		Set<ChunkKey> ckSet = sc.keySet();
		assertTrue(ckSet.size()==listCnt);
		//System.out.println(sckey + " has " + ckSet.size() + " superchunkblobs");
		
		int pCnt = 0;
		int sCnt = 0;
		
		Iterator<ChunkKey> ckSetI = ckSet.iterator();
		
		while(ckSetI.hasNext())
		{
			ChunkKey ck = ckSetI.next();
			List<SuperChunkBlob> scbList = sc.get(ck);
			//System.out.println("  " + ck + " with " + scbList.size() + " superchunkblobs");
			for(int i=0;i<scbList.size();i++)
			{
				SuperChunkBlob scb = scbList.get(i);
				Set<Position> primary = scb.getPrimary();
				Set<Position> secondary = scb.getSecondary();
				pCnt = pCnt + primary.size();
				sCnt = sCnt + secondary.size();
				//System.out.println("     Primary has " + primary.size() + " entries");
				//System.out.println("     Secondary has " + secondary.size() + " entries");
			}
			
		}
		
		//System.out.println("  Primary entries: " + pCnt);
		//System.out.println("  Secondary entries: " + sCnt);

		assertTrue(pCnt == expectedPrimaryCount);
		assertTrue(sCnt == expectedSecondaryCount);

	}
	
	
	private void wipeDirectory(File file)
	{
		File[] files = file.listFiles();
		for(int i=0;i<files.length;i++)
		{
			String name = files[i].getName();
			if(!name.equals(".") && !name.equals(".."))
			{
				if(files[i].isDirectory())
				{
					this.wipeDirectory(files[i]);
				}
				else
				{
					files[i].delete();
				}
			}
		}
		file.delete();
	}

}
