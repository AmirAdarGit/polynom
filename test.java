package myMath;

import java.util.*;

public class test {
	
	public static void main(String[] args){
		Polynom p1 = new Polynom(); //zero polynom
		Polynom p2 = new Polynom("+5x^2      -5 +    7x^3    -----5x +5 +15 +2x^6+++++"); //extra spaces plus and minus
		Polynom p3 = new Polynom(p2.toString());
		Polynom_able p4 = p3.copy();
		
		Polynom p9 = new Polynom("-5x^2 +6x -7x^3 + x^4 -8x^9"); //starts with "-"
		Polynom p20 = new Polynom("x-2"); 
		Polynom p10 = new Polynom("-x"); 
		
//		String str = "+-";
//		System.out.println(str.replaceAll("+-", "-"));
//		
		System.out.println("p1 = " + p1.toString());
		System.out.println("p2 = " + p2.toString());
		System.out.println("p3 is constructed by p2's toString = " + p3.toString());
		System.out.println("p4 is a copy of p3 = " + p4.toString());
		
		System.out.println("p9 = " + p9.toString());
		System.out.println("p20 = " + p20.toString());
		System.out.println("p10 = " + p10.toString());
		
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		
		Polynom p5 = new Polynom("3x^2 +2x +5");
		System.out.println(p5.toString());
		
		p5.add(new Monom("2x^2"));
		p5.add(new Monom("x^5"));
		
		//should be x^5 +5x^2 +2x +5
		System.out.println("after add(2x^2) and add(x^5) = " + p5.toString()); 
		
		p5.add(new Polynom("2x^2 -2x +10"));
		
		//should be x^5 + 7x^2 + 15
		System.out.println("after add(2x^2 -2x +10) = " + p5.toString());
		
		p5.multiply(new Polynom("4x^3 + x^2 -3x"));
		
		//should be 4x^8 +x^7 -3x^6 +28x^5 +7x^4 +39x^3 +15x^2 -45x
		System.out.println("after multiply(4x^3 + x^2 -3x) = " + p5.toString());
		
		p5.substract(new Monom("-15x^2"));
		
		//should be 4x^8 +x^7 -3x^6 +28x^5 +7x^4 +39x^3 +15x^2 -45x
		System.out.println("after subtract(15x^2) = " + p5.toString());
		
		p5.substract(new Polynom("x^7 -3x^6 +10"));
		
		//should be 4x^8 +28x^5 +7x^4 +39x^3 -45x 
		System.out.println("after subtract(x^7 -3x^6 +10) = " + p5.toString());
		
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		
		System.out.println("is p2 a zero polynom? = " + p2.isZero()); //should be false
		System.out.println("is p1 a zero polynom? = " + p1.isZero()); //should be true
		
		Polynom temp1 = new Polynom("3x^2");
		Polynom temp2 = new Polynom("3x^2");
		Polynom temp3 = new Polynom("-3x^2");
		
		System.out.println("is temp1 equal to temp2? = " + temp1.equals(temp2)); //should be true
		System.out.println("is temp1 equal to temp3? = " + temp1.equals(temp3)); //should be false
		
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		
		Polynom temp4 = new Polynom("x^2 -2x +1");
		
		System.out.println("the value P(x=3) is = " + temp4.f(3)); //should be 4
		System.out.println("the value P(x=0) is = " + temp4.f(0)); //should be 1
		System.out.println("the value P(x=-3) is = " + temp4.f(-3)); //should be 16
		
		System.out.println("the root of p(x) between 0 and 1 = " + temp4.root(0, 1, 0.01)); //should be 1
		System.out.println("the root of p(x) between 0 and 2 = " + temp4.root(0, 2, 0.01));
		
		System.out.println("area of p(x) between 0 and 2 = " + temp4.area(0, 2, 0.01)); //shoudl be 2/3
	}
}
