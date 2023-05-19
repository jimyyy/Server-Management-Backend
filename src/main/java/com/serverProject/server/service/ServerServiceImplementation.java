package com.serverProject.server.service;

import com.serverProject.server.enumeration.Status;
import com.serverProject.server.model.server;
import com.serverProject.server.repo.ServerRepo;
import jakarta.servlet.Servlet;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Collection;
import java.util.Random;

import static com.serverProject.server.enumeration.Status.SERVER_DOWN;
import static com.serverProject.server.enumeration.Status.SERVER_UP;
import static java.lang.Boolean.TRUE;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class ServerServiceImplementation implements ServerService {
    private final ServerRepo serverRepo;

    @Override
    public server create(server server) {
        log.info("Saving new server :{}" , server.getName());
        server.setImageUrl(setServerImageUrl());
        return serverRepo.save(server);
    }

    @Override
    public  server ping(String ipAddress) throws IOException {
        log.info("Pinging server Ip :{}" , ipAddress);
        server server = serverRepo.findByIpAddress(ipAddress);
        InetAddress address = InetAddress.getByName(ipAddress);
        server.setStatus(address.isReachable(10000) ? SERVER_UP : SERVER_DOWN);
        serverRepo.save(server);
        return server;
    }

    @Override
    public  Collection<server> list(int limit) {

        return serverRepo.findAll(PageRequest.of(0,limit)).toList();
    }

    @Override
    public server get(Long id) {
        log.info("get by  Id :{}" , id);
        return serverRepo.findById(id).get();
    }

    @Override
    public server update(server server) {
        log.info("Upadting  server :{}" , server.getName());
        return serverRepo.save(server);
    }

    @Override
    public Boolean delete(Long id) {
        log.info("delete  by  Id :{}" , id);
        serverRepo.deleteById(id);
        return TRUE;
    }

    private String setServerImageUrl(){
        String[] imageNames = {"server1.png","server2.png","server3.png","server4.png"};

        return ServletUriComponentsBuilder.fromCurrentContextPath().path("/server/image" + imageNames[new Random().nextInt(4)]).toUriString();
    }
}
