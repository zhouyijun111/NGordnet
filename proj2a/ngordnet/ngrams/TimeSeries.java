package ngordnet.ngrams;

import java.util.*;

/**
 * An object for mapping a year number (e.g. 1996) to numerical data. Provides
 * utility methods useful for data analysis.
 *
 * @author Josh Hug
 */
public class TimeSeries extends TreeMap<Integer, Double> {
    /**
     * Constructs a new empty TimeSeries.
     */
    public TimeSeries() {
        super();
    }

    /**
     * Creates a copy of TS, but only between STARTYEAR and ENDYEAR,
     * inclusive of both end points.
     */
    public TimeSeries(TimeSeries ts, int startYear, int endYear) {
        super();
        for (Integer varYear : ts.keySet()) {
            if (varYear >= startYear && varYear <= endYear) {
                this.put(varYear, ts.get(varYear));
            }
        }
    }

    /**
     * Returns all years for this TimeSeries (in any order).
     */
    public List<Integer> years() {
        return new ArrayList(this.keySet()); /** How to use ArrayList*/
    }

    /**
     * Returns all data for this TimeSeries (in any order).
     * Must be in the same order as years().
     */
    public List<Double> data() {
        return new ArrayList(this.values());
    }

    /**
     * Returns the yearwise sum of this TimeSeries with the given TS. In other words, for
     * each year, sum the data from this TimeSeries with the data from TS. Should return a
     * new TimeSeries (does not modify this TimeSeries).
     */
    public TimeSeries plus(TimeSeries ts) {
        TimeSeries yearSum = new TimeSeries();
        ArrayList arrayList1 = new ArrayList(this.keySet());
        ArrayList arrayList2 = new ArrayList(ts.keySet());
        arrayList1.addAll(arrayList2);
        for (Object varYear : arrayList1) {  /** Question about why we need to use Object data type*/
            double yearValue = 0;
            if (this.containsKey(varYear) && !ts.containsKey(varYear)) {
                yearValue = this.get(varYear);
            } else if (!this.containsKey(varYear) && ts.containsKey(varYear)) {
                yearValue = ts.get(varYear);
            } else {
                yearValue = this.get(varYear) + ts.get(varYear);
            }
            yearSum.put((Integer) varYear, yearValue); /**And hence this place we use casting*/
        }
        return yearSum;
    }

    /**
     * Returns the quotient of the value for each year this TimeSeries divided by the
     * value for the same year in TS. If TS is missing a year that exists in this TimeSeries,
     * throw an IllegalArgumentException. If TS has a year that is not in this TimeSeries, ignore it.
     * Should return a new TimeSeries (does not modify this TimeSeries).
     */
    public TimeSeries dividedBy(TimeSeries ts) {
        TimeSeries yearQuotient = new TimeSeries();
        for (Integer varYear : this.keySet()) {
            double yearValue = 0;
            if (!ts.containsKey(varYear)) {
                throw new IllegalArgumentException("No corresponding year in TS");
            }
            if (this.containsKey(varYear) && ts.containsKey(varYear)) {
                yearValue = this.get(varYear) / ts.get(varYear);
            }
            yearQuotient.put(varYear, yearValue);
        }
        return yearQuotient;
    }

}
