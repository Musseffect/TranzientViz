package Solvers.Math;

import Solvers.Exceptions.GaussDivergenceException;

public class MathUtils {
    public static <T> void swap(T []a,int i,int j)
    {
        T t = a[i];
        a[i] = a[j];
        a[j] = t;
    }
    public static MathVector gauss(Matrix mat, MathVector y) throws GaussDivergenceException
    {
        int rank = y.getLength();
        Integer[] rows=new Integer[rank];
        Integer[] columns=new Integer[rank];
        Matrix tMat=new Matrix(mat);
        MathVector tY=new MathVector(y);
        MathVector result=new MathVector(rank);
        for (int i = 0; i < rank; i++)
        {
            columns[i] = rows[i] = i;
        }
        //прямой проход
        for (int i = 0; i < rank; i++)
        {
            Double maxValue = 0.0;
            int maxColumn = 0;
            int maxRow = 0;
            for (int j = i; j < rank; j++)
            {
                for (int k = i; k < rank; k++)
                {
                    Double val = tMat.get(columns[k], rows[j]);
                    if (Math.abs(val)>Math.abs(maxValue))
                    {
                        maxColumn = k;
                        maxRow = j;
                        maxValue = val;
                    }
                }
            }
            if (Math.abs(maxValue) < 0.001)
                throw new GaussDivergenceException();
            swap(columns,i,maxColumn);
            swap(rows,i,maxRow);
            for (int m = i + 1; m < rank; m++)
            {
                Double value=tMat.get(columns[m],rows[i])/maxValue;
                tMat.set(columns[m], rows[i],value);
            }
            tY.set(rows[i],tY.get(rows[i])/maxValue);
            for (int m = i + 1; m<rank; m++)
            {
                Double val = tMat.get(columns[i], rows[m]);
                for (int l = i + 1; l < rank; l++)
                {
                    Double value = tMat.get(columns[l],rows[m])-val*tMat.get(columns[l], rows[i]);
                    tMat.set(columns[l], rows[m],value);
                }
                tY.set(rows[m],tY.get(rows[m])-tY.get(rows[i])*val);
            }
        }
        MathVector x=new MathVector(rank);
        //обратный проход
        for (int i = rank - 1; i > -1; --i)
        {
            Double val = tY.get(rows[i]);
            for (int j = i + 1; j < rank; j++)
                val -= x.get(columns[j]) * tMat.get(columns[j], rows[i]);
            x.set(columns[i],val);
        }
        return x;
    }
}
