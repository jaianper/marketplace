package com.jaianper.marketplace.user.infrastructure.persistence;

import com.jaianper.marketplace.user.domain.Email;
import com.jaianper.marketplace.user.domain.Role;
import com.jaianper.marketplace.user.domain.User;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

/** Persistence Entity for R2DBC. Decoupled from Domain Model using mapping methods. */
@Table("users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {
  @Id private UUID id;
  private String email;
  private String password;
  private String roles; // Store as comma-separated string or generic
  private LocalDateTime createdAt;
  private LocalDateTime lastLogin;

  // Mapper: Domain -> Entity
  public static UserEntity fromDomain(User user) {
    return UserEntity.builder()
        .id(user.getId())
        .email(user.getEmail().value())
        .password(user.getPasswordHash())
        .roles(user.getRoles().stream().map(Enum::name).collect(Collectors.joining(",")))
        .createdAt(user.getCreatedAt())
        .lastLogin(user.getLastLogin())
        .build();
  }

  // Mapper: Entity -> Domain
  public User toDomain() {
    Set<Role> roleSet =
        Set.of(roles.split(",")).stream().map(Role::valueOf).collect(Collectors.toSet());

    return new User(this.id, new Email(this.email), this.password, roleSet, this.createdAt);
  }
}
