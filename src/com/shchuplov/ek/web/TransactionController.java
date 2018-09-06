package com.shchuplov.ek.web;

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

/**
 * Servlet implementation class TransactionController
 */
@WebServlet("/TransactionController")
public class TransactionController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private RateDbUtil rateDbUtil;
	private NbrbApiUtil nbrbApiUtil;
	private TransactionDbUtil transactionDbUtil;
	
	@Resource(name="jdbc/finance_tracker")
	private DataSource dataSource;
		
	@Override
	public void init() throws ServletException {
		super.init();
		
		try {
			rateDbUtil = new RateDbUtil(dataSource);
			nbrbApiUtil = new NbrbApiUtil();
			transactionDbUtil = new TransactionDbUtil(dataSource);
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
					showTransactions(request, response);
					break;
				default:
					showTransactions(request, response);
					break;
			}			
		} catch (Exception ex) {
			throw new ServletException(ex);
		}
	}

	private void showTransactions(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		List<Transaction> transactions = transactionDbUtil.getTransactions() ;
		request.setAttribute("TRANSACTION_LIST", transactions);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/list-transactions.jsp");
		dispatcher.forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
