
package com.arko.javaproject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


public class ObjectString {
    
    public static byte[] objectBytes(NewsArticle n) throws IOException{
        
      ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
      ObjectOutputStream objectStream = new ObjectOutputStream(byteStream);
      objectStream.writeObject(n);

    return byteStream.toByteArray();
}
    public static NewsArticle getObject(byte[] data) throws IOException, ClassNotFoundException{
        
        ByteArrayInputStream byteStream=new ByteArrayInputStream(data);
        ObjectInputStream objectStream = new ObjectInputStream(byteStream);
        return (NewsArticle)objectStream.readObject();
    }
    
}
