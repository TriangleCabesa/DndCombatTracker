import java.awt.Polygon;

public class Hexagon
{
	private Polygon p;
	public Hexagon(){}
	
	public Hexagon(int x1, int y1, int x2, int y2)
	{
		
		p = new Polygon();
		
        for (int i = 0; i < 6; i++){
			double angleRad = Math.PI / 180 * (60 * i);
            p.addPoint((int) (x1 + x2 * Math.cos(angleRad)),
                       (int) (y1 + y2 * Math.sin(angleRad))); 
		}
		
	}
	
	public Polygon getHex(){return p;}
	
}