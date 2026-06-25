<%@ include file="_header.jsp" %>

<h2 class="mb-4">Enrollments</h2>

<c:if test="${not empty error}">
    <div class="alert alert-danger">${error}</div>
</c:if>

<div class="card shadow-sm mb-4">
<div class="card-body">
    <h5 class="card-title">Assign Student to Course</h5>
    <form method="post" action="${pageContext.request.contextPath}/enrollment" class="row g-2">
        <div class="col-md-5">
            <select name="studentId" class="form-select" required>
                <option value="">-- Select Student --</option>
                <c:forEach var="s" items="${students}">
                    <option value="${s.id}">${s.fullName} (${s.email})</option>
                </c:forEach>
            </select>
        </div>
        <div class="col-md-5">
            <select name="courseId" class="form-select" required>
                <option value="">-- Select Course --</option>
                <c:forEach var="c" items="${courses}">
                    <option value="${c.id}">${c.courseCode} - ${c.courseName}</option>
                </c:forEach>
            </select>
        </div>
        <div class="col-md-2">
            <button type="submit" class="btn btn-primary w-100">Assign</button>
        </div>
    </form>
</div>
</div>

<c:if test="${empty enrollments}">
    <div class="alert alert-warning">No enrollments yet.</div>
</c:if>

<c:if test="${not empty enrollments}">
<div class="table-responsive">
<table class="table table-striped table-hover bg-white shadow-sm">
    <thead class="table-dark">
        <tr><th>ID</th><th>Student</th><th>Course</th><th>Date</th><th>Action</th></tr>
    </thead>
    <tbody>
        <c:forEach var="e" items="${enrollments}">
            <tr>
                <td>${e.id}</td>
                <td>${e.studentName}</td>
                <td>${e.courseCode} - ${e.courseName}</td>
                <td>${e.enrollmentDate}</td>
                <td>
                    <a href="${pageContext.request.contextPath}/enrollment?action=remove&id=${e.id}"
                       class="btn btn-sm btn-danger"
                       onclick="return confirm('Remove this enrollment?');">Remove</a>
                </td>
            </tr>
        </c:forEach>
    </tbody>
</table>
</div>
</c:if>

<%@ include file="_footer.jsp" %>
