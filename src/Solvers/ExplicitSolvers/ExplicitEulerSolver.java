package Solvers.ExplicitSolvers;

import Solvers.Math.MathVector;
import Solvers.Core.ODESolver;
import Solvers.Core.Solution;

public class ExplicitEulerSolver extends ODESolver {
    @Override
    public MathVector step(MathVector y, double t)
    {
        int systemSize=system.getSystemOrder();
        MathVector dy=system.calc(y,t);
        return y.add(dy.multSelf(step));
    }
}
