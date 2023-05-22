package com.chensino.core.security.firewall;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.firewall.FirewalledRequest;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.RequestRejectedException;
import org.springframework.security.web.firewall.StrictHttpFirewall;
import org.springframework.stereotype.Component;

/**
 * 自定义防火墙，自定义后注册为组件，会自动成为默认的防火墙，原先默认的是StrictHttpFirewall，此处demo并未做任何限制，真实
 * 实现可以参考{@link StrictHttpFirewall}
 * @author chenkun
 * @createDate 2023/5/22 上午9:46
 */
@Component
@Slf4j
public class CustomFirewall implements HttpFirewall {
    @Override
    public FirewalledRequest getFirewalledRequest(HttpServletRequest request) throws RequestRejectedException {
        log.info("测试自定义防火墙");
        return  new FirewalledRequest(request) {
            @Override
            public void reset() {
                // to be implemented
            }
        };
    }

    @Override
    public HttpServletResponse getFirewalledResponse(HttpServletResponse response) {
        return response;
    }
}
