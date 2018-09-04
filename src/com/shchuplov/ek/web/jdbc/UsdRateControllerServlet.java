package com.shchuplov.ek.web.jdbc;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.json.JSONObject;

import com.shchuplov.ek.web.jdbc.RateDbUtil;

/**
 * Servlet implementation class UsdRateControllerServlet
 */
@WebServlet("/UsdRateControllerServlet")
public class UsdRateControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private RateDbUtil rateDbUtil;
	private NbrbApiUtil nbrbApiUtil;
	
	@Resource(name="jdbc/finance_tracker")
	private DataSource dataSource;
		
	@Override
	public void init() throws ServletException {
		super.init();
		
		try {
			rateDbUtil = new RateDbUtil(dataSource);
			nbrbApiUtil = new NbrbApiUtil();
		} catch (Exception exc) {
			throw new ServletException(exc);
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			String theCommand = request.getParameter("command");
			if (theCommand == null) {
				theCommand = "LIST";
			}
			switch (theCommand) {
			
				case ("LIST"):
					showRates(request, response);
					break;
				case ("DELETE"):
					deleteRate(request, response);
					break;
				default:
					showRates(request, response);
					break;
			}			
		} catch (Exception ex) {
			throw new ServletException(ex);
		}
	}

	private void deleteRate(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String date = request.getParameter("date");
		rateDbUtil.deleteRate(date);
		showRates(request, response);
		
	}

	private void addRateOnDate(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String date = request.getParameter("date");
		if (rateDbUtil.canAddDate(date)) {
			JSONObject rate = nbrbApiUtil.getRate(date);
			String rateOnDay = rate.getString("Cur_OfficialRate");
			rateDbUtil.add(date, rateOnDay);	
		}
		response.sendRedirect(request.getContextPath() + "/UsdRateControllerServlet?command=LIST");		

		
	}

	private void showRates(HttpServletRequest request, HttpServletResponse response) throws Exception {

		List<UsdRate> rates = rateDbUtil.getRates();
		request.setAttribute("RATE_LIST", rates);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/list-rates.jsp");
		dispatcher.forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String theCommand = request.getParameter("command");
			switch (theCommand) {

				case ("ADD"):
					addRateOnDate(request, response);
					break;
				default:
					showRates(request, response);
					break;
			}			
		} catch (Exception ex) {
			throw new ServletException(ex);
		}
	}

}
