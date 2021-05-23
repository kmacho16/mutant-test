package co.kmacho.model;

public class Dna {
    private String leter;
    private Integer size;

    public Dna(String leter, Integer size) {
        this.leter = leter;
        this.size = size;
    }

    public String getLeter() {
        return leter;
    }

    public void setLeter(String leter) {
        this.leter = leter;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }
}
