package myMath;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.function.Predicate;

import org.knowm.xchart.QuickChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;

import myMath.Monom;
/**
 * This class represents a Polynom with add, multiply functionality, it also should support the following:
 * 1. Riemann's Integral: https://en.wikipedia.org/wiki/Riemann_integral
 * 2. Finding a numerical value between two values (currently support root only f(x)=0).
 * 3. Derivative
 * 
 * @author Boaz
 * @student Yehonatan Tseva 316526474
 *
 */
/**
 * What I did in the assignment is considering each Polynom_able as a polynom via the <Monom> Iterator,
 *  and dividing every each Polynom into Monoms, that's why there are Polynomable functions like add,multiply, and substract
 *  that I implemented twice, to allow them to be generated as Monoms, and access the Monom class.
 *  I hope I did everything correct, thanks in advance.
 */
public class Polynom implements Polynom_able{
	
	private ArrayList<Monom> power = new ArrayList<Monom>();
	private final Monom_Comperator comperator = new Monom_Comperator();

	@Override
	public Iterator<Monom> iteretor() {
		return this.power.iterator();
	}
	
	public Polynom(){
		power = new ArrayList<Monom>(); //פולינום האפס
	}
	
	@Override
	public void add(Polynom_able p1) {

		Iterator<Monom> iter = p1.iteretor();
		while(iter.hasNext()){
			Monom m1 = iter.next();
			this.power.add(m1);
		}
	}

	@Override
	public void add(Monom m1){
		for(int i=0; i<this.power.size();i++)	{
			if(this.power.get(i).get_power()==m1.get_power()){
				try {
					this.power.get(i).add(m1);
				}
				catch(Exception e){
					
				}
			}
		}
	}

	@Override
	public void multiply(Polynom_able p1) {
		power.sort(comperator);
		
		Iterator<Monom> iter = p1.iteretor();
		while(iter.hasNext()){
			Monom m0 = iter.next();
			for(int i=0; i<this.power.size();i++){
				this.power.get(i).multiply(m0);
			}
		}
		
	}
	
	public void multiply(Monom m1){
		for(int i=0; i<this.power.size();i++){
			this.power.get(i).multiply(m1);
		}
	}

	@Override
	public double root(double x0, double x1, double eps) { // taken from Erel's github
			double y0 = this.f(x0);
			double y1 = this.f(x1);
			
			if(y0*y1>0) {
				throw new RuntimeException("Error: f(x0) and f(x1) should be on oposite sides of the X asix");
			}
			
			double delta_x = Math.abs(x0-x1);
			double delta_y = Math.abs(y0-y1);
			if(debug) {
				System.out.println("f("+x0+") = "+y0+"    f("+x1+") = "+y1+"   dx = "+delta_x);
			}
			if (delta_x>eps || delta_y>eps) {
				double x_mid = (x0+x1)/2;
				double y_mid = this.f(x_mid);
				double dir = y0*y_mid;
				if(dir<0) {
					return root(x0,x_mid, eps, debug);
				}
				else {
					return root(x_mid, x1, eps, debug);
				}
			}
			return x0;
		}
		
	@Override
	public Polynom_able derivative() {
			for(int i=0; i<this.power.size();i++){
				this.power.get(i).Derivative();
			}
			return this;
		}
	
	public void derivative(Monom m1){
		for(int i=0; i<this.power.size();i++){
			this.power.get(i).Derivative(m1);
		}
	}

	@Override
	public double area(double x0, double x1, double eps) {
		double sum=0;
		for(double i=x0 ; i < x1 ; i=i+eps)
		{
			sum = sum + f(i) * eps;
		}
		return sum;
	}

@Override
public double f(double x) { 
	Polynom p1 = new Polynom();
	double sum=0;
	Iterator<Monom> iter = p1.iteretor();
	while(iter.hasNext()){
		Monom m0 = iter.next();
		for(int i=0; i<this.power.size();i++){
			sum=sum+this.power.get(i).f(x);
		}
	}
	return x;
	
}

@Override
public void substract(Polynom_able p1) {
	Iterator<Monom> iter = p1.iteretor();
	while(iter.hasNext()){
		Monom m1 = iter.next();
		for(int i=0; i<this.power.size();i++){
		this.power.get(i).substract(m1);
		}
	}
}
	public void substract(Monom m1){
		for(int i=0; i<this.power.size();i++){
			this.power.get(i).substract(m1);
		}
	}

@Override
public boolean equals(Polynom_able p1) {
	Iterator<Monom> iter = p1.iteretor();
	while(iter.hasNext()){
		Monom m0 = iter.next();
		for(int i=0;i<this.power.size();i++)
		if((this.power.get(i).get_power()==m0.get_power()) && this.power.get(i).get_coefficient()==m0.get_coefficient())
		{
			return true;
		}
}
	return false;
}
@Override
public boolean isZero() {
	Polynom p = new Polynom();
	if(this.power==p.power){
	return true;
	}
	return false;
}

@Override
public Polynom copy() {
	Polynom_able pa = new Polynom();
	for(int i=0; i<this.power.size();i++){
		pa.add(new Monom(this.power.get(i).get_coefficient(),this.power.get(i).get_power()));
	}
	return (Polynom) pa;
}
public void Init(){
	power = new ArrayList<Monom>();
}
public void Init(String s){
	String _temp = s;
	String [] _arraytemp = _temp.split("(?=\\+|\\-)");
	int i = 1;
	String []b = _arraytemp[0].split("X\\^");
	if (b.length!=2)
		b = _arraytemp[0].split("x\\^");
	double coeff = Double.parseDouble(b[0]);
	int power = Integer.parseInt(b[1]);
	Monom m = new Monom(coeff,power);
	this.power.add(m);
	while (i<_arraytemp.length){
		b = _arraytemp[i].split("X\\^");
		if (b.length!=2)
			b = _arraytemp[i].split("x\\^");
		if (b.length==2) {
			coeff = Double.parseDouble(b[0]);
			power = Integer.parseInt(b[1]);
		}
		else {
			coeff = Double.parseDouble(b[0]);
			power = 0;
		}
		m = new Monom(coeff,power);
		this.power.add(m);
		i++;		
	}
	this.isZero();
	Collections.sort(this.power , new Monom_Comperator());
}
public String toString(){
	String s = "";
	Iterator<Monom> a = iteretor();
	while (a.hasNext()){
		Monom m = new Monom(a.next());
		s = s + m.get_coefficient() + "X^" + m.get_power()+"+";
	}
	s = s.replace( "+-", "-");
	s = s.replace( "X^0", "");
	s = s.substring(0, s.length()-1);
	return s+"" ;
}
public void GraphicPolynom() {
	ArrayList<Double> xData = new ArrayList<Double>();
	ArrayList<Double> yData = new ArrayList<Double>();
	for (double i = -2; i < 2; i+=0.1) {
		xData.add(i);
		yData.add(this.f(i));
	}
	System.out.println(xData.toString());
	System.out.println(yData.toString());
	XYChart chart = QuickChart.getChart("sample", "X" , "Y" , "y(x)" , xData, yData);
	new SwingWrapper<XYChart>(chart).displayChart();
	
}
public void graphPlot(double x0, double x1) {
	LinePlotTest frame = new LinePlotTest(this ,x0 ,x1 );
	frame.setVisible(true);
}

	
}
