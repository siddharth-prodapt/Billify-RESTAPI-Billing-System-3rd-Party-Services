package com.prodapt.billingsystem.api.invoice.services;

import com.prodapt.billingsystem.api.invoice.entity.Invoice;

import java.util.UUID;

public interface InvoiceService {
    Invoice generateInvoiceByUuid(UUID uuid);
}
