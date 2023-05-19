package com.serverProject.server.service;

import com.serverProject.server.model.server;

import java.io.IOException;
import java.util.Collection;

public interface ServerService {
    server create (server server);
    server ping(String ipAddress) throws IOException;
    Collection<server> list(int limit);
    server get(Long id);
    server update (server server);
    Boolean delete(Long id);

}
