package Solvers.ImplicitSolvers;

import Solvers.Core.Solution;
import Solvers.Math.MathVector;
import Solvers.Math.Matrix;

public class ImplicitEulerSolver extends ImplicitSolverOneStage {

    @Override
    public MathVector function(Double t, MathVector y,MathVector yCurrent) {
        return yCurrent.sub(y.add(system.calc(yCurrent,t).mult(step)));
    }

    @Override
    public Matrix jacobiMatrix(Double t, MathVector y) {
        int systemSize = system.getSystemOrder();
        Matrix result = calcSystemJacobian(t,y);
        for (int i = 0; i < systemSize; i++) {
            for (int j = 0; j < systemSize; j++)
            {
                Double value=(i==j?1.0:0.0)-result.get(i,j)*step;
                result.set(i,j,value);
            }
        }
        return result;
    }
}
