package exercises;

/****************************************************************************
 * <b>Title:</b> PrimitiveData.java
 * <b>Project:</b> brian.training
 * <b>Description:</b> CHANGE ME!!
 * <b>Copyright:</b> Copyright (c) 2023
 * <b>Company:</b> Silicon Mountain Technologies
 * 
 * @author Brian Knight
 * @version 3.x
 * @since Jul 6, 2023
 * <b>updates:</b>
 *  
 ****************************************************************************/

public class PrimitiveData {
	private int i;
	private double d;
	private float f;
	private char c;
	private boolean b;
		
	public PrimitiveData() {
		super();
	}
	
	public PrimitiveData(int i, double d, float f, char c, boolean b) {
		super();
		this.i = i;
		this.d = d;
		this.f = f;
		this.c = c;
		this.b = b;
	}
	
	public int getI() {
		return i;
	}
	public void setI(int i) {
		this.i = i;
	}
	public double getD() {
		return d;
	}
	public void setD(double d) {
		this.d = d;
	}
	public float getF() {
		return f;
	}
	public void setF(float f) {
		this.f = f;
	}
	public char getC() {
		return c;
	}
	public void setC(char c) {
		this.c = c;
	}
	public boolean isB() {
		return b;
	}
	public void setB(boolean b) {
		this.b = b;
	}
	
	/**
	 * Method to print the attribute values.
	 */
	public void printValues() {
		System.out.printf("Attribute values:%nint = %d%ndouble = %f%nfloat = %f%nchar = %s%nboolean = %s%n", i, d, f, c, b);
	}
	
	@Override
	public String toString() {
		return "[i=" + i + ", d=" + d + ", f=" + f + ", c=" + c + ", b=" + b + "]";
	}	
}