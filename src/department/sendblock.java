/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package department;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 *
 * @author Lenovo
 */
public class sendblock 
{
    String addblock(String project,String contractor,String type,double amount)
    {
        String reply="FAILED";
       try
       {
           Socket soc=new Socket(dbpack.ips.blockchainip,dbpack.ips.blockchainport);
           ObjectOutputStream oos=new ObjectOutputStream(soc.getOutputStream());
           ObjectInputStream oin=new ObjectInputStream(soc.getInputStream());
           
           oos.writeObject("ADDBLOCK");
           oos.writeObject(project);
           oos.writeObject(contractor);
           oos.writeObject(type);
           oos.writeObject(amount);
           oos.writeObject(new java.util.Date().toString());
           
           reply=(String)oin.readObject();
       }
       catch(Exception e)
       {
           System.out.println(e);
       }
       
       return reply;
    }
    
}
