package Solvers;

import Solvers.Math.MathVector;
import Solvers.Math.Matrix;
import javafx.scene.control.Alert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TransferFunction {
    List<Double> num;
    List<Double> denum;
    public boolean validate()
    {
        return num.size()<=denum.size();
    }
    public void set(Double[] num,Double[] denum)
    {
        this.num=new ArrayList<Double>(Arrays.asList(num));
        this.denum=new ArrayList<Double>(Arrays.asList(denum));
    }
    public void parse(String num,String denum) throws Exception
    {
        this.num=new ArrayList<Double>();
        this.denum=new ArrayList<Double>();
        try {
            String[] array=num.split("\\s+");
            for(String item:array)
            {
                this.num.add(0,Double.parseDouble(item));
            }
        }
        catch(NumberFormatException exception)
        {
            Alert alert=new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Alert");
            alert.setContentText("numerator cannot be parsed");
            alert.show();
            throw new Exception("numerator cannot be parsed");
        }
        try {
            String[] array=denum.split("\\s+");
            for(String item:array)
            {
                this.denum.add(0,Double.parseDouble(item));
            }
        }
        catch(NumberFormatException exception)
        {
            Alert alert=new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Alert");
            alert.setContentText("denumerator cannot be parsed");
            alert.show();
            throw new Exception("denumerator cannot be parsed");
        }
        if(this.denum.size()==0)
            throw new Exception("zero length transfer function");
    }
    public StateSpace getStateSpace()
    {
        int denumSize=denum.size();
        int numSize=num.size();
        for(int i=numSize;i<denumSize;i++)
            num.add(0.0);
        StateSpace result=new StateSpace();
        Double bn=num.get(denumSize-1);
        Double an=denum.get(denumSize-1);
        result.d=bn/an;
        result.c=new MathVector(denumSize-1);
        if(denumSize>1)
            result.c.set(0,1.0/an);
        result.b=new MathVector(denumSize-1);
        for(int i=0;i<denumSize-1;i++)
        {
            result.b.set(i,num.get(denumSize-i-2)-result.d*denum.get(denumSize-i-2));
        }
        result.A=new Matrix(denumSize-1,denumSize-1);
        for(int i=1;i<denumSize-1;i++)
        {
            result.A.set(i,i-1,1.0);
        }
        for(int j=0;j<denumSize-1;j++)
        {
            result.A.set(0,j,-denum.get(denumSize-2-j)/an);
        }
        return result;
    }
}
