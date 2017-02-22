<%@ page import="org.mskcc.cbio.portal.servlet.QueryBuilder" %>
<%@ page import="org.mskcc.cbio.portal.util.GlobalProperties" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<% request.setAttribute(QueryBuilder.HTML_TITLE, GlobalProperties.getTitle() + "::Tools"); %>

<jsp:include page="WEB-INF/jsp/global/header.jsp" flush="true"/>

<h1>U01 Site</h1>
<table>
<tr>
<td>
    <div id="container">
		<p>Here comes the page to query CasJobs for Raw sequences</p>
		<iframe src="http://dswww02.pha.jhu.edu/TSEweb" style="position: absolute; height: 100%; width: 100%; border: none"/>
		<!-- iframe src="http://portal.sciserver.org/login-portal" frameborder="0" scrolling="yes" onload="resizeIframe(this)"-->
		
        </div>
    </td>
 </tr>
 <tr>
    <td colspan="3">
	<jsp:include page="WEB-INF/jsp/global/footer.jsp" flush="true" />
    </td>
  </tr>
</table>
</body>
</html>

<script>
    $(document).ready( function() {
    });
</script>
