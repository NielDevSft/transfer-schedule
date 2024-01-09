package com.transferschedule.api.models;

import jakarta.persistence.*;

@Table(name="conta")
@Entity(name="conta")
public class Conta extends BaseEntity {
	@Column(name = "cod_conta")
	public String  codConta;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "conta_cliente_uuid")
	public Cliente cliente;
}
