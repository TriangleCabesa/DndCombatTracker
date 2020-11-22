import java.awt.*;
import java.util.Random;
import java.util.Date;

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
		Random random = new Random(new Date().getTime());
		changingGreen = new Color(20+random.nextInt(28),
								  210+random.nextInt(20),
								  70+random.nextInt(32));
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