import java.util.Random;
import java.util.Scanner;

public class CombatGame {

    static Random random = new Random();
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        Player player1 = createPlayer(1);
        Player player2 = createPlayer(2);

        int round = 1;
        while (player1.getLife() > 0 && player2.getLife() > 0) {
            System.out.println("************************************************************************************************");
            System.out.println("RONDA " + round);
            printPlayerInfo(player1);
            printPlayerInfo(player2);

            makeTurn(player1, player2);
            if (player2.getLife() <= 0) {
                break;
            }

            makeTurn(player2, player1);
            round++;

            System.out.println("Introduce cualquier caracter para continuar");
            scanner.nextLine();
        }

        System.out.println("************************************************************************************************");
        if (player1.getLife() <= 0) {
            System.out.println("El jugador 2 ha ganado");
        } else {
            System.out.println("El jugador 1 ha ganado");
        }
    }

    public static Player createPlayer(int number) {
        System.out.println("JUGADOR " + number);
        System.out.println("Introduce valores para la velocidad, vida, defensa y ataque. No deben sumar más de 500.");

        int speed, life, defense, attack;
        do {
            System.out.print("Velocidad: ");
            speed = readAttributeValue();
            System.out.print("Vida: ");
            life = readAttributeValue();
            System.out.print("Defensa: ");
            defense = readAttributeValue();
            System.out.print("Ataque: ");
            attack = readAttributeValue();

            if (speed + life + defense + attack > 500) {
                System.out.println("La suma de los atributos no debe exceder 500. Inténtalo de nuevo.");
            }
        } while (speed + life + defense + attack > 500);

        return new Player(number, speed, life, defense, attack);
    }

    public static int readAttributeValue() {
        int value;
        while (true) {
            try {
                value = Integer.parseInt(scanner.nextLine());
                if (value < 1 || value > 200) {
                    System.out.println("El valor debe estar entre 1 y 200. Inténtalo de nuevo.");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Introduce un número válido.");
            }
        }
        return value;
    }

    public static void printPlayerInfo(Player player) {
        System.out.print("Jugador " + player.getPlayerNumber() + ": " + player.getLife() + " ");
        printLifeBar(player);
        System.out.println();
    }

    public static void printLifeBar(Player player) {
        int bars = player.getLife() / 10;
        for (int i = 0; i < bars; i++) {
            System.out.print("-");
        }
    }

    public static void makeTurn(Player attacker, Player defender) {
        int damage = calculateDamage(attacker.getAttack(), defender.getDefense());
        System.out.println("Jugador " + attacker.getPlayerNumber() + " golpea primero con " + damage + " de daño");
        defender.receiveDamage(damage);
    }

    public static int calculateDamage(int attack, int defense) {
        int baseDamage = random.nextInt(attack / 2) + 1;
        int totalDamage = baseDamage - (defense / 4);
        return Math.max(totalDamage, 1); // Se asegura de que el daño sea al menos 1
    }
}
class Player {
    private int playerNumber;
    private int speed;
    private int life;
    private int defense;
    private int attack;

    public Player(int playerNumber, int speed, int life, int defense, int attack) {
        this.playerNumber = playerNumber;
        this.speed = speed;
        this.life = life;
        this.defense = defense;
        this.attack = attack;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public int getSpeed() {
        return speed;
    }

    public int getLife() {
        return life;
    }

    public int getDefense() {
        return defense;
    }

    public int getAttack() {
        return attack;
    }

    public void receiveDamage(int damage) {
        life -= damage;
        if (life < 0) {
            life = 0;
        }
    }
}
