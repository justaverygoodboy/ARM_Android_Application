package com.example.myarmapp;

import java.util.List;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint.Align;

public class ChartService {
    private GraphicalView mGraphicalView;
    private XYMultipleSeriesDataset multipleSeriesDataset;// 数据集容器
    private XYMultipleSeriesRenderer multipleSeriesRenderer;// 渲染器容器
    private XYSeries mSeries;// 单条曲线数据集
    private XYSeriesRenderer mRenderer;// 单条曲线渲染器
    private Context context;

    public ChartService(Context context) {
        this.context = context;
    }

    public GraphicalView getGraphicalView() {
        mGraphicalView = ChartFactory.getCubeLineChartView(context,
                multipleSeriesDataset, multipleSeriesRenderer, 0.1f);
        return mGraphicalView;
    }

    public void setXYMultipleSeriesDataset(String curveTitle) {
        multipleSeriesDataset = new XYMultipleSeriesDataset();
        mSeries = new XYSeries(curveTitle);
        multipleSeriesDataset.addSeries(mSeries);
    }

    public void setXYMultipleSeriesRenderer(double maxX, double maxY,
                                            String chartTitle, String xTitle, String yTitle, int axeColor,
                                            int labelColor, int curveColor, int gridColor) {
        multipleSeriesRenderer = new XYMultipleSeriesRenderer();
        if (chartTitle != null) {
            multipleSeriesRenderer.setChartTitle(chartTitle);
        }
        multipleSeriesRenderer.setXTitle(xTitle);
        multipleSeriesRenderer.setYTitle(yTitle);
        multipleSeriesRenderer.setRange(new double[] { 0, maxX, 0, maxY });//xy轴的范围
        multipleSeriesRenderer.setLabelsColor(labelColor);
        multipleSeriesRenderer.setXLabels(10);
        multipleSeriesRenderer.setYLabels(10);
        multipleSeriesRenderer.setXLabelsAlign(Align.RIGHT);
        multipleSeriesRenderer.setYLabelsAlign(Align.RIGHT);
        multipleSeriesRenderer.setAxisTitleTextSize(20);
        multipleSeriesRenderer.setChartTitleTextSize(20);
        multipleSeriesRenderer.setLabelsTextSize(20);
        multipleSeriesRenderer.setLegendTextSize(20);
        multipleSeriesRenderer.setPointSize(2f);//曲线描点尺寸
        multipleSeriesRenderer.setFitLegend(true);
        multipleSeriesRenderer.setMargins(new int[] { 20, 30, 15, 20 });
        multipleSeriesRenderer.setShowGrid(true);
        multipleSeriesRenderer.setZoomEnabled(true, false);
        multipleSeriesRenderer.setAxesColor(axeColor);
        multipleSeriesRenderer.setGridColor(gridColor);
        multipleSeriesRenderer.setBackgroundColor(Color.WHITE);//背景色
        multipleSeriesRenderer.setMarginsColor(Color.WHITE);//边距背景色，默认背景色为黑色，这里修改为白色
        mRenderer = new XYSeriesRenderer();
        mRenderer.setColor(curveColor);
        mRenderer.setPointStyle(PointStyle.CIRCLE);//描点风格，可以为圆点，方形点等等
        multipleSeriesRenderer.addSeriesRenderer(mRenderer);
    }

    public void updateChart(double x, double y) {
        mSeries.add(x, y);
        mGraphicalView.repaint();//此处也可以调用invalidate()
    }

    public void updateChart(List<Double> xList, List<Double> yList) {
        for (int i = 0; i < xList.size(); i++) {
            mSeries.add(xList.get(i), yList.get(i));
        }
        mGraphicalView.repaint();//此处也可以调用invalidate()
    }
}