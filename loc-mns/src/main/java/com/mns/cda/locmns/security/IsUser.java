package com.mns.cda.locmns.security;


import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_DEFAULT','ROLE_CDA','ROLE_RESEAU','ROLE_DESIGN','ROLE_ELEVE','ROLE_PROF','ROLE_ADMINISTRATION','ROLE_COMMERCE')")
public @interface IsUser {
}
