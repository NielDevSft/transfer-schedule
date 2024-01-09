package com.transferschedule.api.models;
import java.util.Date;
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
	public UUID uuid;
	public boolean  deleted;
	@Column(name = "dta_create_at")
	public Date  dtaCreateAt;
	@Column(name = "dta_update_at")
	public Date  dtaUpdateAt;
	@Column(name = "dta_delete_at")
	public Date  dtaDeleteAt;
	
}
