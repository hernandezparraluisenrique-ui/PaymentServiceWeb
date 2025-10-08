package ws.beauty.salon.mapper;

import ws.beauty.salon.dto.ClientRequest;
import ws.beauty.salon.dto.ClientResponse;
import ws.beauty.salon.model.Client;

public final class ClientMapper {

    private ClientMapper() {
        // Evita instanciación
    }

    public static ClientResponse toResponse(Client client) {
        if (client == null)
            return null;
        return ClientResponse.builder()
                .idClient(client.getIdClient())
                .firstName(client.getFirstName())
                .lastName(client.getLastName())
                .email(client.getEmail())
                .phone(client.getPhone())
                .registrationDate(client.getRegistrationDate())
                .preferences(client.getPreferences())
                .satisfactionHistory(client.getSatisfactionHistory())
                .build();
    }

    public static Client toEntity(ClientRequest dto) {
        if (dto == null)
            return null;
        return Client.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .email(dto.getEmail())
                .phone(dto.getPhone())
                .registrationDate(dto.getRegistrationDate())
                .preferences(dto.getPreferences())
                .satisfactionHistory(dto.getSatisfactionHistory())
                .build();
    }

    public static void copyToEntity(ClientRequest dto, Client entity) {
        if (dto == null || entity == null)
            return;

        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setEmail(dto.getEmail());
        entity.setPhone(dto.getPhone());
        entity.setRegistrationDate(dto.getRegistrationDate());
        entity.setPreferences(dto.getPreferences());
        entity.setSatisfactionHistory(dto.getSatisfactionHistory());
    }
}
