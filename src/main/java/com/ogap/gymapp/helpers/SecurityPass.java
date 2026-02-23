package com.ogap.gymapp.helpers;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Component
public class SecurityPass {
    @Value("${app.dev.coach-id}")
    private String devCoachId;

    @Value("${app.dev.user-id}")
    private String devUserId;

    public String getCoachId(Jwt jwt) {
        if (jwt != null) return jwt.getSubject();

        System.out.println("⚠️ Modo Dev: Usando " + devCoachId);
        return devCoachId;
    }

    public String getUserId(Jwt jwt) {
        if (jwt != null) return jwt.getSubject();

        System.out.println("⚠️ Modo Dev: Usando " + devUserId);
        return devUserId;
    }
}
