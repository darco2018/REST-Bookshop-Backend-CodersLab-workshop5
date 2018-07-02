package pl.ust.bookshop.user;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.ust.bookshop.model.BaseEntity;


@Getter @Setter @Builder 
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(of = {"username"}, callSuper = false) 
@Entity
@Table(name = "users")
public class User extends BaseEntity{

	/*@Id
	@GeneratedValue*/
	@Column(unique = true, nullable=false) 
	private String username;

	@Column(nullable=false) 
	private String password;
	
	@Column(nullable=false) 
	private boolean enabled;

	@JsonIgnore
	@OneToMany(mappedBy="username", cascade = CascadeType.MERGE, fetch=FetchType.LAZY) 
	private List<UserRole> userRoles;
	


}
