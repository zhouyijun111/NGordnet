package ngordnet.proj2b_testing;

import ngordnet.hugbrowsermagic.NgordnetQueryHandler;
import ngordnet.main.HyponymsHandler;
import ngordnet.main.WordNet;
import ngordnet.ngrams.NGramMap;


public class AutograderBuddy {
    /** Returns a HyponymHandler */
    public static NgordnetQueryHandler getHyponymHandler(
            String wordFile, String countFile,
            String synsetFile, String hyponymFile) {

        /**modified part*/
        NGramMap ngm = new NGramMap(wordFile, countFile);
        WordNet wdn = new WordNet(synsetFile,hyponymFile);
        return new HyponymsHandler(wdn,ngm);
    }
}
