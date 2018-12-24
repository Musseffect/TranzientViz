package Solvers.Inputs;

import Solvers.IInputFunction;

public class PulseInput implements IInputFunction {
    Double width;
    Double dutyCycle;
    Double amp;
    Double shift;
    Double phase;
    public PulseInput()
    {
        width=1.0;
        dutyCycle=0.5;
        amp=1.0;
        shift=0.0;
        phase=0.0;
    }
    public void Init(Double width,Double dutyCycle,Double amp,Double shift,Double phase)
    {
        this.width=width;
        this.dutyCycle=dutyCycle;
        this.amp=amp;
        this.shift=shift;
        this.phase=phase;
    }
    @Override
    public Double f(double t) {
        return ((t+phase)%width)<=(dutyCycle*width)?amp+shift:shift;
    }
}
