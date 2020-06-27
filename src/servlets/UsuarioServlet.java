package servlets;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.codec.binary.Base64;

import beans.BeanCursoJsp;
import dao.UserDao;

@WebServlet("/salvarUsuario")
@MultipartConfig
public class UsuarioServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private UserDao dao = new UserDao();

	public UsuarioServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {

			String acao = request.getParameter("acao");
			String user = request.getParameter("user");

			if (acao.equalsIgnoreCase("delete")) {
				dao.deletarUsuario(!user.isEmpty() ? Long.parseLong(user) : 0);
				RequestDispatcher dispatcher = request.getRequestDispatcher("/cadastroUsuario.jsp");
				request.setAttribute("usuarios", dao.listar());
				request.setAttribute("msg", "Usuário removido com sucesso!");
				dispatcher.forward(request, response);
				
			} else if (acao.equalsIgnoreCase("editar")) {
				BeanCursoJsp beanCursoJsp = dao.consultar(!user.isEmpty() ? Long.parseLong(user) : 0);
				RequestDispatcher dispatcher = request.getRequestDispatcher("/cadastroUsuario.jsp");
				request.setAttribute("user", beanCursoJsp);
				dispatcher.forward(request, response);
				
			} else if (acao.equalsIgnoreCase("listartodos")) {
				RequestDispatcher dispatcher = request.getRequestDispatcher("/cadastroUsuario.jsp");
				request.setAttribute("usuarios", dao.listar());
				dispatcher.forward(request, response);
				
			} else if(acao.equalsIgnoreCase("download")) {
				BeanCursoJsp usuario = dao.consultar(!user.isEmpty() ? Long.parseLong(user) : 0);
				if(usuario != null) {
					response.setHeader("Content-Disposition", "attachment;fileName=arquivo." +usuario.getContentType().split("\\/")[1]);
					
					/* Converte a base64 da imagem do banco de byte*/
					byte[] imageFotoBytes = new Base64().decodeBase64(usuario.getFotoBase64());
					
					/* Coloca os bytes em um objeto para processar */
					InputStream is = new ByteArrayInputStream(imageFotoBytes);
					
					/* inicio da reposta para o navegador */
					
					int read = 0;
					byte[] bytes = new byte[1024];
					OutputStream os = response.getOutputStream();
					
					while((read = is.read(bytes)) != -1) {
						os.write(bytes, 0, read);
					}
					
					os.flush();
					os.close();
					
					/* inicio da reposta para o navegador */
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String acao = request.getParameter("acao");

		if (acao != null && acao.equalsIgnoreCase("reset")) {
			try {
				RequestDispatcher dispatcher = request.getRequestDispatcher("/cadastroUsuario.jsp");
				request.setAttribute("usuarios", dao.listar());
				dispatcher.forward(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		else {
			String id = request.getParameter("id");
			String login = request.getParameter("login");
			String senha = request.getParameter("senha");
			String nome = request.getParameter("nome");
			String cep = request.getParameter("cep");
			String rua = request.getParameter("rua");
			String bairro = request.getParameter("bairro");
			String cidade = request.getParameter("cidade");
			String estado = request.getParameter("estado");
			String ibge = request.getParameter("ibge");

			BeanCursoJsp usuario = new BeanCursoJsp();
			usuario.setId(!id.isEmpty() ? Long.parseLong(id) : null);
			usuario.setLogin(login);
			usuario.setSenha(senha);
			usuario.setNome(nome);
			usuario.setCep(cep);
			usuario.setRua(rua);
			usuario.setBairro(bairro);
			usuario.setCidade(cidade);
			usuario.setEstado(estado);
			usuario.setIbge(ibge);

			try {
				
				//File Upload de imagens e pdf
				
				if(ServletFileUpload.isMultipartContent(request)) {
					
					Part imagemFoto = request.getPart("foto");
					 String fotoBase64 = new Base64()
							.encodeBase64String(convertStreamToByte(imagemFoto.getInputStream()));
					
					usuario.setFotoBase64(fotoBase64);
					usuario.setContentType(imagemFoto.getContentType());
					
				} 
				
				//Fim File Upload
				
				if (id == null || id.isEmpty() && !dao.validarLogin(login)) {
					request.setAttribute("msg", "Usuário já existente com esse login, tente outro!");
					RequestDispatcher dispatcher = request.getRequestDispatcher("/cadastroUsuario.jsp");
					request.setAttribute("user", usuario);
					dispatcher.forward(request, response);
				}

				else if (id == null || id.isEmpty() && dao.validarLogin(login)) {
					dao.salvarUsuario(usuario);
					request.setAttribute("msg", "Usuário cadastrado com sucesso!");
					RequestDispatcher dispatcher = request.getRequestDispatcher("/cadastroUsuario.jsp");
					request.setAttribute("usuarios", dao.listar());
					dispatcher.forward(request, response);

				} else if (id != null && !id.isEmpty()) {
					if (dao.validarLogin(login)) {
						dao.atualizar(usuario);
						request.setAttribute("msg", "Alteração de dados do usuário concluída!");
						RequestDispatcher dispatcher = request.getRequestDispatcher("/cadastroUsuario.jsp");
						request.setAttribute("usuarios", dao.listar());
						dispatcher.forward(request, response);
					}
					else {
						request.setAttribute("msg", "Usuário já cadastrado com este login, escolha outro!");
						RequestDispatcher dispatcher = request.getRequestDispatcher("/cadastroUsuario.jsp");
						request.setAttribute("user", usuario);
						dispatcher.forward(request, response);
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
	
	//Converte a entrada de fluxo de dados da imagem para um array de byte 
	private byte[] convertStreamToByte(InputStream imagem) throws Exception{
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int reads = imagem.read(); //armazena o valor um a um na posicao atual
		while(reads != -1) {
			baos.write(reads); // a cada posicao checada, ele armazena na variavel do baos
			reads = imagem.read(); // e aqui atribui outra posicao ao vetor, fazendo voltar pra checagem até encontrar o fim, ou o -1
		}
		
		return baos.toByteArray();
		
	}

}
