import java.lang.reflect.Field;
import java.util.*;

class Monster {
    String eyeColor;
    String featherColor;
    String magicalAbility;
    int size;
    int strength;
    int durability;
    int weakness;
    int aggressionLevel;

    public Monster(String eyeColor, String featherColor, String magicalAbility, int size,
                   int strength, int durability, int weakness, int aggressionLevel) {
        this.eyeColor = eyeColor;
        this.featherColor = featherColor;
        this.magicalAbility = magicalAbility;
        this.size = size;
        this.strength = strength;
        this.durability = durability;
        this.weakness = weakness;
        this.aggressionLevel = aggressionLevel;
    }

    public Monster() {

    }


    public void printTraits() {
        System.out.println("Eye Color: " + eyeColor);
        System.out.println("Feather Color: " + featherColor);
        System.out.println("Magical Ability: " + magicalAbility);
        System.out.println("Size: " + size);
        System.out.println("Strength: " + strength);
        System.out.println("Durability: " + durability);
        System.out.println("Weakness: " + weakness);
        System.out.println("Aggression Level: " + aggressionLevel);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Monster monster = (Monster) o;
        return size == monster.size && strength == monster.strength && durability == monster.durability && aggressionLevel == monster.aggressionLevel && Objects.equals(eyeColor, monster.eyeColor) && Objects.equals(featherColor, monster.featherColor) && Objects.equals(magicalAbility, monster.magicalAbility) && Objects.equals(weakness, monster.weakness);
    }

    @Override
    public int hashCode() {
        return Objects.hash(eyeColor, featherColor, magicalAbility, size, strength, durability, weakness, aggressionLevel);
    }


}

public class MonsterCreation {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the total no. of monsters to create: ");
        int totalMonsters = scanner.nextInt();
        scanner.nextLine();

        List<Monster> monsters = createMonsters(scanner, totalMonsters);

        Set<Monster> uniqueBabies = new HashSet<>();

        long uniqueBabyCount = calculateUniqueBabyCount(monsters, uniqueBabies);

        System.out.println("\nNumber of Unique Baby Monsters: " + uniqueBabyCount);
        System.out.println("\nUnique Baby Monsters: ");
        System.out.println();

        int count = 1;
        for(Monster baby : uniqueBabies) {
            System.out.println("Monster " + count++ + ": ");
            baby.printTraits();
            System.out.println();
        }

        scanner.close();
    }

    private static Monster createMonster(Scanner scanner, String parent) {

        System.out.println("Eye color: ");
        String eyeColor = scanner.nextLine();

        System.out.println("Feather color: ");
        String featherColor = scanner.nextLine();

        System.out.println("Magical Ability: ");
        String magicalAbility = scanner.nextLine();

        System.out.println("Size: ");
        int size = scanner.nextInt();

        System.out.println("Strength: ");
        int strength = scanner.nextInt();

        System.out.println("Durability: ");
        int durability = scanner.nextInt();

        System.out.println("Weakness: ");
        int weakness = scanner.nextInt();

        System.out.println("Aggression Level: ");
        int aggressionLevel = scanner.nextInt();
        scanner.nextLine();

        return new Monster(eyeColor, featherColor, magicalAbility, size, strength, durability, weakness,aggressionLevel);

    }

    private static List<Monster> createMonsters(Scanner scanner, int totalMonsters) {
        List<Monster> monsters = new ArrayList<>();

        for(int i=1;i<=totalMonsters;i++) {
            System.out.println("\nEnter traits for Monster " + i + ":");

            Monster monster = createMonster(scanner, "Monster " + i);
            monsters.add(monster);
        }
        return monsters;
    }

    private static Monster createBabyMonster(Monster parent1, Monster parent2) {
        Monster babyMonster = new Monster();

        Field[] fields = Monster.class.getDeclaredFields();
        Random random = new Random();

        for(Field field : fields) {
            field.setAccessible(true);

            try {
                field.set(babyMonster, random.nextBoolean() ? field.get(parent1) : field.get(parent2));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return babyMonster;
    }

    private static long calculateUniqueBabyCount(List<Monster> monsters, Set<Monster> uniqueBabies) {
        long totalCombinations = 0;

        for(int i=0;i<monsters.size();i++) {
            for(int j=i+1;j<monsters.size();j++) {
                Monster parent1 = monsters.get(i);
                Monster parent2 = monsters.get(j);

                Monster babyMonster = createBabyMonster(parent1, parent2);

                if(uniqueBabies.add(babyMonster)) {
                    totalCombinations++;
                }
            }
        }
        return totalCombinations;
    }

}


