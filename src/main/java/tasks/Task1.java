package tasks;

import common.Person;
import common.PersonService;

import java.util.List;
import java.util.Set;
import java.util.Map;
import java.util.Comparator;
import java.util.function.Function;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toMap;

/*
Задача 1
Метод на входе принимает List<Integer> id людей, ходит за ними в сервис
(он выдает несортированный Set<Person>, внутренняя работа сервиса неизвестна)
нужно их отсортировать в том же порядке, что и переданные id.
Оценить асимпотику работы
 */
public class Task1 {

  private final PersonService personService;

  public Task1(PersonService personService) {
    this.personService = personService;
  }

  public List<Person> findOrderedPersons(List<Integer> personIds) {
    Set<Person> persons = personService.findPersons(personIds);
    Map<Integer, Person> personsInfo = persons.stream()
            .collect(toMap(Person::getId, Function.identity()));
    return personIds.stream().map(personsInfo::get).toList();
  }
}
