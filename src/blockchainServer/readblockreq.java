/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blockchainServer;

import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Vector;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author Lenovo
 */
public class readblockreq extends Thread
{
    public static ArrayList<Block> blockchain = new ArrayList<Block>();
    public static int difficulty = 5;
    public static String previousHash="0";
    
    readblockreq()
    {
        super();
        start();
    }
    
    public void run()
    {
        try
        {
            ServerSocket ss=new ServerSocket(3000);
            
            while (true)
            {
                Socket soc=ss.accept();
                ObjectOutputStream oos=new ObjectOutputStream(soc.getOutputStream());
                ObjectInputStream oin=new ObjectInputStream(soc.getInputStream());
                
                String req=(String)oin.readObject();
                
                if (req.equals("ADDBLOCK"))
                {
                   String project=(String)oin.readObject();
                   String contractor=(String)oin.readObject();
                   String type=(String)oin.readObject();
                   Double amount=(Double)oin.readObject();
                   String time=(String)oin.readObject();
                  // String opr=pid+"-->"+opr2+"-->"+opr3;
                   
                   blockcserver.jTextArea1.append("Amount details recived for project "+project+"\n");
                   
                   Block b=new Block(project,contractor,type,amount,time,previousHash);
                   blockchain.add(b);
                   blockcserver.jTextArea1.append("Block Successfully added!\n");
                   blockchain.get(blockchain.size()-1).mineBlock(difficulty);
                   
                   oos.writeObject("SUCCESS");
                }
                else
                if (req.equals("GETEXPENSE"))
                {
                    String pid=(String)oin.readObject();
                    Vector v=getexpense(pid);
                    oos.writeObject(v);
                }
                else
                if (req.equals("GETMYBIDS"))
                {
                    String uname=(String)oin.readObject();
                    Vector v=getmybids(uname);
                    oos.writeObject(v);
                }
                else
                if (req.equals("VIEWLOG"))
                {
                    String user=(String)oin.readObject();
                    System.out.println(user);
                    String log=getlog(user);
                    
                    oos.writeObject(log);
                }
                
                
                oos.close();
                oin.close();
                soc.close();
                
            }
            
        }
        catch(Exception e)
        {
            System.out.println(e);
            e.printStackTrace();
        }
    }    
    
    Vector getexpense(String project)
    {
        
        Vector v=new Vector();
        //String reply="";
        
        try
        {
            for (int i=0;i<blockchain.size();i++)
            {
                Block b=blockchain.get(i);
                
                if (b.project.equals(project))
                {
                    v.add(b.contractor+"$"+b.type+"$"+b.amount+"$"+b.time);
                   // System.out.println(v.get(0).toString().trim());
                    
                }
            }
            
           
            
        }
        catch(Exception e)
        {
            System.out.println(e);
        }

        return v;
    }
    
    Vector getmybids(String contractor)
    {
        
        Vector v=new Vector();
        //String reply="";
        
        try
        {
            for (int i=0;i<blockchain.size();i++)
            {
                Block b=blockchain.get(i);
                
                if (b.contractor.equals(contractor))
                {
                    v.add(b.project+"$"+b.type+"$"+b.amount);
                   // System.out.println(v.get(0).toString().trim());
                    
                }
            }
            
           
            
        }
        catch(Exception e)
        {
            System.out.println(e);
        }

        return v;
    }
    
    
    String getlog(String user)
    {
        
        Vector<Block> v=new Vector<Block>();
        String reply="";
        /*
        try
        {
            for (int i=0;i<blockchain.size();i++)
            {
                Block b=blockchain.get(i);
                
                if (b.user.equals(user))
                {
                    v.add(b);
                }
            }
            
            for (int j=0;j<v.size();j++)
            {
                reply+=v.get(j).operation+"$";
            }
            
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
*/
        return reply;
    }
    
    
    void writelogs()
    {
        try
        {
            if (blockchain.size()>0)
            {
                JSONArray blockList = new JSONArray();
                
                for (int i=0;i<blockchain.size();i++)
                {
                    Block b=(Block)blockchain.get(i);
                    JSONObject blockdetails = new JSONObject();
                    blockdetails.put("project", b.project);
                    blockdetails.put("contractor", b.contractor);
                    blockdetails.put("type", b.type);
                    blockdetails.put("amount", b.amount);
                    blockdetails.put("time", b.time);
                    blockdetails.put("previoushash", b.previousHash);
                    blockdetails.put("hash", b.hash);
                    
                    JSONObject blockObject = new JSONObject(); 
                    blockObject.put("block", blockdetails);
                    
                    blockList.add(blockObject);
                }
                
                FileWriter file = new FileWriter("userlogs.json");
                 file.write(blockList.toJSONString()); 
                 file.flush();
                 file.close();
                
            }
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }
        
}
