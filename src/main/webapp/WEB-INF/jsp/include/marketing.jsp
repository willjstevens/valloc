
<%--<meta name="description" content="${tagline}" />--%>

<%--<!-- Schema.org markup for Google+ -->--%>
<meta itemprop="name" content="${page.name}">
<meta itemprop="description" content="${tagline}">
<meta itemprop="image" content="${siteDomainPrefix}/r/i/logo-v.png">

<%--<!-- Twitter Card data -->--%>
<meta name="twitter:card" content="product">
<%--<meta name="twitter:site" content="@publisher_handle">--%>
<meta name="twitter:title" content="${page.name}">
<meta name="twitter:description" content="${tagline}">
<%--<meta name="twitter:creator" content="@author_handle">--%>
<meta name="twitter:image" content="${siteDomainPrefix}/r/i/logo-v.png">

<%--<!-- Open Graph data -->--%>
<meta property="og:title" content="${page.name}" />
<%--<meta property="og:type" content="article" />--%>
<meta property="og:url" content="${page.path}" />
<meta property="og:image" content="${siteDomainPrefix}/r/i/logo-v.png" />
<meta property="og:description" content="${tagline}" />
<%--<meta property="og:site_name" content="Site Name, i.e. Moz" />--%>
<%--<meta property="og:price:amount" content="15.00" />--%>
<%--<meta property="og:price:currency" content="USD" />--%>



<%--<!-- Facebook Pixel Code -->--%>
<script>
    !function(f,b,e,v,n,t,s)
    {if(f.fbq)return;n=f.fbq=function(){n.callMethod?
        n.callMethod.apply(n,arguments):n.queue.push(arguments)};
        if(!f._fbq)f._fbq=n;n.push=n;n.loaded=!0;n.version='2.0';
        n.queue=[];t=b.createElement(e);t.async=!0;
        t.src=v;s=b.getElementsByTagName(e)[0];
        s.parentNode.insertBefore(t,s)}(window, document,'script',
        'https://connect.facebook.net/en_US/fbevents.js');
    fbq('init', '684678825228562');
    fbq('track', 'PageView');
</script>
<noscript><img height="1" width="1" style="display:none"
               src="https://www.facebook.com/tr?id=684678825228562&ev=PageView&noscript=1"
/></noscript>
<%--<!-- End Facebook Pixel Code -->--%>


<%--Google Analytics Tracking--%>
<%--<!-- Global site tag (gtag.js) - Google Analytics -->--%>
<script async src="https://www.googletagmanager.com/gtag/js?id=UA-124597680-1"></script>
<script>
    window.dataLayer = window.dataLayer || [];
    function gtag(){dataLayer.push(arguments);}
    gtag('js', new Date());

    gtag('config', 'UA-124597680-1');
</script>

