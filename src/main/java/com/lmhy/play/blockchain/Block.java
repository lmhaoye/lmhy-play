package com.lmhy.play.blockchain;

import com.lmhy.play.blockchain.tx.Transaction;

import java.util.ArrayList;

public class Block {
    public String hash;
    public String previousHash;
    public String merkleRoot;
    public ArrayList<Transaction> transactions = new ArrayList<>();
    private long timeStamp;
    private int nonce;

    public Block(String previousHash) {
        this.previousHash = previousHash;
        this.timeStamp = System.currentTimeMillis();

        this.hash = calculateHash();
    }

    public String calculateHash() {
        return StringUtil.applySha256(this.previousHash + Long.toString(timeStamp) + Integer.toString(nonce) + merkleRoot);
    }

    public void mineBlock(int difficulty) {
        merkleRoot = StringUtil.getMerkleRoot(transactions);
        String target = StringUtil.getDificultyString(difficulty);
        while (!hash.substring(0, difficulty).equals(target)) {
            nonce++;
            hash = calculateHash();
        }
        System.out.println("Block Mined ==> " + hash);
    }

    public boolean addTransaction(Transaction transaction) {
        if (transaction == null) {
            return false;
        }
        if (previousHash != "0" && !transaction.processTransaction()) {
            System.out.println("Transaction failed to process. Discarded.");
            return false;
        }
        transactions.add(transaction);
        System.out.println("Transaction Successfully added to Block");
        return true;
    }
}
