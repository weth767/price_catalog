package com.jpsouza.webcrawler.feign;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "user", url = "${feign.user.url}")

public class UserFeignClient {
}
