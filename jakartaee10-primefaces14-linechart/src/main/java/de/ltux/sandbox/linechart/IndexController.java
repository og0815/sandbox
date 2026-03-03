package de.ltux.sandbox.linechart;

import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import java.awt.Color;

import java.io.Serializable;
import software.xdev.chartjs.model.charts.LineChart;
import software.xdev.chartjs.model.data.LineData;
import software.xdev.chartjs.model.dataset.LineDataset;
import software.xdev.chartjs.model.options.LineOptions;
import software.xdev.chartjs.model.options.Plugins;
import software.xdev.chartjs.model.options.Title;
import software.xdev.chartjs.model.options.elements.Fill;

@Named
@ViewScoped
public class IndexController implements Serializable {

    private String lineModel;

    @PostConstruct
    private void init() {
        createLineModel();
    }

    public void createLineModel() {
        lineModel = new LineChart()
                .setData(new LineData()
                        .addDataset(new LineDataset()
                                .setData(65, 59, 80, 81, 56, 55, 40)
                                .setLabel("Dataset One")
                                .setBorderColor(new Color(75, 192, 192))
                                .setLineTension(0.1f)
                                .setFill(new Fill<Boolean>(false)))
                        .addDataset(new LineDataset()
                                .setData(22, 33, 44, 1, null, null, 77)
                                .setLabel("Dataset Two")
                                .setBorderColor(Color.GREEN)
                                .setLineTension(0.1f)
                                .setSpanGaps(true)
                                .setFill(new Fill<Boolean>(false)))
                        .setLabels("January", "February", "March", "April", "May", "June", "July"))
                .setOptions(new LineOptions()
                        .setResponsive(true)
                        .setMaintainAspectRatio(false)
                        .setPlugins(new Plugins()
                                .setTitle(new Title()
                                        .setDisplay(true)
                                        .setText("Line Chart Subtitle")))
                ).toJson();
    }

    public String getLineModel() {
        return lineModel;
    }

}
