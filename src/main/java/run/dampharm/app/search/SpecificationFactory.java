package run.dampharm.app.search;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class SpecificationFactory<T> {

	public Specification<T> isLike(String key, Comparable arg) {
		GenericSpecificationsBuilder<T> builder = new GenericSpecificationsBuilder<>();
		return builder.with(key, SearchOperation.LIKE, Collections.singletonList(arg)).build();
	}

	public Specification<T> isEqual(String key, Object arg) {
		GenericSpecificationsBuilder<T> builder = new GenericSpecificationsBuilder<>();
		return builder.with(key, SearchOperation.EQUALITY, Collections.singletonList(arg)).build();
	}

	public Specification<T> isGreaterThan(String key, Comparable arg) {
		GenericSpecificationsBuilder<T> builder = new GenericSpecificationsBuilder<>();
		return builder.with(key, SearchOperation.GREATER_THAN, Collections.singletonList(arg)).build();
	}

	public Specification<T> isBetween(String key, Object... args) {
		GenericSpecificationsBuilder<T> builder = new GenericSpecificationsBuilder<>();
		return builder.with(key, SearchOperation.BETWEEN, Arrays.asList(args)).build();
	}

	public Specification<T> in(String key, List args) {
		GenericSpecificationsBuilder<T> builder = new GenericSpecificationsBuilder<>();
		return builder.with(key, SearchOperation.IN, args).build();
	}

	public Specification<T> isLessThan(String key, Comparable arg) {
		GenericSpecificationsBuilder<T> builder = new GenericSpecificationsBuilder<>();
		return builder.with(key, SearchOperation.LESS_THAN, Collections.singletonList(arg)).build();
	}
}