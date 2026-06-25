<%@ include file="_header.jsp" %>

<h2 class="mb-4">
    <c:choose>
        <c:when test="${not empty student and student.id > 0}">Edit Student</c:when>
        <c:otherwise>Add Student</c:otherwise>
    </c:choose>
</h2>

<c:if test="${not empty error}">
    <div class="alert alert-danger">${error}</div>
</c:if>

<div class="card shadow-sm">
<div class="card-body">
<form method="post" action="${pageContext.request.contextPath}/student">
    <c:if test="${not empty student and student.id > 0}">
        <input type="hidden" name="id" value="${student.id}">
    </c:if>

    <div class="row mb-3">
        <div class="col-md-6">
            <label class="form-label">First Name</label>
            <input type="text" name="firstName" class="form-control"
                   value="${student.firstName}" required>
        </div>
        <div class="col-md-6">
            <label class="form-label">Last Name</label>
            <input type="text" name="lastName" class="form-control"
                   value="${student.lastName}" required>
        </div>
    </div>

    <div class="mb-3">
        <label class="form-label">Email</label>
        <input type="email" name="email" class="form-control"
               value="${student.email}" required>
    </div>

    <div class="row mb-3">
        <div class="col-md-6">
            <label class="form-label">Phone</label>
            <input type="text" name="phone" class="form-control"
                   value="${student.phone}">
        </div>
        <div class="col-md-6">
            <label class="form-label">Date of Birth</label>
            <input type="date" name="dob" class="form-control"
                   value="${student.dob}">
        </div>
    </div>

    <button type="submit" class="btn btn-primary">Save</button>
    <a href="${pageContext.request.contextPath}/student?action=list"
       class="btn btn-secondary">Cancel</a>
</form>
</div>
</div>

<%@ include file="_footer.jsp" %>
