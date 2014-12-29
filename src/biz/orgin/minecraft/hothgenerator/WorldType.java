package biz.orgin.minecraft.hothgenerator;

public enum WorldType {
 HOTH ("hoth"),
 TATOOINE ("tatooine"),
 DAGOBAH ("dagobah"),
 MUSTAFAR ("mustafar");
 
 private final String name;
 
 public static void main(String[] args)
 {
	 try
	 {
		 WorldType wt = WorldType.getType("tatooine");
		 System.out.println("wt = " + wt);
		 if(wt == WorldType.TATOOINE)
		 {
			 System.out.println("wt is indeed " + WorldType.TATOOINE);
		 }
		 
		 System.out.println(WorldType.getType("hoth"));
		 System.out.println(WorldType.getType("tatooine"));
		 System.out.println(WorldType.getType("dagobah"));
		 System.out.println(WorldType.getType("mustafar"));
		 System.out.println(WorldType.getType("earth"));
	 }
	 catch(Exception e)
	 {
		 e.printStackTrace();
	 }
 }
 
 WorldType(String name)
 {
	 this.name = name;
 }
 
 public String toString()
 {
	 return name;
 }
 
 public static WorldType getType(String type) throws InvalidWorldTypeException
 {
	 for(WorldType t : WorldType.values())
	 {
		 if(t.name.equals(type.toLowerCase()))
		 {
			 return t;
		 }
	 }
	 
	 throw new InvalidWorldTypeException();
 }
 
 public static class InvalidWorldTypeException extends Exception
 {
	private static final long serialVersionUID = 2780389615326711849L;

	public InvalidWorldTypeException()
	{
	}
 }
}
