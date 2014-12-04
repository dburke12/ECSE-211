import java.io.File;
import java.util.ArrayList;
import lejos.nxt.LCD;

/**This class generates a Path when given a start, destination, and map.
 * 
 * @author DPM TEAM 4
 *
 */
public class Pathfinder {
	private ArrayList<Path> paths;
	
	private Path path;
	
	private GridSquare destination,start;
	/**
	 * 
	 * @param map the map the robot is on
	 * @param destination destination GridSquare
	 * @param start Starting GridSquare
	 */
	public Pathfinder(Map map, GridSquare destination, GridSquare start){
		this.destination = destination;
		this.start = start;
		this.paths = new ArrayList<Path>();
		Path init = new Path();
		init.addSquare(start);
		paths.add(init);
		start.visit();
	}
	
	private int idealLen(){
		int len = 0;
		len = (Math.abs(destination.getX() - start.getX()) + Math.abs(destination.getY() - start.getY()));
		return len;
	}
	private int validCount(){
		int count = 0;
		for(Path p : paths){
			if(p.isValid()){
				count++;
			}
		}
		return count;
	}
	
	/**
	 * Generates a path. Separate method for debugging purposes
	 */
	public void genPath(){
		int counter = 0;
		ArrayList<Path> temp = new ArrayList<Path>();
		boolean completed = false;
		while(!completed){
			for(Path p : paths){
				
				if(p != null){
					int gCounter = 0;
					for(GridSquare g : p.getEnd().getSquares()){
						if(!g.isVisited()){
							gCounter++;
							LCD.clear();
							counter++;
							Path newPath = new Path();
							newPath = p.clone();
							newPath.addSquare(g);
							if(newPath.getSquares().size() <= idealLen() + 5){
								
							}
							g.visit();
							temp.add(newPath);
							LCD.drawInt(counter, 0, 0);
							LCD.drawInt(File.freeMemory(), 0, 1);
						}
					}

					if(p.getEnd().getX() == destination.getX() && p.getEnd().getY() == destination.getY()){
						this.path = p;
						completed = true;
						
					}
					else if(true){
						p = null;
					}
				}
			}
			paths.addAll(temp);
			temp.clear();
		}
	}
	/**
	 * Gets the path.
	 * @return the path, the light, the way.
	 */
	public Path getPath(){
		return path;
	}
}
