public class Fighter {
    private String name;
    private int victories;
    private int defeats;
    private int draws;

    public Fighter(String name, int victories, int defeats, int draws) {
        this.name = name;
        this.victories = victories;
        this.defeats = defeats;
        this.draws = draws;
    }

    public int getAllFights() {
        return victories + defeats + draws;
    }

    public double winningPercentage() {
        int total = getAllFights();
        if (total != 0) {
            double temp = ((double) victories / total) * 100;
            return Math.round(temp);
        }
        return 0;
    }
    public void sheet(){
        System.out.println("nome: "+name);
        System.out.println("Vitórias: "+victories+"/"+getAllFights());
        System.out.println("Derrotas: "+ defeats+"/"+getAllFights());
        System.out.println("Empates: "+ draws+"/"+getAllFights());
        System.out.println("Chance de vitoria: "+winningPercentage());


    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getVictories() {
        return victories;
    }

    public void setVictories(int victories) {
        this.victories = victories;
    }

    public int getDefeats() {
        return defeats;
    }

    public void setDefeats(int defeats) {
        this.defeats = defeats;
    }

    public int getDraws() {
        return draws;
    }

    public void setDraws(int draws) {
        this.draws = draws;
    }
}
