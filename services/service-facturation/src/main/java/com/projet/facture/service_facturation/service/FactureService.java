package com.example.billing.service;

import com.example.billing.model.Invoice;
import com.example.billing.model.InvoiceStatus;
import com.example.billing.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// Service contenant la logique métier pour les factures
@Service
public class InvoiceService {

    // Injection du repository pour accéder à la base de données
    @Autowired
    private InvoiceRepository invoiceRepository;

    // Crée une nouvelle facture avec validation des champs requis
    public Invoice createInvoice(Invoice invoice) {
        if (invoice.getClientId() == null || invoice.getAmount() == null || 
            invoice.getServiceIds() == null || invoice.getServiceIds().isEmpty()) {
            throw new IllegalArgumentException("Missing required fields: clientId, amount, or serviceIds");
        }
        return invoiceRepository.save(invoice);
    }

    // Récupère toutes les factures ou celles d’un client spécifique
    public List<Invoice> getAllInvoices(String clientId) {
        if (clientId != null) {
            return invoiceRepository.findByClientId(clientId);
        }
        return invoiceRepository.findAll();
    }

    // Met à jour le statut d’une facture
    public Invoice updateInvoiceStatus(Long id, InvoiceStatus status) {
        Optional<Invoice> optionalInvoice = invoiceRepository.findById(id);
        if (optionalInvoice.isEmpty()) {
            throw new IllegalArgumentException("Invoice not found");
        }
        Invoice invoice = optionalInvoice.get();
        invoice.setStatus(status);
        return invoiceRepository.save(invoice);
    }

    // Supprime une facture par son ID
    public void deleteInvoice(Long id) {
        if (!invoiceRepository.existsById(id)) {
            throw new IllegalArgumentException("Invoice not found");
        }
        invoiceRepository.deleteById(id);
    }
}