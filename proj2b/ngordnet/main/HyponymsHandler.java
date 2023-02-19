package ngordnet.main;

import ngordnet.hugbrowsermagic.NgordnetQuery;
import ngordnet.hugbrowsermagic.NgordnetQueryHandler;
import ngordnet.ngrams.NGramMap;
import ngordnet.ngrams.TimeSeries;

import java.util.*;

public class HyponymsHandler extends NgordnetQueryHandler {
    private WordNet wordnetSource;
    private NGramMap ngramSource;

    public HyponymsHandler(WordNet wdn, NGramMap ngm) {
        this.wordnetSource = wdn;
        this.ngramSource = ngm;
    }

    @Override
    public String handle(NgordnetQuery q) {
        List<String> words = q.words();
        int startYear = q.startYear();
        int endYear = q.endYear();
        int k = q.k();

        TreeSet<String> hyponyms = this.wordnetSource.findHyponyms(words.get(0));
        for (int i = 1; i < words.size(); i++) {
            hyponyms.retainAll(this.wordnetSource.findHyponyms(words.get(i)));
            if (hyponyms.isEmpty()) {
                break;
            }
        }
        if (hyponyms.isEmpty()) {
            return Collections.emptyList().toString();
        }

        /**After finding the intersection of the hyponyms of all words (words variable) we input which is
         * we want to find the k most popular e.g. k=5 in a given period time between
         * startYear and endYear*/

        if (k == 0) {
            return hyponyms.toString();
        } else {
            /**Create a HashMap shat store the intersected words' occurrence and itself from startYear to endYear*/
            Map<Double, String> hyponymsMap = new HashMap<Double, String>();
            /**Create a max Priority Queue and pop the kth maximum element after*/
            PriorityQueue<Double> maxPQ = new PriorityQueue<>(Collections.reverseOrder());

            for (String hyponym : hyponyms) {
                TimeSeries hyponymTs = this.ngramSource.countHistory(hyponym, startYear, endYear);
                ArrayList<Double> aLs = (ArrayList<Double>) hyponymTs.data();
                Double sum = Double.valueOf(0);
                for (Double al : aLs) {
                    sum = sum + al;
                }
                hyponymsMap.put(sum, hyponym);
                maxPQ.add(sum);
            }

            TreeSet<String> mostPopular = new TreeSet<>();
            /**When k is less than or equal to hyponyms.size() */
            if (k <= hyponyms.size()) {
                for (int i = 0; i < k; i = i + 1) {
                    Double popularKey = maxPQ.poll();
                    if (popularKey != 0.0) {
                        mostPopular.add(hyponymsMap.get(popularKey));
                    } else {
                        break;
                    }
                }
                if (mostPopular.isEmpty()) {
                    return Collections.emptyList().toString();
                }
                return mostPopular.toString();
            } else { /**When k is greater hyponyms.size()*/
                for (int i = 0; i < hyponyms.size(); i = i + 1) {
                    Double popularKey = maxPQ.poll();
                    if (popularKey != 0.0) {
                        mostPopular.add(hyponymsMap.get(popularKey));
                    } else {
                        break;
                    }
                }
                if (mostPopular.isEmpty()) {
                    return Collections.emptyList().toString();
                }
                return mostPopular.toString();
            }
        }
    }
}
