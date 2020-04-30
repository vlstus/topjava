package ru.javawebinar.topjava.util;


import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.function.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );


        System.out.println(filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
        System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, List<UserMeal>> mealsSortedByDates = new HashMap<>();
        Map<LocalDate, Integer> caloriesByDates = new HashMap<>();
        for (UserMeal userMeal :
                meals) {
            mealsSortedByDates.merge(userMeal.getDateTime().toLocalDate(),
                    Collections.singletonList(userMeal),
                    (oldValue, newValue) -> {
                        List<UserMeal> newList = new ArrayList<>();
                        newList.addAll(oldValue);
                        newList.addAll(newValue);
                        return newList;
                    });
        }
        for (Map.Entry<LocalDate, List<UserMeal>> mealsByDate :
                mealsSortedByDates.entrySet()) {
            caloriesByDates.putIfAbsent(mealsByDate.getKey(),
                    mealsByDate.getValue().stream().mapToInt(UserMeal::getCalories).sum());
        }
        List<UserMealWithExcess> filteredMeals = new LinkedList<>();
        for (Map.Entry<LocalDate, List<UserMeal>> mealsByDate :
                mealsSortedByDates.entrySet()) {
            for (UserMeal mealByDate :
                    mealsByDate.getValue()) {
                if (TimeUtil.isBetweenHalfOpen(mealByDate.getDateTime().toLocalTime(), startTime, endTime)) {
                    if (caloriesByDates.get(mealByDate.getDateTime().toLocalDate()) > caloriesPerDay) {
                        filteredMeals.add(mealByDate.toUserMealWithExcess(true));
                    } else {
                        filteredMeals.add(mealByDate.toUserMealWithExcess(false));
                    }
                }
            }
        }
        return filteredMeals;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        return meals.stream()
                .collect(new Collector<UserMeal, Map<LocalDate, List<UserMeal>>, List<UserMealWithExcess>>() {
                    @Override
                    public Supplier<Map<LocalDate, List<UserMeal>>> supplier() {
                        return TreeMap::new;
                    }

                    @Override
                    public BiConsumer<Map<LocalDate, List<UserMeal>>, UserMeal> accumulator() {
                        return (localDateListMap, userMeal) -> localDateListMap.merge
                                (userMeal.getDateTime().toLocalDate(),
                                        Collections.singletonList(userMeal),
                                        (BiFunction<List<UserMeal>, List<UserMeal>, List<UserMeal>>)
                                                (first, second) -> Stream.concat(first.stream(), second.stream())
                                                        .collect(Collectors.toList()));
                    }

                    @Override
                    public BinaryOperator<Map<LocalDate, List<UserMeal>>> combiner() {
                        return (first, second) -> Stream.concat(first.entrySet().stream(), second.entrySet().stream())
                                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
                    }

                    @Override
                    public Function<Map<LocalDate, List<UserMeal>>, List<UserMealWithExcess>> finisher() {
                        return localDateListMap -> {
                            Map<LocalDate, Integer> caloriesByDate = localDateListMap.entrySet().stream()
                                    .collect(Collectors.groupingBy
                                            (Map.Entry::getKey,
                                                    Collectors.summingInt
                                                            (mapEntry -> mapEntry.getValue()
                                                                    .stream()
                                                                    .mapToInt(UserMeal::getCalories)
                                                                    .sum())));
                            return meals.stream()
                                    .filter(userMeal -> TimeUtil.isBetweenHalfOpen(userMeal.getDateTime().toLocalTime(), startTime, endTime))
                                    .map(userMeal -> {
                                        if (caloriesByDate.get(userMeal.getDateTime().toLocalDate()) > caloriesPerDay) {
                                            return userMeal.toUserMealWithExcess(true);
                                        } else {
                                            return userMeal.toUserMealWithExcess(false);
                                        }
                                    })
                                    .collect(Collectors.toList());
                        };
                    }

                    @Override
                    public Set<Characteristics> characteristics() {
                        return Collections.emptySet();
                    }
                });


    }


}
