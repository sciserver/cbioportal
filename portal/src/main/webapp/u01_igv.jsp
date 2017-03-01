<%@ page import="org.mskcc.cbio.portal.servlet.QueryBuilder" %>
<%@ page import="org.mskcc.cbio.portal.util.GlobalProperties" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<% request.setAttribute(QueryBuilder.HTML_TITLE, GlobalProperties.getTitle() + "::Tools"); %>

<jsp:include page="WEB-INF/jsp/global/header.jsp" flush="true"/>

<h1></h1>
<!-- <div class="jumbotron">

</div> -->
<!-- jumbotron -->
<button onclick="igv.browser.loadTrack({
                            url: 'https://data.broadinstitute.org/igvdata/BodyMap/hg19/IlluminaHiSeq2000_BodySites/brain_merged/accepted_hits.bam',
                            name: 'Brain (BodyMap)'
                        })">LOAD BRAIN</button>
<button onclick='igv.browser.loadTrack({
                            name: "Genes",
                            type: "annotation",
                            format: "bed",
                            sourceType: "file",
                            url: "https://s3.amazonaws.com/igv.broadinstitute.org/annotations/hg19/genes/refGene.hg19.bed.gz",
                            indexURL: "https://s3.amazonaws.com/igv.broadinstitute.org/annotations/hg19/genes/refGene.hg19.bed.gz.tbi",
                            order: Number.MAX_VALUE,
                            visibilityWindow: 300000000,
                            displayMode: "EXPANDED"
                        })'>LOAD GENES</button>
						<br/><hr/><br/>
<div class="container-fluid" id="igvDiv" style="padding:5px; border:1px solid lightgray"></div>


<script type="text/javascript">

    $(document).ready(function () {

        var div = $("#igvDiv")[0],
                options = {
                    showNavigation: true,
                    showRuler: true,
                    genome: "hg19",
                    locus: "chr8:128,747,267-128,754,546",
                    tracks: []
                };

        igv.createBrowser(div, options);

    });

</script>

</body>
</html>

<script>
    $(document).ready( function() {
    });
</script>
