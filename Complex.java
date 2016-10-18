import java.util.*;

class Complex{
    private double r;
    private double i;
    public Complex(double real,double imaginary){
        r=real;
        i=imaginary;
    }

    public double getReal(){
        return r;
    }
    public double getImaginary(){
        return i;
    }
    public void setReal(double real){
        r=real;
        return;
    }
    public void setImaginary(double imaginary){
        i=imaginary;
        return;
    }
    public Complex square(){
        double realPart=Math.pow(r,2)-Math.pow(i,2);
        double imaginaryPart=r*i*2;
        return new Complex(realPart,imaginaryPart);
    }
    public Complex plus(Complex c){
        return new Complex(r+c.getReal(),i+c.getImaginary());
    }
}
