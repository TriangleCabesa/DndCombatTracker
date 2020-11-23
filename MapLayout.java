import java.awt.*;
import java.util.Random;

public class MapLayout
{
	
	private int x;
	private int y; 
	private int id;
	private int drawnX;
	private int drawnY;
	private Color changingGreen;
	
	public MapLayout(){}
	
	public MapLayout(int a, int b, int di)
	{
		
		x = a;
		y = b;
		id = di;
		Random random = new Random((long)Math.random());
		random.setSeed(random.nextLong());
		changingGreen = new Color(20+(int)(Math.random()*28),
								  210+(int)(Math.random()*20),
								  70+(int)(Math.random()*32));
		drawnX = 0;
		drawnY = 0;
		
	}
	
	public void setX(int n){x=n;}
	
	public void setY(int n){y=n;}
	
	public void setCenter(int n, int m)
	{
		
		drawnX = n;
		drawnY = m;
		
	}
	
	public int getX(){return x;}
	
	public int getY(){return y;}
	
	public int getID(){return id;}
	
	public Color getColor(){return changingGreen;}
	
	public int getDrawnX (){return drawnX;}
	
	public int getDrawnY (){return drawnY;}
	
}