package sample;

import Solvers.Core.ODESolver;
import Solvers.Exceptions.GaussDivergenceException;
import Solvers.Exceptions.NewtonDivergenceException;
import Solvers.ExplicitSolvers.*;
import Solvers.ExplicitSolvers.ExplicitRK.*;
import Solvers.ImplicitSolvers.ImplicitRK.*;
import Solvers.ImplicitSolvers.*;
import Solvers.IInputFunction;
import Solvers.Inputs.*;
import Solvers.StateSpaceSolver;
import Solvers.TransferFunction;
import Solvers.XYData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.fx.ChartViewer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultValueDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

enum SolverType
{
    Explicit,
    ExplicitAutoStep,
    Implicit
}
enum InputType
{
    Delta,
    Heaviside,
    Sin,
    Pulse,
    Linear
}
enum UnitType
{
    Gain,
    Integrating,
    Aperiodic,
    Oscillating,
    InertialIntegrating,
    InertialDifferentiating,
    Custom
}


public class Controller {
    @FXML
    private ChartViewer fxchart;
    @FXML
    private Spinner<Double> t0Spinner;
    @FXML
    private Spinner<Double> t1Spinner;
    @FXML
    private ComboBox inputComboBox;

    @FXML
    private VBox inputParameters;
    @FXML
    private ComboBox unitComboBox;

    @FXML
    private VBox unitParameters;
    @FXML
    private ComboBox solverComboBox;

    @FXML
    private VBox solverParameters;

    @FXML
    private FlowPane solverPane;

    private Map<String,Control> solversElements;

