package lk.ijse.newOceansync.service;

import javafx.concurrent.Task;

public class LoadingTask extends Task<Integer> {
    @Override
    protected Integer call() throws Exception {
        for (double i = 0; i <= 100; i++) {
            updateProgress(i, 100.00);
            Thread.sleep(70);
        }
        return 100;
    }
}
