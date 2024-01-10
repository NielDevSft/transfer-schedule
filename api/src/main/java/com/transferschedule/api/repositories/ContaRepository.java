package com.transferschedule.api.repositories;

import com.transferschedule.api.models.Cliente;
import com.transferschedule.api.models.Conta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ContaRepository extends JpaRepository<Conta, UUID> {
    Optional<Conta> findFirstByOrderByDtaCreateAtDesc();
}
