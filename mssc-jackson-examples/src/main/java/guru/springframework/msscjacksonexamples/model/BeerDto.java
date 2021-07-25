package guru.springframework.msscjacksonexamples.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import javax.validation.constraints.Positive;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BeerDto {
	@JsonProperty("beerId")
	@Null
	private UUID id;
	@NotBlank
	private String beerName;
	@NotBlank
	private String beerStyle;
	@Positive
	private Long upc;
	
	@JsonFormat(shape = Shape.STRING)
	private BigDecimal price;
	@JsonFormat(pattern = "yyyy-MM-dd", shape = Shape.STRING)
	private OffsetDateTime createdDate;
	private OffsetDateTime lastUpdatedTime;

	@JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate myLocalDate;
}
