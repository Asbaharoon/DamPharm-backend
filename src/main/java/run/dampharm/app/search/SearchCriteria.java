package run.dampharm.app.search;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchCriteria {

	private String key;
	private SearchOperation searchOperation;
	private boolean isOrOperation;
	private List<Object> arguments;
}