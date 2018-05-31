<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>修改商品信息</title>

</head>
<body>
<%-- 添加一个自定义tags, 将long类型的时间转为固定格式输出 --%>
<!-- 显示错误信息 有就提示  -->
<c:if test="${allErrors!=null}">
    <c:forEach items="${allErrors}" var="error">
        <font color="red">${error.defaultMessage}</font><br/>
    </c:forEach>
</c:if>
<form id="itemForm"
      action="${pageContext.request.contextPath }/updaImage"
      method="post" enctype="multipart/form-data">
        <tr>
            <td>图片</td>
            <td>
                <c:if test="${itemsCustom.pic !=null}">
                    <%-- "/pic"路径是绝对路径，不要加${pageContext.request.contextPath }/... --%>
                    <img src="/pic/${itemsCustom.pic}" width=100 height=100/>
                    <br/>
                </c:if>
                <input type="file" name="item_pic"/>
            </td>
        </tr>
    <tr>
        <td colspan="2" align="center"><input type="submit" value="提交"/>
        </td>
    </tr>
</form>
</body>

</html>