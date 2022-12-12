package tasks;

import common.Area;
import common.Person;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

/*
Имеются
- коллекция персон Collection<Person>
- словарь Map<Integer, Set<Integer>>, сопоставляющий каждой персоне множество id регионов
- коллекция всех регионов Collection<Area>
На выходе хочется получить множество строк вида "Имя - регион". Если у персон регионов несколько, таких строк так же будет несколько
 */
public class Task6 {

  public static Set<String> getPersonDescriptions(Collection<Person> persons,
                                                  Map<Integer, Set<Integer>> personAreaIds,
                                                  Collection<Area> areas) {
    Map<Integer, Area> areasInfo = areas.stream().collect(Collectors.toMap(Area::getId, Function.identity()));
    return  persons.stream()
            .flatMap(person -> personAreaIds.get(person.getId())
                    .stream()
                    .map(areaId -> getPersonDescription(person, areasInfo.get(areaId))))
            .collect(Collectors.toSet());
  }

  private static String getPersonDescription(Person person, Area area) {
    return String.format("%s - %s", person.getFirstName(), area.getName());
  }
}
