package world.nobug.client.admin;

import java.util.List;
import org.apache.pulsar.client.admin.PulsarAdmin;
import org.apache.pulsar.client.admin.PulsarAdminException;
import org.apache.pulsar.client.api.PulsarClientException;

public class TenantApi {
    public static void main(String[] args) throws PulsarAdminException, PulsarClientException {
        PulsarAdmin admin = PulsarAdmin.builder()
                .serviceHttpUrl("pulsar://localhost:8080")
                .build();

        List<String> tenants = admin.tenants().getTenants();
        System.out.println(tenants);
    }
}
