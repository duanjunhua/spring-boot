package com.duanjh.rest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-03-12 周四 15:41
 * @Version: v1.0
 * @Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BootUserResp implements Serializable {

    private String username;

    private String password;

    private String email;
}
