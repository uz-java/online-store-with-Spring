package com.catalina.webspringbootshop.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ServerUser {
    private Long id;
    private String ipAddress;
    private String when;
    private String where;
}