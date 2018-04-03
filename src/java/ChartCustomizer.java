import net.sf.jasperreports.engine.JRChart;
import net.sf.jasperreports.engine.JRChartCustomizer;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;

public class ChartCustomizer implements JRChartCustomizer{
	public void customize(JFreeChart chart, JRChart jasperChart){
		XYPlot plot = chart.getXYPlot();

		ValueAxis rangeAxis = plot.getRangeAxis();

		rangeAxis.setMinorTickCount(4);
		rangeAxis.setMinorTickMarkOutsideLength(1.0f);
		rangeAxis.setMinorTickMarksVisible(true);

		rangeAxis.setTickMarkOutsideLength(2.5f);
	}
}