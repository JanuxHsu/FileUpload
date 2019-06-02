<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form method="post" action="TestForm" enctype="multipart/form-data">

		<p>
			<input type="text" name="form1" />
		</p>
		<p>
			<input type="text" name="form2" />
		</p>
		<p>
			<input type="file" name="multiPartServlet" />
		</p>

		<p>
			<input type="submit" value="Upload" />
		</p>



	</form>
</body>
</html>