package Solvers.Core;

import java.util.List;
import java.util.Vector;

public class Solution {
    List<Double[]> states;
    Vector<Double> t;
    public void add(double t,Double []y)
    {
        this.states.add(y);
        this.t.add(t);
    }
}
