import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

class Display extends JPanel
{
	int DisplayMemory [][] = new int[500][300];
	JLabel osRe,osIm;
	JFrame window;
	JPanel panel ;
    static JTextField wielomianF,grid_point1X,grid_point1Y,grid_point2X,grid_point2Y;
    JLabel point1,point2,point1X,point1Y,point2X,point2Y,grid_size;
    JButton grid_show;
    JCheckBox checkbox;
    Font font = new Font("Serif",Font.PLAIN,20);
    double X_1,Y_1,X_2,Y_2;
    
    public Display()
	{
		 panel = new JPanel();
		 panel.setSize(500,500);
		 panel.setLayout(null);
		 panel.setBackground(Color.white);
	     panel.setBorder(new LineBorder(Color.black, 5));
	     
	 
	     
	     grid_show = new JButton("Wyœwietl");
	     grid_show.setBounds(285,400,110,30);
	     grid_show.setFont(font);	
	     grid_show.addActionListener(new java.awt.event.ActionListener() 
	     {
	    	@Override
			public void actionPerformed(ActionEvent action_event) 
	    	{
	    		Object ButtonSource = action_event.getSource();
	    	
	    		String tekst;
	    	
	    		if(ButtonSource == grid_show)
    			{
	    			if((!wielomianF.getText().equals("") && !grid_point1X.getText().equals("") && !grid_point2Y.getText().equals("") && !grid_point2X.getText().equals("") && !grid_point2Y.getText().equals("")  ) || checkbox.isSelected())
    				{//obliczenia od wybranego przedzialu
    					tekst = wielomianF.getText();
    			
    					Newton newton = new Newton();
    					newton.map = parse(tekst);   //zAMIAST TEGO newton.setMap(parse(text))
    					
    					X_1 = -100;
    					Y_1 = -100;
    					X_2 = 100;
    					Y_2 = 100;
    					
    					if(!checkbox.isSelected()) //jak nie jest wcisniety poczatek ukladu
    					{
	    					X_1 = Double.valueOf(grid_point1X.getText());
	    					Y_1 = Double.valueOf(grid_point1Y.getText());
	    					X_2 = Double.valueOf(grid_point2X.getText());
	    					Y_2 = Double.valueOf(grid_point2Y.getText());
    					
	    		    		
    					}
    					
    					JFrame grid_frame = new JFrame();
    					Grid grid = new Grid(X_1,Y_1,X_2,Y_2,newton);
    		    		grid.setLayout(null);
    		            grid_frame.setMinimumSize(new Dimension(900, 600));
    		            grid_frame.setLocation(500,20);
    		    		grid_frame.add(grid);
    		    		grid_frame.setVisible(true);
    		
    				}
    				else
    				{
    					JOptionPane.showMessageDialog(window,"Proszê uzupe³niæ wszystkie pola","B³¹d danych",JOptionPane.ERROR_MESSAGE);
    				}
    			} 
	    		
	    		
	    	}
	     });
	     
	     grid_size = new JLabel("Dane rozpiêtoœci uk³adu");
	     grid_size.setFont(new Font("Serif",Font.PLAIN,25));
	     grid_size.setBounds(80,20,250,30);
	     
		 point1 = new JLabel("Punkt nr 1");
		 point1.setBounds(70,70,90,30);
	     point1.setFont(font);

		 point2 = new JLabel("Punkt nr 2");
		 point2.setBounds(250,70,90,30);
		 point2.setFont(font);
		 
		 point1X = new JLabel("Sk³adowa x: ");
		 point1X.setBounds(30,120,120,30);
	     point1X.setFont(font);

		 grid_point1X = new JTextField();
		 grid_point1X.setBounds(140,120,60,40);
	     grid_point1X.setFont(font);

	     point1Y = new JLabel("Sk³adowa y: ");
	     point1Y.setBounds(30,170,120,30);
	     point1Y.setFont(font);
		 
		 grid_point1Y = new JTextField();
		 grid_point1Y.setBounds(140,170,60,40);
		 grid_point1Y.setFont(font);
		
		 point2X = new JLabel("Sk³adowa x: ");
		 point2X.setBounds(220,120,120,30);
		 point2X.setFont(font);
	
		 grid_point2X = new JTextField();
		 grid_point2X.setBounds(330,120,60,40);
		 grid_point2X.setFont(font);
		 
		 point2Y = new JLabel("Sk³adowa y: "); 
		 point2Y.setBounds(220,170,120,30); 
		 point2Y.setFont(font);
		 
		 grid_point2Y = new JTextField();
		 grid_point2Y.setBounds(330,170,60,40);
		 grid_point2Y.setFont(font);
		 
		 wielomianF = new JTextField();
	     wielomianF.setBounds(30, 400, 250, 40);
	     wielomianF.setText("");
		 wielomianF.setFont(new Font("Serif",Font.PLAIN,20));
		 
		 checkbox = new JCheckBox("Pocz¹tek uk³adu wspó³rzêdnych");
		 checkbox.setBackground(Color.WHITE);
		 checkbox.setBounds(30, 250, 290, 60);
		 checkbox.setFont(font);
		 checkbox.setSelected(false);
		 checkbox.addItemListener(new ItemListener()
		 {
			@Override
			public void itemStateChanged(ItemEvent event) 
			{
				if(checkbox.isSelected())
    			{
					grid_point1X.setEnabled(false);
    				grid_point1Y.setEnabled(false);
    				grid_point2X.setEnabled(false);
    				grid_point2Y.setEnabled(false);
    			}
				else
				{
					grid_point1X.setEnabled(true);
    				grid_point1Y.setEnabled(true);
    				grid_point2X.setEnabled(true);
    				grid_point2Y.setEnabled(true);
				}
			}	
		 });

		
		 panel.add(checkbox);
		 panel.add(grid_size);
		 panel.add(grid_point1X);
		 panel.add(grid_point1Y);
		 panel.add(grid_point2X);
		 panel.add(grid_point2Y);
		 panel.add(point1X);
		 panel.add(point1Y);
		 panel.add(point2X);
		 panel.add(point2Y);
		 panel.add(point1);
		 panel.add(point2);
		 panel.add(wielomianF);
		 panel.add(grid_show);
	
	
	     window =  new JFrame();
		 window.setTitle("Baseny atrakcji w t³umionej metodzie Newtona");
	     window.pack();

         window.setMinimumSize(new Dimension(700, 800));
	     window.setLocationRelativeTo(null);

         window.setLayout(null);
         window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         window.add(panel);
         window.setVisible(true);
    }
	
