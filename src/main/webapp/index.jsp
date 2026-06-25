<%@ page contentType="text/html;charset=UTF-8" %>
<%-- Landing page: send the user straight to the dashboard. --%>
<% response.sendRedirect(request.getContextPath() + "/dashboard"); %>
