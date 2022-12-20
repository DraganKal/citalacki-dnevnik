package com.citalacki_dnevnik.server.config.auth;

import com.citalacki_dnevnik.server.model.user.enums.UserViewRole;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
    Classes that implement PermissionEvaluator are used whenever @PreAuthorize annotation is used. It has 2 methods (both called
    hasPermission. Which hasPermission method is used depends on how many paramteres you are sending through annotation.
    This class is used for custom evaluaton of permission
 */

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CustomPermissionEvaluator implements PermissionEvaluator {


    @Override
    public boolean hasPermission(Authentication authentication,  Object targetDomainObject, Object permission) {
        if ((authentication == null) || (targetDomainObject == null) || !(permission instanceof String)) {
            return false;
        }

        return hasPrivilege(authentication, (String) targetDomainObject, (String) permission, UserViewRole.ALL.toString());
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetDomainObject, Object permission) {
        if ((authentication == null) || (targetId == null) || (targetDomainObject == null) ||!(permission instanceof String)) {
            return false;
        }

        return hasPrivilege(authentication, targetDomainObject, (String) permission, UserViewRole.OWN.toString());
    }

    private boolean hasPrivilege(Authentication auth, String viewName, String permission, String userViewRole) {
        String authority = viewName + "/" + permission + "/" + userViewRole;
        return auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equalsIgnoreCase(authority));
    }
}
