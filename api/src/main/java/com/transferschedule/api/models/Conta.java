package com.transferschedule.api.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Table(name="conta")
@Entity(name="conta")
public class Conta extends BaseEntity {
	@Column(name = "cod_conta")
	public String  codConta;
	@Column(name = "uuid_cliente")
	public long  uuidCliente;
}
