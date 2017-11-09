import java.awt.Color;
import java.awt.Graphics;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;


import javax.swing.JPanel;

class Grid extends JPanel
{

		double x_1 = 0,x_2= 0,y_1 = 0,y_2 = 0;
		double skok_x,skok_y;
		double x,y;
		int margin_UP_DOWN=20;
		int margin_LEFT_RIGHT=40;
		int window_width;
		int window_height;
		ComplexNumbers [] [] points= null;
		Color[] colorTable = {Color.RED, Color.GREEN,Color.BLUE, Color.ORANGE, Color.MAGENTA, Color.CYAN, Color.YELLOW, Color.PINK };
		Newton newton;
		HashMap <ComplexNumbers,Integer> mapakrotnosci = new HashMap<ComplexNumbers,Integer>();
		ComplexNumbers [] arrayOfColors = new ComplexNumbers[0];
		boolean isComputing = false;

		Grid(double X_1, double Y_1, double X_2, double Y_2, Newton newton)
		{
			x_1 = Math.min(X_1,X_2); 	
			y_1 = Math.min(Y_1,Y_2); 
			x_2 = Math.max(X_2,X_1); 	
			y_2 = Math.max(Y_2,Y_1);
			this.newton = newton;
		}
	
	    static public String Format(double value ) 
	    {
		      DecimalFormat myFormatter = new DecimalFormat("###.##");
		      String output = myFormatter.format(value);
		      return output;
	    }
		
	    public void drawX(Graphics g)
	    {
		
			int wysokoscOsi = 0;
			int height = window_height;
			int width = window_width;
			//os na dole daleko
			if(y_1 > 0 && y_2 > 0)
			{
				wysokoscOsi = (int) (height - margin_UP_DOWN);
			}
			//os na gorze daleko
			else if(y_1 < 0 && y_2 < 0)
			{
				wysokoscOsi = margin_UP_DOWN;
			}
			//os na ekranie
			else
			{
				wysokoscOsi = (int)(height - (Math.abs(y_1)/(Math.abs(y_1)+Math.abs(y_2))*(height-2*margin_UP_DOWN)+margin_UP_DOWN)); //na koniec odbicie lustrzane
			}
	          //os X(re)
            g.drawLine(margin_LEFT_RIGHT, wysokoscOsi, width-margin_LEFT_RIGHT, wysokoscOsi); 
            
				//z¹bki osi X 
            for (int i = margin_LEFT_RIGHT; i < width - margin_LEFT_RIGHT; i += 60) 
            {
                g.drawLine(i, wysokoscOsi-5, i, wysokoscOsi+5);
                double wartX = x_1 + (i - margin_LEFT_RIGHT)*skok_x;
            	g.drawString(Format(wartX), i,wysokoscOsi-5);
            }
            
            //grot osi X
            g.drawLine(width-margin_LEFT_RIGHT, wysokoscOsi, width-10-margin_LEFT_RIGHT, wysokoscOsi+10);
            g.drawLine(width-margin_LEFT_RIGHT, wysokoscOsi, width-10-margin_LEFT_RIGHT, wysokoscOsi-10);   
            //podpisy osi
            String Re = "Re";
            g.drawString(Re,width-20,wysokoscOsi-20);
	    }
		
