package run.dampharm.app.search;

import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;
import javax.persistence.criteria.Predicate;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GenericSpecification<T> implements Specification<T> {

	private SearchCriteria searchCriteria;

	public GenericSpecification(final SearchCriteria searchCriteria) {
		super();
		this.searchCriteria = searchCriteria;
	}

	@Override
	public Predicate toPredicate(Root<T> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
		List arguments = searchCriteria.getArguments();
		Object arg = arguments.get(0);
		switch (searchCriteria.getSearchOperation()) {
		case LIKE:
			System.out.println(searchCriteria.getKey());
//			String[] params=searchCriteria.getKey().split("[.]");
//			Join<T, Object> groupJoin=root.join(params[0]);
			
//			return criteriaBuilder.like(groupJoin.<String>get(params[1]), (String) arg);
			return criteriaBuilder.like(root.get(searchCriteria.getKey()), (String) arg);
		case EQUALITY:
			return criteriaBuilder.equal(root.get(searchCriteria.getKey()), arg);
		case GREATER_THAN:
			return criteriaBuilder.greaterThanOrEqualTo(root.get(searchCriteria.getKey()), (Comparable) arg);
		case LESS_THAN:
			return criteriaBuilder.lessThanOrEqualTo(root.get(searchCriteria.getKey()), (Comparable) arg);
		case IN:
			return root.get(searchCriteria.getKey()).in(arguments);
		case BETWEEN:
			return criteriaBuilder.between(root.get(searchCriteria.getKey()), (Comparable) arg,
					(Comparable) arguments.get(1));
		}
		return null;
	}
}