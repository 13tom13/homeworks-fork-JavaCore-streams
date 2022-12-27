package populationCensus;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static populationCensus.Education.HIGHER;
import static populationCensus.Sex.*;

public class Main {

    public static void main(String[] args) {
        List<String> names = Arrays.asList("Jack", "Connor", "Harry", "George", "Samuel", "John");
        List<String> families = Arrays.asList("Evans", "Young", "Harris", "Wilson", "Davies", "Adamson", "Brown");
        Collection<Person> persons = new ArrayList<>();
        for (int i = 0; i < 10_000_000; i++) {
            persons.add(new Person(
                    names.get(new Random().nextInt(names.size())),
                    families.get(new Random().nextInt(families.size())),
                    new Random().nextInt(100),
                    Sex.values()[new Random().nextInt(Sex.values().length)],
                    Education.values()[new Random().nextInt(Education.values().length)])
            );
        }

        Stream stream = persons.stream()
                .filter(Person -> Person.getAge() > 18);
        System.out.println("количество несовершеннолетних: " + stream.count());
        Stream stream1 = persons.stream()
                .filter(Person -> Person.getSex() == MAN)
                .filter(Person -> Person.getAge() >= 18)
                .filter(Person -> Person.getAge() < 27)
                .map(Person::getFamily);
        List<String> surnamesOfConscripts = (List<String>) stream1.collect(Collectors.toList());
        System.out.println("список фамилий призывников: " + surnamesOfConscripts.get(0) + "... " + surnamesOfConscripts.get(surnamesOfConscripts.size()-1));
        System.out.println("Всего призывников: " + surnamesOfConscripts.size());
        Stream stream2 = persons.stream()
                .filter(Person -> Person.getEducation() == HIGHER)
                .filter(Person -> Person.getAge() >= 18)
                .filter(Person -> Person.getSex() == WOMAN ?
                        Person.getAge() < 60 : Person.getAge() < 65)
                .sorted(Comparator.comparing(Person::getFamily));
        List<Person> workable = (List<Person>) stream2.collect(Collectors.toList());
        System.out.println("Отсортированный по фамилии список потенциально работоспособных людей с высшим образованием: ");
        System.out.println(workable.get(0));
        System.out.println(workable.get(1));
        System.out.println("...");
        System.out.println(workable.get(workable.size()-1));
        System.out.println("Всего: " + workable.size());
    }
}
