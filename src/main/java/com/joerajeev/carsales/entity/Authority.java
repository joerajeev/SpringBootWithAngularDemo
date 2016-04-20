package com.joerajeev.carsales.entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * An authority (a security role) used by Spring Security.
 */
@Entity
@Table(name = "authorities")
public class Authority implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 5626347910381481599L;
	
	@NotNull
    @Size(min = 0, max = 50)
    @Id
    @Column(length = 50)
    private String username;
	
	private String authority;

    public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

	@Override
    public String toString() {
        return "Authority{" +
            "name='" + username + '\'' +
            "}";
    }
}
