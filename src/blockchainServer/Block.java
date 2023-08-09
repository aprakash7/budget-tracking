package blockchainServer;

import blockchainServer.blockcserver;
import blockchainServer.readblockreq;
import java.util.Date;

public class Block {

	public String hash;
	 String previousHash; 
         String project;
	 String contractor; //our data will be a simple message.
         double amount;
         String type;
	 String time; //as number of milliseconds since 1/1/1970.
         
	 int nonce;

         
         public Block()
         {
             
         }
         
	//Block Constructor.
	public Block(String project,String contractor,String type,double amount,String time,String previousHash ) {
                this.project=project;
		
		this.contractor = contractor;
		this.type = type;
                this.amount=amount;
                this.time=time;
                this.hash = calculateHash(); //Making sure we do this after we set the other values.
	
	}
        
        //Calculate new hash based on blocks contents
	public String calculateHash() {
		String calculatedhash = StringUtil.applySha256( 
				previousHash +
				
				Integer.toString(nonce) + 
				project + contractor + amount + time + type 
				);
		return calculatedhash;
	}
        
        
        public void mineBlock(int difficulty) {
		String target = new String(new char[difficulty]).replace('\0', '0'); //Create a string with difficulty * "0" 
		while(!hash.substring( 0, difficulty).equals(target)) {
			nonce ++;
			hash = calculateHash();
		}
		blockcserver.jTextArea1.append("Block Mined!!! : " + hash+"\n");
                readblockreq.previousHash=hash;
	}
}