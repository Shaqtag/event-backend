package com.projet.facture.service_facturation.modele;

public class MyJsonClass {
    private String field1;
    private int field2;

    public MyJsonClass() {}

    public MyJsonClass(String field1, int field2) {
        this.field1 = field1;
        this.field2 = field2;
    }

    // getters et setters
    public String getField1() {
        return field1;
    }

    public void setField1(String field1) {
        this.field1 = field1;
    }

    public int getField2() {
        return field2;
    }

    public void setField2(int field2) {
        this.field2 = field2;
    }
}