		public void drawY(Graphics g)
		{
			
			int pozycjaOsi = 0; 
			int height = window_height;;
			int width = window_width;
			//os w prawo daleko
			if(x_1 > 0 && x_2 > 0)
			{
				pozycjaOsi = (width - margin_LEFT_RIGHT);
			}
			//os w lewo daleko
			else if(x_1 < 0 && x_2 < 0)
			{
				pozycjaOsi = margin_LEFT_RIGHT;
			}
			//os na ekranie
			else
			{
				pozycjaOsi = (int) (Math.abs(x_1)/(Math.abs(x_1)+Math.abs(x_2))*(width-2*margin_LEFT_RIGHT)+margin_LEFT_RIGHT); //na koniec odbicie lustrzane
			}
	          //os Y(im)
            g.drawLine(pozycjaOsi, margin_UP_DOWN, pozycjaOsi, height-margin_UP_DOWN); 
            
				//z¹bki osi Y 
            for (int i = margin_UP_DOWN; i < height-margin_UP_DOWN; i += 60) 
            {
                g.drawLine(pozycjaOsi-5,height-i, pozycjaOsi+5,height-i);
                double wartY = y_1 + (i- margin_UP_DOWN)*skok_y ;
            	g.drawString(Format(wartY), pozycjaOsi+7,height-i);
            }
            
            //grot osi Y
            g.drawLine(pozycjaOsi-10, 10+margin_UP_DOWN, pozycjaOsi, margin_UP_DOWN);
            g.drawLine(pozycjaOsi, 0+margin_UP_DOWN, pozycjaOsi+10, 10+margin_UP_DOWN);   
            
            String Im = "Im";
            g.drawString(Im,pozycjaOsi-25,20);
		}
		
		@Override
		public void paint(Graphics g)
        {
            super.paint(g);
            g.clearRect(0, 0, this.getWidth(), this.getHeight());
        	window_width = this.getWidth();
    		window_height = this.getHeight();
            
    		skok_x = (x_2 - x_1) / (window_width - 2*margin_LEFT_RIGHT);
            skok_y = (y_2 - y_1) / (window_height - 2*margin_UP_DOWN);
            
            if(points != null)
            	drawColors(g);
            
            g.setColor(Color.BLACK);
            drawX(g);
            drawY(g);
        }
		
		public void drawColors(Graphics g)
		{
			
			if(arrayOfColors == null)
				return;
			if(arrayOfColors.length == 0)
				return;
			for(int i=0;i<points.length;i++) //x
			{
				for(int j=0;j<points[0].length;j++) //y
				{
					Color color = Color.WHITE;
					double minDist= Double.MAX_VALUE;
					for(int k= 0; k< arrayOfColors.length; k++)
					{
						ComplexNumbers curr = points[i][j];
						ComplexNumbers col = arrayOfColors[k];
						if(curr.getRe() == Double.MAX_VALUE && curr.getIm() == Double.MAX_VALUE)
						{
							color = Color.BLACK;
							break;
						}
						double dist = (curr.getRe()-col.getRe())*(curr.getRe()-col.getRe()) + (curr.getIm()-col.getIm())*(curr.getIm()-col.getIm()); 						
						if( dist < minDist)
						{
							color= colorTable[k];
							minDist= dist;
						}
					}
					g.setColor(color);
					if(color == Color.BLACK)
					{
						g.fillArc(i+margin_LEFT_RIGHT-9, this.getHeight() - (j+margin_UP_DOWN)-9, 10, 10, 0, 360);
					}
					else
						g.fillRect(i+margin_LEFT_RIGHT, this.getHeight() - (j+margin_UP_DOWN), 1, 1);
				}
			}
		}
  
		public void countBasins()
		{
			isComputing = true;
			int pointsX = (int)(Math.ceil((this.getWidth() - 2*margin_LEFT_RIGHT)/8.0)*8); //jesli piksel wystaje poza uklad to jest w tablicy
			int pointsY = (int)(Math.ceil((this.getHeight() - 2*margin_UP_DOWN)/8.0)*8);
						
			double skok_x = (x_2 - x_1) / (this.getWidth() - 2*margin_LEFT_RIGHT);
            double skok_y = (y_2 - y_1) / (this.getHeight() - 2*margin_UP_DOWN);

			points = new ComplexNumbers[pointsX][pointsY];
			
			for(int i = 0 ; i < this.getWidth()-2*margin_LEFT_RIGHT; i=i+8)
        	{
				for(int j= 0; j< this.getHeight()-2*margin_UP_DOWN; j=j+8)
        		{

					x = x_1 + i*skok_x; // dla i+marg_Y pixela
        			y = y_1 + j*skok_y; // dla j+marg_X pixela
        			
        			ComplexNumbers result = newton.NewtonCount(x,y,newton);
        			if(result.getRe() != Double.MAX_VALUE || result.getIm() != Double.MAX_VALUE)
        			{
        				ColorClassify(result);
        			}
        			
        			for(int n=0; n<8; n++)
        			{
        				for(int m=0; m<8; m++)
        				{
        					points[i+n][j+m] = result; //zapisuje wyniki do kwadratu 8x8
        				}
        			}

        		 }
        	}
			mapSorting();
			this.repaint();
			for(int i = 4; i>0 ; i=i/2)
			{
				countInnerBasin(i);
			}
			isComputing= false;
		}
		