    @FXML
    private void onInputSelection(ActionEvent event)
    {
        int inputIndex=inputComboBox.getSelectionModel().getSelectedIndex();
        InputType []types={InputType.Delta,InputType.Heaviside,InputType.Sin,InputType.Pulse,InputType.Linear};
        createInputControls(types[inputIndex]);
    }
    @FXML
    private void onUnitSelection(ActionEvent event)
    {
        int unitIndex=unitComboBox.getSelectionModel().getSelectedIndex();
        UnitType []types={UnitType.Gain,UnitType.Integrating,UnitType.Aperiodic,UnitType.Oscillating,UnitType.InertialIntegrating,UnitType.InertialDifferentiating,UnitType.Custom};
        createUnitControls(types[unitIndex]);
    }
    private void setSolverParameters(ODESolver solver,SolverType type)
    {
        double value = ((Spinner<Double>) solversElements.get("Step")).getValue();
        solver.setStep(value);
        switch(type) {
            case Explicit: {
                break;
            }
            case Implicit:{
                ImplicitSolver tsolver = (ImplicitSolver) solver;
                int intvalue = ((Spinner<Integer>) solversElements.get("NewtonIterations")).getValue();
                tsolver.setMaxIterations(intvalue);
                value=((Spinner<Double>) solversElements.get("MaxAbsError")).getValue();
                tsolver.setMaxAbsError(value);
            }
                break;
            case ExplicitAutoStep:{
                ExplicitAutoStep tsolver = (ExplicitAutoStep) solver;
                value=((Spinner<Double>) solversElements.get("MaxStep")).getValue();
                tsolver.setMaxStep(value);
                value=((Spinner<Double>) solversElements.get("ErrTol")).getValue();
                tsolver.setErrorTol(value);
            }
                break;
            default:
                return;
        }
    }
    private void createUnitControls(UnitType type)
    {
        unitParameters.getChildren().clear();
        switch(type)
        {
            case Gain: {
                VBox gain = new VBox();
                Label label = new Label();
                label.setText("Усиление");
                Spinner<Double> control = new Spinner<>(-Double.MAX_VALUE, Double.MAX_VALUE, 1.0, 1.0);
                control.setId("gain");
                control.setEditable(true);
                gain.getChildren().addAll(label, control);

                unitParameters.getChildren().addAll(gain);
                break;
            }
            case Aperiodic:{
                VBox gain = new VBox();
                Label label = new Label();
                label.setText("Усиление");
                Spinner<Double> control = new Spinner<>(-Double.MAX_VALUE, Double.MAX_VALUE, 1.0, 1.0);
                control.setId("gain");
                control.setEditable(true);
                gain.getChildren().addAll(label, control);

                VBox period = new VBox();
                label = new Label();
                label.setText("Постоянная времени");
                control = new Spinner<>(-Double.MAX_VALUE, Double.MAX_VALUE, 1.0, 1.0);
                control.setId("period");
                control.setEditable(true);
                gain.getChildren().addAll(label, control);

                unitParameters.getChildren().addAll(gain,period);
                break;
            }
            case Integrating:{
                VBox gain = new VBox();
                Label label = new Label();
                label.setText("Усиление");
                Spinner<Double> control = new Spinner<>(-Double.MAX_VALUE, Double.MAX_VALUE, 1.0, 1.0);
                control.setId("gain");
                control.setEditable(true);
                gain.getChildren().addAll(label, control);

                unitParameters.getChildren().addAll(gain);
                break;
            }
            case Oscillating:{
                VBox gain = new VBox();
                Label label = new Label();
                label.setText("Усиление");
                Spinner<Double> control = new Spinner<>(-Double.MAX_VALUE, Double.MAX_VALUE, 1.0, 1.0);
                control.setId("gain");
                control.setEditable(true);
                gain.getChildren().addAll(label, control);

                VBox period = new VBox();
                label = new Label();
                label.setText("Постоянная времени");
                control = new Spinner<>(0.0, Double.MAX_VALUE, 1.0, 1.0);
                control.setId("period");
                control.setEditable(true);
                gain.getChildren().addAll(label, control);

                VBox damping = new VBox();
                label = new Label();
                label.setText("Демпфирование");
                control = new Spinner<>(-Double.MAX_VALUE, Double.MAX_VALUE, 1.0, 1.0);
                control.setId("damping");
                control.setEditable(true);
                gain.getChildren().addAll(label, control);

                unitParameters.getChildren().addAll(gain,period,damping);
                break;
            }
            case InertialIntegrating:{
                VBox gain = new VBox();
                Label label = new Label();
                label.setText("Усиление");
                Spinner<Double> control = new Spinner<>(-Double.MAX_VALUE, Double.MAX_VALUE, 1.0, 1.0);
                control.setId("gain");
                control.setEditable(true);
                gain.getChildren().addAll(label, control);

                VBox period = new VBox();
                label = new Label();
                label.setText("Постоянная времени");
                control = new Spinner<>(-Double.MAX_VALUE, Double.MAX_VALUE, 1.0, 1.0);
                control.setId("period");
                control.setEditable(true);
                gain.getChildren().addAll(label, control);

                unitParameters.getChildren().addAll(gain,period);
                break;
            }
            case InertialDifferentiating:{
                VBox gain = new VBox();
                Label label = new Label();
                label.setText("Усиление");
                Spinner<Double> control = new Spinner<>(-Double.MAX_VALUE, Double.MAX_VALUE, 1.0, 1.0);
                control.setId("gain");
                control.setEditable(true);
                gain.getChildren().addAll(label, control);

                VBox period = new VBox();
                label = new Label();
                label.setText("Постоянная времени");
                control = new Spinner<>(-Double.MAX_VALUE, Double.MAX_VALUE, 1.0, 1.0);
                control.setId("period");
                control.setEditable(true);
                gain.getChildren().addAll(label, control);

                unitParameters.getChildren().addAll(gain,period);
                break;
            }
            case Custom:{
                VBox numerator=new VBox();
                Label label=new Label();
                label.setText("Коэффициенты числителя");
                TextField control=new TextField();
                control.setMaxWidth(Region.USE_PREF_SIZE);
                control.setText("1");
                control.setId("numerator");
                control.setEditable(true);
                numerator.getChildren().addAll(label,control);

                VBox denominator=new VBox();
                label=new Label();
                label.setText("Коэффициенты знаменателя");
                control=new TextField();
                control.setMaxWidth(Region.USE_PREF_SIZE);
                control.setText("1");
                control.setId("denominator");
                control.setEditable(true);
                denominator.getChildren().addAll(label,control);

                unitParameters.getChildren().addAll(numerator,denominator);
                break;
            }
            default:
                return;
        }
    }
    private void createInputControls(InputType type)
    {
        inputParameters.getChildren().clear();
        switch(type) {
            case Delta:
                break;
            case Sin: {
                VBox phase=new VBox();
                Label label=new Label();
                label.setText("Фаза");
                Spinner<Double> control = new Spinner<>(-Double.MAX_VALUE,Double.MAX_VALUE,0.0,1.0);
                control.setId("phase");
                control.setEditable(true);
                phase.getChildren().addAll(label,control);

                VBox amp=new VBox();
                label=new Label();
                label.setText("Амплитуда");
                control = new Spinner<>(-Double.MAX_VALUE,Double.MAX_VALUE,1.0,1.0);
                control.setId("amp");
                control.setEditable(true);
                amp.getChildren().addAll(label,control);

                VBox shift=new VBox();
                label=new Label();
                label.setText("Вертикальный сдвиг");
                control = new Spinner<>(-Double.MAX_VALUE,Double.MAX_VALUE,0.0,1.0);
                control.setId("shift");
                control.setEditable(true);
                shift.getChildren().addAll(label,control);

                VBox frequency=new VBox();
                label=new Label();
                label.setText("Частота");
                control = new Spinner<>(-Double.MAX_VALUE,Double.MAX_VALUE,1.0,1.0);
                control.setId("frequency");
                control.setEditable(true);
                frequency.getChildren().addAll(label,control);

                inputParameters.getChildren().addAll(phase,amp,shift,frequency);
                break;
            }
            case Pulse:{
                VBox width=new VBox();
                Label label=new Label();
                label.setText("Период");
                Spinner<Double> control = new Spinner<>(0,Double.MAX_VALUE,1.0,1.0);
                control.setId("width");
                control.setEditable(true);
                width.getChildren().addAll(label,control);

                VBox shift=new VBox();
                label=new Label();
                label.setText("Вертикальный сдвиг");
                control = new Spinner<>(-Double.MAX_VALUE,Double.MAX_VALUE,0.0,1.0);
                control.setId("shift");
                control.setEditable(true);
                shift.getChildren().addAll(label,control);

                VBox dutyCycle=new VBox();
                label=new Label();
                label.setText("Коэффициент заполнения");
                control = new Spinner<>(0.0,1.0,0.5,1.0);
                control.setId("dutyCycle");
                control.setEditable(true);
                dutyCycle.getChildren().addAll(label,control);

                VBox amp=new VBox();
                label=new Label();
                label.setText("Амплитуда");
                control = new Spinner<>(0.0,Double.MAX_VALUE,1.0,1.0);
                control.setId("amp");
                control.setEditable(true);
                amp.getChildren().addAll(label,control);

                VBox phase=new VBox();
                label=new Label();
                label.setText("Фаза");
                control = new Spinner<>(-Double.MAX_VALUE,Double.MAX_VALUE,0.0,1.0);
                control.setId("phase");
                control.setEditable(true);
                phase.getChildren().addAll(label,control);

                inputParameters.getChildren().addAll(width,dutyCycle,amp,phase,shift);
                break;
            }
            case Linear: {
                VBox slope=new VBox();
                Label label=new Label();
                label.setText("Наклон");
                Spinner<Double> control = new Spinner<>(-Double.MAX_VALUE,Double.MAX_VALUE,1.0,1.0);
                control.setId("slope");
                control.setEditable(true);
                slope.getChildren().addAll(label,control);

                VBox shift=new VBox();
                label=new Label();
                label.setText("Вертикальный сдвиг");
                control = new Spinner<>(-Double.MAX_VALUE,Double.MAX_VALUE,0.0,1.0);
                control.setId("shift");
                control.setEditable(true);
                shift.getChildren().addAll(label,control);

                inputParameters.getChildren().addAll(slope,shift);
                break;
            }
            case Heaviside:{
                VBox amp=new VBox();
                Label label=new Label();
                label.setText("Амплитуда");
                Spinner<Double> control = new Spinner<>(-Double.MAX_VALUE,Double.MAX_VALUE,1.0,1.0);
                control.setId("amp");
                control.setEditable(true);
                amp.getChildren().addAll(label,control);

                VBox phase=new VBox();
                label=new Label();
                label.setText("Фаза");
                control = new Spinner<>(-Double.MAX_VALUE,Double.MAX_VALUE,0.0,1.0);
                control.setId("phase");
                control.setEditable(true);
                phase.getChildren().addAll(label,control);

                VBox shift=new VBox();
                label=new Label();
                label.setText("Вертикальный сдвиг");
                control = new Spinner<>(-Double.MAX_VALUE,Double.MAX_VALUE,0.0,1.0);
                control.setId("shift");
                control.setEditable(true);
                shift.getChildren().addAll(label,control);

                inputParameters.getChildren().addAll(amp,phase,shift);
                break;
            }
            default:
                return;
        }

    }
    private void createSolverControls(SolverType type)
    {
        solverPane.getChildren().clear();
        VBox vbox;
        Label label;
        Spinner<Double> control;
        vbox=new VBox();
        label=new Label();
        label.setText("Шаг, мс");
        control=(Spinner<Double>)solversElements.get("Step");
        vbox.getChildren().addAll(label,control);
        solverPane.getChildren().add(vbox);
        switch(type)
        {
            case Explicit:
                break;

            case Implicit:
                vbox=new VBox();
                label=new Label();
                label.setText("Кол-во итераций");
                Spinner<Integer> intcontrol=(Spinner<Integer>)solversElements.get("NewtonIterations");
                vbox.getChildren().addAll(label,intcontrol);
                solverPane.getChildren().add(vbox);

                vbox=new VBox();
                label=new Label();
                label.setText("Допустимая абс. ошибка метода Ньютона");
                control=(Spinner<Double>)solversElements.get("MaxAbsError");
                vbox.getChildren().addAll(label,control);
                solverPane.getChildren().add(vbox);
                break;

            case ExplicitAutoStep:

                vbox=new VBox();
                label=new Label();
                label.setText("Максимальный шаг");
                control=(Spinner<Double>)solversElements.get("MaxStep");
                vbox.getChildren().addAll(label,control);
                solverPane.getChildren().add(vbox);

                vbox=new VBox();
                label=new Label();
                label.setText("Допустимая ошибка");
                control=(Spinner<Double>)solversElements.get("ErrTol");
                vbox.getChildren().addAll(label,control);
                solverPane.getChildren().add(vbox);
                break;
            default:
                return;
        }
    }

