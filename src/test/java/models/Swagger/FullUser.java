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
public class FullUser{

	@JsonProperty("pass")
	private String pass;

	@JsonProperty("games")
	private List<GamesItem> games;

	@JsonProperty("login")
	private String login;
}