package ws.beauty.salon.model;

import java.security.Provider.Service;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name = "stylist_services")
public class StylistService {
    @EmbeddedId
    private StylistService id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idStylist")
    @JoinColumn(name = "id_stylist")
    //Clse de Stylist
    private Stylist stylist;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idService")
    @JoinColumn(name = "id_service")
    private Service service;

    public StylistService() {}

    /*public StylistService(Stylist stylist, Service service) {
        this.stylist = stylist;
        this.service = service;
        this.id = new StylistServicePK(stylist.getIdStylist(), service.getIdService());
    }*/


}
