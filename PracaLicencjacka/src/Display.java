import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

class Display
{
	int DisplayMemory [][] = new int[500][300];
	JLabel osRe,osIm;
	JFrame window,helpwindow;
	JPanel panel ;
    static JTextField wielomianF,grid_point1X,grid_point1Y,grid_point2X,grid_point2Y,tlumienieF,epsilonF;
    JLabel point1,point2,point1X,point1Y,point2X,point2Y,grid_size,wielomianL,tlumienieL,epsilonL;
    JButton grid_show, help;
    JCheckBox checkbox;
    Font font = new Font("Serif",Font.PLAIN,20);
    double X_1,Y_1,X_2,Y_2;
    Newton newton;
    boolean errorState = false;
    boolean help_frame = false;
    
    public Display()
	{
    	 panel = new JPanel();
		 panel.setSize(480,500);
		 panel.setLayout(null);
		 panel.setBackground(Color.white);
	     panel.setBorder(new LineBorder(Color.black, 5));
	     
	     grid_show = new JButton("Generuj");
	     grid_show.setBounds(200,430,110,30);
	     grid_show.setFont(font);	
	     grid_show.addActionListener(new java.awt.event.ActionListener() 
	     {
	    	@Override
			public void actionPerformed(ActionEvent action_event) 
	    	{
	    		Object ButtonSource = action_event.getSource();
	    	
	    		String text;
	    	
	    		if(ButtonSource == grid_show)
    			{
	    			if((!wielomianF.getText().equals("") && !grid_point1X.getText().equals("") && !grid_point2Y.getText().equals("") && !grid_point2X.getText().equals("") && !grid_point2Y.getText().equals("")  ) || checkbox.isSelected())
    				{
    					text = wielomianF.getText();
    					
    					newton = new Newton();
    					newton.setMap(parse(text),newton);
    					
    					for(Entry<Integer, ComplexNumbers> entry : newton.map.entrySet()) 
    					{
    						
    						 int key = entry.getKey();
    						 ComplexNumbers value = entry.getValue();
    						System.out.println("klucz: "+key + "wartosci " + value);
    					}
    					
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
    					double tlumienie = Double.valueOf(tlumienieF.getText());
    					double epsilon = Double.valueOf(epsilonF.getText());
    				
    					if( tlumienie <= 1.0 && tlumienie > 0.0 && epsilon > 0 && (X_1 != Y_1 && X_2 != Y_2 && Y_1 != Y_2))
    					{
    						newton.tlumienie = tlumienie;
    						newton.epsilon = epsilon;
    						errorState = false;
    					}
    					else if(epsilon < 0)
    					{
    						
    						errorState = true;
        					JOptionPane.showMessageDialog(window,"Epsilon nie może być wartością ujemną","Błąd danych",JOptionPane.ERROR_MESSAGE);
        					
    					}
    					else if(tlumienie > 1 || tlumienie <= 0.0)
    					{
    						errorState = true;
        					JOptionPane.showMessageDialog(window,"Parametr tłumienia musi być liczbą z przedziału (0,1]","Błąd danych",JOptionPane.ERROR_MESSAGE);
    					}
    					else if(X_1 == Y_1 && X_2 == Y_2 && Y_1 == Y_2)
    					{
    						errorState = true;
        					JOptionPane.showMessageDialog(window,"Punkty muszą wyznaczać płaszczyznę","Błąd danych",JOptionPane.ERROR_MESSAGE);
    					}
    				
    					if(errorState == false)
    					{	
	    					JFrame grid_frame = new JFrame();
	    					grid_frame.setMinimumSize(new Dimension(900, 600));
	    		            grid_frame.setLocation(500,20);
	    					
	    		            
	    		            
	    					Grid grid = new Grid(X_1,Y_1,X_2,Y_2,newton);
	    		    		grid.setLayout(null);
	    		    		
	    		    		
	    		    		grid_frame.add(grid);
	    		    		grid_frame.setVisible(true);
	    		    		
	    		    		//grid.countBasins();
	    		    		grid_frame.repaint();
	    		    		
	    		    		
	    		    		grid_frame.addComponentListener(new ComponentAdapter()
	    		    		{
	    		    			
	    						@Override
	    						public void componentResized(ComponentEvent e) 
	    						{
	    							grid.resizeRefresh();
	    						};
	    					
							});
    					}
    				}
    				else
    				{
    					JOptionPane.showMessageDialog(window,"Proszę uzupełnić wszystkie pola","Błąd danych",JOptionPane.ERROR_MESSAGE);
    				}
    			} 
	    		
	    		
	    	}
	     });
	     
	     grid_size = new JLabel("Rozpiętość płaszczyzny zespolonej");
	     grid_size.setFont(new Font("Serif",Font.PLAIN,25));
	     grid_size.setBounds(60,20,360,30);
	     
		 point1 = new JLabel("Punkt nr 1");
		 point1.setBounds(70,70,90,30);
	     point1.setFont(font);

		 point2 = new JLabel("Punkt nr 2");
		 point2.setBounds(250,70,90,30);
		 point2.setFont(font);
		 
		 point1X = new JLabel("Re: ");
		 point1X.setBounds(60,120,120,30);
	     point1X.setFont(font);

		 grid_point1X = new JTextField();
		 grid_point1X.setBounds(110,120,60,40);
	     grid_point1X.setFont(font);

	     point1Y = new JLabel("Im: ");
	     point1Y.setBounds(60,170,120,30);
	     point1Y.setFont(font);
		 
		 grid_point1Y = new JTextField();
		 grid_point1Y.setBounds(110,170,60,40);
		 grid_point1Y.setFont(font);
		
		 point2X = new JLabel("Re: ");
		 point2X.setBounds(240,120,120,30);
		 point2X.setFont(font);
	
		 grid_point2X = new JTextField();
		 grid_point2X.setBounds(290,120,60,40);
		 grid_point2X.setFont(font);
		 
		 point2Y = new JLabel("Im: "); 
		 point2Y.setBounds(240,170,120,30); 
		 point2Y.setFont(font);
		 
		 grid_point2Y = new JTextField();
		 grid_point2Y.setBounds(290,170,60,40);
		 grid_point2Y.setFont(font);
		 
		 wielomianL = new JLabel("Wielomian: ");
		 wielomianL.setBounds(40, 355, 150, 20);
		 wielomianL.setFont(font);
		 
		 wielomianF = new JTextField();
	     wielomianF.setBounds(150, 350, 250, 40);
	     wielomianF.setText("");
		 wielomianF.setFont(new Font("Serif",Font.PLAIN,20));
		 
		 tlumienieF = new JTextField();
		 tlumienieF.setBounds(150, 290, 60, 40);
		 tlumienieF.setFont(font);
		 tlumienieF.setText("1.0");

		 
		 tlumienieL = new JLabel("α: ");
		 tlumienieL.setBounds(118,295,120,20);
		 tlumienieL.setFont(font);
		 
		 epsilonF = new JTextField();
		 epsilonF.setBounds(340, 290, 60, 40);
		 epsilonF.setFont(font);
		 epsilonF.setText("0.1");
		 
		 epsilonL = new JLabel("Dokładność: ");
		 epsilonL.setBounds(230,295,120,20);
		 epsilonL.setFont(font);
		 
		 checkbox = new JCheckBox("Początek układu współrzędnych");
		 checkbox.setBackground(Color.WHITE);
		 checkbox.setBounds(70, 220, 290, 60);
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
		 
		 Icon helpIcon = new ImageIcon(getClass().getResource("icon.png"));
		 help = new JButton(helpIcon);
	     help.setBounds(50,400,60,60);
	     help.addActionListener(new ActionListener() 
	     {
			
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				if(!help_frame)
				{
					helpwindow = new JFrame("Pomoc");
					helpwindow.setVisible(true);
					helpwindow.setLayout(null);
					helpwindow.setBounds(100,100,570,550);
					helpwindow.setResizable(false);
					JTextArea helpDesc = new JTextArea("Witaj! Ten program służy do obliczania basenów atrakcji przy pomocy iteracyjnej metody Newtona.\n\n"
						+ "Dostępne funkcjonalności: \n"
						+ "1.Rozpiętość płaszczyzny zespolonej \n"
						+ "Do wygenerowania wykresu potrzebne jest podanie dwóch punktów, które będą traktowane jako początek i koniec przekątnej okna wykresu. Jeden w lewym dolnym a drugi w prawym górnym rogu. Proszę pamiętać aby wprowadzane punkty wyznaczały płaszczyznę.\n"
						+ "2.Początek układu współrzędnych \n"
						+ "Zaznaczenie tej opcji jest alternatywą dla wpisywania współrzędnych dwóch punktów. Ustawia ona obszar wykresu na rozciągnięty pomiędzy punktem -100-100i a 100+100i.\n"
						+ "3.Parametr alfa \n"
						+ "Określa wielkość tłumienia w wersji tłumionej wzoru Newtona. Parametr musi być większy od zera lecz nie może być większy niż jeden. \n"
						+ "4.Parametr dokładność \n"
						+ "Jest to liczba określająca dokładność obliczniową jaka zostanie narzucona dla każdego punktu płaszczyzny. Wyznacza dokładność jednocześnie części rzeczywistej i urojonej miejsca zerowego. Im mniejsza wartość tego parametru tym dokładniejsze obliczenia. Nie może być liczbą ujemną. Uwaga! Nawet drobna zmiana tego parametru może znacząco wydłużyć czas oczekiwania na wykres.\n"
						+ "5.Wielomian \n"
						+ "Obsługiwane są tylko wielomiany, których potęgi nie są mniejsze niż 0 i większe niż 8 oraz spełniające konwencje zapisu."
						+ "Konwencja dla wielomianów rzeczywistych: np 1.2x^2+x+4.5 oznacza wielomian stopnia drugiego o współczynniku 1.2 przy najwyższej potędze wraz z jednomianem stopnia pierwszego o współczynniku 1 i wyrazie wolnym równym 4.5."
						+ "Dla wielomianów zespolonych: np (3,4)x^3+(0,-2)x^2-4.6 oznacza wielomian stopnia trzeciego o współczynniku 3+4i przy najwyższej potędze wraz z jednomianem stopnia drugiego o współczynniku -2i i wyrazie wolnym równym -4.6"
						+ "Zapis dla liczb zespolonych (x,y) oznacza że x jest częścią rzeczywistą a y częścią urojoną.");
					helpDesc.setBounds(10,10,400,410);
					helpDesc.setLineWrap(true);
					helpDesc.setWrapStyleWord(true);
					helpDesc.setBorder(BorderFactory.createLineBorder(Color.BLACK));
					helpDesc.setEditable(false);
					helpDesc.setFont(font);
					JScrollPane scroll = new JScrollPane(helpDesc);
					scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
					scroll.setBounds(15,20,520,460);
					helpwindow.add(scroll);
					help_frame = true;
				}
				else
				{
					helpwindow.setVisible(true);
				}
			}
		});
	     
