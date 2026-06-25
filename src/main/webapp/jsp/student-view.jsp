<%@ include file="_header.jsp" %>

<h2 class="mb-4">Student Details</h2>

<c:if test="${empty student}">
    <div class="alert alert-danger">Student not found.</div>
</c:if>

<c:if test="${not empty student}">
<div class="card shadow-sm mb-4">
    <div class="card-body">
        <h4 class="card-title">${student.fullName}</h4>
        <p class="mb-1"><strong>Email:</strong> ${student.email}</p>
        <p class="mb-1"><strong>Phone:</strong> ${student.phone}</p>
        <p class="mb-0"><strong>Date of Birth:</strong> ${student.dob}</p>
    </div>
</div>

<h4 class="mb-3">Enrolled Courses</h4>
<c:choose>
    <c:when test="${empty enrollments}">
        <div class="alert alert-info">This student is not enrolled in any course.</div>
    </c:when>
    <c:otherwise>
        <table class="table table-striped bg-white shadow-sm">
            <thead class="table-dark">
                <tr><th>Course Code</th><th>Course Name</th><th>Enrolled On</th></tr>
            </thead>
            <tbody>
                <c:forEach var="e" items="${enrollments}">
                    <tr>
                        <td>${e.courseCode}</td>
                        <td>${e.courseName}</td>
                        <td>${e.enrollmentDate}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </c:otherwise>
</c:choose>
</c:if>

<a href="${pageContext.request.contextPath}/student?action=list" class="btn btn-secondary">Back to list</a>

<%@ include file="_footer.jsp" %>
