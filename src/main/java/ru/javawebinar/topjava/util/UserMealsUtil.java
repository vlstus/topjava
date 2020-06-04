package ru.javawebinar.topjava.util;


import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class UserMealsUtil {
    public static void main(String[] args) {
        List<Meal> meals = Arrays.asList(
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );


        System.out.println(filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
        System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
    }

    public static List<MealTo> filteredByCycles
            (List<Meal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> caloriesSumByDate = new HashMap<>();
        List<Callable<Void>> tasks = new ArrayList<>();
        final List<MealTo> mealsTo = Collections.synchronizedList(new ArrayList<>());
        meals.forEach(meal -> {
            caloriesSumByDate.merge(meal.getDateTime().toLocalDate(), meal.getCalories(), Integer::sum);
            if (TimeUtil.isBetweenHalfOpen(meal.getDateTime().toLocalTime(), startTime, endTime)) {
                tasks.add(() -> {
                    mealsTo.add
                            (meal.toUserMealWithExcess
                                    (caloriesSumByDate.get(meal.getDateTime().toLocalDate()) > caloriesPerDay));
                    return null;
                });
            }
        });
        try {
            ExecutorService executorService = Executors.newCachedThreadPool();
            executorService.invokeAll(tasks);
            executorService.shutdown();
            return mealsTo;
        } catch (InterruptedException interruptedException) {
            throw new RuntimeException(interruptedException);
        }
    }


    public static List<MealTo> filteredByStreams
            (List<Meal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        return meals.stream()
                .collect(
                        new Collector<Meal, Map<LocalDate, List<Meal>>, List<MealTo>>() {

                            private final Map<LocalDate, Integer> caloriesByDate = new HashMap<>();

                            @Override
                            public Supplier<Map<LocalDate, List<Meal>>> supplier() {
                                return HashMap::new;
                            }

                            @Override
                            public BiConsumer<Map<LocalDate, List<Meal>>, Meal> accumulator() {
                                return (localDateListMap, meal) -> {
                                    caloriesByDate.merge(meal.getDateTime().toLocalDate(),
                                            meal.getCalories(),
                                            Integer::sum);
                                    localDateListMap.merge(meal.getDateTime().toLocalDate(),
                                            Collections.singletonList(meal),
                                            (first, second) -> Stream.concat(first.stream(), second.stream())
                                                    .collect(Collectors.toList()));
                                };
                            }

                            @Override
                            public BinaryOperator<Map<LocalDate, List<Meal>>> combiner() {
                                return (first, second) -> Stream.concat(first.entrySet().stream(), second.entrySet().stream())
                                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
                            }

                            @Override
                            public Function<Map<LocalDate, List<Meal>>, List<MealTo>> finisher() {
                                return localDateListMap -> localDateListMap.values().stream()
                                        .flatMap(List::stream)
                                        .filter(meal -> TimeUtil.isBetweenHalfOpen(meal.getDateTime().toLocalTime(), startTime, endTime))
                                        .map(meal -> {
                                            if (caloriesByDate.get(meal.getDateTime().toLocalDate()) > caloriesPerDay) {
                                                return meal.toUserMealWithExcess(true);
                                            } else {
                                                return meal.toUserMealWithExcess(false);
                                            }
                                        })
                                        .collect(Collectors.toList());
                            }

                            @Override
                            public Set<Characteristics> characteristics() {
                                return Collections.emptySet();
                            }
                        });
    }

}
