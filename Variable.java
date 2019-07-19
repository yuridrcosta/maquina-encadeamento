

public class Variable {
    private String name;
    private boolean status;
    private boolean objetive;

    public Variable(String title, boolean status,boolean objetive){
        this.name = title;
        this.status = status;
        this.objetive = false;
    }

    public String getName() {
        return name;
    }

    public boolean isStatus() {
        return status;
    }

    public boolean isObjetive(){
        return objetive;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setObjetive(boolean objetive) {
        this.objetive = objetive;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
