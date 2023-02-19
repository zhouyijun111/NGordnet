package ngordnet.main;

import edu.princeton.cs.algs4.In;

import java.util.*;

public class WordNet {
    private In synsetsRead;
    private In hymsRead;
    private ArrayList<String[]> countSynsets;
    private HashMap<Integer, String[]> synsetsMap;
    private Graph hymsGraph;

    public WordNet(String synsetFile,String hyponymFile) {
        this.synsetsRead = new In(synsetFile);
        this.hymsRead = new In(hyponymFile);
        this.synsetsMap = new HashMap<Integer, String[]>();
        this.countSynsets = new ArrayList<String[]>();

        /**For synsetFile*/
        while (synsetsRead.hasNextLine()) {
            String[] lineArray = synsetsRead.readLine().split(",");
            String[] synsetWords = lineArray[1].split(" ");
            // put each key-value pairs into HashMap of synsets
            synsetsMap.put(Integer.parseInt(lineArray[0]),synsetWords);
            countSynsets.add(synsetWords);
        }

        /**For hyponymFile*/
        this.hymsGraph = new Graph(countSynsets.size());
        while (hymsRead.hasNextLine()) {
            String[] lineArray2 = hymsRead.readLine().split(",");
            int hyperNym = Integer.parseInt(lineArray2[0]);
            for (int i =1; i <= lineArray2.length - 1;i = i + 1) {
                this.hymsGraph.addEdge(hyperNym,Integer.parseInt(lineArray2[i]));
            }
        }
    }

    public TreeSet<String> findHyponyms(String word) {
        TreeSet<String> result = new TreeSet<String>();
        Set<Integer> synsetsIds = new HashSet<Integer>();
        Set<String> currentSyn;

        /**Add each synset of the input word to the result*/
        Set<Integer> s = synsetsMap.keySet();
        for (Integer indivKey : s) {
            currentSyn = new HashSet<String>(Arrays.asList(synsetsMap.get(indivKey)));
            if (currentSyn.contains(word)) {
                result.addAll(currentSyn);
                synsetsIds.add(indivKey);
            }
        }

        /**Add all hyponyms of the input words to the result*/
        /** graph helper functions findhymNodes write in GraphTraversal Class */
        Set<Integer> hyponymKeys = GraphTraversal.findhymNodes(hymsGraph, synsetsIds);
        for (Integer key : hyponymKeys) {
            currentSyn = new HashSet<String>(Arrays.asList(synsetsMap.get(key)));
            result.addAll(currentSyn);
        }

        return result;
    }
}
