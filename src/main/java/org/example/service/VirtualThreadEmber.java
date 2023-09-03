package org.example.service;

public class VirtualThreadEmber {
    public void impl(){
        Thread.ofPlatform()
                .start(()-> System.out.println("Platform thread: "+Thread.currentThread()));
        Thread.ofVirtual()
                .start(()-> System.out.println("Virtual thread: "+Thread.currentThread()));
    }
}
