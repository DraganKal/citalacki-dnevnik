package com.citalacki_dnevnik.server.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 Abstraction used for every entity with status
 */

@MappedSuperclass
@Data
@ToString(callSuper = true, exclude = "entityStatus")
@NoArgsConstructor
@AllArgsConstructor
public abstract class AbstractStatusEntity extends AbstractEntity {

    private static final long serialVersionUID = 1L;

    @Column(name = "entity_status", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
    private EntityStatus entityStatus;

}
