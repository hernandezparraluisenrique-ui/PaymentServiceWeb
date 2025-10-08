package ws.beauty.salon.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class PriceHistoryDTO {
    private Long idPrice;
    private Long idService;
    private BigDecimal oldPrice;
    private BigDecimal newPrice;
    private LocalDateTime changedAt;
    private Long changedBy;
}
