package Solvers.ImplicitSolvers.ImplicitRK;

public class LOBATTOIIIC extends ImplicitRK {
    public static Double[]a={
            1.0/12.0,-2.23607/12,2.23607/12,-1.0/12.0,
            1.0/12.0,0.25,(10.0-7.0*2.23607)/60.0,2.23607/60.0,
            1.0/12.0,(10.0+7.0*2.23607)/60.0,1.0/4.0,-2.23607/60.0,
            1.0/12.0,5.0/12.0,5.0/12.0,1/12.0
    };
    public static Double[]b={
            1.0/12.0,5.0/12.0,5.0/12.0,1.0/12.0
    };
    public static Double[]c={
            0.0,(0.5-0.1*2.23607),(0.5+0.1*2.23607),1.0
    };
    public LOBATTOIIIC()
    {
        super.a=LOBATTOIIIC.a;
        super.b=LOBATTOIIIC.b;
        super.c=LOBATTOIIIC.c;
        super.stages=4;
    }
}
