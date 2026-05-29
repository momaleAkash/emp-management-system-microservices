package performance;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.68.1)",
    comments = "Source: performance_service.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class PerformanceServiceGrpc {

  private PerformanceServiceGrpc() {}

  public static final java.lang.String SERVICE_NAME = "PerformanceService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<performance.PerformanceRequest,
      performance.PerformanceResponse> getCreatePerformanceAccountMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "CreatePerformanceAccount",
      requestType = performance.PerformanceRequest.class,
      responseType = performance.PerformanceResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<performance.PerformanceRequest,
      performance.PerformanceResponse> getCreatePerformanceAccountMethod() {
    io.grpc.MethodDescriptor<performance.PerformanceRequest, performance.PerformanceResponse> getCreatePerformanceAccountMethod;
    if ((getCreatePerformanceAccountMethod = PerformanceServiceGrpc.getCreatePerformanceAccountMethod) == null) {
      synchronized (PerformanceServiceGrpc.class) {
        if ((getCreatePerformanceAccountMethod = PerformanceServiceGrpc.getCreatePerformanceAccountMethod) == null) {
          PerformanceServiceGrpc.getCreatePerformanceAccountMethod = getCreatePerformanceAccountMethod =
              io.grpc.MethodDescriptor.<performance.PerformanceRequest, performance.PerformanceResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "CreatePerformanceAccount"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  performance.PerformanceRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  performance.PerformanceResponse.getDefaultInstance()))
              .setSchemaDescriptor(new PerformanceServiceMethodDescriptorSupplier("CreatePerformanceAccount"))
              .build();
        }
      }
    }
    return getCreatePerformanceAccountMethod;
  }

  private static volatile io.grpc.MethodDescriptor<performance.PerformanceRequest,
      performance.PerformanceResponse> getDeletePerformanceAccountMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "DeletePerformanceAccount",
      requestType = performance.PerformanceRequest.class,
      responseType = performance.PerformanceResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<performance.PerformanceRequest,
      performance.PerformanceResponse> getDeletePerformanceAccountMethod() {
    io.grpc.MethodDescriptor<performance.PerformanceRequest, performance.PerformanceResponse> getDeletePerformanceAccountMethod;
    if ((getDeletePerformanceAccountMethod = PerformanceServiceGrpc.getDeletePerformanceAccountMethod) == null) {
      synchronized (PerformanceServiceGrpc.class) {
        if ((getDeletePerformanceAccountMethod = PerformanceServiceGrpc.getDeletePerformanceAccountMethod) == null) {
          PerformanceServiceGrpc.getDeletePerformanceAccountMethod = getDeletePerformanceAccountMethod =
              io.grpc.MethodDescriptor.<performance.PerformanceRequest, performance.PerformanceResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "DeletePerformanceAccount"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  performance.PerformanceRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  performance.PerformanceResponse.getDefaultInstance()))
              .setSchemaDescriptor(new PerformanceServiceMethodDescriptorSupplier("DeletePerformanceAccount"))
              .build();
        }
      }
    }
    return getDeletePerformanceAccountMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static PerformanceServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<PerformanceServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<PerformanceServiceStub>() {
        @java.lang.Override
        public PerformanceServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new PerformanceServiceStub(channel, callOptions);
        }
      };
    return PerformanceServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static PerformanceServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<PerformanceServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<PerformanceServiceBlockingStub>() {
        @java.lang.Override
        public PerformanceServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new PerformanceServiceBlockingStub(channel, callOptions);
        }
      };
    return PerformanceServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static PerformanceServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<PerformanceServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<PerformanceServiceFutureStub>() {
        @java.lang.Override
        public PerformanceServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new PerformanceServiceFutureStub(channel, callOptions);
        }
      };
    return PerformanceServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public interface AsyncService {

    /**
     */
    default void createPerformanceAccount(performance.PerformanceRequest request,
        io.grpc.stub.StreamObserver<performance.PerformanceResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getCreatePerformanceAccountMethod(), responseObserver);
    }

    /**
     */
    default void deletePerformanceAccount(performance.PerformanceRequest request,
        io.grpc.stub.StreamObserver<performance.PerformanceResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getDeletePerformanceAccountMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service PerformanceService.
   */
  public static abstract class PerformanceServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return PerformanceServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service PerformanceService.
   */
  public static final class PerformanceServiceStub
      extends io.grpc.stub.AbstractAsyncStub<PerformanceServiceStub> {
    private PerformanceServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected PerformanceServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new PerformanceServiceStub(channel, callOptions);
    }

    /**
     */
    public void createPerformanceAccount(performance.PerformanceRequest request,
        io.grpc.stub.StreamObserver<performance.PerformanceResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getCreatePerformanceAccountMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void deletePerformanceAccount(performance.PerformanceRequest request,
        io.grpc.stub.StreamObserver<performance.PerformanceResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getDeletePerformanceAccountMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service PerformanceService.
   */
  public static final class PerformanceServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<PerformanceServiceBlockingStub> {
    private PerformanceServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected PerformanceServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new PerformanceServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public performance.PerformanceResponse createPerformanceAccount(performance.PerformanceRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getCreatePerformanceAccountMethod(), getCallOptions(), request);
    }

    /**
     */
    public performance.PerformanceResponse deletePerformanceAccount(performance.PerformanceRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getDeletePerformanceAccountMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service PerformanceService.
   */
  public static final class PerformanceServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<PerformanceServiceFutureStub> {
    private PerformanceServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected PerformanceServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new PerformanceServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<performance.PerformanceResponse> createPerformanceAccount(
        performance.PerformanceRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getCreatePerformanceAccountMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<performance.PerformanceResponse> deletePerformanceAccount(
        performance.PerformanceRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getDeletePerformanceAccountMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_CREATE_PERFORMANCE_ACCOUNT = 0;
  private static final int METHODID_DELETE_PERFORMANCE_ACCOUNT = 1;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final AsyncService serviceImpl;
    private final int methodId;

    MethodHandlers(AsyncService serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_CREATE_PERFORMANCE_ACCOUNT:
          serviceImpl.createPerformanceAccount((performance.PerformanceRequest) request,
              (io.grpc.stub.StreamObserver<performance.PerformanceResponse>) responseObserver);
          break;
        case METHODID_DELETE_PERFORMANCE_ACCOUNT:
          serviceImpl.deletePerformanceAccount((performance.PerformanceRequest) request,
              (io.grpc.stub.StreamObserver<performance.PerformanceResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  public static final io.grpc.ServerServiceDefinition bindService(AsyncService service) {
    return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
        .addMethod(
          getCreatePerformanceAccountMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              performance.PerformanceRequest,
              performance.PerformanceResponse>(
                service, METHODID_CREATE_PERFORMANCE_ACCOUNT)))
        .addMethod(
          getDeletePerformanceAccountMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              performance.PerformanceRequest,
              performance.PerformanceResponse>(
                service, METHODID_DELETE_PERFORMANCE_ACCOUNT)))
        .build();
  }

  private static abstract class PerformanceServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    PerformanceServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return performance.PerformanceServiceOuterClass.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("PerformanceService");
    }
  }

  private static final class PerformanceServiceFileDescriptorSupplier
      extends PerformanceServiceBaseDescriptorSupplier {
    PerformanceServiceFileDescriptorSupplier() {}
  }

  private static final class PerformanceServiceMethodDescriptorSupplier
      extends PerformanceServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    PerformanceServiceMethodDescriptorSupplier(java.lang.String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (PerformanceServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new PerformanceServiceFileDescriptorSupplier())
              .addMethod(getCreatePerformanceAccountMethod())
              .addMethod(getDeletePerformanceAccountMethod())
              .build();
        }
      }
    }
    return result;
  }
}
