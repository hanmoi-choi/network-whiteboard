package client;

import client.controller.NwbClientController;
import client.model.NwbClientModel;
import client.view.NwbClientView;
import client.view.factory.NwbMenuFactory;
import org.jdesktop.application.Application;
import org.jdesktop.application.ApplicationContext;

/**
 * Created with IntelliJ IDEA.
 * User: hanmoi
 * Date: 15/09/12
 * Time: 12:54 AM
 * To change this template use File | Settings | File Templates.
 */
public class NwbClientApplication extends Application {
    @Override
    protected void startup() {
        ApplicationContext ctx = getContext();
        NwbClientController controller = new NwbClientController();

        NwbMenuFactory.setActionMap(controller);

        NwbClientView view = new NwbClientView();
        view.setController(controller);
        controller.setView(view);

        NwbClientModel model = new NwbClientModel();
        controller.setModel(model);
        view.setApplicationContext(ctx);
        view.showView();

        System.out.println("start");
    }

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        Application.launch(NwbClientApplication.class, args);
    }
}
