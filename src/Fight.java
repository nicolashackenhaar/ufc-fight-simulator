public class Fight {
    private Fighter fighter1;
    private Fighter fighter2;

    public Fight(Fighter fighter1, Fighter fighter2) {
        this.fighter1 = fighter1;
        this.fighter2 = fighter2;
    }

    public Fighter simulate(Fighter fighter1, Fighter fighter2) {
        double percent1 = this.fighter1.winningPercentage();
        double percent2 = this.fighter2.winningPercentage();

        if (percent1 > percent2) {
            return this.fighter1;
        } else if (percent1 < percent2) {
            return this.fighter2;
        }
        return null;
    }

}
