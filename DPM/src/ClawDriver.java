import lejos.nxt.NXTRegulatedMotor;


public class ClawDriver {
	private NXTRegulatedMotor claw;
	
	private boolean isClosed;
	
	private static final int CLOSING_SPEED = 200;
	
	private static final int CLOSING_DIST = 10;
	
	public ClawDriver(NXTRegulatedMotor claw){
		this.claw = claw;
		isClosed = false;
	}
	
	public void close(){
		if(!isClosed){
			//close here
			isClosed = true;
		}
	}
	
	public void open(){
		if(isClosed){
			//open here
			isClosed = false;
		}
	}
	
}
