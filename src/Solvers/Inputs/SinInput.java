package Solvers.Inputs;

import Solvers.IInputFunction;

public class SinInput implements IInputFunction {
    Double phase;
    Double amp;
    Double shift;
    Double frequency;
    public SinInput()
    {
        phase=0.0;
        amp=1.0;
        frequency=1.0;
        shift=0.0;
    }
    public void Init(Double phase,Double amp,Double shift,Double frequency)
    {
        this.phase=phase;
        this.amp=amp;
        this.frequency=frequency;
        this.shift=shift;
    }
    @Override
    public Double f(double t) {
        return shift+amp*Math.sin(frequency*t+phase);
    }
}
