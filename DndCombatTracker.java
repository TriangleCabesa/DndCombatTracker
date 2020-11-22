import java.util.Scanner;
import java.awt.*;

public class DndCombatTracker 
{
	private static Scanner in = new Scanner(System.in);
	
	public static void main(String[] args)
	{
		MapLayout[][] map;
		System.out.print("Please input the desired map size: ");
		int n = in.nextInt();
		map = new MapLayout[n][n];
		CombatTrackerInterface display = new CombatTrackerInterface();
		int mapSize = map[0].length;
		
		
		for(int i = 0; i < mapSize; i++)
		{
			for(int j = 0; j < mapSize; j++)
			{
				map[i][j] = new MapLayout(i,j,(i*10)+j);
			}
		}
		
		
		
		DndPlayer player = new DndPlayer(n/2,n/2,30,5);
		display.intializeDisplay();
		Point p = MouseInfo.getPointerInfo().getLocation();
		int x;
		int y;
		boolean exitCondition = true;

		while(exitCondition)
		{
			
			p = MouseInfo.getPointerInfo().getLocation();
			x = p.x-display.getFrameX()-7;
			y = p.y-display.getFrameY()-30;
			display.displayUpdate(map, x, y,player);
			if(x+y==10000)
				exitCondition = false;
			
		}
		
	}
	
}