package pl.ust.bookshop.user;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.ust.bookshop.model.BaseEntity;

@Getter @Setter @Builder
@AllArgsConstructor @NoArgsConstructor
@Entity
@Table(name = "user_roles",   // (name = "authorities",
uniqueConstraints = {
	      @UniqueConstraint(
	          columnNames = {"username", "user_role"},
	          name="ix_auth_username"
	      )
	   })
public class UserRole extends BaseEntity{
	

	@Column(nullable=false)
	private String username;

	@Column(name="user_role", nullable=false)
	private String userRole;

	

}
