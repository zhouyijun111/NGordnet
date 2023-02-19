package ngordnet.main;

import ngordnet.hugbrowsermagic.NgordnetQuery;
import ngordnet.hugbrowsermagic.NgordnetQueryHandler;
import ngordnet.ngrams.NGramMap;

import java.util.List;

public class HistoryTextHandler extends NgordnetQueryHandler {
    private NGramMap ngramSource;

    public HistoryTextHandler(NGramMap map) {
        this.ngramSource = map;
    }

    @Override
    public String handle(NgordnetQuery q) {
        List<String> words = q.words();
        int startYear = q.startYear();
        int endYear = q.endYear();

        String response = "";
        for (String word : words) {
            String sumrf = this.ngramSource.weightHistory(word, startYear, endYear).toString();
            response = response + (word + ": " + sumrf + "\n");
        }
        return response;
    }
}
