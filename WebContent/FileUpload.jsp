<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="css/style.css">

</head>
<body>

	<div id="dropzone">Drag files here!</div>
	<ul id="files"></ul>

	<form method="post" action="multiPartServlet" enctype="multipart/form-data">
		<input type="file" name="multiPartServlet" /> 
		<input type="text" name="fileDestination" /> 
		Choose a file: 
		<input type="submit" value="Upload" />
	</form>

	<script src="js/handleUpload.js"></script>
</body>
</html>