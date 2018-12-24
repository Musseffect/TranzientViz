package Solvers.ExplicitSolvers.ExplicitRK;


public class DOPRI57 extends ExplicitAutoStepRK
{
    public static Double[]a={
        1.0/5.0,
        3.0/40.0,9.0/40.0,
        44.0/45.0,-56.0/15.0, 32.0/9.0,
        19372.0/6561.0,-25360.0/2187.0, 64448.0/6561.0,-212.0/729.0,
        9017.0/3168.0,-355.0/33.0, 46732.0/5247.0,49.0/176.0,-5103.0/18656.0,
        35.0/384.0,0.0,500.0/1113.0,125.0/192.0,-2187.0/6784.0,11.0/84.0
    };
    public static Double[]b={35.0/384.0,0.0,500.0/1113.0,125.0/192.0,-2187.0/6784.0,11.0/84.0};
    public static Double[]c={0.0,1.0/5.0,3.0/10.0,4.0/5.0,8.0/9.0,1.0,1.0};
    public static Double[]bDiff={71.0/57600.0,0.0,-71.0/16695.0,71.0/1920.0,-17253.0/339200.0,22.0/525.0,-1.0/40.0};
    public DOPRI57()
    {
        super.a=DOPRI57.a;
        super.b=DOPRI57.b;
        super.c=DOPRI57.c;
        super.bDiff=DOPRI57.bDiff;
        super.stages=6;
    }
}
