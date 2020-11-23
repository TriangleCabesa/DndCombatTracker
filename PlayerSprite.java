import java.awt.*;
import java.util.ArrayList;
import javax.imageio.*;
import java.awt.image.*;
import java.io.*;
import javax.swing.*;

public class PlayerSprite extends JComponent
{
	
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private int xCoordinate;
	private int yCoordinate;
	private int mapScaleX;
	private int mapScaleY;
	private DndPlayer player;
	private ArrayList<Rectangle> spritePixels = new ArrayList<>();
	private ArrayList<Color> spriteColors = new ArrayList<>();

	public PlayerSprite(DndPlayer p)
	{
		
		player = p;
		
	}
	
	public void draw(Graphics2D g2, MapLayout[][] map, int scaleX, int scaleY)
	{
		
		xCoordinate = map[player.getX()-1][player.getY()-1].getDrawnX();
		yCoordinate = map[player.getX()-1][player.getY()-1].getDrawnY();
		mapScaleX = scaleX;
		mapScaleY = scaleY;


		convertImageToRectangle();

		for(int i = 0; i < spritePixels.size(); i++)
		{
			g2.setColor(spriteColors.get(i));
			g2.fill(spritePixels.get(i));
		}
		
	}

	private void convertImageToRectangle()
	{
		BufferedImage img = null;
		try {
    		img = ImageIO.read(new File("Female Berserker.jpg"));
		}
		catch (IOException e) {
			System.out.println("error");
		}
		for(int i = 0; i < 32; i++)
		{
			for(int j = 0; j < 32; j++)
			{
				
				Color c = new Color(img.getRGB(i, j));
				if(c.getRed() >= 235 && c.getGreen() >= 235 && c.getBlue() >= 235)
					continue;

				mapScaleX/=mapScaleX;
				mapScaleY/=mapScaleY;
				spriteColors.add(c);
				spritePixels.add(new Rectangle(xCoordinate+(i-16),yCoordinate+(j-16),mapScaleX,mapScaleY));
				
			}
			
			
		}
	}
	
	
	
}