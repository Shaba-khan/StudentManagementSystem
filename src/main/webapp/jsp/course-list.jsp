<%@ include file="_header.jsp" %>

<div class="d-flex justify-content-between align-items-center mb-3">
    <h2 class="mb-0">Courses</h2>
    <a href="${pageContext.request.contextPath}/course?action=new" class="btn btn-success">+ Add Course</a>
</div>

<c:if test="${empty courses}">
    <div class="alert alert-warning">No courses found.</div>
</c:if>

<c:if test="${not empty courses}">
<div class="table-responsive">
<table class="table table-striped table-hover bg-white shadow-sm">
    <thead class="table-dark">
        <tr><th>ID</th><th>Code</th><th>Name</th><th>Credits</th><th>Actions</th></tr>
    </thead>
    <tbody>
        <c:forEach var="c" items="${courses}">
            <tr>
                <td>${c.id}</td>
                <td>${c.courseCode}</td>
                <td>${c.courseName}</td>
                <td>${c.credits}</td>
                <td class="d-flex gap-1">
                    <a href="${pageContext.request.contextPath}/course?action=edit&id=${c.id}"
                       class="btn btn-sm btn-warning">Edit</a>
                    <a href="${pageContext.request.contextPath}/course?action=delete&id=${c.id}"
                       class="btn btn-sm btn-danger"
                       onclick="return confirm('Delete this course?');">Delete</a>
                </td>
            </tr>
        </c:forEach>
    </tbody>
</table>
</div>
</c:if>

<%@ include file="_footer.jsp" %>
