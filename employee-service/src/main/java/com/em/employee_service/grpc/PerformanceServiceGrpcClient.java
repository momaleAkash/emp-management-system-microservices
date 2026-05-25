package com.em.employee_service.grpc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import performance.PerformanceRequest;
import performance.PerformanceResponse;
import performance.PerformanceServiceGrpc;
import performance.PerformanceServiceGrpc.PerformanceServiceBlockingStub;

import jakarta.annotation.PreDestroy;
import java.util.concurrent.TimeUnit;

@Service
public class PerformanceServiceGrpcClient {
    private static final Logger log = LoggerFactory.getLogger(PerformanceServiceGrpcClient.class);
    private final ManagedChannel channel;
    private final PerformanceServiceBlockingStub performanceServiceBlockingStub;

    public PerformanceServiceGrpcClient(@Value("${performance.service.address:localhost}") String performanceServerAddress,@Value("${performance.service.grpc.port:9001}") int serverPort){
        log.info("Creating PerformanceServiceGrpcClient for server {}:{}", performanceServerAddress, serverPort);

        channel = ManagedChannelBuilder.forAddress(performanceServerAddress,serverPort).usePlaintext().build();

        performanceServiceBlockingStub=PerformanceServiceGrpc.newBlockingStub(channel);
    }

    public PerformanceResponse createPerformanceAccount(String employeeId,String name,String email,String designation){
        PerformanceRequest request=PerformanceRequest.newBuilder().setEmployeeId(employeeId).setName(name).setEmail(email).setDesignation(designation).build();

        PerformanceResponse response=performanceServiceBlockingStub.withDeadlineAfter(5, TimeUnit.SECONDS).createPerformanceAccount(request);
        log.info("Response from PerformanceService: {}", response);

        return response;
    }

    public PerformanceResponse deletePerformanceAccount(String employeeId){
        PerformanceRequest request=PerformanceRequest.newBuilder().setEmployeeId(employeeId).build();

        PerformanceResponse response=performanceServiceBlockingStub.withDeadlineAfter(5, TimeUnit.SECONDS).deletePerformanceAccount(request);
        log.info("Response from PerformanceService: {}", response);

        return response;
    }

    @PreDestroy
    public void shutdown() {
        channel.shutdown();
    }

}
