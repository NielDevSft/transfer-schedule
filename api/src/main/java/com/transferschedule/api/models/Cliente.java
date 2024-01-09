package com.transferschedule.api.models;

import java.util.UUID;

import jakarta.persistence.*;

@Table(name="cliente")
@Entity(name="cliente")
public class Cliente extends BaseEntity {
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "cod_cliente")
	public long codCliente;

	@Column(name = "categoria_plano")
	public long categoriaPlano;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cliente_usuairo_uuid")
	public Usuario usuairo;
}
