/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import view.Menu;
import repository.PersonRepository;

/**
 *
 * @author Administrator
 */
public class PesonInforProgram extends Menu<String>{
    
    static String[] mc = {"Find person info", "Copy Text to new file","Exit"};
    PersonRepository program;
    
    public PesonInforProgram() {
        super("       File Processing", mc);
        program = new PersonRepository();
    }

    @Override
    public void execute(int n) {
        switch(n) {
            case 1:
                program.findPersonInfo();
                break;
            case 2:
                program.copyNewFile();
                break;
            case 3:
                System.exit(0);
        }
    }
    
}
