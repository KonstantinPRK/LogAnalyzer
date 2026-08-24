public class Analizator implements Runnable {
    Loader loader = new FileLoader();
    DateChecker dateChecker = new SomeDateChecker();
    Aggregator aggregator = new StatisticAggregator();
    Reporter reporter = new MarkDownReporter();

    public Analizator(Loader loader, DateChecker dateChecker, Aggregator aggregator, Reporter reporter) {

    }



    public void run(){

    }
}
