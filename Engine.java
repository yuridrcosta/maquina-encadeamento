//Até o momento já foi tudo convertido para objetos (rules)
//Agora é só aplicar o algoritmo

// É preciso colocar no inicio do arraylist
// Algoritmo parece não fazer muito sentido, analisar bem.


import java.io.FileNotFoundException;
import java.util.Arrays;
import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Iterator;

public class Engine {

    /*UDED BY INFERENCE MACHINES*/
    ArrayList<Rule> rules = new ArrayList<Rule>();//all rules
    ArrayList<Variable> knownVariables = new ArrayList<Variable>();//fact base
    Variable goalVariable;
    
    
    //USED BY PROGRAMMING
    Utils utils;
    String saux;
    Variable newVariable;
    BufferedReader readArch;

    public void runBackwardsChaining(){
        askForRules();//OK
        defineKnownVariables();//OK
        convertToRules();
        defineGoal();//OK

    }

    public void runForwardsChaining(){
        askForRules();
        defineKnownVariables();
        convertToRules();
        defineGoal();
    }

    public void askForRules(){
        System.out.println("[+] Digite o nome do arquivo:");
        saux = utils.read.nextLine();
        FileReader f = null;
        try{
          f = new FileReader(saux);
        }catch(FileNotFoundException e){
          System.out.println("[!] Erro: Arquivo não encontrado!");
        }
          readArch = new BufferedReader(f);
    }

    public void defineKnownVariables(){
        String title;
        int iopt;
        String option;
        System.out.println("[!] Deseja registrar uma variável conhecida? [S/N]");
        option = utils.read.nextLine();
        option = option.toLowerCase();
        while (option.equals("s")){

            System.out.println("[+] Digite o nome da variável:");
            title = utils.read.nextLine();
            System.out.println("[+] Digite 1 se o valor for VERDADEIRO (SIM) e 2 se o valor for FALSO (NÃO):");
            iopt = utils.read.nextInt();
            saux = utils.read.nextLine();

            if(iopt == 1) {
                newVariable = new Variable(title, true, false);
            }
            else{
                newVariable = new Variable(title, false, false);
            }

            knownVariables.add(newVariable);

            System.out.println("[!] Foram registradas um total de "+ knownVariables.size() + " variáveis");

            System.out.println("[!] Deseja registrar outra variável conhecida? [S/N]");
            option = utils.read.nextLine();
        }
    }

    public void defineGoal(){
        String name;
        System.out.println("[+] Digite o nome da variável objetivo:");
        name = utils.read.nextLine();

        goalVariable = new Variable(name,false,true);

        //Teste
        Variable test = knownVariables.get(0);

        System.out.println("-> Teste: " + test.getName());

        establishFact(goalVariable);
    }

    public boolean establishFact(Variable objective){
      Variable foundVariable = findVariable(objective.getName(),knownVariables);

      System.out.println("-> Teste de Fatos " + rules.size());

      
      if(foundVariable != null){
        result(foundVariable.isStatus());
        return true;//sucess
      }
      else{
        result(establish1(rules));
      }
      return false;
    }


    public boolean establishConjunction(ArrayList<Variable> goals){
      if(goals.size() == 0){
        return true;
      }
      Variable goal = goals.remove(0);
      System.out.println("-> Teste2 : " + goal.getName());
      if(establishFact(goal)){
        return establishConjunction(goals);
      }
      return false;
    }

    public boolean establish2(Rule rule){
      return establishConjunction(rule.getPremises());
    }

    public boolean establish1(ArrayList<Rule> eRules){
      if(eRules.size() == 0){
        System.out.println("[!] Não foi possível inferir!");
        return false;
      }

      Rule firstRule = eRules.remove(0);

      System.out.println("-> Teste3: "+ eRules.size());

      if(firstRule.getConclusion().getName().equals(goalVariable.getName())){
        if(establish2(firstRule)){
          return true;
        }
      }

      return establish1(eRules);

    }


    public void convertToRules(){
      String sConclusionAux;
      String[] splitConclusion;
      String line = null;
      String [] splitLine;
      Rule newRule = null;
      ArrayList<Variable> allPremises = new ArrayList<Variable>();
      Variable newVariable = null;

      while((line = readFromFile()) != null){
        splitLine = line.split("\\s");
        
        allPremises = splitVariable(splitLine);

        sConclusionAux = splitLine[splitLine.length - 1];
        splitConclusion = sConclusionAux.split("=");

        if(splitConclusion[1] == "não"){
          newVariable = new Variable(splitConclusion[0],false,false);
        }else{
          newVariable = new Variable(splitConclusion[0],  true,false);
        }

        newRule = new Rule(allPremises, newVariable);

        rules.add(newRule);
        
      }
    }

    public ArrayList<Variable> splitVariable(String[] splitLine){
      ArrayList<Variable> allPremises = new ArrayList<Variable>();
      String sVariableAux;
      String[] splitVariable;
      Variable newVariable = null;
      int i=1;

      while(i<= splitLine.length - 2){
        sVariableAux = splitLine[i];
        splitVariable = sVariableAux.split("=");


        if(splitVariable[1] == "não"){
          newVariable = new Variable(splitVariable[0],false,false);
        }else{
          newVariable = new Variable(splitVariable[0],true,false);
        }

        allPremises.add(newVariable);

        i+=2;
    
      }

      return allPremises;
    }


    public Variable findVariable(String name, ArrayList<Variable> variables){
        Variable st;
       for(int i = 0; variables.size()>i;i++){
           st = variables.get(i);
           if(st.getName().equals(name)){
               return st;
           }
       }
       return null;
    }

    public String readFromFile(){
      String line = null;

      try{
        line = readArch.readLine();
      }catch(IOException e){
        System.out.println("[!] Erro: Falha ao ler o arquivo!");
      }

      return line;
    }

    public void result(boolean res){
        System.out.println("[!] A resposta é: " + res);
    }
}
