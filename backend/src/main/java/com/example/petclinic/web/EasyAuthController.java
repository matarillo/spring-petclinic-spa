package com.example.petclinic.web;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;


@RestController
@RequestMapping("/easyauth")
public class EasyAuthController {

    @GetMapping
    public Map<String, String> getInfo(@RequestHeader Map<String, String> headers) {
        HashMap<String, String> map = new HashMap<>();
        copy(headers, map, "x-ms-token-aad-id-token");
        copy(headers, map, "x-ms-token-aad-access-token");
        copy(headers, map, "x-ms-token-aad-expires-on");
        copy(headers, map, "x-ms-token-aad-refresh-token");
        copy(headers, map, "x-ms-client-principal");
        copy(headers, map, "x-ms-client-principal-id");
        copy(headers, map, "x-ms-client-principal-name");
        copy(headers, map, "x-ms-client-principal-idp");
        return map;
    }

    private void copy(Map<String, String> src, Map<String, String> dest, String key) {
        dest.put(key, src.get(key));
    }
}
