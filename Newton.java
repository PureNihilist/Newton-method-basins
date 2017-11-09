import java.util.HashMap;
import java.util.Map.Entry;

public class Newton 
{
	public HashMap<Integer,ComplexNumbers> map = new HashMap<>();
	//dodaniue drugiej mapy na pochodna
	
	/*function set map
	 * 
	 * zapisanie mapy
	 * 
	 * do drugiej mapy policzenie pochodnej wielomianu
	 * 
	 */
	
	public double NewtonCount(double x,double y)
	{
		Double tlumienie = 1.0; // (0-1]
		
		/*for(Entry<Integer, ComplexNumbers> entry : map.entrySet()) 
		{
		    int key = entry.getKey();
		    ComplexNumbers value = entry.getValue();
		   // System.out.println("Stopieñ: " + key +" Re: " + value.getRe() + " Im: "+ value.getIm());
		    
		    if(key > 0)
			{
		    	double wsp = deriv(key,value);
				key = key-1;
				
				for(int i =0 ; i < 100 ;i++) //dla tych x
				{
				 //Math.pow(wsp*i,key);
				}
				
			}
		}*/
		
		/*ogolnego fora odpowiadajacego za ilosc iteracji newtona
		 * {
		 * 		podstawienie do wsoru
		 * 		for po mapie ze wspolczynnikami wielomianu
		 * 		{
		 * 			podstawieni do wzoru i policzenie sumy kolejnych sk³adnik ów
		 * 
		 * 		}
		 * 
		 * 		for po mapie z pochodna
		 * 		{
		 * 			tak samo jak wyuzej
		 * 		}
		 * 
		 * 		podzielenie f(x) / f'(x)
		 * }
		 * 
		 * 
		 */
		
		return 0;
	}
	
	public static ComplexNumbers deriv(double stopien, ComplexNumbers liczba)
	{//dostaje dana liczbe z mapy oblicza jej nowy wspolczynnik
		double wynik;
		wynik = stopien*liczba.getRe();
		liczba.setRe(wynik);
		if(liczba.getIm() != 0)
		{
			wynik = stopien*liczba.getIm();
			liczba.setIm(wynik);
		}
		return liczba;
	}
}
