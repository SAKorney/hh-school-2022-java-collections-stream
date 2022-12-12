package tasks;

import common.Person;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Comparator.nullsFirst;
import static java.util.Comparator.naturalOrder;

/*
Задача 3
Отсортировать коллекцию сначала по фамилии, по имени (при равной фамилии), и по дате создания (при равных фамилии и имени)
 */
public class Task3 {

  public static List<Person> sort(Collection<Person> persons) {
    return persons.stream()
            .sorted(Comparator
                    .comparing(Person::getSecondName, nullsFirst(naturalOrder()))
                    .thenComparing(Person::getFirstName, nullsFirst(naturalOrder()))
                    .thenComparing(Person::getCreatedAt, nullsFirst(naturalOrder()))
            ).collect(Collectors.toList());
  }
}
