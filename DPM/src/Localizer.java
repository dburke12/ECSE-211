import java.util.ArrayList;

import lejos.nxt.Sound;


public class Localizer{
	
	boolean running;
	
	int gridx,gridy,turncount;
	
	private static final int WALL = 25;
	private static final int WALL2 = 50;
	
	double thet;
	
	private Odometer odo;
	
	private Navigation nav;
	
	private Map map;
	
	private ArrayList<Ghost> ghosts;
	
	private UltrasonicPoller controller;
	
	public Localizer(Map map,Odometer odo,Navigation nav,UltrasonicPoller uc){
		this.map = map;
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
	
	/**Gets whether or not the localizer is still running
	 * 
	 * @return running (true = running)
	 */
	public boolean isRunning(){
		return running;
	}
	
	public int numValid(){
		int num = 0;
		for(Ghost g : ghosts){
			if(g.isValid()){
				num++;
			}
		}
		return num;
	}
	
	public void noWall(){
		for(Ghost g: ghosts){
			if(g.wallinFront() != 0){
				g.invalid();
			}
		}
	}
	
	public void wall(){
		for(Ghost g : ghosts){
			if(g.wallinFront() == 0){
				g.invalid();
			}
		}
	}
	
	public void wall2(){
		for(Ghost g : ghosts){
			if(g.wallinFront() != 2){
				g.invalid();
			}
		}
	}
	
	public void run(){
		while(numValid() != 1){
			if(controller.getDist() < WALL){
				wall();
				nav.turnCCW();
				for(Ghost g : ghosts){
					if(g.isValid()){
						g.turnLeft();
					}
				}
			}
			else if(controller.getDist() < WALL2){
				Sound.beep();
				wall2();
				if(turncount >= 3){
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
					nav.oneTileForward();
					for(Ghost g : ghosts){
						if(g.isValid()){
							g.move();
						}
					}
				}
			}
			else{
				Sound.buzz();
				noWall();
				if(turncount >= 3){
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
					nav.oneTileForward();
					for(Ghost g : ghosts){
						if(g.isValid()){
							g.move();
						}
					}
				}

			}
		}
		for(Ghost g : ghosts){
			if(g.isValid()){
				gridx = g.getX();
				gridy = g.getY();
				
				odo.setX(g.getX()*30 + 15);
				odo.setY(g.getY()*30 + 15);
				if(g.getOrientation().equals("N")) {odo.setTheta(0); thet = 0;}
				if(g.getOrientation().equals("S")) {odo.setTheta(Math.PI); thet = Math.PI;}
				if(g.getOrientation().equals("E")) {odo.setTheta(Math.PI/2); thet = Math.PI/2;}
				if(g.getOrientation().equals("W")) {odo.setTheta(3 * (Math.PI/2)); thet = 3 * Math.PI/2;}
			}
		}
		
		//Sound.beep();
		running = false;
	}
	
	public void setOdo(){
		double[] arr = {(gridx*30 + 15), (gridy* 30 + 15),thet};
		boolean[] arr2 = {true,true,true};
		odo.setPosition(arr,arr2);
//		odo.setX(gridx*30 + 15);
//		odo.setY(gridy* 30 + 15);
//		odo.setTheta(thet);
	}
	
	public int getX(){
		return gridx;
	}
	
	public int getY(){
		return gridy;
	}
	
}
