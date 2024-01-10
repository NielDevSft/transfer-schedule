package com.transferschedule.api.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor()
@Setter
@Getter
@Table(name="conta")
@Entity(name="conta")
public class Conta extends BaseEntity {
	@Column(name = "cod_conta")
	public long codConta;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "conta_cliente_uuid")
	public Cliente cliente;

	@Override
	public void isValid() {

	}
}
