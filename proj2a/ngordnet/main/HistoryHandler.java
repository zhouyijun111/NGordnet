package ngordnet.main;

import ngordnet.hugbrowsermagic.NgordnetQuery;
import ngordnet.hugbrowsermagic.NgordnetQueryHandler;
import ngordnet.ngrams.NGramMap;
import ngordnet.ngrams.TimeSeries;
import ngordnet.plotting.Plotter;
import org.knowm.xchart.XYChart;

import java.util.ArrayList;
import java.util.List;

public class HistoryHandler extends NgordnetQueryHandler {
    private NGramMap ngramSource;

    public HistoryHandler(NGramMap map) {
        this.ngramSource = map;
    }

    @Override
    public String handle(NgordnetQuery q) {
        List<String> words = q.words();
        int startYear = q.startYear();
        int endYear = q.endYear();

        /**create the ArrayList that includes the TimeSeries of all words*/
        ArrayList<TimeSeries> lts = new ArrayList<>();
        for (String word : words) {
            lts.add(this.ngramSource.weightHistory(word, startYear, endYear));
        }

        /**create the ArrayList for words*/
        ArrayList<String> labels = new ArrayList<>();
        for (String word : words) {
            labels.add(word);
        }
        /**generate the chart and code*/
        XYChart chart = Plotter.generateTimeSeriesChart(labels, lts);
        String encodedImage = Plotter.encodeChartAsString(chart);
        return encodedImage;
    }

}
