package com.ktds.board.auth.db.entity;

import com.ktds.board.auth.db.entity.enumtype.Role;
import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "user_auth")
@Entity
public class UserAuth {

	@Id
	@Column(name = "user_id", nullable = false)
	private Long id;

	@Enumerated(EnumType.STRING)
	@Column(name = "role", nullable = false)
	private Role role;

	// private String oauthId;

	// @Column(name="password")
	// private String password;
}
