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

package com.pnoker.api.auth.token.feign;

import com.pnoker.api.auth.token.hystrix.TokenAuthFeignClientHystrix;
import com.pnoker.common.bean.Response;
import com.pnoker.common.constant.Common;
import com.pnoker.common.dto.auth.TokenDto;
import com.pnoker.common.model.auth.Token;
import com.pnoker.common.model.auth.User;
import com.pnoker.common.valid.Auth;
import com.pnoker.common.valid.Update;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * <p>TokenAuthFeignClient
 *
 * @author : pnoker
 * @email : pnokers@icloud.com
 */
@FeignClient(path = Common.Service.DC3_TOKEN_URL_PREFIX, name = Common.Service.DC3_AUTH, fallbackFactory = TokenAuthFeignClientHystrix.class)
public interface TokenAuthFeignClient {

    /**
     * 生成用户 Token 令牌
     *
     * @param user
     * @return TokenDto
     */
    @PostMapping
    Response<TokenDto> generateToken(@Validated(Auth.class) @RequestBody User user);

    /**
     * 检测 Token 是否有效
     *
     * @param token
     * @return Boolean
     */
    @PostMapping("/check")
    Response<Boolean> checkTokenValid(@Validated(Auth.class) @RequestBody Token token);

}
