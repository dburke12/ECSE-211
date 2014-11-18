import java.util.ArrayList;


public class MapTester {
	
	
	public static void main(String[] args){
		boolean[][] walls = {{false,false,false,false,false,true,false,false},{false,false,false,false,false,false,false,true},{false,false,false,false,true,false,true,true}
			,{false,false,false,false,false,true,false,false},{false,true,true,true,false,false,false,false},{false,false,false,false,false,false,false,false},{false,false,true,false,false,true,false,false},
			{true,false,true,true,false,false,true,false}};
		
		Map map = new Map(8,1);
		
		map.addWalls(walls);
		
		map.populate();
		if(map.getWalls() != null){
			for(GridSquare s : map.getWalls()){
				System.out.println(s.getX() + " , " + s.getY());
			}
		}
		
		
		ArrayList<Ghost> gs = new ArrayList<Ghost>();
		for(int i = 0; i < map.getSize(); i++){
			for(int j = 0; j < map.getSize(); j++){
				if(!map.getSquare(i,j).isWall()){
					Ghost n = new Ghost(i,j,"N",map);
					Ghost s = new Ghost(i,j,"S",map);
					Ghost w = new Ghost(i,j,"W",map);
					Ghost e = new Ghost(i,j,"E",map);
					gs.add(n);
					gs.add(s);
					gs.add(w);
					gs.add(e);
				}
			}
		}
		
		Ghost robot = new Ghost(3,5,"N",map);
		//robot.turn();
		//System.out.println(robot.getX() + " , " + robot.getY() + " , " + robot.getOrientation() + " , " + robot.wallinFront());
		Localizer loc = new Localizer(map,robot);
		loc.start();

		System.out.println(loc.numValid());
		for(Ghost goul : gs){
			//System.out.println(goul.getX() + " , " + goul.getY() + " , " + goul.getOrientation() + " , " + goul.wallinFront());
			//gs.remove(goul);
		}
		
		//System.out.println(gs.size());
		//System.out.println(g.getX() + " , " + g.getY() + " , " + g.getOrientation() + " , " + g.wallinFront());
		

		System.out.println("Hello");
	}
}