package Solvers.ExplicitSolvers.ExplicitRK;

import Solvers.Core.ODESolver;
import Solvers.Core.Solution;
import Solvers.Math.MathVector;

import java.util.ArrayList;
import java.util.List;


public abstract class ExplicitRK extends ODESolver {
    public Double[] a;
    public Double[] b;
    public Double[] c;
    public int stages;
    @Override
    public MathVector step(MathVector y,double t)
    {
        List<MathVector> k=new ArrayList<MathVector>();
        ((ArrayList<MathVector>) k).ensureCapacity(this.stages);
        for(int i=0;i<this.stages;i++)
        {
            MathVector yt=new MathVector(y);
            for(int j=0;j<i;j++)
                yt.addSelf(k.get(j).mult(step*this.a[j + (i*i - i) / 2]));
            k.add(system.calc(yt,t+step*this.c[i]));
        }
        MathVector result=new MathVector(y);
        for(int i=0;i<this.stages;i++)
            result.addSelf(k.get(i).mult(this.b[i]*step));
        return result;
    }
}


