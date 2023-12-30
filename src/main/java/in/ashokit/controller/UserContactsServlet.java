package in.ashokit.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.ashokit.dao.ContactsDao;
import in.ashokit.dto.ContactsDto;

@WebServlet("/UserContactsServlet")
public class UserContactsServlet extends HttpServlet {

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		int userId = (int) req.getSession().getAttribute("userId"); // Get the user's ID from the session

		String uname = req.getParameter("uname");

		String uemail = req.getParameter("uemail");

		String phno = req.getParameter("uphno");
		int uphno = Integer.parseInt(phno);

		ContactsDto dto = new ContactsDto();
		dto.setuId(userId); // Set the user's ID
		dto.setName(uname);
		dto.setEmail(uemail);
		dto.setPhno(uphno);

		ContactsDao dao = new ContactsDao();
		try {
			boolean saveUserData = dao.saveUserData(dto, userId);

			if (saveUserData) {

				List<ContactsDto> contacts = dao.getContacts(userId);

				req.setAttribute("contacts", contacts);
				req.setAttribute("saveUserData", saveUserData);

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		req.getRequestDispatcher("dashboard.jsp").include(req, resp);

	}

}
