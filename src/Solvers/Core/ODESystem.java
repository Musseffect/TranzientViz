package Solvers.Core;

import Solvers.Math.MathVector;
import Solvers.Math.Matrix;

public abstract class ODESystem {
    public abstract MathVector calc(MathVector y, double t);
    public abstract boolean hasAnalyticalJacobian();
    public Matrix calcAnalticalJacobian(MathVector y, double t)
    {
        return null;
    }
    public Matrix calcNumericalJacobian(MathVector y,double t,double step)
    {
        int systemSize=getSystemOrder();
        Matrix result=new Matrix(systemSize,systemSize);
        for(int i=0;i<systemSize;i++)
        {
            MathVector dy=calc(y.add(i,step),t).subSelf(calc(y.sub(i,step),t)).div(step*2.0);
            for(int j=0;j<systemSize;j++)
            {
                result.set(i,j,dy.get(j));
            }
        }
        return result;
    }
    public abstract int getSystemOrder();
}
