package Solvers.Core;

import Solvers.Math.MathVector;

public abstract class ODESolver {
    protected ODESystem system;
    protected double step;
    public void setSystem(ODESystem system)
    {
        this.system=system;
    }
    public void setStep(double step)
    {
        this.step=step;
    }
    public final Solution solve(double t0,double t1,MathVector y0) throws Exception{
        Solution solution = new Solution();
        MathVector y = new MathVector(y0);
        double t = t0;
        for (t = t0; t <= t1; t += step) {
            solution.add(t, y.getArray());
            y = step(y, t);
        }
        solution.add(t, y.getArray());
        return solution;
    }
    public abstract MathVector step(MathVector y, double t) throws Exception;
    public double getStep()
    {
        return step;
    }
}
