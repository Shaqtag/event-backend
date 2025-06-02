package com.rivaldo_service.prestation_service.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "database_sequences")
@Getter
@Setter
public class DatabaseSequence {

    @Id
    private String id;
    private long seq;

    // Constructeurs
    public DatabaseSequence() {}

    public DatabaseSequence(String id, long seq) {
        this.id = id;
        this.seq = seq;
    }
}
