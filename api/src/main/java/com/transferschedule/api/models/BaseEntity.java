package com.transferschedule.api.models;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "uuid")
@MappedSuperclass
public abstract class BaseEntity {
	
	@Id @GeneratedValue(strategy = GenerationType.UUID)
	protected UUID uuid;
	protected boolean  deleted;
	@Column(name = "dta_create_at")
	protected Date  dtaCreateAt;
	@Column(name = "dta_update_at")
	protected Date  dtaUpdateAt;
	@Column(name = "dta_delete_at", nullable = true)
	protected Date dtaDeleteAt;

	@PrePersist
	private void prePersist() {
		// Define a data atual ao persistir a entidade, se o campo estiver vazio
		dtaCreateAt = new Date();
		dtaUpdateAt = new Date();
		deleted = false;
	}

	public abstract void isValid();
}
