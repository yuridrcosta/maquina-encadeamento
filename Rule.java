import java.util.ArrayList;

public class Rule{
  private ArrayList<Variable> premises = new ArrayList<Variable>();
  private Variable conclusion;

  public Rule(ArrayList<Variable> premises, Variable conclusion){
    this.premises = premises;
    this.conclusion = conclusion;
  }

  public void setPremises(ArrayList<Variable> premises){
    this.premises = premises;
  }

  public ArrayList<Variable> getPremises(){
    return premises;
  }

  public Variable getConclusion(){
    return conclusion;
  }

  public void setConclusion(Variable conclusion){
    this.conclusion = conclusion;
  }

}
