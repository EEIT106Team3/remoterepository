<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<link rel="stylesheet" type="text/css" href="../css/main.css" />

<title>Product</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.0/jquery.min.js"></script>
<script type="text/javascript" src="../css/products.js"></script>
<script type="text/javascript">
var path = "${pageContext.request.contextPath}";
function doBlur() {
	var userInput = $("input[name='id']").val();
	var url = path + "/products/"+userInput;
//  sendRequest(userInput, url);

	if(userInput==null || userInput.length==0) {
		$("#data").html("Id是必要的");
		return;		
	}
	var parsed = parseInt(userInput);
	if(isNaN(parsed)) {
		$("#data").html("Id必需是整數");
		return;
	}
	$.ajax({
		url: url,
		method: 'GET',
		dataType: 'json'
	}).done(function(json) {
		$("#data").html("Id存在");
		
		$("input[name='id']").val(json.id);
		$("input[name='name']").val(json.name);
		$("input[name='price']").val(json.price);
		$("input[name='make']").val(new Date(json.make));
		$("input[name='expire']").val(json.expire);
	}).fail(function() {
		$("#data").html("Id不存在");
	});
}
function clearForm() {
	$("#data").html("");
	$("input[type='text']").val("");
}
</script>
</head>
<body>

<h3>Welcome ${user.email}</h3>

<h3>Product Table</h3>

<form action="<c:url value="/pages/product.controller" />" method="post">
<table>
	<tr>
		<td>ID : </td>
		<td><input type="text" name="id" value="${param.id}" onblur="doBlur()" onfocus="clearForm()"></td>
		<td>${errorMsgs.xxx1}<span id="data"></span></td>
	</tr>
	<tr>
		<td>Name : </td>
		<td><input type="text" name="name" value="${param.name}"></td>
		<td></td>
	</tr>

	<tr>
		<td>Price : </td>
		<td><input type="text" name="price" value="${param.price}"></td>
		<td>${errorMsgs.xxx2}</td>
	</tr>
	<tr>
		<td>Make : </td>
		<td><input type="text" name="make" value="${param.make}"></td>
		<td>${errorMsgs.xxx3}</td>
	</tr>
	<tr>
		<td>Expire : </td>
		<td><input type="text" name="expire" value="${param.expire}"></td>
		<td>${errorMsgs.xxx4}</td>
	</tr>
	<tr>
		<td>
			<input type="submit" name="prodaction" value="Insert">
			<input type="submit" name="prodaction" value="Update">
		</td>
		<td>
			<input type="submit" name="prodaction" value="Delete">
			<input type="submit" name="prodaction" value="Select">
			<input type="button" value="Clear" onclick="clearForm()">
		</td>
	</tr>
</table>

</form>

<h3><span class="error">${errorMsgs.action}</span></h3>

<c:if test="${not empty insert}">
<h3>Insert Product Table Success</h3>
<table border="1">
	<tr><td>Id</td><td>${insert.id}</td></tr>
	<tr><td>Name</td><td>${insert.name}</td></tr>
	<tr><td>Price</td><td>${insert.price}</td></tr>
	<tr><td>Make</td><td>${insert.make}</td></tr>
	<tr><td>Expire</td><td>${insert.expire}</td></tr>
</table>
<script type="text/javascript">clearForm();</script>
</c:if>

<c:if test="${not empty update}">
<h3>Update Product Table Success</h3>
<table border="1">
	<tr><td>Id</td><td>${update.id}</td></tr>
	<tr><td>Name</td><td>${update.name}</td></tr>
	<tr><td>Price</td><td>${update.price}</td></tr>
	<tr><td>Make</td><td>${update.make}</td></tr>
	<tr><td>Expire</td><td>${update.expire}</td></tr>
</table>
<script type="text/javascript">clearForm();</script>
</c:if>

<c:if test="${not empty delete}">
<h3>Delete Product Table Success : ${delete} row deleted</h3>
<script type="text/javascript">clearForm();</script>
</c:if>

</body>
</html>