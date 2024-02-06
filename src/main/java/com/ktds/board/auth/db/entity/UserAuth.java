package com.ktds.board.auth.db.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name="user_auth")
@Entity
public class UserAuth {

	@Id
	@Column(name = "user_id", nullable = false)
	private Long id;

	@Enumerated(EnumType.STRING)
	@Column(name= "role", nullable = false)
	private String role;

	// private String oauthId;

	// @Column(name="password")
	// private String password;
}
