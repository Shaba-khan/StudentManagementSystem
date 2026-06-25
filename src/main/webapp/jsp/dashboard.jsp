<%@ include file="_header.jsp" %>

<h2 class="mb-4">Dashboard</h2>

<div class="row g-4">
    <div class="col-md-4">
        <div class="card text-bg-primary shadow-sm">
            <div class="card-body text-center">
                <h6 class="card-title">Total Students</h6>
                <p class="display-4 mb-0">${totalStudents}</p>
            </div>
        </div>
    </div>
    <div class="col-md-4">
        <div class="card text-bg-success shadow-sm">
            <div class="card-body text-center">
                <h6 class="card-title">Total Courses</h6>
                <p class="display-4 mb-0">${totalCourses}</p>
            </div>
        </div>
    </div>
    <div class="col-md-4">
        <div class="card text-bg-info shadow-sm">
            <div class="card-body text-center">
                <h6 class="card-title">Total Enrollments</h6>
                <p class="display-4 mb-0">${totalEnrollments}</p>
            </div>
        </div>
    </div>
</div>

<div class="mt-4 d-flex gap-2">
    <a href="${pageContext.request.contextPath}/student?action=new" class="btn btn-outline-primary">+ Add Student</a>
    <a href="${pageContext.request.contextPath}/course?action=new" class="btn btn-outline-success">+ Add Course</a>
    <a href="${pageContext.request.contextPath}/enrollment?action=list" class="btn btn-outline-info">Manage Enrollments</a>
</div>

<%@ include file="_footer.jsp" %>
