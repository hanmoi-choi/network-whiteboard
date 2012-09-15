package client.model;

import client.controller.NwbClientController;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.List;
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
    private List<NwbClientController> subscribers;

    public NwbClientModel() {
        commandStack = new Stack<NwbDrawingCommand>();
        redoStack = new Stack<NwbDrawingCommand>();
        subscribers = new ArrayList<NwbClientController>();
    }

    public void pushDrawingCommand(NwbDrawingCommand command) {
        commandStack.add(command);

        updateSubscribers();
    }

    private void updateSubscribers() {
        for (NwbClientController controller : subscribers) {
            controller.update(ImmutableList.copyOf(commandStack));
        }
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

    public void register(NwbClientController subscriber) {
        subscribers.add(subscriber);
    }
}
