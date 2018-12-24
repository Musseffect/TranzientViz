package Solvers.ImplicitSolvers.ImplicitRK;

import Solvers.Core.Solution;
import Solvers.Exceptions.NewtonDivergenceException;
import Solvers.ImplicitSolvers.ImplicitSolver;
import Solvers.Math.MathUtils;
import Solvers.Math.MathVector;
import Solvers.Math.Matrix;


public abstract class ImplicitRK extends ImplicitSolver {
    public Double[]a;
    public Double[]b;
    public Double[]c;
    public int stages;

    private Matrix jacobiMatrix(MathVector y,double t,MathVector k)
    {
        int systemSize = y.getLength();
        Matrix result=new Matrix(systemSize*this.stages, systemSize*this.stages);
        MathVector yValues;
        for (int n = 0; n < this.stages; n++)
        {
            yValues = new MathVector(y);
            Double time = t + step*this.c[n];
            for (int i = 0; i < this.stages; i++)
            {
                MathVector value=k.getSubVector(i*systemSize,i*systemSize+systemSize).multSelf(step*this.a[n*this.stages + i]);
                yValues.addSelf(value);
            }
            Matrix jacobi = calcSystemJacobian(time, yValues);
            for (int j = 0; j < systemSize; j++)
            {
                for (int m = 0; m < this.stages; m++)
                {
                    for (int i = 0; i < systemSize; i++)
                    {
                        Double value=((m == n&&i == j) ? 1.0 : 0.0) - jacobi.get(i, j)*step*a[n*this.stages + m];
                        result.set(i + m*systemSize, n*systemSize + j,value);
                    }
                }
            }
        }
        return result;
    }
    public MathVector function(MathVector y,double t,MathVector k)
    {
        MathVector result=new MathVector(k);
        int systemSize = y.getLength();
        MathVector yValues;
        for (int m = 0, j = 0; m < this.stages; m++)
        {
            yValues = new MathVector(y);
            Double time = t + step*this.c[m];
            for (int i = 0; i < this.stages; i++)
            {
                MathVector value=k.getSubVector(i*systemSize, i*systemSize + systemSize).multSelf(step*this.a[m*stages + i]);
                yValues.addSelf(value);
            }
            MathVector f = system.calc( yValues,time);
            for (int i = 0; i < systemSize; i++, j++)
            {
                result.subSelf(j,f.get(i));
            }
        }
        return result;
    }
    @Override
    public MathVector step(MathVector y, double t) throws Exception
    {
        MathVector kVector = solveNewton(y, t);
        MathVector result=new MathVector(y);
        int systemSize = system.getSystemOrder();
        for (int i = 0; i <this.stages; i++)
        {
            for (int k = 0; k < systemSize; k++)
            {
                Double value=step*kVector.get(i*systemSize + k) * this.b[i];
                result.addSelf(k,value);
            }
        }
        return result;
    }
    public MathVector solveNewton(MathVector y,double t) throws Exception
    {
        int systemSize = y.getLength();
        Double absDifference = Double.MAX_VALUE;
        Double relDifference = Double.MAX_VALUE;
        Double lastNorm = Double.MAX_VALUE;
        MathVector kVector=new MathVector(this.stages*systemSize,0.0);
        MathVector fVector = function(y, t, kVector).negationSelf();
        for (int i = 0; true; i++)
        {
            Matrix jMatrix = jacobiMatrix(y, t, kVector);
            MathVector dk = MathUtils.gauss(jMatrix, fVector);

            Double kNorm = kVector.maxNorm();
            kVector.addSelf(dk);
            fVector = function(y, t, kVector).negationSelf();

            Double fNorm = fVector.maxNorm();
            lastNorm = fNorm;

            Double dkNorm = dk.maxNorm();
            if (fNorm<maxAbsError)
            {
                break;
            }
            if (fNorm>lastNorm&&i==maxIterations)
                throw new NewtonDivergenceException();
            if (dkNorm<maxAbsDifference)
            {
                break;
            }
            if (dkNorm<maxRelDifference*kNorm)
            {
                break;
            }
            if (i >= maxIterations)
            {
                break;
            }
        }
        return kVector;
    }
}