    @FXML
    private void onSolverChange(ActionEvent event)
    {
        int solverIndex=solverComboBox.getSelectionModel().getSelectedIndex();
        SolverType []types={SolverType.ExplicitAutoStep,SolverType.Explicit,SolverType.Explicit,SolverType.Implicit,SolverType.Implicit,SolverType.Explicit,SolverType.Implicit,SolverType.Explicit};
        createSolverControls(types[solverIndex]);
    }
    @FXML
    private void onClickMethod(ActionEvent event)
    {
        IInputFunction input=createInput();
        if(input==null)
            return;

        int unitIndex=unitComboBox.getSelectionModel().getSelectedIndex();

        TransferFunction tf=null;
        try {
            tf = createTransferFunction();
        }catch(Exception exc)
        {
            return;
        }
        if(tf.validate()!=true)
        {
            Alert alert=new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Alert");
            alert.setContentText("Impossible system");
            alert.show();
            return;
        }
        ODESolver solver=createSolver();
        Double t0=t0Spinner.getValue();
        Double t1=t1Spinner.getValue();
        if(t1<t0)
        {
            Alert alert=new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Alert");
            alert.setContentText("Начальное время должно быть не больше конечного.");
            alert.show();
            return;
        }
        if(solver==null)
            return;
        List<XYData> data;
        Double step=solver.getStep();
        try {
            data = StateSpaceSolver.getSolution(tf.getStateSpace(), input, solver, t0, t1);
        }catch(Exception exc)
        {
            Alert alert=new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Alert");
            if(exc instanceof NewtonDivergenceException)
            {
                alert.setContentText("NewtonDivergence");
            }else if(exc instanceof GaussDivergenceException){
                alert.setContentText("GaussDivergence");
            }
            alert.setContentText(exc.getStackTrace()[0].getMethodName());
            alert.show();
            return;
        }/*
        XYChart.Series<Number,Number> outputSeries=new XYChart.Series<Number,Number>();
        outputSeries.setName("Выходной сигнал");
        List<XYChart.Data<Number,Number>> outputSeriesData=new ArrayList<>();
        for(XYData item: data)
        {
            outputSeriesData.add(new XYChart.Data<Number,Number>(item.x,item.y));
        List<XYChart.Data<Number,Number>> inputSeriesData=new ArrayList<>();
        for(Double t=t0;t<t1;t+=step)
        {
            inputSeriesData.add(new XYChart.Data<Number,Number>(t,input.f(t)));
        }
        XYChart.Series<Number,Number> inputSeries=new XYChart.Series<Number,Number>();
        inputSeries.setName("Входной сигнал");

        outputSeries.setData(FXCollections.<XYChart.Data<Number,Number>>observableArrayList(outputSeriesData));
        inputSeries.setData(FXCollections.<XYChart.Data<Number,Number>>observableArrayList(inputSeriesData));
        chart.setData(FXCollections.<XYChart.Series<Number,Number>>observableArrayList(outputSeries,inputSeries));
        */
        XYSeriesCollection dataset = new XYSeriesCollection();
        XYSeries outputSeries=new XYSeries("Выходной сигнал");
        for(XYData item: data)
        {
            outputSeries.add(item.x,item.y);
        }
        XYSeries inputSeries=new XYSeries("Входной сигнал");
        if(input instanceof DeltaInput)
        {
            inputSeries.add(t0, (Number) 0);
            inputSeries.add(t1, (Number)0);
            if(t0<=0&&t1>=0)
            {
                inputSeries.add(0,1);
                inputSeries.add(step,(Number)0);
            }
        }else
        {
            for(Double t=t0;t<t1;t+=step)
            {
                inputSeries.add(t,input.f(t));
            }
        }
        dataset.addSeries(outputSeries);
        dataset.addSeries(inputSeries);

        /*
        XYPlot xyPlot = jfreechart.getXYPlot();
        ValueAxis domainAxis = xyPlot.getDomainAxis();
        ValueAxis rangeAxis = xyPlot.getRangeAxis();

        domainAxis.setRange(0.0, 1.0);
        domainAxis.setTickUnit(new NumberTickUnit(0.1));
        rangeAxis.setRange(0.0, 1.0);
        rangeAxis.setTickUnit(new NumberTickUnit(0.05));
        */
        JFreeChart chart = ChartFactory.createXYLineChart("","Время","",dataset);
        fxchart.setChart(chart);
    }
    @FXML
    private void initialize() {

        solversElements=new HashMap<>();
        Spinner<Double> control;
        SpinnerValueFactory.DoubleSpinnerValueFactory dblfactory;

        control=new Spinner<Double>(0.0001,10.0,0.01,0.01);
        control.setEditable(true);
        solversElements.put("Step",control);
        control=new Spinner<>(0.0001,10.0,0.01,0.01);
        control.setEditable(true);
        solversElements.put("MaxStep",control);
        control=new Spinner<>(0.0001,10.0,0.01,0.01);
        control.setEditable(true);
        solversElements.put("ErrTol",control);
        Spinner<Integer> intcontrol=new Spinner<>(1,40,20,5);
        intcontrol.setEditable(true);
        solversElements.put("NewtonIterations",intcontrol);
        control=new Spinner<>(0.0001,10.0,0.01,0.01);
        control.setEditable(true);
        solversElements.put("MaxAbsError",control);

        ObservableList<String> inputOptions =
                FXCollections.observableArrayList(
                        "Дельта функция",
                        "Функция Хевисайда",
                        "Синус",
                        "Периодические импульсы",
                        "Линейная функция"
                );
        inputComboBox.setItems(inputOptions);
        inputComboBox.getSelectionModel().select(0);
        onInputSelection(new ActionEvent());

        ObservableList<String> unitOptions =
                FXCollections.observableArrayList(
                        "Усилительное звено",
                        "Интегрирующее звено",
                        "Апериодическое звено",
                        "Колебательное звено",
                        "Инерциальное интегрирующее звено",
                        "Инерциальное дифференцирующее звено",
                        "Произвольная передаточная функция"
                );
        unitComboBox.setItems(unitOptions);
        unitComboBox.getSelectionModel().select(0);
        onUnitSelection(new ActionEvent());

        ObservableList<String> solverOptions =FXCollections.observableArrayList(
                "Дормунд-Принс 57 (явный,вар. шаг)",
                "Рунге-Кутта 4 (явный)",
                "Рунге-Кутта 6 (явный)",
                "Радо IA (неявный)",
                "Лобато IIIC (неявный)",
                "Метод Эйлера (явный)",
                "Метод Эйлера (неявный)",
                "Метод средней точки (явный)"
                );
        solverComboBox.setItems(solverOptions);
        solverComboBox.getSelectionModel().select(0);
        onSolverChange(new ActionEvent());
        JFreeChart chart = ChartFactory.createLineChart("","Время","",null);
        fxchart.setChart(chart);
    }

