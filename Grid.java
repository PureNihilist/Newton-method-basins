import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.swing.JPanel;

	class Grid extends JPanel
	{
		static int szerokoscKratki= 5;
		
		double x_1 = 0,x_2= 0,y_1 = 0,y_2 = 0;
		double skok_x,skok_y;
		double x,y;
		int margines_osiX=20;
		int margines_osiY=40;
		Newton newton;
		

		Grid(double X_1, double Y_1, double X_2, double Y_2, Newton newton)
		{
			x_1 = X_1; 	
			y_1 = Y_1; 
			x_2 = X_2; 	
			y_2 = Y_2;
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
			int height = (int) this.getHeight();
			int width = (int) this.getWidth();
			//os na dole daleko
			if(y_1 > 0 && y_2 > 0)
			{
				wysokoscOsi = (int) (height - margines_osiX);
			}
			//os na gorze daleko
			else if(y_1 < 0 && y_2 < 0)
			{
				wysokoscOsi = margines_osiX;
			}
			//os na ekranie
			else
			{
				wysokoscOsi = (int) (height - (Math.abs(y_1)/(Math.abs(y_1)+Math.abs(y_2))*(height-2*margines_osiX)+margines_osiX)); //na koniec odbicie lustrzane
			}
	          //os X(re)
            g.drawLine(margines_osiY, wysokoscOsi, width-margines_osiY, wysokoscOsi); 
            
				//z¹bki osi X 
            for (int i = margines_osiY; i < width - margines_osiY; i += 60) 
            {
                g.drawLine(i, wysokoscOsi-5, i, wysokoscOsi+5);
                double wartX = x_1 + (i - margines_osiY)*skok_x;
            	g.drawString(Format(wartX), i+2,wysokoscOsi-5);
            }
            
            //grot osi X
            g.drawLine(width-margines_osiY, wysokoscOsi, width-10-margines_osiY, wysokoscOsi+10);
            g.drawLine(width-margines_osiY, wysokoscOsi, width-10-margines_osiY, wysokoscOsi-10);   
            //podpisy osi
            String Re = "Re";
            g.drawString(Re,width-20,wysokoscOsi-20);
     
            
		}
		
		public void drawY(Graphics g)
		{
			
			int pozycjaOsi = 0; 
			int height = (int) this.getHeight();
			int width = (int) this.getWidth();
			//os w prawo daleko
			if(x_1 > 0 && x_2 > 0)
			{
				pozycjaOsi = (int) (width - margines_osiY);
			}
			//os w lewo daleko
			else if(x_1 < 0 && x_2 < 0)
			{
				pozycjaOsi = margines_osiY;
			}
			//os na ekranie
			else
			{
				pozycjaOsi = (int) (Math.abs(x_1)/(Math.abs(x_1)+Math.abs(x_2))*(width-2*margines_osiY)+margines_osiY); //na koniec odbicie lustrzane
			}
	          //os Y(im)
            g.drawLine(pozycjaOsi, margines_osiX, pozycjaOsi, height-margines_osiX); 
            
				//z¹bki osi Y 
            for (int i = margines_osiX; i < height-margines_osiY; i += 60) 
            {
                g.drawLine(pozycjaOsi-5,height-i, pozycjaOsi+5,height-i);
                double wartY = y_1 + (i-margines_osiX)*skok_y;
            	g.drawString(Format(wartY), pozycjaOsi+7,height-i);
            }
            
            //grot osi Y
            g.drawLine(pozycjaOsi-10, 10+margines_osiX, pozycjaOsi, margines_osiX);
            g.drawLine(pozycjaOsi, 0+margines_osiX, pozycjaOsi+10, 10+margines_osiX);   
            
            String Im = "Im";
            g.drawString(Im,pozycjaOsi-25,20);
		}
		
		@Override
		public void paint(Graphics g)
        {
            super.paint(g);
            g.clearRect(0, 0, this.getWidth(), this.getHeight());
            
         //   Toolkit tk = Toolkit.getDefaultToolkit();
          //  Dimension d = tk.getScreenSize();
         //   System.out.println(x_1+ " "+ x_2+ " " + y_1+ " " + y_2);
            
            
           
            	skok_x = (x_2 - x_1) / this.getWidth();
	            skok_y = (y_2 - y_1) / this.getHeight();
	    	
	            drawX(g);
	            drawY(g);
	            
	            for(int i = 0 ; i < this.getWidth()-2*margines_osiY; i++)
	            {
	            	for(int j= 0; j< this.getHeight()-2*margines_osiX; j ++)
	            	{
	            		x = x_1 + i*skok_x; // dla i+marg_Y pixela
	            		y = y_1 + j*skok_y; // dla j+marg_X pixela
	            		newton.NewtonCount(x,y);
	            		//na 8 watkow wedlug stalej
	            	}
	         
	            }

            
        }
	
	}