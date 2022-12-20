package com.citalacki_dnevnik.server.model.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.citalacki_dnevnik.server.model.AbstractEntity;
import com.citalacki_dnevnik.server.model.user.enums.UserRight;
import com.citalacki_dnevnik.server.model.user.enums.UserViewRole;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
@Entity
@Table(name = "user_permission")
public class UserPermission extends AbstractEntity {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Column(name = "view_name", length = 50, nullable = false)
    private String viewName;

    @NotNull
    @Column(name = "view_right", nullable = false)
    private UserRight viewRight;


    @NotNull
    @Column(name = "user_view_role", nullable = false)
    private UserViewRole userViewRole;


    @JsonIgnore
    @ManyToMany(mappedBy = "userPermissions")
    private List<UserGroup> userGroup = new ArrayList<>();
}
