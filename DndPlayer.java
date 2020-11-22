
public class DndPlayer 
{
	
	private int xCoordinate, yCoordinate, zCoordinate;
	private int movementSpeed; 
	private int attackRange;
	private int playerSkin;
	private String playerID;
	
	public DndPlayer()
	{
		
		xCoordinate = 0;
		yCoordinate = 0;
		zCoordinate = 0;
		movementSpeed = 30;
		attackRange = 5;
		playerSkin = 0;
		playerID = "";
		
	}
	
	public DndPlayer(int x, int y, int m, int a)
	{
		
		xCoordinate = x;
		yCoordinate = y;
		movementSpeed = m;
		attackRange = a;
		playerSkin = 0;
		playerID = "";
		
	}
	
	public void setX(int x){xCoordinate = x;}
	
	public void setY(int y){yCoordinate = y;}
	
	public void setZ(int z){zCoordinate = z;}
	
	public void setSpeed(int s){movementSpeed = s;}
	
	public void setRange(int r){attackRange = r;}
	
	public void setID(String i){playerID = i;}
	
	public void setSkin(int n){playerSkin = n;}
	
	public int getX(){return xCoordinate;}
	
	public int getY(){return yCoordinate;}
	
	public int getZ(){return zCoordinate;}
	
	public int getSpeed(){return movementSpeed;}
	
	public int getRange(){return attackRange;}
	
	public String getID(){return playerID;}
	
	public int getSkin(){return playerSkin;}
	
}