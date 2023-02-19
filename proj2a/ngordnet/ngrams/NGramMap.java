package ngordnet.ngrams;

import edu.princeton.cs.algs4.In;

import java.util.Collection;
import java.util.HashMap;


/**
 * An object that provides utility methods for making queries on the
 * Google NGrams dataset (or a subset thereof).
 * <p>
 * An NGramMap stores pertinent data from a "words file" and a "counts
 * file". It is not a map in the strict sense, but it does provide additional
 * functionality.
 *
 * @author Josh Hug
 */
public class NGramMap {
    /**
     * Constructs an NGramMap from WORDSFILENAME and COUNTSFILENAME.
     */
    private In wordsRead;
    private In countsRead;
    private HashMap<String, TimeSeries> wordsAll;
    private TimeSeries countsAll;

    public NGramMap(String wordsFilename, String countsFilename) {
        this.wordsRead = new In(wordsFilename);
        this.countsRead = new In(countsFilename);
        this.wordsAll = new HashMap<String, TimeSeries>();
        this.countsAll = new TimeSeries();
        /**For wordsFile*/
        while (wordsRead.hasNextLine()) {
            String[] currentLine = wordsRead.readLine().split("\\t");
            if (!wordsAll.containsKey(currentLine[0])) {
                TimeSeries t = new TimeSeries();
                t.put(Integer.parseInt(currentLine[1]), Double.parseDouble(currentLine[2]));
                wordsAll.put(currentLine[0], t);
            } else {
                TimeSeries getValue = wordsAll.get(currentLine[0]);
                getValue.put(Integer.parseInt(currentLine[1]), Double.parseDouble(currentLine[2]));
            }
        }

        /**For countsFile*/
        while (countsRead.hasNextLine()) {
            String[] currentLine = countsRead.readLine().split(",");
            countsAll.put(Integer.parseInt(currentLine[0]), Double.parseDouble(currentLine[1]));
        }

    }

    /**
     * Provides the history of WORD. The returned TimeSeries should be a copy,
     * not a link to this NGramMap's TimeSeries. In other words, changes made
     * to the object returned by this function should not also affect the
     * NGramMap. This is also known as a "defensive copy".
     */
    public TimeSeries countHistory(String word) {
        TimeSeries t = new TimeSeries();
        for (Integer varYear : wordsAll.get(word).keySet()) {
            t.put(varYear, wordsAll.get(word).get(varYear));
        }
        return t;
    }

    /**
     * Provides the history of WORD between STARTYEAR and ENDYEAR, inclusive of both ends. The
     * returned TimeSeries should be a copy, not a link to this NGramMap's TimeSeries. In other words,
     * changes made to the object returned by this function should not also affect the
     * NGramMap. This is also known as a "defensive copy".
     */
    public TimeSeries countHistory(String word, int startYear, int endYear) {
        TimeSeries t = new TimeSeries();
        for (Integer varYear : wordsAll.get(word).keySet()) {
            if (varYear >= startYear && varYear <= endYear) {
                t.put(varYear, wordsAll.get(word).get(varYear));
            }
        }
        return t;
    }

    /**
     * Returns a defensive copy of the total number of words recorded per year in all volumes.
     */
    public TimeSeries totalCountHistory() {
        TimeSeries t = new TimeSeries();
        for (Integer varYear : countsAll.keySet()) {
            t.put(varYear, countsAll.get(varYear));
        }
        return t;
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD compared to
     * all words recorded in that year.
     */
    public TimeSeries weightHistory(String word) {
        return wordsAll.get(word).dividedBy(countsAll);
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD between STARTYEAR
     * and ENDYEAR, inclusive of both ends.
     */
    public TimeSeries weightHistory(String word, int startYear, int endYear) {
        TimeSeries t = new TimeSeries(wordsAll.get(word), startYear, endYear);
        TimeSeries t2 = new TimeSeries(countsAll, startYear, endYear);
        return t.dividedBy(t2);
    }

    /**
     * Returns the summed relative frequency per year of all words in WORDS.
     */
    public TimeSeries summedWeightHistory(Collection<String> words) {
        TimeSeries sumW = new TimeSeries();
        for (String word : words) {
            sumW = sumW.plus(weightHistory(word));
        }
        return sumW;
    }

    /**
     * Provides the summed relative frequency per year of all words in WORDS
     * between STARTYEAR and ENDYEAR, inclusive of both ends. If a word does not exist in
     * this time frame, ignore it rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> words, int startYear, int endYear) {
        TimeSeries sumW = new TimeSeries();
        for (String word : words) {
            sumW = sumW.plus(weightHistory(word, startYear, endYear));
        }
        return sumW;
    }

}
