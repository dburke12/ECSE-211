/**The controller class that takes data from the poller, and filters the data.
 *
 * @author Daniel
 *
 */
import java.util.Arrays;

import lejos.nxt.comm.RConsole;

public class UltrasonicController extends Thread{
	private UltrasonicPoller poller;
	private int distance;
	private int[] measurements = new int[6];
	private int valCount = 0;
	private int FWidth = 5;
	
	public UltrasonicController(UltrasonicPoller p) {
		this.poller = p;
	}

	/**
	 *
	 * @return the filtered distance from the ultrasonic sensor
	 */
	public int getFilteredDist() {

		measurements[5] = poller.getDist();

		updateReadings();
		if (measurements[2] == 0 || measurements[3] == 0
				|| measurements[4] == 0 || measurements[5] == 0) {
			return measurements[5];
		} else {

			Arrays.sort(measurements);
			// median
			if (measurements[4] > measurements[2]) {
				return measurements[2];
			} else {
				// RConsole.print(Integer.toString(measurements[0]) + " ");
				// RConsole.print(Integer.toString(measurements[1]) + " ");
				// RConsole.print(Integer.toString(measurements[2]) + " ");
				// RConsole.print(Integer.toString(measurements[3]) + " ");
				RConsole.println(Integer.toString(measurements[4]));

				return measurements[4];
			}
		}
		/*
		 * 
		 * RConsole.println(Integer.toString(poller.getDist())); return
		 * poller.getDist();
		 */
	}

	public void updateReadings() {

		measurements[0] = measurements[1];
		measurements[1] = measurements[2];
		measurements[2] = measurements[3];
		measurements[3] = measurements[4];
		measurements[4] = measurements[5];

	}
	
	public void run(){
		getFilteredDist();
	}
}