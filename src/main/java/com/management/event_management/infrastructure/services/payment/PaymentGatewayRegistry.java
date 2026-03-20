package com.management.event_management.infrastructure.services.payment;

import com.management.event_management.domain.enums.PaymentMethod;
import com.management.event_management.domain.services.payment.PaymentGateway;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Component
public class PaymentGatewayRegistry {
    private final Map<PaymentMethod, PaymentGateway> gatewayMap = new EnumMap<>(PaymentMethod.class);

    public PaymentGatewayRegistry(List<PaymentGateway> gateways) {
        for (PaymentGateway gateway : gateways) {
            PaymentMethod method = gateway.getSupportedMethod();
            if (gatewayMap.containsKey(method)) {
                throw new IllegalStateException("Duplicate PaymentGateway for method: " + method);
            }
            gatewayMap.put(method, gateway);
        }
    }

    public PaymentGateway getGateway(PaymentMethod method) {
        PaymentGateway gateway = gatewayMap.get(method);
        if (gateway == null) {
            throw new IllegalArgumentException("No PaymentGateway found for method: " + method);
        }
        return gateway;
    }
}