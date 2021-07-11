package guru.springframework.msscbrewery.web.model;

import java.util.UUID;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDto {
	@Null
	private UUID id;
	@NotNull
	@Size(min = 3, max = 100)
	private String name;

}
