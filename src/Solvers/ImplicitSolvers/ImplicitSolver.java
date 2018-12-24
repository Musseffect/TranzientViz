package Solvers.ImplicitSolvers;

import Solvers.Core.ODESolver;
import Solvers.Math.MathVector;
import Solvers.Math.Matrix;

public abstract class ImplicitSolver extends ODESolver {
    protected int maxIterations;
    protected double maxRelDifference;
    protected double maxAbsDifference;
    protected double maxAbsError;
    protected double epsilon;

    public ImplicitSolver()
    {
        maxIterations=20;
        maxRelDifference=0.0;
        maxAbsDifference=0.0;
        maxAbsError=0.0;
        epsilon=0.01;
    }
    public void setEpsilon(double epsilon)
    {
        this.epsilon=epsilon;
    }
    public void setMaxAbsError(double maxAbsError)
    {
        this.maxAbsError=maxAbsError;
    }

    public void setMaxIterations(int maxIterations) {
        this.maxIterations = maxIterations;
    }
    protected Matrix calcSystemJacobian(Double t, MathVector y)
    {
        if(system.hasAnalyticalJacobian())
            return system.calcAnalticalJacobian(y,t);
        return system.calcNumericalJacobian(y,t,epsilon);
    }
}
