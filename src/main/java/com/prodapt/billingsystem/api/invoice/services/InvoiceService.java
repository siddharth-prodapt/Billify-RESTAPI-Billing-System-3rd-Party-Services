package com.prodapt.billingsystem.api.invoice.services;

import com.prodapt.billingsystem.api.invoice.dto.InvoiceResDTO;
import com.prodapt.billingsystem.api.invoice.entity.Invoice;

import java.util.List;
import java.util.UUID;

public interface InvoiceService {
    Invoice generateInvoiceByUuid(UUID uuid);


    List<Invoice>  getAllUserInvoiceUuid(UUID uuid);

    InvoiceResDTO generateInvoiceByUserUuid(UUID uuid);
}
