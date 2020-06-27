<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Cadastro de Usuarios</title>
<link rel="stylesheet" href="resources/css/cadastro.css">
</head>
<body>

	<a href="acessopermitido.jsp">Menu</a>
	<a href="index.jsp">Logout</a>

	<div class="container">
		<center> 
			<h1 style="color: blue;">Cadastrar Produtos:</h1>
			<h3 style="color: orange;">${msg}</h3>
		</center>
		<br>
		<br>
		<form action="salvarProduto" method="post" id="form-produto-cadastro" 
				onsubmit="return validarCampos() ? true : false">
			<table class="form-style-1">
				<tr>
					<td>ID:</td>
					<td><input type="text" id="id" name="id" value="${product.id}" readonly="readonly"></td>
				</tr>
				<tr>
					<td>Nome:</td>
					<td><input type="text" id="nome" name="nome" value="${product.nomeProduto}"></td>
				</tr>
				<tr>
					<td>Quantidade:</td>
					<td><input type="text" id="quantidade" name="quantidade" value="${product.quantidade}"></td>
				</tr>
				<tr>
					<td>Valor(R$):</td>
					<td><input type="text" id="valor" name="valor" value="${product.valor}"></td>
				</tr>
				<tr>
					<td><input type="submit" value="Salvar"></td>
					<td><input type="submit" value="Cancelar" onclick="document.getElementById('form-produto-cadastro').action='salvarProduto?acao=reset'"></td>
				</tr>
			</table>
		</form>
		
		<br/><br/><br/><br/>

		<table class="customTable">
			<thead>
				<tr>
					<th><strong>ID:</strong></th>
					<th><strong>Nome:</strong></th>
					<th><strong>Quantidade:</strong></th>
					<th><strong>Valor(R$):</strong></th>
					<th><strong>Excluir:</strong></th>
					<th><strong>Editar:</strong></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${produtos}" var="product">
				<tr>
					<td style="width: 45px"><c:out value="${product.id}"></c:out></td>
					<td style="width: 160px"><c:out value="${product.nomeProduto}"></c:out></td>
					<td style="width: 100px"><c:out value="${product.quantidade}"></c:out></td>
					<td style="width: 150px"><c:out value="${product.valor}"></c:out></td>
					<td style="width: 75px">
						<a href="salvarProduto?acao=delete&product=${product.id}">
							<img alt="Excluir Produto" src="resources/img/remove.png" style="width: 26px; height: 26px">
						</a>
					</td>
					<td style="width: 75px">
						<a href="salvarProduto?acao=editar&product=${product.id}">
							<img alt="Editar Produto" src="resources/img/edit.png" style="width: 20px; height: 20px">
						</a>
					</td>
				</tr>
				</c:forEach>
			</tbody>
		</table>

	</div>
	
	<script type="text/javascript">
		
		function validarCampos() {
			var name = document.getElementById('nome').value;
			var qtd = document.getElementById('quantidade').value;
			var value = document.getElementById('valor').value;
			
			if(name == '' || qtd == '' || value == '') {
				alert('É necessário que seja preenchido todos os campos!');
				return false;
			}
			else {
				return true;
			}
			
		}
	</script>
</body>
</html>