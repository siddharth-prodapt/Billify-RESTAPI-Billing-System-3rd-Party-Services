package com.prodapt.billingsystem.config.SchedulerErrorHandler;

import org.springframework.util.ErrorHandler;

public class CustomTaskSchedulerHandler implements ErrorHandler {

    @Override
    public void handleError(Throwable throwable) {
        // Custom error handling logic here
        System.err.println("An error occurred during scheduled task execution: " + throwable.getMessage());

        // You can choose to log the error, send notifications, or take other corrective actions.
    }


}
