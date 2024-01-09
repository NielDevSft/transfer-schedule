package com.transferschedule.api.models;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Table(name="transacao")
@Entity(name="transacao")
public class Transacao extends BaseEntity {
		@Column(name = "uuid_usuario")
		public UUID uuidUsuario;
		@Column(name = "cod_conta_origem")
		public String codContaOrigem;
		@Column(name = "cod_conta_destino")
		public String codContaDestino;
		@Column(name = "num_val_transferencia")
		public BigDecimal numValTransferencia;
		@Column(name = "num_val_taxa_prevista")
		public BigDecimal numValTaxaPrevista;
		@Column(name = "dta_transacao")
		public Date dtaTransacao;

}
