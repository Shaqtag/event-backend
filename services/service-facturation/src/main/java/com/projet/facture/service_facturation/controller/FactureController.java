package com.example.billing.controller;

import com.example.billing.model.Invoice;
import com.example.billing.model.InvoiceStatus;
import com.example.billing.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Contrôleur REST pour gérer les endpoints liés aux factures
@RestController
@RequestMapping("/invoices")
public class InvoiceController {

    // Injection du service pour la logique métier
    @Autowired
    private InvoiceService invoiceService;

    // Crée une nouvelle facture
    @PostMapping
    public ResponseEntity<Invoice> createInvoice(@RequestBody Invoice invoice) {
        try {
            Invoice createdInvoice = invoiceService.createInvoice(invoice);
            return new ResponseEntity<>(createdInvoice, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    // Liste toutes les factures ou celles d’un client spécifique
    @GetMapping
    public ResponseEntity<List<Invoice>> getAllInvoices(@RequestParam(required = false) String clientId) {
        List<Invoice> invoices = invoiceService.getAllInvoices(clientId);
        return new ResponseEntity<>(invoices, HttpStatus.OK);
    }

    // Met à jour le statut d’une facture
    @PutMapping("/{id}")
    public ResponseEntity<Invoice> updateInvoiceStatus(@PathVariable Long id, @RequestBody InvoiceStatus status) {
        try {
            Invoice updatedInvoice = invoiceService.updateInvoiceStatus(id, status);
            return new ResponseEntity<>(updatedInvoice, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    // Supprime une facture
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteInvoice(@PathVariable Long id) {
        try {
            invoiceService.deleteInvoice(id);
            return new ResponseEntity<>("Invoice deleted successfully", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("Invoice not found", HttpStatus.NOT_FOUND);
        }
    }
}