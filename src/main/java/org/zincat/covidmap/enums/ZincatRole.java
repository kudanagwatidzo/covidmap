package org.zincat.covidmap.enums;

import org.springframework.security.core.GrantedAuthority;

public enum ZincatRole implements GrantedAuthority {
    ROLE_SUPERADMIN,
    ROLE_USER;

    public String getAuthority()
    {
        return name();
    }
}
