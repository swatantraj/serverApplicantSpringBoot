package com.mytaxi.datatransferobject;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {
	@JsonIgnore
	private Long id;

	@NotNull(message = "Username can not be null!")
	private String username;

	@NotNull(message = "Password can not be null!")
	private String password;

	@NotNull(message = "Password can not be null!")
	private String role;

	private UserDTO() {
	}

	private UserDTO(Long id, String username, String password, String role) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.role = role;
	}

	public static UserDTOBuilder newBuilder() {
		return new UserDTOBuilder();
	}

	@JsonProperty
	public Long getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public String getRole() {
		return role;
	}

	public static class UserDTOBuilder {
		private Long id;
		private String username;
		private String password;
		private String role;

		public UserDTOBuilder setId(Long id) {
			this.id = id;
			return this;
		}

		public UserDTOBuilder setUsername(String username) {
			this.username = username;
			return this;
		}

		public UserDTOBuilder setPassword(String password) {
			this.password = password;
			return this;
		}

		public UserDTOBuilder setRole(String role) {
			this.role = role;
			return this;
		}

		public UserDTO createUserDTO() {
			return new UserDTO(id, username, password, role);
		}

	}
	
	@Override
	public String toString() {
		return "UserId = " + getId() + " UserName = " + getUsername() + " UserRole = " + getRole(); 
	}
}
