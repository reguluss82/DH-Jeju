package service.dh;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.Commu;
import dao.CommuDao;
import service.CommandProcess;

public class CommuContentAction implements CommandProcess {

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("CommuContentAction Service start...");
		
		int c_num = Integer.parseInt(request.getParameter("c_num"));
		String pageNum = request.getParameter("pageNum");
		
		try {
			CommuDao cd = CommuDao.getInstance();
			Commu commu = cd.select(c_num);
			
			request.setAttribute("c_num", c_num);
			request.setAttribute("pageNum", pageNum);
			request.setAttribute("commu", commu);
		} catch (Exception e) {
			System.out.println("select, set try" + e.getMessage());
		}
		return "dh/commuContent.jsp";
	}

}