	public static void main(String[] args)
    {
    	EventQueue.invokeLater(new Runnable() 
        {
            @Override
            public void run() 
            {
                new Display();
            }
        });
    }
	public void CheckDegree(int degree)
	{
		if(degree > 8 )
		{
			wielomianF.setText("Zbyt wysoki stopieñ wielomianu (max = 8)");
		}
		else if(degree < 1 )
		{
			wielomianF.setText("Zbyt ma³y stopieñ jednomianu (min = 1)");
		}
	}
	public HashMap<Integer, ComplexNumbers> parse(String s)
	 {
		HashMap<Integer, ComplexNumbers> mapa = new HashMap<Integer,ComplexNumbers>();
		String [] result;
		String str_stopien = "",str_liczba = " ";
		int stopien;
		double Re,Im = 0.0;
		
		try
	    {
	    	s = s.replaceAll("\\s+", ""); //usuwa wszystkie spacje i inne niewidoczne znaki specjalne
	    	
	    	s = s.replaceAll("\\-\\(","!-(");
			s = s.replaceAll("\\+\\(","!+(");
			
		    //dla stopnia = 1 i nie podanego
	        s = s.replaceAll("x\\+","x^1+");
	        s = s.replaceAll("x\\-","x^1-");
			
	        //dla stopnia = 1 na koncu lini 2x
	        s = s.replaceAll("x$","x^1");
	        
			//dla wspolczynnika = 1 rzeczywiste x^5
	        s = s.replaceAll("^x", "1x"); //na poczatku linii x
	        s = s.replaceAll("\\+x","!+1x");
	        s = s.replaceAll("\\-x","!-1x");
 
			for (int i = -1; (i = s.indexOf("+", i + 1)) != -1; ) 
			{
				int maxLen = s.length();
				if( (i > 0) && (i < maxLen-1) )
			    {
			    	if( (s.charAt(i-1) >= '0') && (s.charAt(i+1) >= '0') && (s.charAt(i-1) <= '9') && (s.charAt(i+1) <= '9') )
			    	{
			    		s = s.substring(0, i) + "!" + s.substring(i, maxLen);
			    	}
			    }
			}
			
			for (int i = -1; (i = s.indexOf("-", i + 1)) != -1; ) 
			{
				int maxLen = s.length();
				if( (i > 0) && (i < maxLen-1) )
			    {
			    	if( (s.charAt(i-1) >= '0') && (s.charAt(i+1) >= '0') && (s.charAt(i-1) <= '9') && (s.charAt(i+1) <= '9') )
			    	{
			    		s = s.substring(0, i) + "!" + s.substring(i, maxLen);
			    	}
			    }
			}
			
			result = s.split("!");
			int i = 0;
			System.out.println(s);
			
			if(result[0].equals(""))
			{ // np dla przypadku -x^4
				i++;
			}
			for(; i< result.length; i++)
			{
				System.out.println(result[i]);

				if(result[i].matches(".*(\\+|\\-)(\\d+|\\d+.\\d+)") || result[i].matches("(\\d+|\\d+.\\d+)"))
				{ //wyrazy wolne oddzielone	+4.5 -3.4			
					if(result[i].contains("+"))
					{
						Re = Double.valueOf(result[i].split("\\+")[1]);
					}
					else if(result[i].contains("-"))
					{
						Re = -Double.valueOf(result[i].split("\\-")[1]);
					}
					else
					{
						Re = Double.valueOf(result[i]);
					}
					mapa.put(0,new ComplexNumbers(Re,0));
				}
				else if(result[i].matches(".*(\\d+|\\d+.\\d+)\\w\\^\\d.*") )
				{ //wielomiany rzeczywiste 
					str_stopien = result[i].split("\\^")[1];
					stopien = Integer.valueOf(str_stopien);
					CheckDegree(stopien);
					if(result[i].contains("+"))
					{
						result[i] = result[i].substring(1,result[i].length());
						Re = Double.valueOf(result[i].split("\\w\\^")[0]);
					}
					else if(result[i].contains("-"))
					{
						result[i] = result[i].substring(1,result[i].length());
						Re = -Double.valueOf(result[i].split("\\w\\^")[0]);
					}
					else
					{
						Re = Double.valueOf(result[i].split("\\w\\^")[0]);
					}
					mapa.put(stopien,new ComplexNumbers(Re,0));
				}
				else 
				{ //wielomiany zespolone 
					str_stopien = result[i].split("\\)")[1];
					str_stopien = str_stopien.split("\\^")[1];
					stopien = Integer.valueOf(str_stopien);
					CheckDegree(stopien);
					str_liczba = result[i].split("\\)")[0];
					str_liczba = str_liczba.split("\\(")[1];
					if(result[i].startsWith("-("))
					{
						Re = Double.valueOf(str_liczba.split(",")[0]);
						Im = Double.valueOf(str_liczba.split(",")[1]);
						mapa.put(stopien,new ComplexNumbers(-Re,-Im));
					}
					else
					{
						Re = Double.valueOf(str_liczba.split(",")[0]);
						Im = Double.valueOf(str_liczba.split(",")[1]);
						mapa.put(stopien,new ComplexNumbers(Re,Im));
					}
				}
	    	}	    	
		}
		catch(Exception ex)
		{
			JOptionPane.showMessageDialog(window,"le wpisane dane","B³¹d danych",JOptionPane.ERROR_MESSAGE);
	    }
    
		return mapa;
	}

	
	 

}
