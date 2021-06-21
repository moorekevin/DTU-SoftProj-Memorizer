package dtu.softproj.memorizer;

public class User {
    private String name;
    private int score;

    public User() {
        // Needs empty constructor
    }

    public User(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    public String getName() {
        return name.toUpperCase();
    }
}
