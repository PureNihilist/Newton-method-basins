public class ComplexNumbers
{
		private double re;   // czêœæ rzeczywista
	    private double im;   // czêœæ urojona
	 
	    public ComplexNumbers(double real, double imag) 
	    {
	        re = real;
	        im = imag;
	    }
	 
	    // metody dostêpowe do czêœci urojonej/rzeczywistej
	    public double getRe() 
	    {
	    	return re; 
	    }
	    
	    public double getIm() 
	    { 
	    	return im;
	    }
	    
	    public void setRe(double x)
	    {
	    	ComplexNumbers a = this;
	    	a.re = x;
	    }
	    
	    public void setIm(double x)
	    {
	    	ComplexNumbers a = this;
	    	a.im = x;
	    }
	    
	    // zwraca reprezentacje stringow¹ liczby zespolonej
	    public String toString() 
	    {
	        if (im == 0) return re + "";
	        if (re == 0) return im + "i";
	        if (im <  0) return re + " - " + (-im) + "i";
	        return re + " + " + im + "i";
	    }
	 
	    //dodawanie 
	    public ComplexNumbers plus(ComplexNumbers b) 
	    {
	        ComplexNumbers a = this;             
	        double real = a.re + b.re;
	        double imag = a.im + b.im;
	        return new ComplexNumbers(real, imag);
	    }
	 
	    //odejmowanie 
	    public ComplexNumbers minus(ComplexNumbers b)
	    {
	        ComplexNumbers a = this;
	        double real = a.re - b.re;
	        double imag = a.im - b.im;
	        return new ComplexNumbers(real, imag);
	    }
	 
	    //mno¿enie 
	    public ComplexNumbers times(ComplexNumbers b) 
	    {
	        ComplexNumbers a = this;
	        double real = a.re * b.re - a.im * b.im;
	        double imag = a.re * b.im + a.im * b.re;
	        return new ComplexNumbers(real, imag);
	    }
	 
	    
	    // mno¿enie przez skalar
	    public ComplexNumbers times(double alpha) 
	    {
	        return new ComplexNumbers(alpha * re, alpha * im);
	    }
	    
	    //zwraca odwrotn¹ liczbe zespolon¹
	    public ComplexNumbers reverse() 
	    {
	        double scale = re*re + im*im;
	        return new ComplexNumbers(re / scale, -im / scale);
	    }
	 
	    //dzielenie
	    public ComplexNumbers divides(ComplexNumbers b)
	    {
	        ComplexNumbers a = this;
	        return a.times(b.reverse());
	    }
	    
	    //potegowanie
	    public ComplexNumbers power(int power)
	    {
	    	ComplexNumbers c = this;
			if (power == 0)
			{
				return(new ComplexNumbers(1.0, 0.0));
			} 
			else if (power > 0) 
			{
				for (int i = 1; i < power; ++i) 
				{
					c = c.times(this);
				}
				return(c);
			} 
			else 
			{
				c = c.power(-power);
				return((new ComplexNumbers(1.0,0.0)).divides(c));
			}		
		}
}