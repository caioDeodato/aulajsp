<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>

<meta charset="ISO-8859-1">
<title>Cadastro de Telefones</title>
<link rel="stylesheet" href="resources/css/cadastro.css">

</head>
<body>

	<a href="salvarUsuario?acao=listartodos">Voltar para a lista</a>

	<div class="container">
		<center>
			<h1 style="color: blue;">Cadastro de telefones:</h1>
			<h3 style="color: orange;">${msg}</h3>
		</center>
		<br> <br>
		<form action="salvarTelefones" method="post" id="form-user-cadastro"
			onsubmit="return validarCampos() ? true : false">
			<table class="form-style-1">
				<tr>
					<td>ID:</td>
					<td><input type="text" id="id" name="id" value="${userEscolhido.id}"
						readonly="readonly"></td>
					
					<td>Nome:</td>
					<td><input type="text" id="nome" name="nome"
						value="${userEscolhido.nome}"></td>
					
				</tr>
				
				<tr>
					<td>Número:</td>
					<td><input type="text" id="numero" name="numero"></td>
					
					<td><select name="tipo" id="tipo">
						<option>Fixo</option>
						<option>Celular</option>
						<option>Fax</option>
					</select></td>
				</tr>
				
				<tr>
					<td/>
					<td><input type="submit" value="Salvar"></td>
				</tr>
			</table>
		</form>

		<br /> <br /> <br /> <br />

		<table class="customTable">
			<thead>
				<tr>
					<th><strong>ID:</strong></th>
					<th><strong>Número:</strong></th>
					<th><strong>Tipo:</strong></th>
					<th><strong>Excluir:</strong></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${telefones}" var="fones">
					<tr>
						<td style="width: 45px"><c:out value="${fones.id}"></c:out></td>
						<td style="width: 160px"><c:out value="${fones.numero}"></c:out></td>
						<td style="width: 100px"><c:out value="${fones.tipo}"></c:out></td>
						<td style="width: 75px"><a
							href="salvarTelefones?acao=delete&foneId=${fones.id}"> <img
								alt="Excluir Usuário" src="resources/img/remove.png"
								style="width: 26px; height: 26px">
						</a></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>

	</div>

	<script type="text/javascript">
		function validarCampos() {
			
			var phone = document.getElementById('numero').value;
			var tipo = document.getElementById('tipo').value;

			if (phone == '' ||  tipo == '') {
				alert('É necessário que seja preenchido todos os campos!');
				return false;
			} else {
				return true;
			}

		}
	</script>

</body>
</html>