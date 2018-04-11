package com.lmhy.play.blockchain;

import com.lmhy.play.blockchain.tx.Transaction;
import com.lmhy.play.blockchain.tx.TransactionInput;
import com.lmhy.play.blockchain.tx.TransactionOutput;

import java.security.*;
import java.security.spec.ECGenParameterSpec;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Wallet {
    public PrivateKey privateKey;
    public PublicKey publicKey;

    public HashMap<String, TransactionOutput> utxos = new HashMap<>(); //only UTXOs owned by this wallet.

    public Wallet() {
        generateKeyPair();
    }

    public void generateKeyPair() {
        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("ECDSA", "BC");
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            ECGenParameterSpec ecSpec = new ECGenParameterSpec("prime192v1");
            keyGen.initialize(ecSpec, random);
            KeyPair keyPair = keyGen.generateKeyPair();
            privateKey = keyPair.getPrivate();
            publicKey = keyPair.getPublic();
        } catch (NoSuchAlgorithmException | NoSuchProviderException | InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }
    }

    public float getBalance() {
        float total = 0;
        for (Map.Entry<String, TransactionOutput> set : NodeChain.UTXOs.entrySet()) {
            TransactionOutput utxo = set.getValue();
            if (utxo.isMine(publicKey)) {
                utxos.put(utxo.id, utxo);
                total += utxo.value;
            }
        }
        return total;
    }

    public Transaction sendFunds(PublicKey _recipient, float value) {
        if (getBalance() < value) {
            System.out.println("#Not Enough funds to send transaction. Transaction Discarded.");
            return null;
        }

        ArrayList<TransactionInput> inputs = new ArrayList<>();
        float total = 0;
        for (Map.Entry<String, TransactionOutput> item : utxos.entrySet()) {
            TransactionOutput utxo = item.getValue();
            total += utxo.value;
            inputs.add(new TransactionInput(utxo.id));
            if (total > value) break;
        }

        Transaction newTransaction = new Transaction(publicKey, _recipient, value, inputs);
        newTransaction.generateSignature(privateKey);
        for (TransactionInput input : inputs) {
            utxos.remove(input.transactionOutputId);
        }
        return newTransaction;
    }
}
