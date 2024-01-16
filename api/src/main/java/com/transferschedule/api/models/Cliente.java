package com.transferschedule.api.models;

import com.transferschedule.api.models.authentication.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@NoArgsConstructor()
@Setter
@Getter
@Table(name = "cliente",
        uniqueConstraints =
        @UniqueConstraint(columnNames = {"cod_cliente"})
)
@Entity(name = "cliente")
public class Cliente extends BaseEntity {

    @Column(name = "cod_cliente")
    public long codCliente;

    @Column(name = "des_nome_completo")
    public String desNomeCompleto;

    @Column(name = "desCPF")
    public String desCpf;

    @Column(name = "dta_nascimento")
    public Date dtaNascimento;

    @Column(name = "num_telefone")
    public String numTelefone;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_usuairo_uuid")
    private User usuairo;

    @Override
    public void isValid() {
        if (this.usuairo == null) {
            throw new IllegalArgumentException("O usuario é obrigatório para a criação de clientes");
        }
    }
}
