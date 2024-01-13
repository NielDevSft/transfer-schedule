package com.transferschedule.api.repositories;

import com.transferschedule.api.models.authentication.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, Long>
{
    
}
