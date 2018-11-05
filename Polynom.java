package myMath;

import java.util.*;
import java.util.function.Predicate;

import myMath.Monom;
/**
 * This class represents a Polynom with add, multiply functionality, it also should support the following:
 * 1. Riemann's Integral: https://en.wikipedia.org/wiki/Riemann_integral
 * 2. Finding a numerical value between two values (currently support root only f(x)=0).
 * 3. Derivative
 * 
 * @author Boaz
 *
 */
public class Polynom implements Polynom_able{

	// ********** add your code below ***********
	ArrayList<Monom> list;	
	private static final Monom_Comperator copmerator = new Monom_Comperator();
	
	/**
	 * Empty constructor.
	 * this constructor represent zero polynom.
	 */
	public Polynom(){ //this is zero polynom
		
	        list = new ArrayList<Monom>();
	}
	/**
	 * intialize a Polynom from a string.
	 * @param str - input string.
	 */
	public Polynom(String str){	
		list = new ArrayList<Monom>();
		if(str.startsWith("+")) str = str.replaceFirst("\\+", "");
		
		String[] arr = str.split("[+]");
		
		boolean flag = false;
		
		for(int i = 0; i < arr.length; i++){
			String[] arr2 = arr[i].split("-");
			for(int j = 0; j < arr2.length; j++){
			if(arr2[j].equals("")){//this means the first Monom is negative. so we flag it for later.
				flag = true;
			}
			else{
				if(arr2.length == 1){ //if the array is sized 1 than the Monom is surely positive.
					list.add(new Monom(arr2[j]));
					break;
				}
			
				if(flag){ //case 1: first slot is empty -> 2nd slot is a negative monom
					list.add(new Monom("-" + arr2[j]));
				
					flag = false;
					}	else if(j == 0 && !flag){ //case 2: first slot is not empty -> 1st slot is a positive monom
							list.add(new Monom(arr2[j]));
					
					}else //after spliting by "-" the rest are negative.
						list.add(new Monom("-" + arr2[j]));	
					
				}	
			}
				
		} //sort the list so the polynom is printed correctly.
		list = sortAndMerge(list);
	}
	/**
	 * copy constructor
	 * @param p2 - input polynom
	 */
	public Polynom(Polynom p2){
		this();
		Iterator<Monom> iter = p2.iteretor();
		while(iter.hasNext()) {
			Monom m = iter.next();
			this.add(new Monom(m));
		}
	}
	/**
	 * toString function to print a Polynom
	 * in descecnding order
	 * @return string of this polynom
	 */
	public String toString() {
		String ans = "";
		if(this.isZero()) {
			return "0";
		}
		else{
			Iterator<Monom> iter = this.iteretor();
			while(iter.hasNext()){
				Monom m = iter.next();
					ans += m + " +";
			}
				ans.replaceAll("\\+-", "-");
			return ans.substring(0, ans.length() -2);
		}
	}
	
	/**
	 * computes this Polynom value at x
	 * @return value of P(x)
	 */
	@Override
	public double f(double x){
		double ans = 0;
		Iterator<Monom> iter = this.iteretor();
		while(iter.hasNext()) {
			Monom m = iter.next();
			ans += m.f(x);
		}
		return ans;
	}
	/**
	 * Add p1 to this Polynom
	 * @param p1 - input polynom
	 */
	@Override
	public void add(Polynom_able p1){
		Iterator<Monom> iterP1 = p1.iteretor();
		while(iterP1.hasNext()){
			Monom takeMon = iterP1.next();
			this.add(takeMon);
		}
		list = sortAndMerge(list);
	}
	/**
	 * Add m1 to this Polynom
	 * @param m1 Monom
	 */
	@Override
	public void add(Monom m1) {
		boolean is_there=false;
		Monom help = null;
		Iterator<Monom> mymonom = this.iteretor();
		
		while(mymonom.hasNext()){
			
			help = mymonom.next();
			if(help.get_power()==m1.get_power()){
				help.add(m1);
				is_there = true;
				break;
			}
		}
		if(!is_there){
			list.add(m1);
			list = sortAndMerge(list);
		}
	}
	/**
	 * Subtract p1 from this Polynom
	 * @param p1 - input polynom
	 */
	@Override
	public void substract(Polynom_able p1){
		Iterator<Monom> iterP1=p1.iteretor();
		while(iterP1.hasNext()){
			Monom takeMon = iterP1.next();
			this.substract(takeMon);
		}
	}
	/**
	 * substract monom m1 from this polynom
	 * @param m1 - input monom
	 */
	public void substract(Monom m1) {
		
		boolean is_there=false;
		Monom help;
		Monom temp = new Monom(-1 * m1.get_coefficient() , m1.get_power());
		Iterator<Monom> mymonom = this.iteretor();
		while(mymonom.hasNext()){
			help = mymonom.next();
			if(help.get_power() == temp.get_power()){
				help.substract(m1);
				is_there = true;
				break;
			}
		}
		
		
		if(!is_there){
			list.add(temp);
			list = sortAndMerge(list);
		}
	}
	/**
	 * a cover method to multiply a polynom by a second polynom.
	 * uses multiply(p0,p1) 
	 */
	@Override
	public void multiply(Polynom_able p1) { //find a way to add 2 lists together
		Polynom p2 = Polynom.multiply(this, p1);
		this.list = p2.list;
	}
	/**
	 * multiply polynom by monom m1
	 * @param m1 - input monom
	 * @return a polynom that is multiplied by a monom
	 */
	public Polynom multiply(Monom m1) {
		Polynom p0 = new Polynom();
		if(m1.get_coefficient()==0) {
			this.list.clear();
		}
		else {
			Monom m0 = new Monom(m1);
			Iterator<Monom> iter = this.iteretor();
			while(iter.hasNext()) {
				Monom m = iter.next();
				m0 = m.multiplyMonom(m1);
				p0.add(m0);
			}
		}
		return p0;
	}
	/**
	 * multiply polynom p0 with polynom p1;
	 * @param p0 - input polynom
	 * @param p1 - input polynom
	 * @return polynom that is the multiplication of p0 by p1
	 */
	public static Polynom multiply(Polynom p0, Polynom_able p1) {
		Polynom ans = new Polynom();
		Polynom res = new Polynom();
		Polynom p2 = (Polynom) p1.copy();
		
		if(!p0.isZero() && !p1.isZero()) {
				for (Monom monom : p0.list) {
				res = p2.multiply(monom);
				ans.add(res);
			}
		}
		return ans;
	}
	