		void countInnerBasin(int innerX)
		{
			double skok_x = (x_2 - x_1) / (this.getWidth() - 2*margin_LEFT_RIGHT);
            double skok_y = (y_2 - y_1) / (this.getHeight() - 2*margin_UP_DOWN);
            
			for(int i = 0 ; i < this.getWidth()-2*margin_LEFT_RIGHT; i=i+2*innerX)
        	{
				for(int j= 0; j< this.getHeight()-2*margin_UP_DOWN; j=j+2*innerX)
        		{
					x = x_1 + (i+innerX)*skok_x; 
        			y = y_1 + j*skok_y; 
        			
        			ComplexNumbers result = newton.NewtonCount(x,y,newton);

        			for(int n=0; n<innerX; n++)
        			{
        				for(int m=0; m<innerX; m++)
        				{
        					points[i+n+innerX][j+m] = result; 
        				}
        			}
        			
        			x = x_1 + i*skok_x; 
        			y = y_1 + (j+innerX)*skok_y; 
        			
        			result = newton.NewtonCount(x,y,newton);
       
        			for(int n=0; n<innerX; n++)
        			{
        				for(int m=0; m<innerX; m++)
        				{
        					points[i+n][j+m+innerX] = result; 
        				}
        			}
        			
        			x = x_1 + (i+innerX)*skok_x; 
        			y = y_1 + (j+innerX)*skok_y; 
        			
        			result = newton.NewtonCount(x,y,newton);

        			for(int n=0; n<innerX; n++)
        			{
        				for(int m=0; m<innerX; m++)
        				{
        					points[i+n+innerX][j+m+innerX] = result; 
        				}
        			}
        		}
			}
			this.repaint();
		}
	
		void ColorClassify(ComplexNumbers result)
		{
			
			boolean znaleziono = false;
			for(Entry<ComplexNumbers, Integer> entry : mapakrotnosci.entrySet())
			{
			   ComplexNumbers liczba = entry.getKey();
			   
			   int value = entry.getValue();
			   
			   if(Math.abs(liczba.getRe() - result.getRe()) < newton.epsilon && Math.abs(liczba.getIm() - result.getIm()) < newton.epsilon)
			   {
				   znaleziono = true;
				   mapakrotnosci.put(liczba,value+1);
				   break;
			   }
			   
			}
			if(!znaleziono)
			{
				mapakrotnosci.put(result, 1);
			}
			
		}
		
		public void resizeRefresh()
		{
			if(!isComputing)
				countBasins();
		}
		
		
		@SuppressWarnings({ "unchecked", "rawtypes" })
		void mapSorting()
		{
			Object[] a = mapakrotnosci.entrySet().toArray();
		    Arrays.sort(a, new Comparator() {
		        public int compare(Object o1, Object o2) {
		            return ((Map.Entry<ComplexNumbers, Integer>) o2).getValue().compareTo(
		                    ((Map.Entry<ComplexNumbers, Integer>) o1).getValue());
		        }
		    });
		    
		    int licznik = 0;
		    arrayOfColors = new ComplexNumbers[newton.maxkey];
		    for (Object e : a) {
		    	if(licznik >= newton.maxkey)
		    		break;
		    	ComplexNumbers cn = ((Map.Entry<ComplexNumbers, Integer>) e).getKey();
		        arrayOfColors[licznik]= cn;
		        licznik++;
		    }
		    
		}
}