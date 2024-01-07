package com.prodapt.billingsystem.email;

import com.prodapt.billingsystem.api.user.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
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
//        String message = " <h1>Hiii testting </h1> <br>" +
//                "<h2 style='color:red;'>Hii h2</h2><br>" +
//                "<p>Sample mail this is coloured text i.e.<span style='color:blue;'>Blue text</span> in this para</p> ";

        String message = " <!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <title>Invoice Details</title>\n" +
                "    <style>\n" +
                "        body {\n" +
                "            font-family: 'Arial', sans-serif;\n" +
                "            margin: 0;\n" +
                "            padding: 0;\n" +
                "            background-color: #f4f4f4;\n" +
                "        }\n" +
                "\n" +
                "        .container {\n" +
                "            width: 80%;\n" +
                "            margin: 20px auto;\n" +
                "            background-color: #fff;\n" +
                "            padding: 20px;\n" +
                "            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);\n" +
                "        }\n" +
                "\n" +
                "        h2 {\n" +
                "            color: #333;\n" +
                "        }\n" +
                "\n" +
                "        img {\n" +
                "            max-width: 100%;\n" +
                "            height: auto;\n" +
                "        }\n" +
                "\n" +
                "        .company-logo {\n" +
                "            text-align: center;\n" +
                "            margin-bottom: 20px;\n" +
                "        }\n" +
                "\n" +
                "        table {\n" +
                "            width: 100%;\n" +
                "            border-collapse: collapse;\n" +
                "            margin-top: 20px;\n" +
                "        }\n" +
                "\n" +
                "        th, td {\n" +
                "            border: 1px solid #ddd;\n" +
                "            padding: 12px;\n" +
                "            text-align: left;\n" +
                "        }\n" +
                "\n" +
                "        th {\n" +
                "            background-color: #f2f2f2;\n" +
                "        }\n" +
                "\n" +
                "        .due-date {\n" +
                "            color: #e44d26;\n" +
                "            font-weight: bold;\n" +
                "        }\n" +
                "\n" +
                "        .total-section {\n" +
                "            margin-top: 20px;\n" +
                "            font-weight: bold;\n" +
                "            background-color: #f2f2f2;\n" +
                "            padding: 10px;\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body style=\"font-family: 'Arial', sans-serif; margin: 0; padding: 0; background-color: #f4f4f4;\">\n" +
                "\n" +
                "    <div class=\"container\">\n" +
                "        <div class=\"company-logo\">\n" +
                "            <img src=\"C:\\Users\\siddharth.sp\\IdeaProjects\\Billing-System\\src\\main\\resources\\assets\\invoice\\Billify-logo.png\" alt=\"Company Logo\">\n" +
                "        </div>\n" +
                "\n" +
                "        <h2 style=\"color: #333;\">Invoice Details</h2>\n" +
                "\n" +
                "        <div>\n" +
                "            <p><strong>Name of Payee:</strong> John Doe</p>\n" +
                "            <p><strong>Address:</strong> 123 Main Street, Cityville</p>\n" +
                "            <p><strong>Phone No:</strong> +1 123-456-7890</p>\n" +
                "            <p><strong>Email ID:</strong> john.doe@example.com</p>\n" +
                "            <p><strong>Bill Due Date:</strong> <span class=\"due-date\">January 21, 2024</span></p>\n" +
                "            <p><strong>Plan Type:</strong> POSTPAID</p>\n" +
                "        </div>\n" +
                "\n" +
                "        <table style=\"width: 100%; border-collapse: collapse; margin-top: 20px;\">\n" +
                "            <thead>\n" +
                "                <tr>\n" +
                "                    <th style=\"border: 1px solid #ddd; padding: 12px; text-align: left; background-color: #f2f2f2;\">Sno</th>\n" +
                "                    <th style=\"border: 1px solid #ddd; padding: 12px; text-align: left; background-color: #f2f2f2;\">Plan Type</th>\n" +
                "                    <th style=\"border: 1px solid #ddd; padding: 12px; text-align: left; background-color: #f2f2f2;\">Plan Amount</th>\n" +
                "                    <th style=\"border: 1px solid #ddd; padding: 12px; text-align: left; background-color: #f2f2f2;\">SubTotal</th>\n" +
                "                </tr>\n" +
                "            </thead>\n" +
                "            <tbody>\n" +
                "                <tr>\n" +
                "                    <td style=\"border: 1px solid #ddd; padding: 12px; text-align: left;\">1</td>\n" +
                "                    <td style=\"border: 1px solid #ddd; padding: 12px; text-align: left;\">Basic Plan</td>\n" +
                "                    <td style=\"border: 1px solid #ddd; padding: 12px; text-align: left;\">$50</td>\n" +
                "                    <td style=\"border: 1px solid #ddd; padding: 12px; text-align: left;\">$50</td>\n" +
                "                </tr>\n" +
                "                <tr>\n" +
                "                    <td style=\"border: 1px solid #ddd; padding: 12px; text-align: left;\">2</td>\n" +
                "                    <td style=\"border: 1px solid #ddd; padding: 12px; text-align: left;\">Premium Plan</td>\n" +
                "                    <td style=\"border: 1px solid #ddd; padding: 12px; text-align: left;\">$80</td>\n" +
                "                    <td style=\"border: 1px solid #ddd; padding: 12px; text-align: left;\">$80</td>\n" +
                "                </tr>\n" +
                "                <!-- Add more rows as needed -->\n" +
                "            </tbody>\n" +
                "        </table>\n" +
                "\n" +
                "        <div class=\"total-section\">\n" +
                "            <p><strong>\n";


