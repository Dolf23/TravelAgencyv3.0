package by.it_academy.agency.commands.user;

import by.it_academy.agency.beans.User;
import by.it_academy.agency.commands.AbstractCommand;
import by.it_academy.agency.constants.ConfigsConstants;
import by.it_academy.agency.constants.MessageConstants;
import by.it_academy.agency.constants.Parameters;
import by.it_academy.agency.exceptions.ServiceException;
import by.it_academy.agency.logger.logger;
import by.it_academy.agency.managers.ConfigurationManager;
import by.it_academy.agency.managers.MessageManager;
import by.it_academy.agency.services.ActionService;
import by.it_academy.agency.services.interfaces.IActionService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class GoToReservedToursCommand extends AbstractCommand {

    @Autowired
    private IActionService actionService;

    @Override
    public String execute(HttpServletRequest request) {
        String page;
        try {
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute(Parameters.USER);

            page = ConfigurationManager.INSTANCE.getProperty(ConfigsConstants.USER_RESERVED_TOURS_PAGE_PATH);
            request.setAttribute(Parameters.TOURS_MAP, actionService.getUserActions(user));
        } catch (ServiceException e) {
            logger.writeLog(e.getMessage());
            page = ConfigurationManager.INSTANCE.getProperty(ConfigsConstants.ERROR_PAGE_PATH);
            request.setAttribute(Parameters.ERROR_DATABASE, MessageManager.INSTANCE.getProperty(MessageConstants.ERROR_DATABASE));
        }
        return page;
    }
}