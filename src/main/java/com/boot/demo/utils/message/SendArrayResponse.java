package com.boot.demo.utils.message;

import lombok.Data;

import java.io.Serializable;

/**
 * @author zhw
 * @Project: etc-manage
 * @Package com.etc.client
 * @Description: ${todo}
 * @date 2019/4/1 15:14
 */

@Data
public class SendArrayResponse implements Serializable {

    private String mobile;

    private String reason;

    private String status;
}
