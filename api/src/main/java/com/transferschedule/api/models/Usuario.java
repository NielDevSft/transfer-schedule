package com.transferschedule.api.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Table(name="usuario")
@Entity(name="usuario")
public class Usuario extends BaseEntity {
	@Column(name = "des_nome")
	public String desNome;
	@Column(name = "des_email")
	public String desEmail;
	@Column(name = "cod_role")
	public long codRole;
	@Column(name = "hash_password")
	public String hashPassword;
}
