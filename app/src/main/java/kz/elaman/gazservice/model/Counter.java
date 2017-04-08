package kz.elaman.gazservice.model;

/**
 * Created by Myrzabek on 12/03/2017.
 */

public class Counter {
    private String id;
    private String email;
    String counter;

    public Counter(String id, String email, String counter) {
        this.id = id;
        this.email = email;
        this.counter = counter;
    }

    public Counter() {
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCounter(String counter) {
        this.counter = counter;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getCounter() {
        return counter;
    }
}
