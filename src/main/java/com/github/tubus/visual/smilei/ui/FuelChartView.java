package com.github.tubus.visual.smilei.ui;

import com.github.tubus.visual.smilei.service.FuelServiceImpl;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.*;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.timepicker.TimePicker;
import com.vaadin.flow.router.Route;
import java.time.LocalDateTime;

@Route("FuelTable")
public class FuelChartView extends VerticalLayout {

    FuelServiceImpl fuelService = new FuelServiceImpl();

    private Integer deviceId = 669;
    private Integer operatorId = 1001;
    private LocalDateTime fromTime = LocalDateTime.of(2020, 3, 1, 10, 0, 0);
    private LocalDateTime toTime = LocalDateTime.of(2020, 4, 1, 15, 0, 0);
    private Chart previousChart;

    public FuelChartView() {
        HorizontalLayout header = new HorizontalLayout();
        Label headText = new Label("Служебный сервис для тестирования сглаживания сырых данных по топливу для устройтва");
        header.add(headText);
        HorizontalLayout deviceInputs = new HorizontalLayout();

        NumberField deviceIdInput = new NumberField("DeviceId");
        deviceIdInput.setValue(669.);
        deviceIdInput.addValueChangeListener(event -> deviceId = event.getValue().intValue());

        NumberField operatorIdInput = new NumberField("OperatorId");
        operatorIdInput.setValue(1001.);
        operatorIdInput.addValueChangeListener(event -> operatorId = event.getValue().intValue());

        DatePicker fromDateField = new DatePicker("fromDate");
        fromDateField.addValueChangeListener(event -> fromTime = fromTime.with(event.getValue()));

        TimePicker fromTimeField = new TimePicker("fromTime");
        fromTimeField.addValueChangeListener(event -> fromTime = fromTime.with(event.getValue()));

        DatePicker toDateField = new DatePicker("toDate");
        toDateField.addValueChangeListener(event -> toTime = toTime.with(event.getValue()));

        TimePicker toTimeField = new TimePicker("toTime");
        toTimeField.addValueChangeListener(event -> toTime = toTime.with(event.getValue()));

        Button okButton = new Button("OK");
        okButton.addClickListener(event -> {
            Chart chart = createChart();
            if (previousChart != null) {
                replace(previousChart, chart);
            } else {
                add(chart);
            }
            previousChart = chart;
        });

        deviceInputs.add(deviceIdInput, operatorIdInput, fromDateField, fromTimeField, toDateField, toTimeField, okButton);


        add(header, deviceInputs);
    }
    
    private Chart createChart() {
        Chart chart = new Chart();

        Configuration configuration = chart.getConfiguration();
        //configuration.getChart().setType(ChartType.LINE);

        configuration.getTitle()
                .setText("Данные по топливу для устроства ");
        //configuration.getSubTitle().setText("Source: WorldClimate.com");

        //configuration.getxAxis().setCategories("Jan", "Feb", "Mar", "Apr",
        //        "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec");

        YAxis yAxis = configuration.getyAxis();
        yAxis.setTitle(new AxisTitle("Fuel Value"));

        //configuration
        //        .getTooltip()
        //        .setFormatter(
        //                "'<b>'+ this.series.name +'</b><br/>'+this.x +': '+ this.y +'°C'");

        //PlotOptionsLine plotOptions = new PlotOptionsLine();
        //plotOptions.setEnableMouseTracking(false);
        //configuration.setPlotOptions(plotOptions);

/*        DataSeries ds = new DataSeries();
        ds.setName("Tokyo");
        ds.setData(7.0, 6.9, 9.5, 14.5, 18.2, 21.5, 25.2, 26.5, 23.3, 18.3,
                13.9, 9.6);
        DataLabels callout = new DataLabels(true);
        callout.setShape(Shape.CALLOUT);
        callout.setY(-12);
        ds.get(5).setDataLabels(callout);
        configuration.addSeries(ds);

        ds = new DataSeries();
        ds.setName("London");
        ds.setData(3.9, 4.2, 5.7, 8.5, 11.9, 15.2, 17.0, 16.6, 14.2, 10.3, 6.6,
                4.8);
        ds.get(6).setDataLabels(callout);
        configuration.addSeries(ds);*/

        fuelService.getFuel(operatorId, deviceId, fromTime, toTime)
                .forEach(configuration::setSeries);

        RangeSelector rangeSelector = new RangeSelector();
        rangeSelector.setSelected(2);
        configuration.setRangeSelector(rangeSelector);

        chart.setTimeline(true);
        return chart;
    }
}