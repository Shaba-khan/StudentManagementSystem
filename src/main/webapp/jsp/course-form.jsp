<%@ include file="_header.jsp" %>

<h2 class="mb-4">
    <c:choose>
        <c:when test="${not empty course and course.id > 0}">Edit Course</c:when>
        <c:otherwise>Add Course</c:otherwise>
    </c:choose>
</h2>

<c:if test="${not empty error}">
    <div class="alert alert-danger">${error}</div>
</c:if>

<div class="card shadow-sm">
<div class="card-body">
<form method="post" action="${pageContext.request.contextPath}/course">
    <c:if test="${not empty course and course.id > 0}">
        <input type="hidden" name="id" value="${course.id}">
    </c:if>

    <div class="mb-3">
        <label class="form-label">Course Name</label>
        <input type="text" name="courseName" class="form-control"
               value="${course.courseName}" required>
    </div>

    <div class="row mb-3">
        <div class="col-md-6">
            <label class="form-label">Course Code</label>
            <input type="text" name="courseCode" class="form-control"
                   value="${course.courseCode}" required>
        </div>
        <div class="col-md-6">
            <label class="form-label">Credits</label>
            <input type="number" name="credits" class="form-control" min="1"
                   value="${course.credits}" required>
        </div>
    </div>

    <button type="submit" class="btn btn-success">Save</button>
    <a href="${pageContext.request.contextPath}/course?action=list"
       class="btn btn-secondary">Cancel</a>
</form>
</div>
</div>

<%@ include file="_footer.jsp" %>
