package Solvers;

import Solvers.Core.ODESolver;
import Solvers.Math.MathVector;

import java.util.ArrayList;
import java.util.List;

public class StateSpaceSolver {
    public static List<XYData> getSolution(StateSpace ss, IInputFunction input, ODESolver solver, double t1, double t2) throws Exception
    {
        if(ss.c.getLength()==0)
        {
            double step=solver.getStep();
            List<XYData> array=new ArrayList<>();
            double yOut=ss.d*input.f(t1);
            array.add(new XYData(t1,yOut));
            double t;
            for(t=t1;t<=t2;t+=step)
            {
                yOut=ss.d*input.f(t);
                array.add(new XYData(t,yOut));
            }
            yOut=ss.d*input.f(t);
            array.add(new XYData(t,yOut));
            return array;
        }
        List<XYData> array=new ArrayList<>();
        double step=0;
        MathVector y=new MathVector(ss.b.getLength());
        double yOut=ss.c.dot(y)+ss.d*input.f(t1);
        array.add(new XYData(t1,yOut));
        StateSpaceODESystem system=new StateSpaceODESystem();
        system.Init(ss.A,ss.b,input);
        solver.setSystem(system);
        for(double t=t1;t<=t2;t+=step)
        {
            y=solver.step(y,t);
            step=solver.getStep();
            yOut=ss.c.dot(y)+ss.d*input.f(t);
            array.add(new XYData(t,yOut));
        }
        return array;
    }
}
