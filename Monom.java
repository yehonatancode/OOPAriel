
package myMath;
/**
 * This class represents a simple "Monom" of shape a*x^b, where a is a real number and a is an integer (summed a none negative), 
 * see: https://en.wikipedia.org/wiki/Monomial 
 * The class implements function and support simple operations as: construction, value at x, derivative, add and multiply. 
 * @author Boaz
 *
 */
public class Monom implements function{
	
	public Monom(double a, int b){
		this.set_coefficient(a);
		this.set_power(b);
	}
	public Monom(Monom ot) {
		this(ot.get_coefficient(), ot.get_power());
	}
	public Monom(String om){ //The monom is consisted of 4 chars - i.e. 4x^2
		double c = om.charAt(0);
		int p = om.charAt(3);
		Monom m= new Monom(c,p);
		
	}
	
	@Override
	public double f(double x) {
		double a = this._coefficient;
		int b = this._power;
		int temp = (int)Math.pow(x, b);
		return a*temp;
		
	} 

public double get_coefficient() {
		// TODO Auto-generated method stub
		return this._coefficient;
	}
public int get_power() {
		// TODO Auto-generated method stub
		return this._power;
	}

public Monom Derivative(Monom m){
	m._coefficient=m._power*m._coefficient;
	m._power=m._power-1;
	return m;
	
}
public void Derivative(){
	this._coefficient=this._power*this._coefficient;
	this._power=this._power-1;
}
public Monom substract(Monom om){
	Monom_Comperator mc = new Monom_Comperator();
	if(this.get_power()==om.get_power()){
	if(mc.compare(this, om)<0){
		this._coefficient=om._coefficient-this._coefficient;
	}
	else this._coefficient=this._coefficient-om._coefficient;
}
	return this;
}
public Monom add(Monom om) throws Exception{
	if(this._power==om._power){
		om._coefficient=om._coefficient+this._coefficient;
		return om;
	}
	throw new Exception();
}

public Monom multiply(Monom om){
	this._coefficient=this._coefficient*om._coefficient;
	this._power=this._power+om._power;
	return this;
	
}
	private void set_coefficient(double a){
		this._coefficient = a;
	}
	private void set_power(int p) {
		this._power = p;
	}
	
	private double _coefficient; // 
	private int _power;
	

}
