package com.self.framework.config.oauth;

import com.alibaba.druid.wall.violation.ErrorCode;
import com.self.framework.cenum.HttpResultEnum;
import com.self.framework.http.HttpResult;
import com.self.framework.ucenter.vo.TokenVo;
import com.self.framework.utils.ConvertDataUtil;
import com.self.framework.utils.ObjectCheckUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.expression.spel.ast.NullLiteral;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Component;

/**
 * @author qiuhang
 * @desc：${DESCRIPTION}
 * @date 2019/10/5/005
 * @return ${return}
 */
@Component
@Aspect
public class MyOAuthTokenResultAop {

    @Around("execution(* org.springframework.security.oauth2.provider.endpoint.TokenEndpoint.postAccessToken(..))")
    public ResponseEntity<TokenVo> hanldControllerMethod(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object proceed = proceedingJoinPoint.proceed();
        TokenVo tokenVo = new TokenVo();
        if (!ObjectCheckUtil.checkIsNullOrEmpty(proceed)){
            ResponseEntity<OAuth2AccessToken> tokenResponseEntity = (ResponseEntity<OAuth2AccessToken>) proceed;
            if (tokenResponseEntity.getStatusCode().is2xxSuccessful()){
                OAuth2AccessToken token = tokenResponseEntity.getBody();
                tokenVo.setAccessToken(token.getValue());
                tokenVo.setRefreshToken(token.getRefreshToken().getValue());
                tokenVo.setAccessTokenExpireTime(ConvertDataUtil.convertStr(token.getExpiresIn()));
                return ResponseEntity.status(HttpResultEnum.SUCCESS.getCode()).body(tokenVo);
            }else {
                tokenVo.setErrorCode(HttpResultEnum.USER_ACCESS_TOKEN_FAILED.getCode());
                tokenVo.setErrorMsg(HttpResultEnum.USER_ACCESS_TOKEN_FAILED.getMessage());
            }
        }
        return ResponseEntity.status(HttpResultEnum.SUCCESS.getCode()).body(tokenVo);
    }
}
