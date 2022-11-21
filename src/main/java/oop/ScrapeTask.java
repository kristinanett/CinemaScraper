package oop;

import javafx.concurrent.Task;

public class ScrapeTask extends Task<Integer> {

    @Override
    protected Integer call() throws Exception {
        //ApolloScrape.apolloScrape();
        this.updateMessage("Apollo kino kooritud");
        CinamonScrape.cinamonScrape();
        this.updateMessage("Cinamon kooritud");
        //ForumCinemasScrape.forumCinemasScrape();
        return 1;
    }
}
