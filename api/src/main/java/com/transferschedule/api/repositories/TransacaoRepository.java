package com.transferschedule.api.repositories;

import com.transferschedule.api.models.Cliente;
import com.transferschedule.api.models.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TransacaoRepository extends JpaRepository<Transacao, UUID> {
    Optional<Transacao> findFirstByOrderByDtaCreateAtDesc();
    List<Transacao> findAllByClienteUuid(UUID uuid);
}
