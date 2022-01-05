package br.com.ioasys.imdbservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@AllArgsConstructor
@Entity
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@Setter
@RequiredArgsConstructor
@Table(name = "roles")
@ToString
public class RoleModel implements GrantedAuthority, Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @Column(columnDefinition = "BINARY(36)")
  @GeneratedValue
  private UUID roleId;

  @Enumerated(EnumType.STRING)
  @Column(length = 16, nullable = false, unique = true)
  private RoleType name;

  @JsonIgnore
  @Override
  public String getAuthority() {
    return this.name.toString();
  }

  @Override
  public boolean equals(Object object) {
    if (this == object) return true;
    if (object == null || getClass() != object.getClass()) return false;
    if (!super.equals(object)) return false;
    RoleModel roleModel = (RoleModel) object;
    return roleId.equals(roleModel.roleId) && name == roleModel.name;
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), roleId, name);
  }
}
