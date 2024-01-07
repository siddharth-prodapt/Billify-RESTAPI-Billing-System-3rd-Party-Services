package com.prodapt.billingsystem.common;

import com.prodapt.billingsystem.api.invoice.entity.Invoice;
import com.prodapt.billingsystem.api.invoice.repository.InvoiceRepo;
import com.prodapt.billingsystem.api.user.dao.UserRepository;
import com.prodapt.billingsystem.api.user.entity.User;
import com.prodapt.billingsystem.email.EmailServices;
import com.prodapt.billingsystem.utility.UtilityMethods;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.ScheduledMethodRunnable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Period;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ScheduledFuture;

@Slf4j
@Service
@ConditionalOnProperty(name = "scheduling.enabled", havingValue = "true", matchIfMissing = true)
public class AccountTimerService {
    private boolean shouldRunScheduledTask = false;



    @Autowired
    private InvoiceRepo invoiceRepo;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailServices emailServices;


/* One more scheduler required which will check every day regarding
* payment status
* -> change payment status and update BillingDate:-
* */






    // invoice scheduler which will calculate the bill on every 21st of month
    @Scheduled(cron = "0 0 0 21 * ?")  // Execute at midnight on the 21st of every month
    public void myScheduleFunction(){
        System.out.println("CHECK PAYMENT STATUS TRIGGER SCHEDULER CALLED....");

        // Set up a timer to check for arrears every day
        if (shouldRunScheduledTask) {
            // Task logic
            checkUserAccountStatus();
        }
    }

    public void stopScheduledTask() {
        shouldRunScheduledTask = false;
    }


    public void startScheduledTask() {
        shouldRunScheduledTask = true;
    }

    public void checkUserAccountStatus(){
        log.info("checking account status");

        List<Invoice> invoiceList = invoiceRepo.findAll();

        invoiceList.forEach((invoice)-> {

            Timestamp dueTimestamp = Timestamp.valueOf(invoice.getDueDate());
            User user = userRepository.findById(invoice.getUserId()).orElseThrow(()->new RuntimeException("User Not found"));

//            user has not made payment yet
            if(invoice.getPaymentDate() == null){
                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                int diff = Period.between( dueTimestamp.toLocalDateTime().toLocalDate(),  timestamp.toLocalDateTime().toLocalDate()).getDays();

                log.info("Due Date: "+dueTimestamp+" Today: "+timestamp);

                log.info("User"+ invoice.getEmailId() +" has not made payment \n Diff b/w invoice due date and todays-date: "+diff);

                if( diff > 0 && diff<5){
                    //set account status = ACTIVE
                    user.setAccountStatus("ACTIVE");
                    log.info("User: "+invoice.getEmailId()+" status= ACTIVE");
                }else if(diff > 5){
                    //set account status = SUSPENDED
                    user.setAccountStatus("SUSPENDED");
                    emailServices.sendAccountStatusToUser(user.getEmail(), user.getUsername(), user.getAccountStatus());
                }else{
//                    .. ACCOUNT STATUS = TERMINATED
                    user.setAccountStatus("TERMINATED");
                    emailServices.sendAccountStatusToUser(user.getEmail(), user.getUsername(), user.getAccountStatus());
                }
            }
            else{

                invoice.setStatus("PAID");

//                Timestamp paymentTime = Timestamp.valueOf(invoice.getPaymentDate());
//
//                Period diff = Period.between(dueTimestamp.toLocalDateTime().toLocalDate(), paymentTime.toLocalDateTime().toLocalDate());
//                log.info("Diff b/w datee"+diff);
//                int remainingDays = diff.getDays();
//
//                if(remainingDays < 0){
//
//                    invoice.setStatus("PAID");
//                    user.setAccountStatus("ACTIVE");
//                    // invoice status = PAID
//                    ACCOUNT STATUS = ACTIVE
//                }
//            else{
//                    user.setAccountStatus("SUSPENDED");
//
//                }

            }

            invoiceRepo.saveAll(invoiceList);
            userRepository.save(user);

        } );

        invoiceRepo.saveAll(invoiceList);
    }




    //second min hour dayOfMonth month dayOfWeek
    // Use this scheduler on daily basis
    @Scheduled( cron = "0 48 23 7 * *")
    public void checkAccountStatus(){
    //  Scheduler for If bill not paid
    //        List<User> usersList = userRepository.findAll();
        log.info("Account Status Scheduler Started");
        List<Invoice> invoiceList = invoiceRepo.findAll();

        for(Invoice inv : invoiceList){
            long userId = inv.getUserId();
            String dueDate = inv.getDueDate();
            Timestamp todayTimestamp = new Timestamp(System.currentTimeMillis());

            long days = UtilityMethods.nosOfDays(Timestamp.valueOf(dueDate), todayTimestamp  );
            log.info("DueDate : "+dueDate);
            log.info("Difference in nos of days from DueDate and currentDate: "+days);


            //for SUSPENDING THE ACCOUNT
            //User has not made payment till due date && invoice is still false
            if(days>0 && !inv.isPaymentStatus()){
                log.info("Payment Not Made condition is true");

                User user = userRepository.findById(userId).get();
                user.setAccountAccess(false);
                user.setAccountStatus("SUSPENDED");
                // email account suspended
                userRepository.save(user);
                log.info("Account suspended for user: "+ userRepository.findById(userId).get().getName());
            }

            //CHECK FOR TERMINATION OF ACCOUNT
            if(days>30 && !inv.isPaymentStatus()){
                User user = userRepository.findById(userId).get();
                user.setAccountAccess(false);
                user.setAccountStatus("TERMINATED");
                userRepository.save(user);
                log.info("Account terminated for user: "+ userRepository.findById(userId).get().getName());
            }
        }
        log.info("Scheduler Closed!!");

    }
}
