package client.view.ui.comp;

import client.view.ui.controller.NwbUIMediator;

/**
 * Created with IntelliJ IDEA.
 * User: hanmoi
 * Date: 22/09/12
 * Time: 5:56 PM
 * To change this template use File | Settings | File Templates.
 */
public interface NwbUIMediatee {
    void setMediator(NwbUIMediator mediator);

    void notifyToMediator();
}
