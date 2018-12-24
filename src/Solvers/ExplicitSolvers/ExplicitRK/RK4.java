package Solvers.ExplicitSolvers.ExplicitRK;


public class RK4 extends ExplicitRK
{
    public static Double []a={
     0.5,
     0.0, 0.5,
     0.0, 0.0, 1.0};
    public static Double []b={1.0 / 6.0, 1.0 / 3.0, 1.0 / 3.0, 1.0 / 6.0};
    public static Double []c={0.0, 0.5, 0.5, 1.0};
    public RK4()
    {
        super.a=RK4.a;
        super.b=RK4.b;
        super.c=RK4.c;
        super.stages=4;

    }
}
