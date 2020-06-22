package com.mtucholski.reservation.app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity(name = "roles")
@Table( name ="roles", uniqueConstraints = @UniqueConstraint(columnNames ="username"))
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Role extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "username")
    @JsonIgnore
    private User user;

    @Column(name = "role", nullable = false)
    @NotEmpty
    private String roleName;
}
