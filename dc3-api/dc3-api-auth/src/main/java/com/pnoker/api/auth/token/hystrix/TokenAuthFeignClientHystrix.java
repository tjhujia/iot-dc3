/*
 * Copyright 2019 Pnoker. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.pnoker.api.auth.token.hystrix;

import com.pnoker.api.auth.token.feign.TokenAuthFeignClient;
import com.pnoker.common.bean.Response;
import com.pnoker.common.dto.auth.TokenDto;
import com.pnoker.common.model.auth.Token;
import com.pnoker.common.model.auth.User;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * <p>TokenAuthFeignClientHystrix
 *
 * @author : pnoker
 * @email : pnokers@icloud.com
 */
@Slf4j
@Component
public class TokenAuthFeignClientHystrix implements FallbackFactory<TokenAuthFeignClient> {

    @Override
    public TokenAuthFeignClient create(Throwable throwable) {
        String message = throwable.getMessage() == null ? "No available server for client: DC3-AUTH" : throwable.getMessage();
        log.error("TokenAuthFeignClient:{},hystrix服务降级处理", message, throwable);

        return new TokenAuthFeignClient() {

            @Override
            public Response<TokenDto> generateToken(User user) {
                return Response.fail(message);
            }

            @Override
            public Response<Boolean> checkTokenValid(Token token) {
                return Response.fail(message);
            }
        };
    }
}