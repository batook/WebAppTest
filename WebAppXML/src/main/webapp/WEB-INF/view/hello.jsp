<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<title>Hello</title>
<body>
<h2>${message}</h2>
<table>
<c:forEach items="${list}" var="e" >
<tr>
<td>${e}</td>
</tr>
</c:forEach>
</table>
</body>
</html>
