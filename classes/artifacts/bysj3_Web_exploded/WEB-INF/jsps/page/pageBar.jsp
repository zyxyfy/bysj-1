<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<c:choose>
    <c:when test="${totalPages<5 }">
        <c:set value="1" var="begin"/>
        <c:set value="${ totalPages}" var="end"/>
    </c:when>
    <c:otherwise>
        <c:set value="${pageNumber+1-2 }" var="begin"/>
        <c:set value="${pageNumber+1+2 }" var="end"/>

        <c:if test="${begin<=0 }">
            <c:set value="1" var="begin"/>
            <c:set value="5" var="end"/>
        </c:if>

        <c:if test="${end>totalPages}">
            <c:set value="${totalPages -4 }" var="begin"/>
            <c:set value="${totalPages }" var="end"/>
        </c:if>
    </c:otherwise>

</c:choose>


<div class="row">

    <ul class="pagination pagination-centered  pagination-sm ">
        <c:choose>
            <c:when test="${count<=0 }">
                <li class="disabled"> 共0页，共0条</li>
            </c:when>
            <c:otherwise>
                <li class="disabled"> 共<label>${totalPages }</label>页，共<label id="viewCount">${count }</label>条</li>
            </c:otherwise>

        </c:choose>

        <c:choose>
            <c:when test="${count<=0 }">
                <li class="disabled"><a href="#">&larr; 第一页</a></li>
            </c:when>
            <c:otherwise>
                <li><a href="${pagingUrl }?pageNo=1&pageSize=10&no=${no}&name=${name}&departmentId=${departmentId}
                &schoolId=${schoolId}&studentClassId=${studentClassId}&departmentSelect=${departmentSelect}&title=${title}
                &studentClassSelect=${studentClassSelect}&schoolSelect=${schoolSelect}&majorId=${majorId}&description=${description}">&larr;
                    第一页</a></li>
            </c:otherwise>
        </c:choose>

        <c:forEach begin="${begin }" end="${end }" var="page">


            <c:choose>
                <c:when test="${page==pageNumber+1 }">
                    <li class="active"><a
                            href="${pagingUrl }?pageNo=${page}&pageSize=10&no=${no}&name=${name}&departmentId=${departmentId}
                &schoolId=${schoolId}&studentClassId=${studentClassId}&departmentSelect=${departmentSelect}&title=${title}
                &studentClassSelect=${studentClassSelect}&schoolSelect=${schoolSelect}&majorId=${majorId}&description=${description}">${page }</a>
                    </li>
                </c:when>
                <c:otherwise>
                    <li>
                        <a href="${pagingUrl }?pageNo=${page}&pageSize=10&no=${no}&name=${name}&departmentId=${departmentId}
                &schoolId=${schoolId}&studentClassId=${studentClassId}&departmentSelect=${departmentSelect}&title=${title}
                &studentClassSelect=${studentClassSelect}&schoolSelect=${schoolSelect}&majorId=${majorId}&description=${description}">${page }</a>
                    </li>
                </c:otherwise>

            </c:choose>
        </c:forEach>

        <c:choose>
            <c:when test="${count<=0}">
                <li class="disabled"><a>最后一页&larr; </a></li>
            </c:when>
            <c:otherwise>
                <li>
                    <a href="${pagingUrl }?pageNo=${totalPages}&pageSize=10&no=${no}&name=${name}&departmentId=${departmentId}
                &schoolId=${schoolId}&studentClassId=${studentClassId}&departmentSelect=${departmentSelect}&title=${title}
                &studentClassSelect=${studentClassSelect}&schoolSelect=${schoolSelect}&majorId=${majorId}
                &description=${description}">最后一页&larr; </a></li>
            </c:otherwise>
        </c:choose>

    </ul>
</div>