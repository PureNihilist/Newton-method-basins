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
	 
	    // zwraca modu³/norme liczby zespolonej
	    public double abs() 
	    {
	    	return Math.hypot(re, im);
	    }  
	    
	    //zwraca argument/faze liczby zespolonej od -pi do pi
	    public double phase()
	    { 
	    	return Math.atan2(im, re);
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
	 
	    //sprzê¿enie 
	    public ComplexNumbers conjugate() 
	    { 
	    	return new ComplexNumbers(re, -im); 
	    }
	 
	    //zwraca odwrotn¹ liczbe zespolon¹
	    public ComplexNumbers reciprocal() 
	    {
	        double scale = re*re + im*im;
	        return new ComplexNumbers(re / scale, -im / scale);
	    }
	 
	    //dzielenie
	    public ComplexNumbers divides(ComplexNumbers b)
	    {
	        ComplexNumbers a = this;
	        return a.times(b.reciprocal());
	    }
}