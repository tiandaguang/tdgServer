package com.etc.utils.message;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhw
 * @Project: etc-manage
 * @Package com.etc.client
 * @Description: ${todo}
 * @date 2019/4/1 14:39
 */

@Data
public class SendClientResponse implements Serializable {
    private String status;

    private String reason;

    private List<SendArrayResponse> sendArrayResponseList;

    public SendClientResponse() {
        this.sendArrayResponseList = new ArrayList<>();
    }

    public SendClientResponse(String status, String reason) {
        this.status = status;
        this.reason = reason;
        this.sendArrayResponseList = new ArrayList<>();
    }
}