	/*
	 * sortAndMerge: utility function that merges similar Monoms 
	 * in the list and then sort it in descending order.
	 * @return list<Monom>;
	 */
	private ArrayList<Monom> sortAndMerge(ArrayList<Monom> list){	
		ArrayList<Monom> stam = new ArrayList<Monom>();
		
		for (int i = 0; i < list.size(); i++) {
			Monom temp = list.get(i);
			for (Iterator<Monom> iterator = stam.iterator(); iterator.hasNext();) {
				Monom monom = (Monom) iterator.next();
				if(monom.get_power() == temp.get_power()){
					temp.add(monom);
					iterator.remove();
				}
			}
			if(temp.get_coefficient() != 0.0)
			stam.add(temp);
		}
		
		stam.sort(copmerator);
		return stam;
	}
	
	/**
	 * Test if this Polynom is logically equals to p1.
	 * @param p1 - input polynom
	 * @return boolean
	 */
	@Override
	public boolean equals(Polynom_able p1) {
		boolean answer = false;
		
		Iterator<Monom> thisIter = this.iteretor();
		Iterator<Monom> p1Iter = p1.iteretor();
		
		while(thisIter.hasNext() && p1Iter.hasNext()){
			answer = true;
			Monom m0 = thisIter.next();
			Monom m1 = p1Iter.next();
			if(!m0.isEqual(m1)){
				answer = false;
				break;
			}
		}
		return answer;
	}
	
	/**
	 * Test if this is the Zero Polynom
	 * @return true if this is zero polynom
	 */
	@Override
	public boolean isZero() {
		return (this.list.size() == 0);
	}
	/**
	 * this function cheak if there is a root between 2 pointe of the graph, acording the "coshee sentens" (MISPAT EREH HABINAIM)
	 * it chack the root between 2 points in range of epsilon
	 * assuming (f(x0)*f(x1) smaller than 0
	 * @param x0 represent one point in 'x' line
	 * @param x1 represent the secont point in 'x' line
	 * @param eps represent the range of mistake
	 * @return value of root - returns Integer.min_value if f(x0)*f(x1) greater than 0
	 */
	@Override
	public double root(double x0, double x1, double eps) {//eps epsilon

		double bigger = Math.max(x0, x1);		
		double smaller = Math.min(x0, x1);		
		double mid = 0;//mid point (x) between x0,x1
		double c = 0;//y val at mid point
			if(f(x0) * f(x1) <= 0){
				while(bigger - smaller >= eps)
				{
					mid = (bigger + smaller) / 2;
					c = this.f(mid);
					if(c == 0) {
						return mid;
					}
					else if(c > 0){
						bigger = mid;
					}
					else{
						smaller = mid;
					}
				}
				return mid;
			}else
				return Integer.MIN_VALUE;
	}
	/**
	 * create a deep copy of this Polynum
	 * @return return the copy of the polynom
	 */
	@Override
	public Polynom_able copy() {
		Polynom p1 = new Polynom();
		Iterator<Monom> iter = this.iteretor();
		while(iter.hasNext()){
			Monom m = iter.next();
			p1.add(m);
		}
		p1.list = sortAndMerge(p1.list);
		return p1;
		
	}
	/**
	 * Compute a new Polynom which is the derivative of this Polynom
	 * @return the the derivative of the polynom
	 */
	@Override
	public Polynom_able derivative() {
		Iterator<Monom>myIter = this.iteretor();
		while(myIter.hasNext())
		{
			Monom get_one =myIter.next();
			if(get_one._power==0)
			{
				myIter.remove();
			}
			else
			{
				get_one._coefficient*=get_one._power;
				get_one._power--;
			}
		}
		return this;
	}
	/**
	 * Compute Riemann's Integral over this Polynom starting from x0, till x1 using eps size steps,
	 * see: https://en.wikipedia.org/wiki/Riemann_integral
	 * @return the approximated area above the x-axis below this Polynom and between the [x0,x1] range.
	 */
	@Override
	public double area(double x0, double x1, double eps) {
		double bigger=Math.max(x0, x1);		
		double smaller=Math.min(x0, x1);	
		double sum=0;
		for(double i=smaller;i<bigger;i=i+eps)
		{
			sum+=f(i)*eps;
		}
		return sum;
	}
	/**
	 * Iterate over this list using Iterator.
	 */
	@Override
	public Iterator<Monom> iteretor() {
		return this.list.iterator();
	}
}
