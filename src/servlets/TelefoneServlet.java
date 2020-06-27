package servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.BeanCursoJsp;
import beans.Telefone;
import dao.TelefoneDao;
import dao.UserDao;

@WebServlet("/salvarTelefones")
public class TelefoneServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private UserDao dao = new UserDao();
	private TelefoneDao telefoneDao = new TelefoneDao();

	public TelefoneServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			String acao = request.getParameter("acao");
			String user = request.getParameter("user");

			if (acao.equalsIgnoreCase("addFone")) {

				BeanCursoJsp usuario = dao.consultar(!user.isEmpty() ? Long.parseLong(user) : 0);

				// manter a sessao do usuario que estará fazendo o cadastro de seus telefones,
				// quase um SessionStorage

				request.getSession().setAttribute("userEscolhido", usuario);
				request.setAttribute("userEscolhido", usuario);

				RequestDispatcher dispatcher = request.getRequestDispatcher("/telefones.jsp");
				request.setAttribute("telefones", telefoneDao.listar(usuario.getId()));
				dispatcher.forward(request, response);

			} else if(acao.equalsIgnoreCase("delete")) {
				String foneId = request.getParameter("foneId");
				telefoneDao.deletarTelefone(!foneId.isEmpty() ? Long.parseLong(foneId) : 0);
				
				BeanCursoJsp beanCursoJsp = (BeanCursoJsp) request.getSession().getAttribute("userEscolhido");
				
				RequestDispatcher dispatcher = request.getRequestDispatcher("/telefones.jsp");
				request.setAttribute("telefones", telefoneDao.listar(beanCursoJsp.getId()));
				request.setAttribute("msg", "Telefone removido com sucesso");
				dispatcher.forward(request, response);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			BeanCursoJsp beanCursoJsp = (BeanCursoJsp) request.getSession().getAttribute("userEscolhido");

			String numero = request.getParameter("numero");
			String tipo = request.getParameter("tipo");

			Telefone telefone = new Telefone();
			telefone.setNumero(numero);
			telefone.setTipo(tipo);
			telefone.setIdUsuario(beanCursoJsp.getId());

			telefoneDao.salvarTelefone(telefone);

			request.getSession().setAttribute("userEscolhido", beanCursoJsp);
			request.setAttribute("userEscolhido", beanCursoJsp);

			RequestDispatcher dispatcher = request.getRequestDispatcher("/telefones.jsp");
			request.setAttribute("msg", "Telefone cadastrado com sucesso!");
			request.setAttribute("telefones", telefoneDao.listar(beanCursoJsp.getId()));
			dispatcher.forward(request, response);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
