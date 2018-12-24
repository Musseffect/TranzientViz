package Solvers.ExplicitSolvers;

import Solvers.Core.ODESolver;
import Solvers.Math.MathVector;

public abstract class ExplicitAutoStep extends ODESolver {
    protected double minStep;
    protected double maxStep;
    protected double errorTolerance;
    protected abstract MathVector autoStep(MathVector y,double t);
    protected abstract MathVector normalStep(MathVector y,double t);
    @Override
    public final MathVector step(MathVector y,double t)
    {
        if(minStep==maxStep)
        {
            return normalStep(y,t);
        }
        return autoStep(y,t);
    }
    public void setMaxStep(double maxStep)
    {
        this.maxStep=maxStep;
    }
    public void setErrorTol(double errorTolerance)
    {
        this.errorTolerance=errorTolerance;
    }
    @Override
    public void setStep(double step)
    {
        minStep=step;
        this.step=step;
    }
}
