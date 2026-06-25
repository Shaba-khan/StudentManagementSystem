<%@ include file="_header.jsp" %>

<div class="d-flex justify-content-between align-items-center mb-3">
    <h2 class="mb-0">Students</h2>
    <a href="${pageContext.request.contextPath}/student?action=new" class="btn btn-primary">+ Add Student</a>
</div>

<form class="row g-2 mb-3" method="get" action="${pageContext.request.contextPath}/student">
    <input type="hidden" name="action" value="search">
    <div class="col-auto flex-grow-1">
        <input type="text" name="keyword" class="form-control"
               placeholder="Search by name or email" value="${keyword}">
    </div>
    <div class="col-auto">
        <button type="submit" class="btn btn-outline-secondary">Search</button>
    </div>
    <div class="col-auto">
        <a href="${pageContext.request.contextPath}/student?action=list" class="btn btn-outline-secondary">Reset</a>
    </div>
</form>

<c:if test="${empty students}">
    <div class="alert alert-warning">No students found.</div>
</c:if>

<c:if test="${not empty students}">
<div class="table-responsive">
<table class="table table-striped table-hover bg-white shadow-sm">
    <thead class="table-dark">
        <tr>
            <th>ID</th><th>Name</th><th>Email</th><th>Phone</th><th>DOB</th><th>Actions</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach var="s" items="${students}">
            <tr>
                <td>${s.id}</td>
                <td>${s.fullName}</td>
                <td>${s.email}</td>
                <td>${s.phone}</td>
                <td>${s.dob}</td>
                <td class="d-flex gap-1">
                    <a href="${pageContext.request.contextPath}/student?action=view&id=${s.id}"
                       class="btn btn-sm btn-info">View</a>
                    <a href="${pageContext.request.contextPath}/student?action=edit&id=${s.id}"
                       class="btn btn-sm btn-warning">Edit</a>
                    <a href="${pageContext.request.contextPath}/student?action=delete&id=${s.id}"
                       class="btn btn-sm btn-danger"
                       onclick="return confirm('Delete this student?');">Delete</a>
                </td>
            </tr>
        </c:forEach>
    </tbody>
</table>
</div>
</c:if>

<%@ include file="_footer.jsp" %>
