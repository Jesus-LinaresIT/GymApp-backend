package com.ogap.gymapp.config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ClerkRoleConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

   @Override
   public Collection<GrantedAuthority> convert(Jwt jwt) {
      // Clerk often stores metadata in "public_metadata" or custom claims.
      // Assuming "roles" is a list of strings implementation.
      // If roles are nested in public_metadata, we need to extract from there.
      // For this implementation, I will check both top-level "roles" claim and
      // "public_metadata.roles"

      List<String> roles = getRoles(jwt);

      return roles.stream()
            .map(role -> role.startsWith("ROLE_") ? role : "ROLE_" + role)
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toList());
   }

   @SuppressWarnings("unchecked")
   private List<String> getRoles(Jwt jwt) {
      if (jwt.hasClaim("roles")) {
         return jwt.getClaimAsStringList("roles");
      }

      Map<String, Object> publicMetadata = jwt.getClaim("public_metadata");
      if (publicMetadata != null && publicMetadata.containsKey("roles")) {
         Object rolesObj = publicMetadata.get("roles");
         if (rolesObj instanceof List) {
            return (List<String>) rolesObj;
         }
      }

      return List.of("USER"); // Default role if none found
   }
}
