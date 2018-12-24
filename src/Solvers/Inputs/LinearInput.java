package Solvers.Inputs;

import Solvers.IInputFunction;

public class LinearInput implements IInputFunction {
    Double slope;
    Double shift;
    public LinearInput()
    {
        slope=1.0;
        shift=0.0;
    }
    public void Init(Double slope,Double shift) {
        this.slope = slope;
        this.shift = shift;
    }

    @Override
    public Double f(double t) {
        return shift+slope*t;
    }
}