//        File file = new File("../resources/assets/invoice/Billify-logo.png");
//        List<File> filesList= new ArrayList<>();

//        filesList.add(file);
//        filesList.add(new File("C:\\Users\\siddharth.sp\\Desktop\\sample.txt"));

//        email.setAttachments(filesList);

        email.setBody(message);
        email.setHtml(true);

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


    @Async
    public void sendInvoiceFromCSV(String emailId, String phoneNo, String amount,String name){
        MessageEmail email = new MessageEmail();

        email.setSubject(" Billify: Provider Suscription Reminder");

        email.setTo(Collections.singletonList(emailId));
        String message = "Dear "+name+",\n\n" +
                "\n\nName: "+name+"\nEmailId: "+emailId+"PhoneNo: "+phoneNo+
                "\n\nHere is your invoice reminder from your service provider to pay the remaining dues for subscribed service" +
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

    public void sendAccountStatusToUser(String emailId, String username, String accountStatus){
        MessageEmail email = new MessageEmail();

        email.setSubject(" Billify: Account Status-"+accountStatus);

        email.setTo(Collections.singletonList(emailId));
        String message = "Dear "+username+"\n\n" +
                "Here is your invoice reminder from your service provider to pay the remaining dues of the service" +
                "\n\n" +
                "Your account is "+accountStatus+". Kindly login in the portal pay the remaining dues to continue with subscription"+
                "\n\n" +
                "\n\n" +
                "Thanks and Regards";



        email.setBody(message);
        email.setHtml(false);

        smtpEmailSender.sendMessage(email);
        log.info("CSV Email sent to "+emailId);

    }

    public void sendEmailToEmailId(String emailId, String amount){
        MessageEmail email = new MessageEmail();

        email.setSubject("Billify: Reminder for payment");

        email.setTo(Collections.singletonList(emailId));
        String message = "Dear Billify User"+"\n\n Kindly pay the remaining dues of amount : Rs. "+ amount+" by logging in to dashboard.\n\nThanks & Regards,\nTeam Billify";

        List<File> filesList= new ArrayList<>();

        email.setAttachments(filesList);

        email.setBody(message);
        email.setHtml(false);

        smtpEmailSender.sendMessage(email);

    }

}
