//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Fighter fighter1 = new Fighter("Jon Jones", 27, 1, 10);
        Fighter fighter2 = new Fighter("Stipe Miocic", 20, 2, 15);

        Fight fight = new Fight(fighter1, fighter2);
        Fighter winner = fight.simulate(fighter1, fighter2);


    fighter1.sheet();
    fighter2.sheet();

        System.out.println("o vencedor é: "+winner.getName());



    }
}