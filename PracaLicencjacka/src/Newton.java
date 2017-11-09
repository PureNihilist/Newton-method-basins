import java.util.HashMap;
import java.util.Map.Entry;

public class Newton 
{
	HashMap<Integer,ComplexNumbers> map = new HashMap<>();
	HashMap<Integer,ComplexNumbers> derivmap = new HashMap<>();
	
	double tlumienie = 1.0;
	double epsilon = 0.1;
	int maxkey = 0;

	public void setMap(HashMap<Integer,ComplexNumbers> mapa, Newton newton) //ustawia mape parsowania na mape z tej klasy
	{
		newton.map.putAll(mapa);
	}
	
	public void deriv(Newton newton) //oblicza pochodna i wstawia do mapy z pochodnymi
	{
		for(Entry<Integer, ComplexNumbers> entry : newton.map.entrySet()) 
		{
		    int key = entry.getKey();
		    ComplexNumbers value = entry.getValue();
		  
		    if(key > 0)
			{
		        double Re = value.getRe()*key;
		    	double Im = value.getIm()*key;
		    	newton.derivmap.put(key-1,new ComplexNumbers(Re,Im));
			}
		}
	}
	
	public ComplexNumbers Count(double x, double y, Newton newton) //f(x)/(f(x))'
	{
		ComplexNumbers result_wsp = new ComplexNumbers(0,0);
		ComplexNumbers result_der = new ComplexNumbers(0,0);
		ComplexNumbers wsp = new ComplexNumbers(0,0);
		ComplexNumbers function_x = new ComplexNumbers(x,y);//punkt na ktorym wykonuje obliczenia
		for(Entry<Integer, ComplexNumbers> entry : newton.map.entrySet()) 
		{
			
			 int key = entry.getKey();
			 ComplexNumbers value = entry.getValue();
			 if(key > newton.maxkey)
			 {
				 newton.maxkey = key;
			 } 
			 wsp = value.times(function_x.power(key)); // podnosze do potegi i mnoze razy wspolczynnik
			 result_wsp = result_wsp.plus(wsp); //dodaje wszystkie skladniki i otrzymuje f(x)
		}
		deriv(newton);
		for(Entry<Integer, ComplexNumbers> entry : newton.derivmap.entrySet()) 
		{
			int key = entry.getKey();
			ComplexNumbers value = entry.getValue();
			if(key > 0)
			{
				wsp = value.times(function_x.power(key));
			}
			result_der = result_der.plus(wsp);
		}
		ComplexNumbers result = result_wsp.divides(result_der);
		return result;
	}
	
	public ComplexNumbers NewtonCount(double x,double y, Newton newton)
	{

		ComplexNumbers start = new ComplexNumbers(x,y);
		ComplexNumbers result = new ComplexNumbers(0,0);
		ComplexNumbers root = new ComplexNumbers(0,0);
		ComplexNumbers resultbefore = new ComplexNumbers(Double.MAX_VALUE,Double.MAX_VALUE);
		int dwucykl = 0;
		
		result = start.minus( Count(x,y,newton).times(newton.tlumienie)  ); // x1
		for(int i = 0 ; i < 100 ;i++)
		{
			double result_x = result.getRe();
			double result_y = result.getIm();

			root = result.minus( Count(result_x,result_y,newton).times(newton.tlumienie) );
			
			if(Math.abs(root.getIm()-result.getIm())< newton.epsilon && Math.abs(root.getRe()-result.getRe())< newton.epsilon )
			{
				result = root;
				break;
			}
			//wykrywanie dwucyklu
			if(Math.abs(resultbefore.getRe() - root.getRe()) < newton.epsilon && Math.abs(resultbefore.getIm() - root.getIm()) < newton.epsilon)
			{
				dwucykl++;
				ComplexNumbers cn = new ComplexNumbers(Double.MAX_VALUE,Double.MAX_VALUE);

	
				if(dwucykl == 30)
				{
					return cn;
				}
		

			}
			
			resultbefore = result;
			result = root;

		}

		return result;
	}
	
	
}
