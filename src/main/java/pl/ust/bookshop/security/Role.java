package pl.ust.bookshop.security;

import lombok.Getter;

@Getter
public enum Role {

    ADMIN("ADMIN", "admin"),
    USER("USER", "user"),
    DEVELOPER("DEVELOPER", "dev"),
    LIBRARIAN("LIBRARIAN", "lib");

    private String role;
    private String login;

    Role(String role, String login) {
        this.role = role;
        this.login = login;
    }

    public boolean isEqualTo(String role) {
        return this.role.equals(role);
    }

    public String value() {
        return role;
    }
    
    
    
    

}