package com.transferschedule.api.models;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
@Table(name = "transacao")
@Entity(name = "transacao")
public class Transacao extends BaseEntity {
    @Column(name = "num_val_transferencia")
    public BigDecimal numValTransferencia;
    @Column(name = "num_val_taxa_prevista")
    public BigDecimal numValTaxaPrevista;
    @Column(name = "dta_transacao")
    public Date dtaTransacao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conta_origem_uuid") // Mapeamento para a chave estrangeira da conta de origem
    private Conta contaOrigem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conta_destino_uuid") // Mapeamento para a chave estrangeira da conta de destino
    private Conta contaDestino;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_responsavel_transacao_uuid")
    public Cliente cliente;

    @Override
    public void isValid() {

    }
}
