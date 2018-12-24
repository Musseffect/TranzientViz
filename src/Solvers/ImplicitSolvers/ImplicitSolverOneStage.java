package Solvers.ImplicitSolvers;

import Solvers.Core.ODESolver;
import Solvers.Exceptions.NewtonDivergenceException;
import Solvers.Math.MathUtils;
import Solvers.Math.MathVector;
import Solvers.Math.Matrix;

public abstract class ImplicitSolverOneStage extends ImplicitSolver {

    public abstract MathVector function(Double t, MathVector y,MathVector yCurrent);//F(x(n))
    public abstract Matrix jacobiMatrix(Double t, MathVector y);
    public MathVector solveNewton(MathVector y,Double t) throws Exception
    {
        int systemSize = y.getLength();
        Double absDifference = Double.MAX_VALUE;
        Double relDifference = Double.MAX_VALUE;
        Double lastNorm = Double.MAX_VALUE;
        MathVector yCurrent=new MathVector(y);
        MathVector fVector = function(t+step, y,yCurrent).negationSelf();
        for (int i = 0; true; i++)
        {
            Matrix jMatrix = jacobiMatrix(t+step, yCurrent);
            MathVector dy = MathUtils.gauss(jMatrix, fVector);

            Double yNorm = yCurrent.maxNorm();
            yCurrent.addSelf(dy);
            fVector = function(t+step,y, yCurrent).negationSelf();

            Double fNorm = fVector.maxNorm();
            lastNorm = fNorm;

            Double dyNorm = dy.maxNorm();
            if (fNorm<maxAbsError)
            {
                break;
            }
            if (fNorm>lastNorm&&i==maxIterations)
                throw new NewtonDivergenceException();
            if (dyNorm<maxAbsDifference)
            {
                break;
            }
            if (dyNorm<maxRelDifference*yNorm)
            {
                break;
            }
            if (i >= maxIterations)
            {
                break;
            }
        }
        return yCurrent;
    }

    public MathVector step(MathVector y, double t) throws Exception
    {
        return solveNewton(y,t);
    }
}
/*
        template<class T>
        Vector<T> ImplicitODESolver<T>::function(T t, Vector<T>& x)
        {
        return Vector<T>();
        }
        template<class T>
        void ImplicitODESolver<T>::setEpsilon(T eps)
        {
        epsilon = eps;
        }
        template<class T>
        Matrix<T> ImplicitODESolver<T>::jacobiMatrix(T t, Vector<T>& x)//J(x(n))
        {
        if (this->system->canCalcJacobi())
        return this->system->calcAnalyticalJacobiMatrix(t, x);
        return system->calcNumericalJacobiMatrix(t, x, epsilon);
        }
        template<class T>
//newton method, finds x(n)-x(n-1) every step by solving (x(n)-x(n-1))*J(x(n))=-F(x(n))
        Vector<T> ImplicitODESolver<T>::solveNewton(Vector<T> x, T t)
        {
        int systemSize = x.size();
        float absDifference = std::numeric_limits<T>::max();
        float relDifference = std::numeric_limits<T>::max();
        T lastNorm = std::numeric_limits<T>::max();
        Vector<T> fVector = -Function(t, x);
        for (int i = 0; true; i++)
        {
        Matrix<T> jMatrix = jacobiMatrix(t, x);
        Vector<T> dx = solveGauss(jMatrix, fVector);

        T xNorm = x.maxNorm();
        x = x + dx;
        fVector = -Function(t, x);

        T fNorm = fVector.maxNorm();
        lastNorm = fNorm;
        if (fNorm>lastNorm&&i==maxIterations)
        throw DivergenceException("Implicit ODE solver: newton method failed to converge");

        T dxNorm = dx.maxNorm();
        if (fNorm<maxAbsError)
        {
        break;
        }
        if (dxNorm<maxAbsDifference)
        {
        break;
        }
        if (dxNorm<maxRelDifference*xNorm)
        {
        break;
        }
        if (i >= maxIterations)
        {
        break;
        }
        }
        return x;
        }
        */