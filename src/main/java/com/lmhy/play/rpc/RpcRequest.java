package com.lmhy.play.rpc;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class RpcRequest implements Serializable {
    private static final long serialVersionUID = 7503710091945320739L;

    private String method;
    private String name;
    private String doing;
    private int num;

}
