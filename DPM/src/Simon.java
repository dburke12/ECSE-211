import lejos.nxt.Button;
import lejos.nxt.ColorSensor;
import lejos.nxt.LCD;
import lejos.nxt.Motor;
import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.SensorPort;
import lejos.nxt.Sound;
import lejos.nxt.UltrasonicSensor;
import lejos.robotics.Color;

/**
 * MAIN RUNNING CLASS
 * @author DPM Team 4
 *
 */
public class Simon {
	public static void main(String[] args) {
		int buttonChoice;
		

		
		boolean[][] walls1 = {{true,true,false,false,false,false,true,false,false,true,false,false},
				{true,true,false,true,false,false,false,false,false,false,false,false},
				{false,false,true,false,false,false,false,false,false,false,false,true},
				{false,false,false,false,false,true,false,false,false,false,true,false},
				{false,false,false,false,true,false,false,true,false,false,false,false},
				{true,false,true,false,false,false,false,false,false,false,false,false},
				{false,false,false,true,false,false,false,false,false,false,false,false},
				{false,false,false,false,false,false,false,true,false,false,false,false},
				{true,true,false,false,false,false,true,false,false,false,false,false},
				{false,false,false,false,true,false,false,true,false,true,false,false},
				{false,false,true,false,false,false,true,false,false,false,false,false},
				{false,true,false,false,false,false,false,false,false,false,false,false}};	
		
		boolean[][] walls2 = {{true,true,false,false,true,true,false,false,false,false,false,false},
				{true,true,false,false,false,false,false,false,false,false,false,false},
				{false,false,false,false,false,false,false,false,false,false,false,false},
				{false,false,true,false,false,true,true,false,false,true,false,false},
				{false,false,false,false,false,true,false,false,false,true,true,false},
				{false,false,false,false,false,true,false,false,false,false,false,false},
				{false,false,false,false,false,false,false,false,true,true,false,false},
				{true,false,false,false,false,false,false,false,false,false,false,false},
				{false,false,false,false,true,true,false,false,false,false,true,false},
				{false,true,false,false,false,false,false,false,false,false,false,true},
				{false,false,false,false,false,false,false,true,false,false,false,false},
				{false,false,true,false,false,true,false,false,true,false,false,false}};
		
		boolean[][] walls3 = {{true,true,false,true,false,false,false,false,true,false,false,false},
				{true,true,false,false,false,false,false,false,false,false,false,false},
				{false,false,false,true,false,false,true,false,false,false,false,false},
				{false,false,true,false,true,false,false,false,false,false,true,false},
				{false,true,false,false,false,true,true,true,false,false,false,false},
				{false,false,false,false,false,false,false,false,false,false,false,false},
				{false,false,false,false,false,false,true,false,true,false,false,false},
				{true,false,false,false,false,false,false,false,false,false,false,true},
				{false,false,false,false,false,false,false,false,false,false,false,false},
				{false,false,false,false,false,false,true,false,false,false,false,false},
				{true,false,false,true,true,false,false,false,false,true,false,false},
				{false,false,true,false,false,false,false,false,false,false,false,true}};
		
		//Other three maps.
		boolean[][] walls4 = {{true, true, true, true, true, false, false, false, true, false, false, false},
				{true, true, false, false, true, false, false, false, false, false, false, false},
				{true, false, false, false, false, true, false, false, false, false, true, false},
				{false, false, true, false, false, false, false, false, false, true, false, true},
				{false, false, false, true, false, false, false, false, false, false, false, false},
				{false, false, false, false, false, false, false, false, false, false, true, false},
				{false, false, false, false, true, false, false, false, false, false, false, false},
				{false, false, false, false, false, false, false, false, false, false, false, false},
				{false, false, false, false, true, false, false, false, true, false, false, false},
				{true, false, false, false, true, false, true, false, false, false, false, false},
				{false, false, false, false, false, false, false, false, false, false, false, false},
				{false, true, false, false, false, true, false, false, false, false, true, false}};
		
		
		boolean[][] walls5 = {{true, true, false, true, false, true, false, false, true, false, false, false},
				{true, true, false, false, false, false, false, false, false, false, false, false},
				{false, false, false, false, false, false, false, false, false, false, false, false},
				{false, true, false, true, false, false, false, false, false, false, true, false},
				{false, false, false, true, false, false, true, true, false, false, true, false},
				{false, false, false, false, false, false, false, false, true, false, false, false},
				{false, false, false, false, false, false, false, false, false, true, false, false},
				{false, false, false, false, false, false, true, true, false, false, false, false},
				{false, true, false, false, false, true, false, false, false, false, true, false},
				{false, false, true, false, false, false, true, false, false, false, false, false},
				{false, false, false, false, false, false, false, false, false, false, true, false},
				{false, false, true, false, false, false, false, false, false, true, false, false}};
		
		
		boolean[][] walls6 = {{true, true, false, false, false, false, false, false, false, false, true, false},
				{true, true, false, false, false, true, false, false, false, false, false, false},
				{false, false, false, true, false, false, false, false, false, false, false, false},
				{false, false, true, false, false, false, false, false, false, false, true, false},
				{false, false, true, false, false, false, false, true, false, false, false, false},
				{false, false, true, false, true, false, true, false, false, false, false, true},
				{false, false, false, false, false, true, false, false, false, true, false, false},
				{false, false, false, false, false, false, true, false, true, false, false, false},
				{false, false, true, false, false, false, false, false, false, false, true, false},
				{true, false, false, false, true, false, false, false, true, false, false, false},
				{false, false, false, false, false, true, false, false, false, false, true, false},
				{false, false, false, false, false, false, false, false, false, false, false, false}};

		
		
		
		//Initialize EVERYTHING HERE DAMMIT
		
		//Motor/Driver initialization
		NXTRegulatedMotor leftMotor = Motor.A;
		NXTRegulatedMotor rightMotor = Motor.B;
		NXTRegulatedMotor clawMotor = Motor.C;
		WheelDriver wheels = new WheelDriver(leftMotor,rightMotor);
		ClawDriver claw = new ClawDriver(clawMotor);
		
		//LightSensor Initialization
		ColorSensor rightCS = new ColorSensor(SensorPort.S2);
		ColorSensor leftCS = new ColorSensor(SensorPort.S3);
		ColorSensor clawCS = new ColorSensor(SensorPort.S4);
		//clawCS.setFloodlight(true);
		
		LightSensorPoller rightPoll = new LightSensorPoller(rightCS);
		LightSensorController rightCSControl = new LightSensorController(rightPoll);
		LightSensorPoller leftPoll = new LightSensorPoller(leftCS);
		LightSensorController leftCSControl = new LightSensorController(leftPoll);
		
		FindLine lineLeft = new FindLine(leftPoll);
		FindLine lineRight = new FindLine(rightPoll);
		
		
		//Odometer Initialization
		Odometer odometer = new Odometer();
		//Initialize map, localization, and navigation.
		Navigation nav = new Navigation(0,0,odometer, wheels,lineLeft,lineRight);
		CorrectionAngel correction = new CorrectionAngel(odometer, lineLeft, lineRight, nav);
		LCDDisplay display = new LCDDisplay(odometer);
		
		//Ultrasonic Initialization
		UltrasonicSensor us = new UltrasonicSensor(SensorPort.S1);
		UltrasonicPoller usPoller = new UltrasonicPoller(us);
		usPoller.start();
		UltrasonicController usController = new UltrasonicController(usPoller);
		
		
		

		int mapID = 1;
		
		//Start all the threads
		rightPoll.start();
		leftPoll.start();
		lineLeft.start();
		lineRight.start();
//		odometer.start();
//		nav.start();
		display.setDisplay(String.valueOf(mapID));
		display.start();
		while(true) {

			clawCS.setFloodlight(Color.RED);
			
			// clear the display
			LCD.clear();
			
			

			buttonChoice = Button.waitForAnyPress();
			if(buttonChoice == Button.ID_ENTER){
				display.setDisplay("NONE");
				break;
			}
			else if(buttonChoice == Button.ID_RIGHT){
				LCD.clear();
				mapID++;
				buttonChoice = Button.waitForAnyPress();
				display.setDisplay(String.valueOf(mapID));
			}
			else if(buttonChoice == Button.ID_RIGHT){
				LCD.clear();
				mapID--;
				buttonChoice = Button.waitForAnyPress();
				display.setDisplay(String.valueOf(mapID));
			}
		}

		if (buttonChoice == Button.ID_LEFT) {


		} 
		else if(buttonChoice == Button.ID_RIGHT) {

			
		}
		else{
			display.setDisplay("NONE");
			LCD.clear();
			int dropX = 0;
			int dropY = 0;
			do{
				buttonChoice = Button.waitForAnyPress();
				LCD.clear();
				LCD.drawInt(dropX, 0, 0);
				LCD.drawInt(dropY,0,2);
				if(buttonChoice == Button.ID_RIGHT){
					dropX++;
					buttonChoice = Button.waitForAnyPress();
				}
				if(buttonChoice == Button.ID_LEFT){
					dropY++;
					buttonChoice = Button.waitForAnyPress();
				}
			}while(buttonChoice != Button.ID_ENTER);
			clawCS.setFloodlight(Color.BLUE);
			LCD.clear();
			LCD.drawInt(dropX, 0, 0);
			LCD.drawInt(dropY,0,2);
			try {
				Thread.sleep(00);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			odometer.start();
			//correction.start();
			nav.setAngel(correction);
			Map map = new Map(12,1);
			if(mapID == 1){
				Sound.beep();
				map.addWalls(walls1);
				walls1 = null;
				walls2 = null;
				walls3 = null;
				walls4 = null;
				walls5 = null;
				walls6 = null;
			}
			else if(mapID == 2){
				Sound.twoBeeps();
				map.addWalls(walls2);
				walls1 = null;
				walls2 = null;
				walls3 = null;
				walls4 = null;
				walls5 = null;
				walls6 = null;
			}
			else if(mapID == 3){
				Sound.buzz();
				map.addWalls(walls3);
				walls1 = null;
				walls2 = null;
				walls3 = null;
				walls4 = null;
				walls5 = null;
				walls6 = null;
			}
			else if(mapID == 4){
				Sound.beepSequenceUp();
				map.addWalls(walls4);
				walls1 = null;
				walls2 = null;
				walls3 = null;
				walls4 = null;
				walls5 = null;
				walls6 = null;
			}
			else if(mapID == 5){
				Sound.beepSequence();
				map.addWalls(walls5);
				walls1 = null;
				walls2 = null;
				walls3 = null;
				walls4 = null;
				walls5 = null;
				walls6 = null;
			}
			else if(mapID == 6){
				Sound.twoBeeps();
				Sound.buzz();
				map.addWalls(walls6);
				walls1 = null;
				walls2 = null;
				walls3 = null;
				walls4 = null;
				walls5 = null;
				walls6 = null;
				
			}
			clawCS.setFloodlight(Color.RED);
			LCD.clear();
			LCD.drawInt(dropX, 0, 0);
			LCD.drawInt(dropY,0,2);
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			clawCS.setFloodlight(Color.BLUE);
			display.setDisplay("ODOMETER");
			
			map.populate();
			display.setDisplay("ODOMETER");
			display.setDisplay("OFF");
			display = null;
			LCD.clear();

			Localizer localizer = new Localizer(map,odometer,nav,usController);
			localizer.run();
			clawCS.setFloodlight(Color.GREEN);
			int x = localizer.getX();
			int y = localizer.getY();
			int startX = localizer.getStartX();
			int startY = localizer.getStartY();
			String startO = localizer.getStartO();
			String o = localizer.getO();
			localizer = null;
			Pathfinder pf = new Pathfinder(map,map.getSquare(2, 1),map.getSquare(x, y));
			
			pf.genPath();
			map.reInitialize();
			clawCS.setFloodlight(Color.RED);
			PathTravel pt = new PathTravel(x,y,o,nav,pf.getPath());
			pt.travelPath();
			pt.faceWest();
			blockPickUp bp = new blockPickUp(claw,leftMotor,rightMotor, usController);
			bp.scanRange();
			try {
				Thread.sleep(1500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//bp.scanRange();
			bp = null;
			clawCS.setFloodlight(Color.GREEN);
			pf = new Pathfinder(map,map.getSquare(dropX, dropY),map.getSquare(2, 1));
			pf.genPath();
			clawCS.setFloodlight(Color.RED);
			pt = new PathTravel(2,1,"E",nav,pf.getPath());
			pt.travelPath();
			claw.open();
			nav.BBBACKDATASSUP();
			LCD.clear();
			LCD.drawInt(startX, 0, 0);
			LCD.drawInt(startY, 0, 1);
			LCD.drawString(startO, 0, 2);
		}
		
		while (Button.waitForAnyPress() != Button.ID_ESCAPE);
		System.exit(0);
	}
}
