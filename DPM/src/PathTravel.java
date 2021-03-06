/**
 * Travels w/out use of odometer
 * @author DPM TEAM 4
 *
 */
public class PathTravel {
	/*     N    
	 *     |
	 * W - - - E
	 *     |
	 *     S
	 */
	private int x,y;
	private String orientation;
	private Navigation navi;
	private Path path;
	/**
	 * 
	 * @param x starting x
	 * @param y starting y
	 * @param o starting orientation
	 * @param n navigator
	 * @param p the path to travel
	 */
	public PathTravel(int x,int y, String o, Navigation n,Path p){
		this.x = x;
		this.y = y;
		path = p;
		orientation = o;
		navi = n;
	}
	/**
	 * Moves north one tile
	 */
	public void moveNorth(){
		y++;
		if(orientation.equals("N")){
			navi.testTile();
		}
		else if(orientation.equals("S")){
			navi.turnCCW();
			navi.turnCCW();
			navi.testTile();
		}
		else if(orientation.equals("E")){
			navi.turnCCW();
			navi.testTile();
		}
		else if(orientation.equals("W")){
			navi.turnCW();
			navi.testTile();
		}
		orientation = "N";
	}
	/**
	 * moves south one tile
	 */
	public void moveSouth(){
		y--;
		if(orientation.equals("S")){
			navi.testTile();
		}
		else if(orientation.equals("N")){
			navi.turnCCW();
			navi.turnCCW();
			navi.testTile();
		}
		else if(orientation.equals("W")){
			navi.turnCCW();
			navi.testTile();
		}
		else if(orientation.equals("E")){
			navi.turnCW();
			navi.testTile();
		}
		orientation = "S";
	}
	/**
	 * moves east one tile
	 */
	public void moveEast(){
		x++;
		if(orientation.equals("E")){
			navi.testTile();
		}
		else if(orientation.equals("W")){
			navi.turnCCW();
			navi.turnCCW();
			navi.testTile();
		}
		else if(orientation.equals("S")){
			navi.turnCCW();
			navi.testTile();
		}
		else if(orientation.equals("N")){
			navi.turnCW();
			navi.testTile();
		}
		orientation = "E";
	}
	/**
	 * moves west one tile
	 */
	public void moveWest(){
		x--;
		if(orientation.equals("W")){
			navi.testTile();
		}
		else if(orientation.equals("E")){
			navi.turnCCW();
			navi.turnCCW();
			navi.testTile();
		}
		else if(orientation.equals("N")){
			navi.turnCCW();
			navi.testTile();
		}
		else if(orientation.equals("S")){
			navi.turnCW();
			navi.testTile();
		}
		orientation = "W";
	}
	
	private String getNext(GridSquare next){
		String mov = "";
		if(next.getY() == y+1 && next.getX() == x){
			mov = "N";
		}
		else if(next.getY() == y-1 && next.getX() == x){
			mov = "S";
		}
		else if(next.getX() == x+1 && next.getY() == y){
			mov = "E";
		}
		else if(next.getX() == x-1 && next.getY() == y){
			mov = "W";
		}
		return mov;
	}
	/**
	 * Sets a new path
	 * @param pat new path to travel
	 */
	public void setPath(Path pat){
		this.path = pat;
	}
	/**
	 * moves the robot through the path
	 */
	public void travelPath(){
		for(int i = 0; i < path.getSquares().size(); i++){
			String next = getNext(path.getSquares().get(i));
			if(next.equals("N")){
				moveNorth();
			}
			else if(next.equals("S")){
				moveSouth();
			}
			else if(next.equals("E")){
				moveEast();
			}
			else if(next.equals("W")){
				moveWest();
			}
		}
	}
	/**
	 * faces robot west
	 */
	public void faceWest() {
		if(orientation.equals("W")){
			navi.testTile();
		}
		else if(orientation.equals("E")){
			navi.turnCCW();
			navi.turnCCW();
		}
		else if(orientation.equals("N")){
			navi.turnCCW();
		}
		else if(orientation.equals("S")){
			navi.turnCW();
		}
		orientation = "W";
		
	}
}
