package org.example;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.*;

/**
 * @author 12
 * Create By 上午10:40
 */
public class IO {

    public void bio() throws  Exception  {
        ServerSocket server = new ServerSocket(8080);
        while (true) {
            Socket bio = server.accept();
            new Thread(()->{
                InputStream in = null;
                try {
                    in = bio.getInputStream();
                    in.read();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }).start();
        }
    }

    public void nio() throws Exception {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        Selector selector = Selector.open();
        ServerSocketChannel server = ServerSocketChannel.open();
        server.bind(new java.net.InetSocketAddress(8080));
        server.configureBlocking(false);
        server.register(selector, SelectionKey.OP_ACCEPT);
        while (selector.select()>0) {
            for(SelectionKey key:selector.selectedKeys()){
                if(key.isAcceptable()){
                    SocketChannel socket = server.accept();
                    socket.configureBlocking(false);
                    socket.register(selector,SelectionKey.OP_READ);
                }else if(key.isReadable()){
                    SocketChannel socket = (SocketChannel) key.channel();
                    socket.read(buffer);
                }
            }
        }
    }

    public void aio() throws Exception {
        AsynchronousServerSocketChannel server = AsynchronousServerSocketChannel.open();
        server.bind(new java.net.InetSocketAddress(8080));
        server.accept(null, new CompletionHandler<AsynchronousSocketChannel, Object>() {
            @Override
            public void completed(AsynchronousSocketChannel result, Object attachment) {
                server.accept(null,this);
                ByteBuffer buffer = ByteBuffer.allocate(1024);
                result.read(buffer, null, new CompletionHandler<Integer, Void>() {
                    @Override
                    public void completed(Integer result, Void attachment) {

                    }

                    @Override
                    public void failed(Throwable exc, Void attachment) {

                    }
                });
            }

            @Override
            public void failed(Throwable exc, Object attachment) {

            }
        });
    }

}
