package com.prodapt.billingsystem.email;

import com.prodapt.billingsystem.api.user.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
public class EmailServices implements EmailServiceInterface{
    @Autowired
    private SMTPEmailSender smtpEmailSender;

    public void testEmail(String emailId) {

        MessageEmail email = new MessageEmail();

        email.setSubject("Registration Successful | SHIKSHAK SETU");

        email.setTo(Collections.singletonList(emailId));
        String message = "Dear ";

//        File file = new File("../resources/assets/invoice/Billify-logo.png");
        List<File> filesList= new ArrayList<>();

//        filesList.add(file);
        filesList.add(new File("C:\\Users\\siddharth.sp\\Desktop\\sample.txt"));

        email.setAttachments(filesList);

        email.setBody(message);
        email.setHtml(false);

        smtpEmailSender.sendMessage(email);
    }

    public void sendForgotPasswordEmail(String emailId, String username, String otp){
        MessageEmail email = new MessageEmail();
        email.setSubject("Billify: Forgot Password Recovery");

        email.setTo(Collections.singletonList(emailId));
        String message = "Dear "+username+"" +
                "\n\n" +
                "Enter this code to complete the reset" +
                "\n\n" +
                " "+otp+"" +
                "\n\n" +
                "Thanks for helping us keep your account secure." +
                "\n\n" +
                "The Team Billify";

        email.setBody(message);
        email.setHtml(false);

        smtpEmailSender.sendMessage(email);

    }

    public void sendInvoiceDetails( String emailId, String username, String invoiceNo, String invoiceBalance, String invoiceDueDate){
        MessageEmail email = new MessageEmail();
        email.setSubject("Billify: Forgot Password Recovery");

        email.setTo(Collections.singletonList(emailId));
        String message ="Hello"+ username +",\n" +
                "\n" +
                "I hope that you are well. I am contacting you on behalf of Billify with regard to the following invoice:\n" +
                "\n" +
                invoiceNo +" / "+invoiceBalance +"\n" +
                "\n" +
                "This invoice is due for payment on "+invoiceDueDate+". Please could you kindly confirm receipt of this invoice and advise as to whether payment has been scheduled.\n" +
                "\n" +
                "I have attached a copy of the invoice for your reference. If you require any further information, please let me know.\n" +
                "\n" +
                "Best regards,\n" +
                "\n" +
                "Team Billify\n" +
                "\n" +
                "\n" +
                "\n" ;

        File file = new File("../resources/assets/invoice/Billify-logo.png");
        List<File> filesList= new ArrayList<>();

        filesList.add(file);
        filesList.add(new File("../../test.pdf"));

        email.setAttachments(filesList);
        email.setBody(message);
        email.setHtml(false);

    }

    public void sendPasswordChangedEmail(String emailId, String username){
        MessageEmail email = new MessageEmail();

        email.setSubject("Billify: Password Change Request Successful");

        email.setTo(Collections.singletonList(emailId));
        String message = "Dear "+username+"\n\nYou have successfully changed the password of your Billify Account.\n\nIf it was not you kindly inform admin regarding same.\n\n Thanks and Regards";

//        File file = new File("../resources/assets/invoice/Billify-logo.png");
        List<File> filesList= new ArrayList<>();

//        filesList.add(file);
//        filesList.add(new File("C:\\Users\\siddharth.sp\\Desktop\\sample.txt"));

        email.setAttachments(filesList);

        email.setBody(message);
        email.setHtml(false);

        smtpEmailSender.sendMessage(email);
    }


    public void sendInvoiceFromCSV(String emailId, String username, String amount){
        MessageEmail email = new MessageEmail();

        email.setSubject(" Billify: Provider Suscription Reminder");

        email.setTo(Collections.singletonList(emailId));
        String message = "Dear "+username+"\n\n" +
                "Here is your invoice reminder from your service provider to pay the remaining dues of the service" +
                "\n\n" +
                "Kindly pay total dues of Rs. "+amount+"to continue with the service" +
                "\n\n" +
                "\n\n" +
                "Thanks and Regards";

//        File file = new File("../resources/assets/invoice/Billify-logo.png");
//        List<File> filesList= new ArrayList<>();

//        filesList.add(file);
//        filesList.add(new File("C:\\Users\\siddharth.sp\\Desktop\\sample.txt"));

//        email.setAttachments(filesList);

        email.setBody(message);
        email.setHtml(false);

        smtpEmailSender.sendMessage(email);
        log.info("CSV Email sent to "+emailId);

    }
}
