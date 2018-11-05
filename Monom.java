package myMath;
/**
 * This class represents a simple "Monom" of shape a*x^b, where a is a real number and a is an integer (summed a none negative), 
 * see: https://en.wikipedia.org/wiki/Monomial 
 * The class implements function and support simple operations as: construction, value at x, derivative, add and multiply. 
 * @author  Ido & Amir

 *
 */
public class Monom implements function{
	
	double _coefficient; 
	int _power;

	/**
	 * Default constructor.
	 * setting the coefficient and power to zero.
	 */
	public Monom(){
		this.set_coefficient(0);
		this.set_power(0);
	}
	
	/**
	 * This constructor a Monom with specified power and coefficient.
	 * power must be a positive integer.
	 * @param a represent the Monom's coefficient
	 * @param b represent the Monom's power
	 */
	public Monom(double a, int b){
		if(b<0)
		{
			throw new RuntimeException("error cannot init negativ power in monom"+get_power());
		}
		this.set_coefficient(a);
		this.set_power(b);
	}
	/**
	 * Copy constructor.
	 * @param ot - other monom we want to copy from.
	 */
	public Monom(Monom ot) {
		this(ot.get_coefficient(), ot.get_power()); //this is a call to the constructor.
	}
 
	
	
	/**
	 * This constractor cover the function init_from_string, it construct an monom from String input to monom
	 * @param s represent the String that will be a monom
	 */
	public Monom(String s) {
		Monom th = init_from_string(s);
		this.set_coefficient(th.get_coefficient());
		this.set_power(th.get_power());
	}
	/**
	 * This function get the String from the constractor and build monom with it
	 * @param s represent the input String 
	 * @return return a monom acording to the String input
	 */
	private static Monom init_from_string(String s) {
		if(s.startsWith("+")) s = s.replaceFirst("\\+", "");
		if(s == null) {
			throw new RuntimeException("String is Null.");
			}
		s = s.replaceAll("\\s",""); //to avoid spaces

		double  coef = 1;
		int pow = 0;
		Monom ans = new Monom();
		
		if(s.contains("x")) {
			int ind = s.indexOf("x");
			String co = s.substring(0, ind);
			if(co.equals("-")) co = "-1";
			try{
				double c = Double.parseDouble(co);
				coef = c;
			}
			catch(Exception e) {coef = 1;}
			
			if(s.contains("^")){
				int powerIndex = s.indexOf("^");
				String po = s.substring(powerIndex +1);
				try{
					int p = Integer.parseInt(po);
					pow = p;
				}
				catch(Exception e) {pow = 1;}
			}else
				pow = 1;
		}else
			coef = Double.parseDouble(s); //just a  number.
		ans = new Monom(coef, pow);	
		return ans;
	}
	/**
	 * this function is return the power of the monom
	 * @return return the power of the monom
	 */
	public int get_power() {
		return _power;
	}
	/**
	 * this function is return the coefficient of the monom
	 * @return return the coefficient of the monom
	 */
	public double get_coefficient() {
		return _coefficient;
	}
	/**
	 * this function is set a coefficient to the monom
	 * @param a represent the new coefficient
	 */
	private void set_coefficient(double a){
		this._coefficient = a;
	}
	/**
	 * this function is set a power to the monom
	 * @param a represent the new power
	 */
	private void set_power(int p) {
		this._power = p;
	}
 /**
  * this function get value for the monom 'x', and return the 'x' value
  * @param x the x value for the monom
  * @return return the 'y' value of the monom
  */
	public double f(double x) {
		return get_coefficient() * (Math.pow(x, get_power()));
	}
	/**
	 * this function calculates the derivative for x.
	 * @param x represent the point for the derivative
	 * @return value of derivative.
	 */
	public double derivative(double x){
		return get_coefficient()*get_power() * (Math.pow(x, get_power() - 1));
	}
	
	/**
	 * this function make sum add 2 monom (the sum of the 2 monoms) if they have diferent power it send exeption
	 * @param f2 represent the new monom that the function will add to the monom
	 */
	public void add(Monom f2){		
		if(f2.get_power() == this._power){
			this._coefficient += f2.get_coefficient();
		}
		else 
			throw new IllegalArgumentException("illegal move");
	}
	
	/**
	 * Substract f2 from this Monom.
	 * @param f2.
	 */
	public void substract(Monom f2){		
		if(f2.get_power() == this._power){
			this._coefficient -= f2.get_coefficient();
		}
		else 
			throw new IllegalArgumentException("illegal move");
	}
	/**
	 * Multiply this Monom by f2(Monom).
	 * @param f2 - input monom
	 */
	public void multiply(Monom f2){
		this._coefficient *= f2.get_coefficient();
		this._power += f2.get_power();
	}
	/**
	 * multiply this Monom by m0.
	 * @param m0 - input monom
	 * @return Monom.
	 */
	public Monom multiplyMonom(Monom m0){ //take m0 and return a new monom multiplied by this.
		Monom m1 = new Monom(this);
		m1.multiply(m0);
		return m1;
	}
	
	/**
	 * print this Monom as a*x^b
	 */
	public String toString() {
		return get_coefficient()+"x^"+get_power();
	}
	/**
	 * test if this Monom is logically equal to m.
	 * @param m - input monom
	 * @return boolean
	 */
	public boolean isEqual(Monom m) {
		if(this._coefficient==m._coefficient && this._power==m._power)
			return true;
		else
			return false;
	}
	
	public Monom multiplyHelper(Monom m) {
		Monom ans = new Monom(this.get_coefficient(), this.get_power());
		ans.set_coefficient(this.get_coefficient() * m.get_coefficient());
		ans.set_power(this.get_power() + m.get_power());
		return ans;
	}
}