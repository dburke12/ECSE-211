import java.util.ArrayList;
import java.util.Stack;

import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;

public class Navigation extends Thread{
	
	private double x,y,theta;
	Odometer odometer;
	UltrasonicSensor usensor;
	
	WheelDriver driver;
	
	//tolerance / error
	private static final double TOLERANCE = 2.0, RADS = 0.1;
	
	private boolean navigating;
	
	public Stack<Waypoint> waypoints;
	

	
	//Constructor
	public Navigation(double x, double y, Odometer odometer, WheelDriver driver){
		this.x = x;
		this.y = y;
		theta = 0;
		
		this.driver = driver;
		
		waypoints = new Stack<Waypoint>();
		
		this.odometer = odometer;
		
		navigating = true;
		
		//initialize the motors acceleration
		Motor.A.setAcceleration(3000);
		Motor.B.setAcceleration(3000);
		
		
	}
	
	//travel to 25 cm at right angle to obstruction, then 25 cm to right angle, then should go back to original wayoint
	public void avoid(){
		update();
		double avoidX = this.x + (25*Math.cos(this.theta));
		double avoidY = this.y - (25*Math.sin(this.theta));
		double secondX = avoidX - (25*Math.cos(this.theta + Math.PI/2));
		double secondY = avoidY + (25*Math.sin(this.theta + Math.PI/2));
		waypoints.push(new Waypoint(secondX,secondY));
		waypoints.push(new Waypoint(avoidX,avoidY));
	}
	
	//Constructor, takes a list of waypoints as parameter
	public Navigation(double x, double y, Odometer odometer, ArrayList<Waypoint> points){
		this.x = x;
		this.y = y;
		theta = 0;
		waypoints = new Stack<Waypoint>();
		usensor = new UltrasonicSensor(SensorPort.S1);
		
		//pushes waypoints onto stack (will be called in REVERSE ORDER)
		for(Waypoint w : points){
			waypoints.push(w);
		}
		
		this.odometer = odometer;
		
		navigating = true;
		
		//initialize the motors acceleration
		Motor.A.setAcceleration(3000);
		Motor.B.setAcceleration(3000);
		
		
	}
	
	//updates navigation with odometer values
	public void update(){
		this.x = odometer.getX();
		this.y = odometer.getY();
		this.theta = odometer.getTheta();
	}
	
	//Gets the angle between robot and destination
	public double getAngle(double x, double y){
		update();
		double ydiff = y - this.y;
		double xdiff = x - this.x;
		
		double angle = (Math.atan2(xdiff, ydiff));
		if(angle < 0){
			angle += (Math.PI*2);
		}
		return angle;
		
	}
	
	//Travels to Point on the arena (uses coords)
	public void travelTo(double x, double y){
		
		navigating = true;
		update();
		
		//get and turn to the correct heading
		double destinationAngle = getAngle(x,y);
		turnTo(destinationAngle);
		update();
		
		//move until the odometer reads close to the destination
		while(Math.abs(this.x - x) > TOLERANCE || Math.abs(this.y - y) > TOLERANCE){
			driver.setSpeed(200,200);
			update();
		}
		//Stop Robot
		driver.setSpeed(0,0);
		
		
		navigating = false;
	}
	
	//Travels to Point on the arena(use waypoint instead of coords)
	private void travelTo(Waypoint point){
		
		navigating = true;
		update();
		
		//get and turn to the correct heading
		double destinationAngle = getAngle(point.getX(),point.getY());
		turnTo(destinationAngle);
		update();
		
		//move until the odometer reads close to the destination
		while(Math.abs(this.x - point.getX()) > TOLERANCE || Math.abs(this.y - point.getY()) > TOLERANCE){
			driver.setSpeed(200,200);
			update();
			
			//pushes this waypoint back onto the stack and breaks from loop
			if(usensor.getDistance() <= 10){
				waypoints.push(point);
				break;
			}
			
			destinationAngle = getAngle(point.getX(),point.getY());
			if(Math.abs(destinationAngle - this.theta) > 0.1 && Math.abs(destinationAngle - this.theta) < Math.PI/2){
				turnTo(destinationAngle);
				update();
			}
			
		}
		//Stop Robot
		driver.setSpeed(0,0);
		
		//waypoint will still be on stack if broken from while loop
		waypoints.pop();
		navigating = false;
	}
	
	//Turns to angle theta (absolute)
	public void turnTo(double theta){
		//get current theta from odometer
		this.theta = odometer.getTheta();
		
		double diff = theta - this.theta;
		
		//check tolerance
		while(Math.abs(this.theta - theta) > RADS){
			navigating = true;
			update();
			//check for clockwise or counter clockwise rotation
			
			//For CounterclockWise Rotation(Difference less than -180)
			if(diff < (-1 * Math.PI)){
				driver.setSpeed(200,-200);
			}
			//otherwise Clockwise (either between -80 )
			else if(diff < 0 || diff > Math.PI){
				driver.setSpeed(-200,200);
			}
			//Is between 0 and 180 (counterClockwise)
			else{
				driver.setSpeed(200,-200);;
			}
			
			//Stops motors once completed
			
			
		}
		driver.setSpeed(0,0);
		
		navigating = false;
	}
	
	//Returns true if there is another thread using travelto or turnto
	boolean isNavigating(){
		return navigating;
	}
	
	//Main Runnable
	public void run(){
		//Will pop untill not navigating or waypoints all popped
		while(navigating){
			if(waypoints.isEmpty()){
				break;
			}
			travelTo(waypoints.peek());
			
			if(usensor.getDistance() <= 10){
				avoid();
			}
			if(!waypoints.isEmpty()){
				navigating = true;
			}
		}
		
	}
}