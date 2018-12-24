package Solvers.Math;

import java.util.Arrays;

public class MathVector {
    Double[] vector;
    public MathVector(int size)
    {
        vector=new Double[size];
        Arrays.fill(vector,0.0);
    }
    public MathVector(int size,Double value)
    {
        vector=new Double[size];
        Arrays.fill(vector,value);
    }
    public MathVector(int size,Double[] data)
    {
        vector=Arrays.copyOf(data,size);
    }
    public MathVector(Double[] data)
    {
        vector=data.clone();
    }
    public MathVector()
    {
        vector=new Double[0];
    }
    public MathVector(MathVector b)
    {
        vector=b.vector.clone();
    }
    public void resize(int size)
    {
        vector=Arrays.copyOf(vector,size);
    }
    public void set(int index,Double value)
    {
        assert index>=0&&index<vector.length;
        vector[index]=value;
    }
    public Double get(int index)
    {
        return vector[index];
    }
    public MathVector addSelf(MathVector b)
    {
        assert b.vector.length==vector.length;
        for(int i=0;i<vector.length;i++)
        {
           vector[i]+=b.vector[i];
        }
        return this;
    }
    public MathVector subSelf(MathVector b)
    {
        assert b.vector.length==vector.length;
        for(int i=0;i<vector.length;i++)
        {
            vector[i]-=b.vector[i];
        }
        return this;
    }
    public MathVector multSelf(MathVector b)
    {
        assert b.vector.length==vector.length;
        for(int i=0;i<vector.length;i++)
        {
            vector[i]*=b.vector[i];
        }
        return this;
    }
    public MathVector divSelf(MathVector b)
    {
        assert b.vector.length==vector.length;
        for(int i=0;i<vector.length;i++)
        {
            vector[i]/=b.vector[i];
        }
        return this;
    }
    public MathVector add(MathVector b)
    {
        assert b.vector.length==vector.length;
        MathVector result=new MathVector(this);
        for(int i=0;i<vector.length;i++)
        {
            result.vector[i]+=b.vector[i];
        }
        return result;
    }
    public MathVector mult(MathVector b)
    {
        assert b.vector.length==vector.length;
        MathVector result=new MathVector(this);
        for(int i=0;i<vector.length;i++)
        {
            result.vector[i]*=b.vector[i];
        }
        return result;
    }
    public MathVector div(MathVector b)
    {
        assert b.vector.length==vector.length;
        MathVector result=new MathVector(this);
        for(int i=0;i<vector.length;i++)
        {
            result.vector[i]/=b.vector[i];
        }
        return result;
    }
    public Double[] getArray()
    {
        return vector.clone();
    }
    public MathVector sub(MathVector b)
    {
        assert b.vector.length==vector.length;
        MathVector result=new MathVector(this);
        for(int i=0;i<vector.length;i++)
        {
            result.vector[i]-=b.vector[i];
        }
        return result;
    }
    public MathVector mult(double scalar)
    {
        MathVector result=new MathVector(this);
        for(int i=0;i<vector.length;i++)
        {
            result.vector[i]*=scalar;
        }
        return result;
    }
    public MathVector div(double scalar)
    {
        MathVector result=new MathVector(this);
        for(int i=0;i<vector.length;i++)
        {
            result.vector[i]/=scalar;
        }
        return result;
    }
    public MathVector sub(double scalar)
    {
        MathVector result=new MathVector(this);
        for(int i=0;i<vector.length;i++)
        {
            result.vector[i]-=scalar;
        }
        return result;
    }
    public MathVector add(double scalar)
    {
        MathVector result=new MathVector(this);
        for(int i=0;i<vector.length;i++)
        {
            result.vector[i]+=scalar;
        }
        return result;
    }
    public MathVector addSelf(double scalar)
    {
        for(int i=0;i<vector.length;i++)
        {
            vector[i]+=scalar;
        }
        return this;
    }
    public MathVector addSelf(int i,double scalar)
    {
        assert i>=0&&i<vector.length;
        vector[i]+=scalar;
        return this;
    }
    public MathVector subSelf(double scalar)
    {
        for(int i=0;i<vector.length;i++)
        {
            vector[i]-=scalar;
        }
        return this;
    }
    public MathVector subSelf(int i,double scalar)
    {
        assert i>=0&&i<vector.length;
        vector[i]-=scalar;
        return this;
    }
    public MathVector multSelf(double scalar)
    {
        for(int i=0;i<vector.length;i++)
        {
            vector[i]*=scalar;
        }
        return this;
    }
    public MathVector divSelf(double scalar)
    {
        for(int i=0;i<vector.length;i++)
        {
            vector[i]/=scalar;
        }
        return this;
    }
    public MathVector add(int index,double scalar)
    {
        assert index>=0&&index<vector.length;
        MathVector result=new MathVector(this);
        result.vector[index]+=scalar;
        return result;
    }
    public MathVector getSubVector(int i0,int i1)
    {
        assert (i1 > i0&&i1 <= vector.length&&i0>=0);
        MathVector result=new MathVector(Arrays.copyOfRange(vector,i0,i1));
        return result;
    }
    public MathVector negation()
    {
        MathVector result=new MathVector(this);
        for(int i=0;i<vector.length;i++)
            result.vector[i]=-result.vector[i];
        return result;
    }
    public MathVector negationSelf()
    {
        for(int i=0;i<vector.length;i++)
            vector[i]=-vector[i];
        return this;
    }
    public MathVector sub(int index,double scalar)
    {
        assert index>=0&&index<vector.length;
        MathVector result=new MathVector(this);
        result.vector[index]-=scalar;
        return result;
    }
    public double dot(MathVector b)
    {
        assert vector.length==b.vector.length;
        double result=0.0;
        for(int i=0;i<vector.length;i++)
            result+=vector[i]*b.vector[i];
        return result;
    };
    public int getLength(){return vector.length;}
    public double maxNorm()
    {
        double result=0.0;
        for(int i=0;i<vector.length;i++)
        {
            result=Math.max(result,Math.abs(vector[i]));
        }
        return result;
    }
}
