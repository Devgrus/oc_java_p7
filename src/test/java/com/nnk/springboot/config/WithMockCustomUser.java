package com.nnk.springboot.config;

import com.nnk.springboot.domain.Provider;
import com.nnk.springboot.domain.Role;
import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithUserDetailsSecurityContextFactory.class)
public @interface WithMockCustomUser {
    String username() default "abc";
    String password() default "abc";
    String fullname() default "abcabc";
    Role role() default Role.USER;
    Provider provider() default Provider.LOCAL;
}
