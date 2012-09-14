package client.model;

import com.google.common.collect.ImmutableList;

import java.util.Stack;

/**
 * Created with IntelliJ IDEA.
 * User: hanmoi
 * Date: 14/09/12
 * Time: 9:58 PM
 * To change this template use File | Settings | File Templates.
 */
public class NwbClientModel {
    private Stack<NwbDrawingCommand> commandStack;
    private Stack<NwbDrawingCommand> redoStack;

    public NwbClientModel(){
        commandStack = new Stack<NwbDrawingCommand>();
        redoStack = new Stack<NwbDrawingCommand>();
    }

    public void pushDrawingCommand(NwbDrawingCommand command) {
        commandStack.add(command);
    }

    public ImmutableList<NwbDrawingCommand> getActionCommands() {
        return ImmutableList.copyOf(commandStack);
    }

    public void undo() {
        redoStack.push(commandStack.pop());
    }

    public void redo() {
        commandStack.push(redoStack.pop());
    }
}
