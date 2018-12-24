package Solvers;

import Solvers.Core.ODESystem;
import Solvers.Math.MathVector;
import Solvers.Math.Matrix;

public class StateSpaceODESystem extends ODESystem {
    Matrix A;
    MathVector b;
    IInputFunction u;
    public void Init(Matrix A, MathVector b,IInputFunction u)
    {
        this.A=new Matrix(A);
        this.b=new MathVector(b);
        this.u=u;
    }
    @Override
    public MathVector calc(MathVector y, double t) {
        return A.multRight(y).addSelf(b.mult(u.f(t)));
    }

    @Override
    public boolean hasAnalyticalJacobian() {

        return true;
    }

    @Override
    public Matrix calcAnalticalJacobian(MathVector y, double t) {

        return this.A;
    }

    @Override
    public int getSystemOrder() {
        return b.getLength();
    }
}
