/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tilegame.states;

/**
 * based on CodeNMore's tutorial, see: https://github.com/CodeNMore/New-Beginner-Java-Game-Programming-Src
 * expanded on by Loes Immens
 */
public class StateManager 
{
    private static StateManager stateManager = new StateManager();
    
    private StateManager(){}
    
    public State currentState = null;
    
    public static StateManager getStateManager()
    {
        return stateManager;
    }
    
    public void setState(State state)
    {
        currentState = state;
    }
    
    public State getState()
    {
        return currentState;
    }
}
