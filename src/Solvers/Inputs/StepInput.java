package Solvers.Inputs;

import Solvers.IInputFunction;

public class StepInput implements IInputFunction {
    Double amp;
    Double shift;
    Double phase;
    public StepInput()
    {
        amp=1.0;
        shift=0.0;
        phase=0.0;
    }
    public void Init(Double amp,Double shift,Double phase)
    {
        this.amp=amp;
        this.shift=shift;
        this.phase=phase;
    }
    protected Double step(double t)
    {
        if(t>-phase)
            return 1.0;
        return 0.0;
    }
    @Override
    public Double f(double t) {
        return step(t)*amp+shift;
    }
}
