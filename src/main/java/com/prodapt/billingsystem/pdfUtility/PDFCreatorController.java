package com.prodapt.billingsystem.pdfUtility;

import com.itextpdf.text.DocumentException;
import com.prodapt.billingsystem.api.invoice.dto.InvoicePDFTemplateDTO;
import com.prodapt.billingsystem.api.invoice.dto.InvoiceResponseDTO;
import com.prodapt.billingsystem.api.invoice.entity.Invoice;
import com.prodapt.billingsystem.api.invoice.services.InvoiceService;
import com.prodapt.billingsystem.api.plans.dto.PlanResponseDTO;
import com.prodapt.billingsystem.api.user.dao.UserRepository;
import com.prodapt.billingsystem.api.user.entity.User;
import com.prodapt.billingsystem.api.user.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.w3c.tidy.Tidy;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.*;
import java.nio.file.FileSystems;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@CrossOrigin
@Controller
@RestController
@RequestMapping("/api/v1/user/testpdf")
public class PDFCreatorController {
    @Autowired
    SpringTemplateEngine templateEngine;

    @Autowired
    private InvoiceService invoiceService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/{uuid}")
    public ResponseEntity<HttpStatus> createPdfByUserUuid(@PathVariable UUID uuid) {

        System.out.println("API HIT");

        User user = userRepository.findByUuid(uuid).orElseThrow(() -> new UsernameNotFoundException("User Not found exception"));

        List<Invoice> invoiceList = invoiceService.getAllUserInvoiceUuid(uuid);

        List<PlanResponseDTO> subscribedPlans = userService.getSubscribedPlansList(uuid);

        List<InvoiceResponseDTO> invoiceResponseDTOList = new ArrayList<>();

        InvoicePDFTemplateDTO pdfTemplate = new InvoicePDFTemplateDTO();

        pdfTemplate.setUserId(String.valueOf(user.getId()));
        pdfTemplate.setCity(user.getCity());
        pdfTemplate.setCountry(user.getCountry());
        pdfTemplate.setState(user.getState());
        pdfTemplate.setEmailId(user.getEmail());
        pdfTemplate.setPincode(user.getPincode());

//        pdfTemplate.setAmount();
//        pdfTemplate.setInvoiceId( );
//        pdfTemplate.setNoOfPlans();
//        pdfTemplate.setSubscribedPlans();

        invoiceList.forEach((invoice) -> {
            InvoiceResponseDTO res = new InvoiceResponseDTO();

            res.setInvoiceUuid(invoice.getUuid());
            res.setEmailId(invoice.getEmailId());
            res.setAmount(invoice.getAmount());
            res.setNoOfPlans(invoice.getNosOfPlans());
            res.setPaymentStatus(invoice.isPaymentStatus());
            res.setUserId(uuid.toString());
            res.setSubscribedPlans(subscribedPlans);

            invoiceResponseDTOList.add(res);

            pdfTemplate.setAmount(invoice.getAmount());
            pdfTemplate.setInvoiceId(invoice.getId());
            pdfTemplate.setNoOfPlans(invoice.getNosOfPlans());

        });
        pdfTemplate.setSubscribedPlans(subscribedPlans);

        try {
            savePDF(pdfTemplate);
        } catch (Exception e) {
            e.printStackTrace();
        }


        return new ResponseEntity<>(HttpStatus.OK);

    }


    public void savePDF(InvoicePDFTemplateDTO pdfTemplate) throws IOException, DocumentException, IOException, DocumentException {


        Context context = new Context();

        context.setVariable("InvoiceId", pdfTemplate.getInvoiceId());
        context.setVariable("Email", pdfTemplate.getEmailId());
        context.setVariable("PayeeName", pdfTemplate.getUsername());
        context.setVariable("PhoneNo", pdfTemplate.getPhoneNo());
        context.setVariable("City", pdfTemplate.getCity());
        context.setVariable("State", pdfTemplate.getState());
        context.setVariable("Country", pdfTemplate.getCountry());
        context.setVariable("Address", pdfTemplate.getCity() + "," + pdfTemplate.getState());
        context.setVariable("Pincode", pdfTemplate.getPincode());

        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yy");
        String currentDate = formatter.format(date);


        context.setVariable("Date", currentDate);

        PlanResponseDTO plan = pdfTemplate.getSubscribedPlans().get(0);
        context.setVariable("PlanName", plan.getName());
        context.setVariable("PlanType", plan.getPlanType());
        context.setVariable("PlanPrice", plan.getPrice());
        context.setVariable("Amount", pdfTemplate.getAmount());


        String htmlContentToRender = templateEngine.process("pdf-template", context);
        String xHtml = xhtmlConvert(htmlContentToRender);

        ITextRenderer renderer = new ITextRenderer();

        String baseUrl = FileSystems
                .getDefault()
                .getPath("src", "main", "resources", "templates")
                .toUri()
                .toURL()
                .toString();

        renderer.setDocumentFromString(xHtml, baseUrl);
        renderer.layout();

        OutputStream outputStream = new FileOutputStream("src//test.pdf");
        renderer.createPDF(outputStream);
        outputStream.close();

    }

    private String xhtmlConvert(String html) throws UnsupportedEncodingException {
        Tidy tidy = new Tidy();
        tidy.setInputEncoding("UTF-8");
        tidy.setOutputEncoding("UTF-8");
        tidy.setXHTML(true);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(html.getBytes("UTF-8"));
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        tidy.parseDOM(inputStream, outputStream);
        return outputStream.toString("UTF-8");
    }

    @GetMapping
    public String testify(){
        return "Working";
    }
}

