package com.prodapt.billingsystem.email;

public interface EmailServiceInterface {

    public void testEmail(String emailId);

    public void sendForgotPasswordEmail(String emailId, String username, String otp);

    public void sendInvoiceDetails( String emailId, String username, String invoiceNo, String invoiceBalance, String invoiceDueDate);

    public void sendPasswordChangedEmail(String emailId, String username);

    public void sendInvoiceFromCSV(String emailId, String username, String amount);

}
