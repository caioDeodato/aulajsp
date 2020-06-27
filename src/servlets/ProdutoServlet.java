package servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.Produtos;
import dao.ProdutoDao;


@WebServlet("/salvarProduto")
public class ProdutoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    private ProdutoDao dao = new ProdutoDao();   
    
    public ProdutoServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {

			String acao = request.getParameter("acao");
			String product = request.getParameter("product");

			if (acao.equalsIgnoreCase("delete")) {
				dao.deletarProduto(!product.isEmpty() ? Long.parseLong(product) : 0);
				RequestDispatcher dispatcher = request.getRequestDispatcher("/cadastroProduto.jsp");
				request.setAttribute("produtos", dao.listar());
				request.setAttribute("msg", "Produto removido com sucesso!");
				dispatcher.forward(request, response);
			} else if (acao.equalsIgnoreCase("editar")) {
				Produtos produto = dao.consultar(!product.isEmpty() ? Long.parseLong(product) : 0);
				RequestDispatcher dispatcher = request.getRequestDispatcher("/cadastroProduto.jsp");
				request.setAttribute("product", produto);
				dispatcher.forward(request, response);
			} else if (acao.equalsIgnoreCase("listartodos")) {
				RequestDispatcher dispatcher = request.getRequestDispatcher("/cadastroProduto.jsp");
				request.setAttribute("produtos", dao.listar());
				dispatcher.forward(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String acao = request.getParameter("acao");

		if (acao != null && acao.equalsIgnoreCase("reset")) {
			try {
				RequestDispatcher dispatcher = request.getRequestDispatcher("/cadastroProduto.jsp");
				request.setAttribute("produtos", dao.listar());
				dispatcher.forward(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		else {
			String id = request.getParameter("id");
			String nome = request.getParameter("nome");
			String quantidade = request.getParameter("quantidade");
			String valor = request.getParameter("valor");

			Produtos produto = new Produtos();
			produto.setId(!id.isEmpty() ? Long.parseLong(id) : null);
			produto.setQuantidade(quantidade);
			produto.setValor(valor);
			produto.setNomeProduto(nome);

			try {
				if (id == null || id.isEmpty() && !dao.validarProduto(nome)) {
					request.setAttribute("msg", "Produto já existente com esse nome, tente outro!");
					RequestDispatcher dispatcher = request.getRequestDispatcher("/cadastroProduto.jsp");
					request.setAttribute("product", produto);
					dispatcher.forward(request, response);
				}

				if (id == null || id.isEmpty() && dao.validarProduto(nome)) {
					dao.salvarProduto(produto);
					request.setAttribute("msg", "Produto cadastrado com sucesso!");
					RequestDispatcher dispatcher = request.getRequestDispatcher("/cadastroProduto.jsp");
					request.setAttribute("produtos", dao.listar());
					dispatcher.forward(request, response);

				} else if (id != null && !id.isEmpty()) {
					if (dao.validarProduto(nome)) {
						dao.atualizar(produto);
						request.setAttribute("msg", "Alterando dados do produto");
						RequestDispatcher dispatcher = request.getRequestDispatcher("/cadastroProduto.jsp");
						request.setAttribute("product", produto);
						dispatcher.forward(request, response);
					}
					else {
						request.setAttribute("msg", "Produto já cadastrado com este nome, escolha outro!");
						RequestDispatcher dispatcher = request.getRequestDispatcher("/cadastroUsuario.jsp");
						request.setAttribute("product", produto);
						dispatcher.forward(request, response);
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

}
