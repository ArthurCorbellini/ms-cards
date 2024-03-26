package com.artcorb.cards.audit;

import java.util.Optional;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

@Component
public class AuditAwareImpl implements AuditorAware<String> {

  @Override
  public Optional<String> getCurrentAuditor() {

    // TODO implement saving with the logged user

    return Optional.of("ms-cards");
  }

}
