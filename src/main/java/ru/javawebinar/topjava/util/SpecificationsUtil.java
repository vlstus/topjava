package ru.javawebinar.topjava.util;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;


public class SpecificationsUtil {

    public static <T, O> Specification<T> fieldIsEqual(O objectToCompare, String... fieldName) {
        return (Specification<T>) (root, query, criteriaBuilder) -> {
            Path<T> objectPath = null;
            for (int i = 0; i < fieldName.length; i++) {
                objectPath = root.get(fieldName[i++]);
            }
            return criteriaBuilder.equal(objectPath, objectToCompare);
        };
    }


    public static <T> Specification<T> fieldIsEqual(T objectToCompare, String fieldName) {
        return (Specification<T>) (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(fieldName), objectToCompare);
    }

    public static <T, O extends Comparable<? super O>> Specification<T> greaterThan(O objectToCompare, String fieldName) {
        return (Specification<T>) (root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThan(root.get(fieldName), objectToCompare);
    }

    public static <T, O extends Comparable<? super O>> Specification<T> greaterThanOrEquals(O objectToCompare, String fieldName) {
        return (Specification<T>) (root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThanOrEqualTo(root.get(fieldName), objectToCompare);
    }

    public static <T, O extends Comparable<? super O>> Specification<T> lessThen(O objectToCompare, String fieldName) {
        return (Specification<T>) (root, query, criteriaBuilder) ->
                criteriaBuilder.lessThan(root.get(fieldName), objectToCompare);
    }

    public static <T, O extends Comparable<? super O>> Specification<T> lessThenOrEquals(O objectToCompare, String fieldName) {
        return (Specification<T>) (root, query, criteriaBuilder) ->
                criteriaBuilder.lessThanOrEqualTo(root.get(fieldName), objectToCompare);
    }
}
