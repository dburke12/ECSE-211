import java.util.ArrayList;

import lejos.nxt.LCD;
import lejos.nxt.Sound;

/**
 * Will move the robot about the map, analyzing movements and wall locations,
 * will stop once the position of the robot has been determined
 * 
 * @author Daniel
 *
 */
public class Localizer {

	boolean running;

	int gridx, gridy, turncount,startX,startY;
	String startO;
	private static final int WALL = 30;
	private static final int WALL2 = 50;
	
	private String orientation;

	double thet;

	private Odometer odo;

	private Navigation nav;

	private ArrayList<Ghost> ghosts;

	private UltrasonicController controller;

	/**
	 * Creates a new localizer
	 * 
	 * @param map
	 *            Map the robot is on
	 * @param odo
	 *            Robot's odometer
	 * @param nav
	 *            robots navigator
	 * @param uc
	 *            the ultrasonic Controller
	 */
	public Localizer(Map map, Odometer odo, Navigation nav, UltrasonicController uc) {
		running = true;
		this.nav = nav;
		this.odo = odo;
		this.controller = uc;
		ghosts = new ArrayList<Ghost>();
		for(int i = 0; i < map.getSize(); i++){
			for(int j = 0; j < map.getSize(); j++){
				if(!map.getSquare(i,j).isWall()){
					Ghost n = new Ghost(i,j,"N",map);
					Ghost s = new Ghost(i,j,"S",map);
					Ghost w = new Ghost(i,j,"W",map);
					Ghost e = new Ghost(i,j,"E",map);
					ghosts.add(n);
					ghosts.add(s);
					ghosts.add(w);
					ghosts.add(e);
				}
			}
		}
	}

	/**
	 * Gets whether or not the localizer is still running
	 * 
	 * @return running (true = running)
	 */
	public boolean isRunning(){
		return running;
	}
	
	private int numValid(){
		int num = 0;
		for(Ghost g : ghosts){
			if(g.isValid()){
				num++;
			}
		}
		return num;
	}
	
	private void noWall(){
		for(Ghost g: ghosts){
			if(g.wallinFront() != 0){
				g.invalid();
			}
		}
	}
	
	private void wall(){
		for(Ghost g : ghosts){
			if(g.wallinFront() == 0){
				g.invalid();
			}
		}
	}
	
	private void wall2(){
		for(Ghost g : ghosts){
			if(g.wallinFront() != 2){
				g.invalid();
			}
		}
	}

	/**
	 * Main runnable, will localize to given map
	 */
	public void run() {
		controller.start();
		do {
			for(int i = 0; i != 50; i++){
				controller.getFilteredDist();
				LCD.clear();
				LCD.drawInt(controller.getFilteredDist(), 0, 0);
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (controller.getFilteredDist() < WALL) {
				Sound.beep();
				wall();
				nav.turnCCW();
				for (Ghost g : ghosts) {
					if (g.isValid()) {
						g.turnLeft();
					}
				}
			} 
			else if(controller.getFilteredDist() < WALL2){
				Sound.beepSequenceUp();
				wall2();
				if(turncount >= 3){
					//thresh++;
					turncount = 0;
					nav.turnCCW();
					for(Ghost g : ghosts){
						if(g.isValid()){
							g.turnLeft();
						}
					}
				}
				else{
					turncount++;
					nav.testTile();
					for(Ghost g : ghosts){
						if(g.isValid()){
							g.move();
						}
					}
				}
			}
			else {
				noWall();
				if (turncount >= 3) {
					turncount = 0;
					nav.turnCCW();
					for (Ghost g : ghosts) {
						if (g.isValid()) {
							g.turnLeft();
						}
					}
				} else {
					turncount++;
					nav.testTile();
					for (Ghost g : ghosts) {
						if (g.isValid()) {
							g.move();
						}
					}
				}

			}
		}while(numValid() > 1);
		for (Ghost g : ghosts) {
			if (g.isValid()) {
				LCD.clear();
				gridx = g.getX();
				gridy = g.getY();
				startX = g.getStartX();
				startY = g.getStartY();
				startO = g.getStartO();
				orientation = g.getOrientation();
				LCD.drawInt(g.getStartX(), 0, 0);
				LCD.drawInt(g.getStartY(), 0, 2);
//				odo.setX(g.getX() * 30 + 15);
//				odo.setY(g.getY() * 30 + 15);
//				if (g.getOrientation().equals("N")) {
//					odo.setTheta(0);
//					thet = 0;
//				}
//				if (g.getOrientation().equals("S")) {
//					odo.setTheta(Math.PI);
//					thet = Math.PI;
//				}
//				if (g.getOrientation().equals("E")) {
//					odo.setTheta(Math.PI / 2);
//					thet = Math.PI / 2;
//				}
//				if (g.getOrientation().equals("W")) {
//					odo.setTheta(3 * (Math.PI / 2));
//					thet = 3 * Math.PI / 2;
//				}
			}
			else{
				g = null;
			}
		}

		Sound.beepSequence();
		running = false;
	}


	/**
	 * 
	 * @return grid X position
	 */
	public int getX() {
		return gridx;
	}
	/**
	 * 
	 * @return grid y position
	 */
	public int getY() {
		return gridy;
	}
	/**
	 * 
	 * @return orientation
	 */
	public String getO(){
		return orientation;
	}
	/**
	 * 
	 * @return start x
	 */
	public int getStartX() {
		// TODO Auto-generated method stub
		return startX - 1;
	}
	/**
	 * 
	 * @return start Y
	 */
	public int getStartY() {
		// TODO Auto-generated method stub
		return startY - 1;
	}
	/**
	 * 
	 * @return start orientation
	 */
	public String getStartO(){
		return startO;
	}
}
