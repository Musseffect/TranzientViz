package Solvers.Inputs;

import Solvers.IInputFunction;

public class DeltaInput implements IInputFunction {

    double width;
    public DeltaInput()
    {
        width=0.01;
    }
    public void Init(Double width)
    {
        this.width=width;
    }
    @Override
    public Double f(double t) {
        return t<=width&&t>=0.0?(1.0/width):0;
    }
}
