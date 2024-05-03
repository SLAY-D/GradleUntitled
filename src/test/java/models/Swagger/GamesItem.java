package models.Swagger;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL) // Игнорирование незаполненных полей при отправке запроса, чтобы в запросе не отправлялся null
@JsonIgnoreProperties(ignoreUnknown = true) // Если возвращаются неизвестные поля, то просто игнорируем их
public class GamesItem{

	@JsonProperty("gameId")
	private Integer gameId;

	@JsonProperty("requirements")
	private Requirements requirements;

	@JsonProperty("requiredAge")
	private boolean requiredAge;

	@JsonProperty("rating")
	private Integer rating;

	@JsonProperty("description")
	private String description;

	@JsonProperty("title")
	private String title;

	@JsonProperty("tags")
	private List<String> tags;

	@JsonProperty("isFree")
	private boolean isFree;

	@JsonProperty("price")
	private Integer price;

	@JsonProperty("dlcs")
	private List<DlcsItem> dlcs;

	@JsonProperty("genre")
	private String genre;

	@JsonProperty("company")
	private String company;

	@JsonProperty("publish_date")
	private String publishDate;
}