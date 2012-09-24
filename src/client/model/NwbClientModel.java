package client.model;

import client.controller.NwbDrawingCanvasController;
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
    protected Stack<NwbDrawingCommand> commandStack;
    protected Stack<NwbDrawingCommand> redoStack;
    private NwbDrawingCanvasController subscriber;

    public NwbClientModel() {
        commandStack = new Stack<NwbDrawingCommand>();
        redoStack = new Stack<NwbDrawingCommand>();
    }

    public void pushDrawingCommand(NwbDrawingCommand command) {
        commandStack.add(command);
        updateSubscribers();
    }

    protected void updateSubscribers() {
        subscriber.update(ImmutableList.copyOf(commandStack));
    }

    public void undo() {
        if (!commandStack.empty()) {
            redoStack.push(commandStack.pop());
            updateSubscribers();
        }
    }

    public void redo() {
        if (!redoStack.empty()) {
            commandStack.push(redoStack.pop());
            updateSubscribers();
        }
    }

    public void clearStack(){
        commandStack.clear();
        redoStack.clear();
        clearCanvas();
    }

    protected void clearCanvas() {
        subscriber.update(null);
    }

    public void register(NwbDrawingCanvasController subscriber) {
        this.subscriber = subscriber;
    }
}
