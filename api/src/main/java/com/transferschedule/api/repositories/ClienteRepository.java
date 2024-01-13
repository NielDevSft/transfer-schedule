package com.transferschedule.api.repositories;

import com.transferschedule.api.models.Cliente;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ClienteRepository extends JpaRepository<Cliente, UUID> {
    Optional<Cliente> findFirstByOrderByDtaCreateAtDesc();
    Optional<Cliente> findFirstByUsuairoId(long id);
}