		 panel.add(help);
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
		 panel.add(wielomianL);
		 panel.add(tlumienieF);
		 panel.add(tlumienieL);
		 panel.add(epsilonF);
		 panel.add(epsilonL);
		 panel.add(grid_show);
	
	
	     window =  new JFrame();
		 window.setTitle("Baseny atrakcji w tłumionej metodzie Newtona");
		 window.setResizable(false);
	     window.pack();

         window.setMinimumSize(new Dimension(485, 535));
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
			errorState = true;
			JOptionPane.showMessageDialog(window,"Zbyt wysoki stopień wielomianu (max = 8)","Błąd danych",JOptionPane.ERROR_MESSAGE);
		}
		else if(degree < 1 )
		{
			errorState = true;
			JOptionPane.showMessageDialog(window,"Zbyt mały stopień wielomianu (min = 1)","Błąd danych",JOptionPane.ERROR_MESSAGE);
		}
		else
		{
			errorState = false;
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

				if(result[i].matches(".*(\\+|\\-)(\\d+|\\d+.\\d+)") || result[i].matches("(\\d+|\\d+.\\d+)") || result[i].matches(".*(\\+|\\-)(.*\\d+,.*\\d+\\))"))
				{ //wyrazy wolne oddzielone	+4.5 -3.4		
					//System.out.println("hej");
					
					if(result[i].contains("+("))
					{
						System.out.println("+");
						result[i] = result[i].split("\\+\\(")[1];
						System.out.println(result[i]);
						Re = Double.valueOf(result[i].split("\\,")[0]);
						result[i] = result[i].split("\\)")[0];
						Im = Double.valueOf(result[i].split("\\,")[1]);
						mapa.put(0,new ComplexNumbers(Re,Im));
					}
					else if(result[i].contains("-("))
					{
						result[i] = result[i].split("\\-\\(")[1];
						Re = -Double.valueOf(result[i].split(",")[0]);
						result[i] = result[i].split("\\)")[0];
						Im = -Double.valueOf(result[i].split(",")[1]);
						mapa.put(0,new ComplexNumbers(Re,Im));
					}
					else if(result[i].contains("+"))
					{
						Re = Double.valueOf(result[i].split("\\+")[1]);
						mapa.put(0,new ComplexNumbers(Re,0));
					}
					else if(result[i].contains("-"))
					{
						Re = -Double.valueOf(result[i].split("\\-")[1]);
						mapa.put(0,new ComplexNumbers(Re,0));
					}
					else
					{
						Re = Double.valueOf(result[i]);
						mapa.put(0,new ComplexNumbers(Re,0));
					}
					
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
			JOptionPane.showMessageDialog(window,"Źle wpisane dane","Błąd danych",JOptionPane.ERROR_MESSAGE);
	    }
    
		return mapa;
	}
}
