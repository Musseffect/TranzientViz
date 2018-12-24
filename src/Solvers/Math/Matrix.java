package Solvers.Math;

import java.util.Arrays;

public class Matrix {
    int width;
    int height;
    Double[] data;
    public Matrix()
    {
        width=0;
        height=0;
        data=new Double[0];
    }
    public Matrix(Matrix b)
    {
        width=b.width;
        height=b.height;
        data=b.data.clone();
    }
    public Matrix(int w,int h)
    {
        width=w;
        height=h;
        data=new Double[w*h];
        Arrays.fill(data,0.0);
    }
    public Matrix(int w, int h,Double[] data)
    {
        width=w;
        height=h;
        this.data=Arrays.copyOf(data,w*h);
    }
    public Double get(int i,int j)
    {
        return data[i+j*width];
    }
    public void set(int i,int j,Double value)
    {
        data[i+j*width]=value;
    }
    public int getWidth(){
        return width;
    }
    public int getHeight()
    {
        return height;
    }
    public MathVector multRight(MathVector v)
    {
        assert v.vector.length==width;
        MathVector result=new MathVector(height);
        for(int i=0;i<height;i++)
        {
            Double value=0.0;
            for(int j=0;j<width;j++)
            {
                value+=get(j,i)*v.get(j);
            }
            result.set(i,value);
        }
        return result;
    }
    public MathVector multLeft(MathVector v)
    {
        assert v.vector.length==height;
        MathVector result=new MathVector(width);
        for(int i=0;i<width;i++)
        {
            Double value=0.0;
            for(int j=0;j<height;j++)
            {
                value+=get(i,j)*v.get(j);
            }
            result.set(i,value);
        }
        return result;
    }
}
