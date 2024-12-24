package mysite.controller;

import jakarta.servlet.annotation.WebServlet;
import mysite.controller.action.board.DeleteAction;
import mysite.controller.action.board.ListAction;
import mysite.controller.action.board.UpdateAction;
import mysite.controller.action.board.UpdateFormAction;
import mysite.controller.action.board.ViewAction;
import mysite.controller.action.board.WriteAction;
import mysite.controller.action.board.WriteFormAction;

import java.util.Map;


@WebServlet("/board")
public class BoardServlet extends ActionServlet {
	private static final long serialVersionUID = 1L;

	private Map<String, Action> mapAction = Map.of(
			"writeform", new WriteFormAction(),
			"write", new WriteAction(),
			"list", new ListAction(),
			"delete", new DeleteAction(),
			"view", new ViewAction(),
			"update", new UpdateAction(),
			"updateform", new UpdateFormAction()
			);
	
	@Override
	protected Action getAction(String actionName) {
		return mapAction.getOrDefault(actionName, new ListAction());
	}

}
