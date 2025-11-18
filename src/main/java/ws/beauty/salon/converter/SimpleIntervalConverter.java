package ws.beauty.salon.converter;

import jakarta.persistence.AttributeConverter;
import java.time.Duration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import jakarta.persistence.Converter;



@Converter
public class SimpleIntervalConverter implements AttributeConverter<Duration, String> {
    private static final Pattern TIME_PATTERN = Pattern.compile("(\\d+):(\\d+):(\\d+)(?:\\.(\\d+))?");

    @Override
    public String convertToDatabaseColumn(Duration duration) {
        if (duration == null) return null;
        
        long totalSeconds = duration.getSeconds();
        long hours = totalSeconds / 3600;
        long minutes = (totalSeconds % 3600) / 60;
        long seconds = totalSeconds % 60;
        
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    @Override
    public Duration convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.trim().isEmpty()) return null;
        
        if (dbData.startsWith("PT") || dbData.startsWith("P")) {
            return Duration.parse(dbData);
        }
        
        Matcher matcher = TIME_PATTERN.matcher(dbData);
        if (matcher.matches()) {
            int hours = Integer.parseInt(matcher.group(1));
            int minutes = Integer.parseInt(matcher.group(2));
            int seconds = Integer.parseInt(matcher.group(3));
            
            return Duration.ofHours(hours).plusMinutes(minutes).plusSeconds(seconds);
        }
        
        throw new IllegalArgumentException("Cannot parse interval: " + dbData);
    }
}
