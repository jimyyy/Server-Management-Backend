package com.serverProject.server.repo;

import com.serverProject.server.model.server;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServerRepo extends JpaRepository<server,Long> {
    server findByIpAddress(String ipAddress);
}
