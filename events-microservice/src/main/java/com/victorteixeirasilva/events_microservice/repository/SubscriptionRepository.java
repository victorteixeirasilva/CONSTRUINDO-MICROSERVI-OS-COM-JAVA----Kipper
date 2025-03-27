package com.victorteixeirasilva.events_microservice.repository;

import com.victorteixeirasilva.events_microservice.domain.entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
}
