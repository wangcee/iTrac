<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<c:set var="app_cssJsInclude">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <link rel="stylesheet" href="${ctx}/assets/css/bootstrap.min.css" />
    <link rel="stylesheet" href="${ctx}/assets/css/itrac.css" />

    <!--[if lt IE 9]> <script src="${ctx}/assets/js/html5shiv.js"></script> <![endif]-->

    <script type="text/javascript" src="${ctx}/assets/js/jquery-1.8.1.min.js"></script>
    <script type="text/javascript" src="${ctx}/assets/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="${ctx}/assets/js/itrac.js"></script>

    <script>
        // Include the UserVoice JavaScript SDK (only needed once on a page)
        UserVoice=window.UserVoice||[];(function(){var uv=document.createElement('script');uv.type='text/javascript';uv.async=true;uv.src='//widget.uservoice.com/g0yNHMK9MWxq3gNtkMyg.js';var s=document.getElementsByTagName('script')[0];s.parentNode.insertBefore(uv,s)})();


        //
        // UserVoice Javascript SDK developer documentation:
        // https://www.uservoice.com/o/javascript-sdk
        //


        // Set colors
        UserVoice.push(['set', {
            accent_color: '#448dd6',
            trigger_color: 'white',
            trigger_background_color: 'rgba(46, 49, 51, 0.6)'
        }]);


        // Identify the user and pass traits
        // To enable, replace sample data with actual user traits and uncomment the line
        UserVoice.push(['identify', {
            //email:      'john.doe@example.com', // User’s email address
            //name:       'John Doe', // User’s real name
            //created_at: 1364406966, // Unix timestamp for the date the user signed up
            //id:         123, // Optional: Unique id of the user (if set, this should not change)
            //type:       'Owner', // Optional: segment your users by type
            //account: {
            //  id:           123, // Optional: associate multiple users with a single account
            //  name:         'Acme, Co.', // Account name
            //  created_at:   1364406966, // Unix timestamp for the date the account was created
            //  monthly_rate: 9.99, // Decimal; monthly rate of the account
            //  ltv:          1495.00, // Decimal; lifetime value of the account
            //  plan:         'Enhanced' // Plan name for the account
            //}
        }]);


        // Add default trigger to the bottom-right corner of the window:
        UserVoice.push(['addTrigger', { mode: 'contact', trigger_position: 'bottom-right' }]);


        // Or, use your own custom trigger:
        //UserVoice.push(['addTrigger', '#id', { mode: 'contact' }]);


        // Autoprompt for Satisfaction and SmartVote (only displayed under certain conditions)
        UserVoice.push(['autoprompt', {}]);
    </script>

</c:set>

<c:set var="pageHead_signedIn">
    <nav class="navbar navbar-default" role="navigation">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-ex1-collapse">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="${ctx}/report">iTrac</a>
        </div>

        <div class="collapse navbar-collapse navbar-ex1-collapse">
            <ul class="nav navbar-nav">
                <li ><a href="${ctx}/report">Report</a></li>
                <li><a href="${ctx}/ticket">+Ticket</a></li>
                <li><a href="${ctx}/import/wrikerss">Import</a></li>
            </ul>

            <ul class="nav navbar-nav navbar-right">
                <li><a href="http://shinejava.uservoice.com/forums/226709-general" target="_blank"><i class="glyphicon glyphicon-question-sign"></i>  Feedback</a></li>
                <li><a href="${ctx}/user/profile"> <i class="glyphicon glyphicon-user"></i> My Profile</a></li>
                <li><a href="${ctx}/user/signin"><i class="glyphicon glyphicon-log-out"></i> Sign out</a></li>
            </ul>
        </div><!-- /.navbar-collapse -->
    </nav>
</c:set>
