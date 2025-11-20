package com.Enotes_Api_Service.Enotes_Api.Config;

import com.Enotes_Api_Service.Enotes_Api.entity.User;
import com.Enotes_Api_Service.Enotes_Api.handler.CommonUtil;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

public class AuditAwareConfig implements AuditorAware<Integer> {
    @Override
    public Optional<Integer> getCurrentAuditor() {

        User user = CommonUtil.getLoggedUser();
        return Optional.of(user.getId());
    }
}
