package com.transferschedule.api.models;

import java.util.UUID;

import jakarta.persistence.*;

@Table(name="cliente")
@Entity(name="cliente")
public class Cliente extends BaseEntity {
	@Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "cod_cliente")
	public long codCliente;
	@Column(name = "uuid_usuario_cliente")
	public UUID uuidUsuarioCliente;
	@Column(name = "categoria_plano")
	public long categoriaPlano;
}
