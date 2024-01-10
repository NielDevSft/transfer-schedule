package com.transferschedule.api.models;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor()
@Setter
@Getter
@Table(name="cliente",
	uniqueConstraints=
		@UniqueConstraint(columnNames = {"cod_cliente"})
)
@Entity(name="cliente")
public class Cliente extends BaseEntity {

	@Column(name = "cod_cliente")
	public long codCliente;

	@Column(name = "categoria_plano")
	private long categoriaPlano;


	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cliente_usuairo_uuid")
	private Usuario usuairo;

	@Override
	public void isValid() {
		if(this.usuairo == null){
			throw new IllegalArgumentException("O usuario é obrigatório para a criação de clientes");
		}
	}
}