    private TransferFunction createTransferFunction() throws Exception
    {
        int unitIndex=unitComboBox.getSelectionModel().getSelectedIndex();

        TransferFunction tf=new TransferFunction();
        switch(unitIndex) {
            case 0: {
                //"Усилительное звено"
                Double gain = ((Spinner<Double>) unitParameters.lookup("#gain")).getValue();
                tf.set(new Double[]{gain}, new Double[]{1.0});
                break;
            }
            case 1: {
                //"Интегрирующее звено"
                Double gain = ((Spinner<Double>) unitParameters.lookup("#gain")).getValue();
                tf.set(new Double[]{gain}, new Double[]{0.0,1.0});
                break;
            }
            case 2: {
                //"Апериодическое звено"
                Double gain = ((Spinner<Double>) unitParameters.lookup("#gain")).getValue();
                Double period = ((Spinner<Double>) unitParameters.lookup("#period")).getValue();
                tf.set(new Double[]{gain}, new Double[]{1.0,period});
                break;
            }
            case 3: {
                //"Колебательное звено"
                Double gain = ((Spinner<Double>) unitParameters.lookup("#gain")).getValue();
                Double period = ((Spinner<Double>) unitParameters.lookup("#period")).getValue();
                Double damping = ((Spinner<Double>) unitParameters.lookup("#damping")).getValue();
                tf.set(new Double[]{gain}, new Double[]{1.0,2.0*damping*period,period*period});
                break;
            }
            case 4: {
                //"Инерциальное интегрирующее звено"
                Double gain = ((Spinner<Double>) unitParameters.lookup("#gain")).getValue();
                Double period = ((Spinner<Double>) unitParameters.lookup("#period")).getValue();
                tf.set(new Double[]{gain}, new Double[]{0.0,1.0,period});
                break;
            }
            case 5: {
                //"Инерциальное дифференцирующее звено"
                Double gain = ((Spinner<Double>) unitParameters.lookup("#gain")).getValue();
                Double period = ((Spinner<Double>) unitParameters.lookup("#period")).getValue();
                tf.set(new Double[]{0.0,gain}, new Double[]{1.0,period});
                break;
            }
            case 6:{
                //"Произвольная передаточная функция"
                    String num = ((TextField) unitParameters.lookup("#numerator")).getText();
                    String denom = ((TextField) unitParameters.lookup("#denominator")).getText();
                    tf.parse(num, denom);
                break;
            }
        }
        return tf;
    }
    private IInputFunction createInput()
    {
        int inputIndex = inputComboBox.getSelectionModel().getSelectedIndex();
        IInputFunction input=null;
        switch(inputIndex)
        {
            case 0:
                input = new DeltaInput();
                ((DeltaInput) input).Init(((Spinner<Double>)solversElements.get("Step")).getValue()*2.0);
                break;
            case 1: {
                input = new StepInput();
                Double amp = ((Spinner<Double>) inputParameters.lookup("#amp")).getValue();
                Double shift = ((Spinner<Double>) inputParameters.lookup("#shift")).getValue();
                Double phase = ((Spinner<Double>) inputParameters.lookup("#phase")).getValue();
                ((StepInput) input).Init(amp, shift, phase);
            }
            break;
            case 2:{
                Double frequency = ((Spinner<Double>) inputParameters.lookup("#frequency")).getValue();
                Double amp = ((Spinner<Double>) inputParameters.lookup("#amp")).getValue();
                Double shift = ((Spinner<Double>) inputParameters.lookup("#shift")).getValue();
                Double phase = ((Spinner<Double>) inputParameters.lookup("#phase")).getValue();
                input = new SinInput();
                ((SinInput)input).Init(phase,amp,shift,frequency);
                break;
            }
            case 3:{
                Double width = ((Spinner<Double>) inputParameters.lookup("#width")).getValue();
                Double dutyCycle = ((Spinner<Double>) inputParameters.lookup("#dutyCycle")).getValue();
                Double amp = ((Spinner<Double>) inputParameters.lookup("#amp")).getValue();
                Double shift = ((Spinner<Double>) inputParameters.lookup("#shift")).getValue();
                Double phase = ((Spinner<Double>) inputParameters.lookup("#phase")).getValue();
                input=new PulseInput();
                ((PulseInput)input).Init(width,dutyCycle,amp,shift,phase);
                break;
            }
            case 4:{
                Double slope = ((Spinner<Double>) inputParameters.lookup("#slope")).getValue();
                Double shift = ((Spinner<Double>) inputParameters.lookup("#shift")).getValue();
                input=new LinearInput();
                ((LinearInput)input).Init(slope,shift);
                break;
            }
            default:
        }
        return input;
    }
    private ODESolver createSolver()
    {
        int solverIndex=solverComboBox.getSelectionModel().getSelectedIndex();
        ODESolver solver=null;
        switch(solverIndex)
        {
            case 0://Дормунд-Принс 57 (явный,вар. шаг)
                solver=new DOPRI57();
                setSolverParameters(solver,SolverType.ExplicitAutoStep);
                break;
            case 1://Рунге-Кутта 4 (явный)
                solver=new RK4();
                setSolverParameters(solver,SolverType.Explicit);
                break;
            case 2://Рунге-Кутта 6 (явный)
                solver=new RK6();
                setSolverParameters(solver,SolverType.Explicit);
                break;
            case 3://Радо IA (неявный)
                solver=new RADAUIA();
                setSolverParameters(solver,SolverType.Implicit);
                break;
            case 4://Лобато IIIC (неявный)
                solver=new LOBATTOIIIC();
                setSolverParameters(solver,SolverType.Implicit);
                break;
            case 5://Метод Эйлера (явный)
                solver=new ExplicitEulerSolver();
                setSolverParameters(solver,SolverType.Explicit);
                break;
            case 6://Метод Эйлера (неявный)
                solver=new ImplicitEulerSolver();
                setSolverParameters(solver,SolverType.Implicit);
                break;
            case 7://Метод средней точки (явный)
                solver=new MidpointSolver();
                setSolverParameters(solver,SolverType.Explicit);
                break;
            default:
        }
        return solver;
    }
}
