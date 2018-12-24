package Solvers.ExplicitSolvers.ExplicitRK;

import Solvers.Core.Solution;
import Solvers.ExplicitSolvers.ExplicitAutoStep;
import Solvers.Math.MathVector;

import java.util.ArrayList;
import java.util.List;


public abstract class ExplicitAutoStepRK extends ExplicitAutoStep {
    protected Double[]a;
    protected Double[]b;
    protected Double[]c;
    protected Double[]bDiff;
    protected int stages;

    @Override
    protected MathVector autoStep(MathVector y, double t) {

        List<MathVector> k=new ArrayList<>();
        MathVector yTemp;
        MathVector result=new MathVector(y);
        ((ArrayList<MathVector>) k).ensureCapacity(this.stages);
        for (int i = 0; i < this.stages; i++)
        {
            yTemp = new MathVector(y);
            for (int j = 0; j < i; j++)
            {
                yTemp.addSelf(k.get(j).mult(step*this.a[j + (i*i - i) / 2]));//low triangular matrix without diagonale
            }
            k.add(system.calc(yTemp,t+step*this.c[i]));
            result.addSelf(k.get(i).mult(step*this.b[i]));
        }
        yTemp=new MathVector(y.getLength());
        for (int i = 0; i < this.stages; i++)
        {
            yTemp.addSelf(k.get(i).mult(this.bDiff[i]));
        }
        Double difference = yTemp.maxNorm();
        Double stepOpt = Math.pow(errorTolerance*step*0.5 / difference, 0.20)*step;
        stepOpt = Math.min(Math.max(minStep, stepOpt), maxStep);
        if (stepOpt * 2.0<step)
        {
            step = stepOpt;
            return normalStep(y,t);
        }
        return result;
    }

    @Override
    protected MathVector normalStep(MathVector y, double t) {

        List<MathVector> k=new ArrayList<>();
        MathVector yTemp;
        MathVector result=new MathVector(y);
        ((ArrayList<MathVector>) k).ensureCapacity(this.stages);
        for (int i = 0; i < this.stages; i++)
        {
            yTemp = new MathVector(y);
            for (int j = 0; j < i; j++)
            {
                yTemp.addSelf(k.get(j).mult(step*this.a[j + (i*i - i) / 2]));//low triangular matrix without diagonale
            }
            k.add(system.calc(yTemp,t+step*this.c[i]));
            result.addSelf(k.get(i).mult(step*this.b[i]));
        }
        return result;
    }
}
