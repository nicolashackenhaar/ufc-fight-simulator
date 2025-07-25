public class Main {
    public static void main(String[] args) {
        Fighter fighter1 = new Fighter("Jon Jones", 80, 10, 10, 5);
        Fighter fighter2 = new Fighter("Stipe Miocic", 80, 10, 10, 15);

        Fight fight = new Fight(fighter1, fighter2);
        Fighter winner = fight.simulate();

        fighter1.sheet();
        fighter2.sheet();

        System.out.println("o vencedor é: " + winner.getName());

        attDatabase.execute();
    }
}