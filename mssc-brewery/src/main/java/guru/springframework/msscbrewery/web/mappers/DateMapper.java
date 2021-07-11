package guru.springframework.msscbrewery.web.mappers;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import org.springframework.stereotype.Component;

@Component
public class DateMapper {

	public OffsetDateTime asOffsetDateTime(Timestamp ts) {
		if(ts == null) {
			return null;
		}
		LocalDateTime localDateTime = ts.toLocalDateTime();
		return OffsetDateTime.of(localDateTime.getYear(),
				localDateTime.getMonthValue(),
				localDateTime.getDayOfMonth(),
				localDateTime.getHour(),
				localDateTime.getMinute(),
				localDateTime.getSecond(),
				localDateTime.getNano(),
				ZoneOffset.UTC);
	}
	
	public Timestamp asTimestamp(OffsetDateTime odt) {
		if(odt == null) {
			return null;
		}
		return Timestamp.valueOf(odt.atZoneSameInstant(ZoneOffset.UTC).toLocalDateTime());
	}
}
