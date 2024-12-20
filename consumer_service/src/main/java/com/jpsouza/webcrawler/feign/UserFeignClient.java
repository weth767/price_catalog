package com.jpsouza.webcrawler.feign;

import com.jpsouza.webcrawler.feign.dtos.UserFeignDTO;
import java.util.Optional;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "user", url = "${feign.user.url}")
public interface UserFeignClient {
    @RequestMapping(method = RequestMethod.GET, value = "/by-email/{email}")
    Optional<UserFeignDTO> findByEmail(@PathVariable String email);

    @RequestMapping(method = RequestMethod.GET, value = "/by-username/{username}")
    Optional<UserFeignDTO> findByUsername(@PathVariable String username);
}
