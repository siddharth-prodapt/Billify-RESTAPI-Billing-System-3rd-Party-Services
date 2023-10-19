package com.prodapt.billingsystem.common;

import com.prodapt.billingsystem.api.invoice.entity.Invoice;
import com.prodapt.billingsystem.api.invoice.repository.InvoiceRepo;
import com.prodapt.billingsystem.api.user.dao.UserRepository;
import com.prodapt.billingsystem.api.user.entity.User;
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
    private boolean shouldRunScheduledTask = true;



    @Autowired
    private InvoiceRepo invoiceRepo;

    @Autowired
    private UserRepository userRepository;

//    @Scheduled(fixedRate = 20*1000)
    public void myScheduleFunction(){
        System.out.println("CHECK PAYMENT STATUS TRIGGER SCHEDULER CALLED....");
//        timer run every 24 hrs to check payment status
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

                log.info("User"+ invoice.getEmailId() +"has not made payment \n No of days remaining: "+diff);
                if( diff > 0 && diff<5){
                    //set account status = ACTIVE
                    user.setAccountStatus("ACTIVE");
                    log.info("User: "+invoice.getEmailId()+" status= ACTIVE");
                }else if(diff > 5){
                    //set account status = SUSPENDED
                    user.setAccountStatus("SUSPENDED");
                }else{
//                    .. ACCOUNT STATUS = TERMINATED
                    user.setAccountStatus("TERMINATED");
                }
            }
            else{
                Timestamp paymentTime = Timestamp.valueOf(invoice.getPaymentDate());

                Period diff = Period.between(dueTimestamp.toLocalDateTime().toLocalDate(), paymentTime.toLocalDateTime().toLocalDate());

                int remainingDays = diff.getDays();

                if(remainingDays < 0){

                    invoice.setStatus("PAID");
                    user.setAccountStatus("ACTIVE");
                    // invoice status = PAID
//                    ACCOUNT STATUS = ACTIVE
                }else{
                    user.setAccountStatus("SUSPENDED");
                    //ACCOUNT STATUS = SUSPENDED
                }

            }

            userRepository.save(user);
//            invoiceRepo.save(invoice);



        } );

        invoiceRepo.saveAll(invoiceList);
    }





    void checkAccountStatus(){
//  Every 14 days and send mail accordingly

    }
}
