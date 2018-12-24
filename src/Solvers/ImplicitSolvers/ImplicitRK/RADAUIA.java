package Solvers.ImplicitSolvers.ImplicitRK;



public class RADAUIA extends ImplicitRK {
    public static Double[]a={
            1.0 / 9.0, (-1.0 - Math.sqrt(6.0)) / 18.0, (-1.0 + Math.sqrt(6.0)) / 18.0,
            1.0 / 9.0, (88.0 + 7.0 * Math.sqrt(6.0)) / 360.0, (88.0 - 43.0 * Math.sqrt(6.0)) / 360.0,
            1.0 / 9.0, (88.0 + 43.0 * Math.sqrt(6.0)) / 360.0, (80.0 - 7.0 * Math.sqrt(6.0)) / 360.0 };
    public static Double[]b={ 1.0 / 9.0, (16.0 + Math.sqrt(6.0)) / 36.0, (16.0 - Math.sqrt(6.0)) / 36.0 };
    public static Double[]c={ 0.0, (6.0 - Math.sqrt(6.0)) / 10.0, (6.0 + Math.sqrt(6.0)) / 10.0 };
    public RADAUIA()
    {
        super.a=RADAUIA.a;
        super.b=RADAUIA.b;
        super.c=RADAUIA.c;
        super.stages=3;
    }
}

