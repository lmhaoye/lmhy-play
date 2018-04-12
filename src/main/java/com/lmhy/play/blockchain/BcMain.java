package com.lmhy.play.blockchain;

import com.lmhy.play.blockchain.tx.Transaction;
import com.lmhy.play.blockchain.tx.TransactionOutput;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.security.Security;

public class BcMain {
    private static void genesisBlock(Wallet coinbase) {
        NodeChain.genesisTransaction = new Transaction(coinbase.publicKey, NodeChain.walletA.publicKey, 100f, null);
        NodeChain.genesisTransaction.generateSignature(coinbase.privateKey);
        NodeChain.genesisTransaction.transactionId = "0";
        NodeChain.genesisTransaction.outputs.add(new TransactionOutput(NodeChain.genesisTransaction.reciepient, NodeChain.genesisTransaction.value, NodeChain.genesisTransaction.transactionId));
        NodeChain.UTXOs.put(NodeChain.genesisTransaction.outputs.get(0).id, NodeChain.genesisTransaction.outputs.get(0));
    }

    private static Block startBlock(Wallet coinbase,float value) {
        Wallet walletA = NodeChain.walletA;
        Wallet walletB = NodeChain.walletB;
        int size = NodeChain.blockchain.size();
        if (size == 0) {
            genesisBlock(coinbase);
            System.out.println("Creating and Mining Genesis block... ");
            Block genesis = new Block("0");
            genesis.addTransaction(NodeChain.genesisTransaction);
            NodeChain.addBlock(genesis);
            return genesis;
        } else {
            Block preBlock = NodeChain.blockchain.get(size - 1);
            Block block = new Block(preBlock.hash);
            System.out.println("\nWalletA's balance is: " + walletA.getBalance());
            System.out.println("\nWalletA is Attempting to send funds (40) to WalletB...");
            block.addTransaction(walletA.sendFunds(walletB.publicKey, value));
            NodeChain.addBlock(block);
            System.out.println("\nWalletA's balance is: " + walletA.getBalance());
            System.out.println("WalletB's balance is: " + walletB.getBalance());
            return block;
        }
    }

    public static void main(String[] args) {

        Security.addProvider(new BouncyCastleProvider());

        NodeChain.walletA = new Wallet();
        NodeChain.walletB = new Wallet();
        Wallet coinbase = new Wallet();
        System.out.println("coin balance:" + coinbase.getBalance());
        startBlock(coinbase,0);
        startBlock(coinbase,20f);
        startBlock(coinbase,80f);
        startBlock(coinbase,0f);
//        startBlock(coinbase);
        System.out.println("coin balance:" + coinbase.getBalance());

        NodeChain.isChainValid();


       /* int i=0;
        Long start = System.currentTimeMillis();

        while(i<100){
            String preHash = "0";
            if(i>0){
                preHash = NodeChain.blockchain.get(i-1).hash;
            }
            Block block = new Block(preHash);
            NodeChain.blockchain.add(block);
            System.out.println("Trying to Mine block "+(i+1));
            block.mineBlock(NodeChain.difficulty);
            System.out.println(String.format("Block %d hash ==> %s",i,block.hash));
            i++;
        }
        System.out.print("time is:");
        System.out.println(System.currentTimeMillis() - start);*/
/*

        Block b1 = new Block("No.1", "0");
        System.out.println("block 1:" + b1.hash);

        Block b2 = new Block("No.2", b1.hash);
        System.out.println("block 2:" + b2.hash);

        Block b3 = new Block("No.3", b2.hash);
        System.out.println("block 3:" + b3.hash);
*/

    }
}
