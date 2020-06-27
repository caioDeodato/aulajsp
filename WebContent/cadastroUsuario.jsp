<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Cadastro de Usuarios</title>
<link rel="stylesheet" href="resources/css/cadastro.css">
<!-- Adicionando JQuery -->
<script src="https://code.jquery.com/jquery-3.4.1.min.js"
	integrity="sha256-CSXorXvZcTkaix6Yvo6HppcZGetbYMGWSFlBw8HfCJo="
	crossorigin="anonymous">
	
</script>

</head>
<body>

	<a href="acessopermitido.jsp">Menu</a>
	<a href="index.jsp">Logout</a>

	<div class="container">
		<center>
			<h1 style="color: blue;">Cadastrar Usuários:</h1>
			<h3 style="color: orange;">${msg}</h3>
		</center>
		<br> <br>
		<form action="salvarUsuario" method="post" id="form-user-cadastro"
			onsubmit="return validarCampos() ? true : false" enctype="multipart/form-data">
			<table class="form-style-1">
				<tr>
					<td>ID:</td>
					<td><input type="text" id="id" name="id" value="${user.id}"
						readonly="readonly"></td>

					<td>Nome:</td>
					<td><input type="text" id="nome" name="nome"
						value="${user.nome}"></td>

				</tr>

				<tr>

					<td>Login:</td>
					<td><input type="text" id="login" name="login"
						value="${user.login}"></td>

					<td>IBGE:</td>
					<td><input type="text" id="ibge" name="ibge"
						readonly="readonly" value="${user.ibge}"></td>

				</tr>

				<tr>
					<td>Senha:</td>
					<td><input type="password" id="senha" name="senha"
						value="${user.senha}"></td>

					<td>CEP:</td>
					<td><input type="text" id="cep" name="cep" value="${user.cep}"
						onblur="consultaCep();" maxlength="8"></td>

				</tr>

				<tr>
					<td>Rua:</td>
					<td><input type="text" id="rua" name="rua" readonly="readonly"
						value="${user.rua}"></td>

					<td>Bairro:</td>
					<td><input type="text" id="bairro" name="bairro"
						readonly="readonly" value="${user.bairro}"></td>

				</tr>

				<tr>
					<td>Estado:</td>
					<td><input type="text" id="estado" name="estado"
						readonly="readonly" value="${user.estado}"></td>

					<td>Cidade:</td>
					<td><input type="text" id="cidade" name="cidade"
						readonly="readonly" value="${user.cidade}"></td>

				</tr>
				
				<tr>
					<td>Foto:</td>
					<td><input type="file" name="foto" value="foto"></td>
				</tr>

				<tr>
					<td><input type="submit" value="Salvar"></td>
					<td><input type="submit" value="Cancelar"
						onclick="document.getElementById('form-user-cadastro').action='salvarUsuario?acao=reset'"></td>
				</tr>
			</table>
		</form>

		<br /> <br /> <br /> <br />

		<table class="customTable">
			<thead>
				<tr>
					<th><strong>ID:</strong></th>
					<th><strong>Foto:</strong></th>
					<th><strong>Nome:</strong></th>
					<th><strong>Login:</strong></th>
					<th><strong>Excluir:</strong></th>
					<th><strong>Editar:</strong></th>
					<th><strong>Telefones:</strong></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${usuarios}" var="user">
					<tr>
						<td style="width: 45px"><c:out value="${user.id}"></c:out></td>
						<td style="width: 75px">
							<a href="salvarUsuario?acao=download&user=${user.id}">
								<img src='<c:out value="${user.tempFotoUser}"/>' alt="Foto" name="fotouser" style="width: 32px; height: 32px;">
							</a>
						</td>
						<td style="width: 160px"><c:out value="${user.nome}"></c:out></td>
						<td style="width: 100px"><c:out value="${user.login}"></c:out></td>
						<td style="width: 75px"><a
							href="salvarUsuario?acao=delete&user=${user.id}"> <img
								alt="Excluir Usuário" src="resources/img/remove.png"
								style="width: 26px; height: 26px">
						</a></td>
						<td style="width: 75px"><a
							href="salvarUsuario?acao=editar&user=${user.id}"> <img
								alt="Editar Usuário" src="resources/img/edit.png"
								style="width: 20px; height: 20px">
						</a></td>
						<td style="width: 75px"><a
							href="salvarTelefones?acao=addFone&user=${user.id}"> <img
								alt="Mostrar telefones" src="resources/img/phone.png"
								style="width: 20px; height: 20px">
						</a></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>

	</div>

	<script type="text/javascript">
		function validarCampos() {
			var name = document.getElementById('nome').value;
			var login = document.getElementById('login').value;
			var password = document.getElementById('senha').value;
			var cep = document.getElementById('cep').value;
			var rua = document.getElementById('rua').value;
			var bairro = document.getElementById('bairro').value;
			var cidade = document.getElementById('cidade').value;
			var estado = document.getElementById('estado').value;
			var ibge = document.getElementById('ibge').value;

			if (name == '' || login == '' || password == '' || cep == ''
					|| rua == '' || bairro == '' || cidade == ''
					|| estado == '' || ibge == '') {
				alert('É necessário que seja preenchido todos os campos!');
				return false;
			} else {
				return true;
			}

		}

		function consultaCep() {
			var cep = document.getElementById('cep').value;

			//Consulta o webservice viacep.com.br/
			$.getJSON("https://viacep.com.br/ws/" + cep + "/json/?callback=?",
					function(dados) {

						$("#rua").val(dados.logradouro);
						$("#bairro").val(dados.bairro);
						$("#cidade").val(dados.localidade);
						$("#estado").val(dados.uf);
						$("#ibge").val(dados.ibge);

						if (!("erro" in dados)) {

						} else {
							//CEP pesquisado não foi encontrado.
							$("#rua").val('');
							$("#bairro").val('');
							$("#cidade").val('');
							$("#estado").val('');
							$("#ibge").val('');

							alert("CEP não encontrado.");
						}
					});
		}
	</script>

</body>
</html>