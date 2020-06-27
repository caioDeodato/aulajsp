<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Testando Formulario JSP</title>
<link rel="stylesheet" href="resources/css/estilo.css">
</head>

<body>
	<div class="login-page">
		<div class="form">
			<form action="LoginServlet" method="post" class="login-form">
				<input type="text" id="login" name="login" value="admin"
					placeholder="Login" class="form-control"> <br> 
					<input type="password" id="senha" name="senha" value="admin" placeholder="Senha" class="form-control"> <br> 
					<button type="submit">Logar</button>
			</form>
		</div>
	</div>
</body>

</html>